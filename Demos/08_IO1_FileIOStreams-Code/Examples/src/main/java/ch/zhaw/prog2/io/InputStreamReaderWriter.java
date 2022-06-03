package ch.zhaw.prog2.io;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class InputStreamReaderWriter {
    public static void main(String[] args) throws IOException {
        String sourcePathName = (args.length >= 1)? args[0] : "./demo/Demo.txt";
        String targetPathName = (args.length >= 1)? args[0] : "./demo/Demo-iso-8859-1.txt";
        try (Reader reader = new InputStreamReader(new FileInputStream(sourcePathName), StandardCharsets.UTF_8);
             Writer writer = new OutputStreamWriter(new FileOutputStream(targetPathName), StandardCharsets.ISO_8859_1)) {
            // read character-by-charachter
            int charValue;   // variable to hold a value
            do {
                charValue = reader.read();    // read next char from file
                if(charValue !=-1) {           // if not end of file
                    writer.write(charValue);  // write char value to console
                }
            } while(charValue != -1);  // reached end of file ?
        } // fin & fout will be automatically closed here ( -> try-with-resource)
        catch(FileNotFoundException e) { System.out.println("File not found: " + e.getMessage()); }
        catch(IOException e) { System.out.println("IO Error: " + e.getMessage()); }
    }
}
