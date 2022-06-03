package ch.zhaw.prog2.io;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileInputStreamPositioning {
    public static void main(String[] args) throws IOException {
        try (InputStream file = new FileInputStream("./demo/Demo.txt");
             PrintWriter console = new PrintWriter(System.out, true, StandardCharsets.ISO_8859_1))
        {
            int size= file.available();
            int n = size/40;
            console.println("* First " + n +" bytes of the file one read() at a time");
            for (int i=0; i < n; i++) {
                console.print((char) file.read());
            }
            console.println("\n* Still Available: " + (size = file.available()));
            long skipped = file.skip(size/2);   // forward file pointer half of remaining content
            console.println("* Skipped " + skipped + " Bytes still available: " + file.available());
            n = size/5;
            for (int i=0; i < n; i++) {
                console.print((char) file.read());
            }
            console.println("\n* Still Available: " + file.available());
        }
    }

}
