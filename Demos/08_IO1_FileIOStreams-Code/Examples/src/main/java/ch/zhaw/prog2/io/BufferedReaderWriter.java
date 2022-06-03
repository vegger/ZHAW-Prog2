package ch.zhaw.prog2.io;

import java.io.*;

public class BufferedReaderWriter {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
            System.out.println("Enter lines of text.");
            System.out.println("Enter 'stop' to quit.");
            String line;
            do {
                line = br.readLine();
                bw.write(line);
                bw.newLine();
            } while(!line.equals("stop"));
        }
    }
}
