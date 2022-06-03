package ch.zhaw.prog2.io.picturedb;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Implements the PictureDatasource Interface storing the data in
 * Character Separated Values (CSV) format, where each line consists of a record
 * whose fields are separated by the DELIMITER value ";"
 * See example file: db/picture-data.csv
 */
public class FilePictureDatasource implements PictureDatasource {
    // Charset to use for file encoding.
    protected static final Charset CHARSET = StandardCharsets.UTF_8;
    // Delimiter to separate record fields on a line
    protected static final String DELIMITER = ";";
    // Date format to use for date specific record fields
    protected static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final Logger log = Logger.getLogger(FilePictureDatasource.class.getCanonicalName());

    private final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private final Path dataFile;    // data file path
    private final Path tempFile;    // temporary file uses for write operations

    /**
     * Creates the FilePictureDatasource object with the given file path as datafile.
     * Creates the file if it does not exist.
     * Also creates an empty temp file for write operations.
     *
     * @param filepath of the file to use as database file.
     * @throws IOException if accessing or creating the file fails
     */
    public FilePictureDatasource(String filepath) throws IOException {
        try {
            this.dataFile = Path.of(filepath).normalize();
            if (!Files.exists(dataFile)) {
                Files.createFile(dataFile);
                log.log(Level.INFO,"Created data file {0}", dataFile);
            } else {
                log.log(Level.FINE, "Data file already exists: {0}", dataFile);
            }
            this.tempFile = Files.createTempFile("temp-", null);
        } catch (IOException e) {
            throw new IOException("Error setting up data file", e);
        }
    }


    /**
     * {@inheritDoc}
     *
     * In this implementation we walk line by line through the data file, find the highest available id (maxId)
     * and write each line to the temp file (next file version).
     * At the end the next id is calculated (maxId + 1) and the new record added to the temp file.
     * If successful the current data-file us replaced by the temp-file (next file version),
     * otherwise the temp-file is deleted.
     */
    @Override
    public void insert(Picture picture) {
        Objects.requireNonNull(picture, "Picture may not be null.");
        boolean changed = false;
        try (BufferedReader in = Files.newBufferedReader(dataFile, CHARSET);
             PrintWriter out = new PrintWriter(Files.newBufferedWriter(tempFile, CHARSET)))
        {
            long maxId = 0;
            String line;
            // copy entries to tempFile and find maxId
            while ((line = in.readLine()) != null && !line.isBlank()) {
                long id = parseId(line);
                maxId = Math.max(id, maxId);
                out.println(line);
            }
            // set new Id to picture and add new picture record to the end of the file
            picture.setId(maxId + 1);
            out.println(createPictureRecord(picture));
            changed = true;
            log.info(() -> "insert(%d) successful".formatted(picture.getId()));
        } catch (IOException e) {
            Supplier<String> errorMessage = () -> "File operation failed: insert(%d): %s".formatted(picture.getId(), picture);
            log.log(Level.SEVERE, e, errorMessage);
            throw new RuntimeException(errorMessage.get(), e);
        } finally {
            // if the insert was successful move tempFile to dataFile, otherwise delete tempFile
            persistOrDeleteTempFile(changed);
        }
    }

    /**
     * {@inheritDoc}
     *
     * In this implementation we walk line by line through the data file.
     * If the line contains the id, the new record is written to the temp-file, otherwise the existing line is written.
     * If at the end no record is found (!changed), a RecordNotFoundException is thrown.
     * If successful (changed), the current data-file is replaced by the temp-file (next file version).
     */
    @Override
    public void update(Picture picture) throws RecordNotFoundException {
        Objects.requireNonNull(picture, "Picture may not be null.");
        boolean changed = false;
        try (BufferedReader in = Files.newBufferedReader(dataFile, CHARSET);
             PrintWriter out = new PrintWriter(Files.newBufferedWriter(tempFile, CHARSET)))
        {
            String prefix = "" + picture.getId() + DELIMITER;
            String line;
            // find line starting with record id and write new record line, otherwise simply copy old line
            // not required to parse the whole line here
            while ((line = in.readLine()) != null) {
                if (!changed && line.strip().startsWith(prefix)) {
                    out.println(createPictureRecord(picture));
                    changed = true;
                } else {
                    out.println(line);
                }
            }
            if (!changed) {
                throw new RecordNotFoundException("Updating picture failed, no rows affected.");
            }
            log.info(() -> "update(%d) successful".formatted(picture.getId()));
        } catch (IOException e) {
            Supplier<String> errorMessage = () -> "File operation failed: update(%d): %s".formatted(picture.getId(), picture);
            log.log(Level.SEVERE, e, errorMessage);
            throw new RuntimeException(errorMessage.get(), e);
        } finally {
            // if the insert was successful move tempFile to dataFile, otherwise delete tempFile
            persistOrDeleteTempFile(changed);
        }
    }

