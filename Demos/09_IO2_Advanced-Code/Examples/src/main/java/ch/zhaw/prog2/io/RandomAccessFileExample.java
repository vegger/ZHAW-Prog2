package ch.zhaw.prog2.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.RandomAccessFile;

class RandomAccessFileExample{
    public static void main(String[] args) throws Exception {
        // create file and write some data into it
        File f = new File("./demo/randomaccessfile.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f, true))) {
            for (int i = 2; i <= 10; i += 2) {
                bw.write(Integer.toString(i));
                bw.newLine();
            }
        }

        try (RandomAccessFile randomFile = new RandomAccessFile(f,"rw")) { // read-write mode
            randomFile.seek(f.length());                     // set file pointer to end of file (EOF)
            for(int i=1; i<=5; i++){
                randomFile.writeBytes(Integer.toString(i));  // append "1","2","3","4","5" at EOF
            }
        }

        try (RandomAccessFile randomFile = new RandomAccessFile(f, "r")) { // read-only mode
            int i = (int) randomFile.length();             // get length of file in bytes
            System.out.println("Length: " + i);
            randomFile.seek(i-3);                     // set file pointer to EOF-3 bytes
            for(int ct = 0; ct < 3; ct++){
                byte b = randomFile.readByte();            //read byte(s)
                System.out.println((char)b);
            }
        }
    }
}

