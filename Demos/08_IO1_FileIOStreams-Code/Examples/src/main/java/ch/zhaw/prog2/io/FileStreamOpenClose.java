package ch.zhaw.prog2.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileStreamOpenClose {
    public static void main(String[] args) {
        String pathName = (args.length >= 1)? args[0] : "./demo/Demo.txt";
        FileInputStream fin = null;  //  file input stream
        try {
            fin = new FileInputStream(pathName);   // try to open file
            System.out.println("File found and opened");
            int oneByte = fin.read();
            // … other File IO operations come here
        }
        catch(FileNotFoundException e) { System.out.println("File not found: " + e.getMessage()); }
        catch(IOException e) { System.out.println("IO Error: " + e.getMessage()); }
        finally {
            try {  // closing file if required
                if (fin != null) fin.close();
            } catch(IOException e) {
                System.out.println("Error while trying to close File");
            }
        }
    }
}