    /**
     * {@inheritDoc}
     *
     * In this implementation we walk line by line through the data file.
     * If the line contains the id, the line is skipped, otherwise the existing line is written to the temp-file.
     * If at the end no record is found (!changed), a RecordNotFoundException is thrown.
     * If successful (changed), the current data-file is replaced by the temp-file (next file version).
     */
    @Override
    public void delete(Picture picture) throws RecordNotFoundException {
        Objects.requireNonNull(picture, "Picture may not be null.");
        boolean found = false;
        try (BufferedReader in = Files.newBufferedReader(dataFile, CHARSET);
             PrintWriter out = new PrintWriter(Files.newBufferedWriter(tempFile, CHARSET)))
        {
            String prefix = "" + picture.getId() + DELIMITER;
            String line;
            // find line starting with record id and write new record line, otherwise simply copy old line
            // not required to parse the whole line here
            while ((line = in.readLine()) != null) {
                if (!found && line.startsWith(prefix)) {
                    // do write nothing
                    found = true;
                } else {
                    out.println(line);
                }
            }
            if (!found) {
                throw new RecordNotFoundException("Deleting picture failed, no rows affected.");
            }
            log.info(() -> "delete(%d) successful".formatted(picture.getId()));
        } catch (IOException e) {
            Supplier<String> errorMessage = () -> "File operation failed: delete(%d): %s".formatted(picture.getId(), picture);
            log.log(Level.SEVERE, e, errorMessage);
            throw new RuntimeException(errorMessage.get(), e);
        } finally {
            // if delete was successful move tempFile to dataFile, otherwise delete tempFile
            persistOrDeleteTempFile(found);
        }
    }

