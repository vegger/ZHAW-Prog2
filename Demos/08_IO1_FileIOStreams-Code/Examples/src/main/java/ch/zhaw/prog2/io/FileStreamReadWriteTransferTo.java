package ch.zhaw.prog2.io;

import java.io.*;

public class FileStreamReadWriteTransferTo {
    public static void main(String[] args) {
        String sourcePathName = (args.length >= 1)? args[0] : "./demo/Demo.txt";
        String targetPathName = (args.length >= 2)? args[1] : "./demo/DemoCopy.txt";
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(sourcePathName));
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(targetPathName))) {
            long totalBytesCopied = copyStream(in, out);
            //long totalBytesCopied = in.transferTo(out);
            System.out.println("Copied bytes: " + totalBytesCopied);
        } // in & out will be automatically closed here ( -> try-with-resource)
        catch(FileNotFoundException e) { System.out.println("File not found: " + e.getMessage()); }
        catch(IOException e) { System.out.println("IO Error: " + e.getMessage()); }
    }

    /**
     * Copies the content of the {@link InputStream} in to the {@link OutputStream} out
     * using a fixed size byte[] buffer.
     * This method is almost identical to the {@link InputStream#transferTo(OutputStream)} method
     * introduced with Java 9
     * @param in  InputStream to read all data from
     * @param out OutputStream to write all data to
     * @return number of copied bytes
     * @throws IOException if an error occurs when reading / writing
     */
    private static long copyStream(InputStream in, OutputStream out) throws IOException {
        long totalBytesCopied = 0;
        int bytesRead;
        byte[] byteBuffer = new byte[32];
        while ((bytesRead = in.read(byteBuffer)) >= 0) {  // read available bytes until end of file (-1)
            out.write(byteBuffer, 0, bytesRead);      // write the received bytes to the output stream
            totalBytesCopied += bytesRead;                // sum up the handled bytes
        }
        return totalBytesCopied;
    }
}

