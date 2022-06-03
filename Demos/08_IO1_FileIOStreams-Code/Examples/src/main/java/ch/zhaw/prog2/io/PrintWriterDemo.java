package ch.zhaw.prog2.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class PrintWriterDemo {
    public static void main(String[] args) throws IOException {
        String targetPathName = (args.length >= 1)? args[1] : "./demo/PrintWriter.txt";
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(targetPathName), true, StandardCharsets.UTF_8)) {
            pw.printf("Number of Arguments: %d ", args.length);
            int filenameLength = targetPathName.length();
            pw.print("Filename length: ");
            pw.println(filenameLength);
            pw.print("PI: ");
            pw.print(Math.PI); // convert to string an println
        }
    }
}
