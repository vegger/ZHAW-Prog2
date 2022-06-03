package ch.zhaw.prog2.io.picturedb;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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

    private final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    private File file;

    /**
     * Creates the FilePictureDatasource object with the given file path as datafile.
     * Creates the file if it does not exist.
     * Also creates an empty temp file for write operations.
     *
     * @param filepath of the file to use as database file.
     * @throws IOException if accessing or creating the file fails
     */
    public FilePictureDatasource(String filepath) throws IOException {
        file = new File(filepath);
        //file.createNewFile(); TODO check if necessary
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(Picture picture) {
        if(picture.getId() == -1) {
            picture.setId(getNextId());
        }
        //TODO check if ID already exists
        try (BufferedWriter fw = new BufferedWriter(new FileWriter(file, true))) {
            writePictureToCSV(picture, fw);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Picture picture) throws RecordNotFoundException {
        Objects.requireNonNull(picture);
        Picture pictureBefore;

        try {
            pictureBefore = findById(picture.getId());
            delete(pictureBefore);
        } catch (NullPointerException e) {
            throw new RecordNotFoundException();
        }
        insert(picture);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Picture picture) throws RecordNotFoundException {
        Objects.requireNonNull(picture);
        boolean fileDeleted = false;

        Collection<Picture> pictures = findAll();
        Collection<Picture> updatedPictures = new ArrayList<>();

        pictures.forEach(pic -> {
            if (pic.getId() != picture.getId()) {
                updatedPictures.add(pic);
            }
        });

        fileDeleted = pictures.size() > updatedPictures.size();

        try {
            new PrintWriter(file.getPath()).close(); //Delete content of file
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //write updated file
        try(FileWriter fw = new FileWriter(file, true); BufferedWriter bw = new BufferedWriter(fw)) {
            String csv = "";
            for(Picture pic : updatedPictures) {
                csv += pic.toCSV() + "\n";
            }
            bw.write(csv);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!fileDeleted) throw new RecordNotFoundException();
    }

    private void writePictureToCSV(Picture tempPic, BufferedWriter bufferedWriter) {
        try {
            bufferedWriter.append(tempPic.toCSV());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Optional<Picture> CSVLineToPicture(String csvline) {
        Optional<Picture> picture = Optional.empty();
        String[] line = csvline.split(DELIMITER);
        try {
            picture = Optional.of(new Picture(Long.parseLong(line[0]), new URL(line[5]), Picture.df.parse(line[1]), line[4], Float.parseFloat(line[2]), Float.parseFloat(line[3])));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return picture;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count() {
        return findAll().size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Picture findById(long id) {
        Picture picture = null;
        Optional<String> csv = Optional.empty();
        Collection<Picture> allPicture = findAll();

        for(Picture pic : allPicture) {
            if(pic.getId() == id) return pic;
        }

        if (!csv.isEmpty()) {
            Optional<Picture> pic = CSVLineToPicture(csv.get());
            if(!pic.isEmpty()) return pic.get();
        }
        return picture;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Picture> findAll() {
        Collection<Picture> pictures = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            pictures = picturesFromReader(bufferedReader);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pictures;
    }

    private Collection<Picture> picturesFromReader(BufferedReader bufferedReader) {
        Collection<Picture> pictures = new ArrayList<>();

        bufferedReader.lines().forEach(line -> {
            String[] pictureDetails = line.split(DELIMITER);
            Optional<Picture> picture = CSVLineToPicture(line);
            picture.ifPresent(pic -> {
                pic.setId(Long.valueOf(pictureDetails[0]));
                pictures.add(pic);
            });

        });

        return pictures;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Picture> findByPosition(float longitude, float latitude, float deviation) {
        Collection<Picture> allPictures = findAll();
        Collection<Picture> pictures = new ArrayList<>();

        for(Picture picture : allPictures) {
            if(picture.getLatitude() >= latitude-deviation &&
                picture.getLatitude() <= latitude+deviation &&
                picture.getLongitude() >= longitude-deviation &&
                picture.getLongitude() <= longitude+deviation) {
                pictures.add(picture);
            }
        }
        return pictures;
    }

    private long getNextId() {
        Collection<Picture> pictures = findAll();
        //TODO theoretisch sollte mit findAll nicht die ganze Datenbank eingelesen werden nur um ID's zu verwenden
        Optional<Picture> latestPicture = pictures.stream().max(Comparator.comparing(Picture::getId));
        if (latestPicture.isEmpty()) {
            return 1;
        }
        return latestPicture.get().getId() + 1;
    }
}
