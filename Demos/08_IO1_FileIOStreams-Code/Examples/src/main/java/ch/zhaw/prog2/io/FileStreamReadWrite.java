package ch.zhaw.prog2.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileStreamReadWrite {
    public static void main(String[] args) {
        String sourcePathName = (args.length >= 1)? args[0] : "./demo/Demo.txt";
        String targetPathName = (args.length >= 2)? args[1] : "./demo/DemoCopy.txt";
        try (FileInputStream fin = new FileInputStream(sourcePathName);
             FileOutputStream fout = new FileOutputStream(targetPathName)) {
            // Copy (READ, WRITE) File *byte by byte*
            int byteValue;   // variable to hold a value
            do {
                byteValue = fin.read();     // read next byte from fin
                if (byteValue !=-1) {        // if not end of file
                    fout.write(byteValue);  // write byte value to fout
                }
            } while (byteValue != -1);  // reached end of file ?
        } // fin & fout will be automatically closed here ( -> try-with-resource)
        catch(FileNotFoundException e) { System.out.println("File not found: " + e.getMessage()); }
        catch(IOException e) { System.out.println("IO Error: " + e.getMessage()); }
    }
}
