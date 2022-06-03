package ch.zhaw.prog2.io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

public class FileReaderDemo {
    public static void main(String[] args) {
        String sourcePathName = (args.length >= 1)? args[0] : "./demo/Demo.txt";
        Charset defaultCharset = Charset.defaultCharset();
        System.out.println("Default Charset: " + defaultCharset);
        try (FileReader reader = new FileReader(sourcePathName, defaultCharset)) {
            // read character-by-charachter
            int charValue;   // variable to hold a value
            do {
                charValue = reader.read();  // read next byte from file
                if (charValue !=-1) {         // if not end of file
                    System.out.print((char) charValue);  // write char value to console
                }
            } while(charValue != -1);  // reached end of file ?
        } // fin & fout will be automatically closed here ( -> try-with-resource)
        catch(FileNotFoundException e) { System.out.println("File not found: " + e.getMessage()); }
        catch(IOException e) { System.out.println("IO Error: " + e.getMessage()); }
    }
}
