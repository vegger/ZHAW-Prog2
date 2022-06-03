package ch.zhaw.prog2.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZoneId;

class FilesDemo {

    public static void main(String[] args) throws IOException {
        String pathName = (args.length == 1)? args[0] : "./demo";

        Path path = Path.of(pathName);
        System.out.println("File Name: " + path);
        if (Files.isDirectory(path)) {
            for (Path filePath : Files.newDirectoryStream(path)) {
                printAttributes(filePath);
            }
        } else {
            printAttributes(path);
        }
    }
    static void printAttributes(Path path) throws IOException {
        System.out.printf(" - %-10s %c%c%c%c%c modified %tF %<tT size %d bytes%n",
            path.getFileName(),
            Files.isDirectory(path)  ? 'd':'-',
            Files.isReadable(path)   ? 'r':'-',
            Files.isWritable(path)   ? 'w':'-',
            Files.isExecutable(path) ? 'x':'-',
            Files.isHidden(path)     ? 'h':'-',
            Files.getLastModifiedTime(path).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
            Files.size(path)
        );
    }
}
