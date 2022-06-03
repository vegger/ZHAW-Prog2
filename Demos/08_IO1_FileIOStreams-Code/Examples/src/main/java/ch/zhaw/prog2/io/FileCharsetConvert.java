package ch.zhaw.prog2.io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileCharsetConvert {
    public static void main(String[] args) {
        String sourcePathName = (args.length >= 1)? args[0] : "./demo/Demo.txt";
        String targetPathName = (args.length >= 1)? args[0] : "./demo/Demo-windows-1252.txt";
        try (FileReader reader = new FileReader(sourcePathName, StandardCharsets.UTF_8);
             FileWriter writer = new FileWriter(targetPathName, Charset.forName("windows-1252"))) {
            // read character-by-charachter
            int charValue;   // variable to hold a value
            do {
                charValue = reader.read();    // read next char from file
                if (charValue !=-1) {          // if not end of file
                    writer.write(charValue);  // write char value to console
                }
            } while(charValue != -1);  // reached end of file ?
        } // fin & fout will be automatically closed here ( -> try-with-resource)
        catch(FileNotFoundException e) { System.out.println("File not found: " + e.getMessage()); }
        catch(IOException e) { System.out.println("IO Error: " + e.getMessage()); }
    }
}