    /**
     * {@inheritDoc}
     *
     * In this implementation we walk line by line through the data file and count all non-blank lines.
     */
    @Override
    public long count() {
        long count = 0;
        try (BufferedReader in = Files.newBufferedReader(dataFile, CHARSET))
        {
            String line;
            while ((line = in.readLine()) != null && !line.isBlank()) {
                count++;
            }
            log.fine("count(): %d".formatted(count));
        } catch (IOException e) {
            String errorMessage = "File operation failed: count(): ";
            log.log(Level.SEVERE,errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
        return count;
    }

    /**
     * {@inheritDoc}
     *
     * In this implementation we walk line by line through the data file, parse all non-blank lines and return the
     * Pixture for the given id. null if the id is not found.
     */
    @Override
    public Picture findById(long id) {
        Picture result = null;
        try (BufferedReader in = Files.newBufferedReader(dataFile, CHARSET)) {
            String prefix = "" + id + DELIMITER;
            String line;
            while ((line = in.readLine()) != null && !line.isBlank()) {
                if (line.startsWith(prefix)) {
                    result = parsePictureRecord(line);
                    break;
                }
            }
            log.fine("findById("+id+"): " + result);
        } catch (IOException e) {
            Supplier<String> errorMessage = () -> "File operation failed: findById(%d)".formatted(id);
            log.log(Level.SEVERE, e, errorMessage);
            throw new RuntimeException(errorMessage.get(), e);
        } catch (RuntimeException e) {
            Supplier<String> errorMessage = () -> "File operation failed: findById(%d): %s".formatted(id, e.getMessage());
            log.log(Level.SEVERE, e.getCause(), errorMessage);
            throw new RuntimeException(errorMessage.get(), e.getCause());
        }
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * In this implementation we walk line by line through the data file, parse all non-blank lines and add the
     * Picture objects to the result list.
     */
    @Override
    public Collection<Picture> findAll() {
        List<Picture> pictures = new ArrayList<>();
        try (BufferedReader in = Files.newBufferedReader(dataFile, CHARSET)) {
            String line;
            while ((line = in.readLine()) != null && !line.isBlank()) {
                pictures.add(parsePictureRecord(line));
            }
            log.fine(() -> "Fetched %d entries".formatted(pictures.size()));
        } catch (IOException e) {
            String errorMessage = "File operation failed: findAll()";
            log.log(Level.SEVERE, errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        } catch (RuntimeException e) {
            Supplier<String> errorMessage = () -> "File operation failed: findAll(): %s".formatted(e.getMessage());
            log.log(Level.SEVERE, e.getCause(), errorMessage);
            throw new RuntimeException(errorMessage.get(), e.getCause());
        }
        return pictures;
    }

    /**
     * {@inheritDoc}
     *
     * In this implementation we walk line by line through the data file, parse all non-blank lines,
     * verify the position is within the bounds and add the Picture objects to the result list.
     */
    @Override
    public Collection<Picture> findByPosition(float longitude, float latitude, float deviation) {
        List<Picture> pictures = new ArrayList<>();
        try (BufferedReader in = Files.newBufferedReader(dataFile, CHARSET)) {
            String line;
            while ((line = in.readLine()) != null && !line.isBlank()) {
                Picture pict = parsePictureRecord(line);
                if (pict.getLongitude() >= longitude - deviation
                    && pict.getLongitude() <= longitude + deviation
                    && pict.getLatitude() >= latitude - deviation
                    && pict.getLatitude() <= latitude + deviation)
                {
                    pictures.add(pict);
                }
            }
            log.fine("findByPosition() found %d entries".formatted(pictures.size()));
        } catch (IOException e) {
            String errorMessage = "File operation failed: findByPosition()";
            log.log(Level.SEVERE, errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        } catch (RuntimeException e) {
            Supplier<String> errorMessage = () -> "File operation failed: findByPosition(): %s".formatted(e.getMessage());
            log.log(Level.SEVERE, e.getCause(), errorMessage);
            throw new RuntimeException(errorMessage.get(), e.getCause());
        }
        return pictures;
    }

    /**
     * Parses the id from a line in the database file.
     * Expects that the id is the first field on the line.
     *
     * @param line String to be parsed for the id in starting position
     * @return id found in the line
     */
    private long parseId(String line) {
        String[] fields = line.split(DELIMITER);
        return Long.parseLong(fields[0]);
    }

    /**
     * Parses a CSV-line entry into a Picture record object
     *
     * @param line String to be parsed for Picture data
     * @return Picture data object containing the values parsed from the line
     * @throws RuntimeException if the parsing of a field fails
     */
    private Picture parsePictureRecord(String line) {
        String[] fields = line.split(DELIMITER);
        try {
            return new Picture(
                Long.parseLong(fields[0]),      // id
                new URL(fields[5].strip()),     // URL
                dateFormat.parse(fields[1].strip()),    // date
                fields[4].strip(),              // name
                Float.parseFloat(fields[2]),    // longitude
                Float.parseFloat(fields[3]));   // latitude
        } catch (MalformedURLException | ParseException e) {
            Supplier<String> errorMessage = () -> "Failed parsing picture line: %s".formatted(line);
            log.log(Level.SEVERE,e, errorMessage);
            throw new RuntimeException(errorMessage.get(), e);
        }
    }

    /**
     * Creates a line CSV-line entry for a Picture record object
     * @param picture Picture data object to create CSV-entry for
     * @return String containing the CSV-line entry
     */
    private String createPictureRecord(Picture picture) {
        Objects.requireNonNull(picture,"Picture must not be null");
        return String.join(DELIMITER,
            String.valueOf(picture.getId()),
            dateFormat.format(picture.getDate()),
            String.valueOf(picture.getLongitude()),
            String.valueOf(picture.getLatitude()),
            picture.getTitle(),
            picture.getUrl().toExternalForm()
        );
    }

    /**
     * Cleanup of the temporary file after completing an operation.
     * If successful (temp-file complete and changed) the temp-file will replace the data file
     * if not successful (not changed) the temp-file is deleted.
     *
     * @param changed boolean signal to indicate a successful operation and to replace the data file.
     */
    private void persistOrDeleteTempFile(boolean changed) {
        if(Files.exists(tempFile)) {
            try {
                if (changed) {
                    Files.move(tempFile, dataFile, REPLACE_EXISTING);
                } else {
                    Files.delete(tempFile);
                }
            } catch (IOException e) {
                log.log(Level.WARNING, "Error cleaning up temp file", e);
            }
        }
    }
}
