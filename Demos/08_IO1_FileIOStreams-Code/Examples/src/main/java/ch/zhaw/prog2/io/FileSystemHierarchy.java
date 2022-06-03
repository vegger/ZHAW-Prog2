package ch.zhaw.prog2.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class FileSystemHierarchy {
    private static final boolean USE_RELATIVE_PATHS = true;
    private static int startDepth = 0;

    public static void main(String[] args) {
        String startPath = (args.length >= 1)? args[0] : System.getProperty("user.dir");  // current working dir
        // System.getProperty("user.dir");   // --> current working directory (from where the jvm was started, Project root in IDE)
        // System.getProperty("user.home");  // --> users home directory ($HOME resp. %HOME%)
        System.out.printf("Start Directory: %s%n", startPath);   // --> /Users/..../PROG2-IO-Code
        startDepth = Path.of(startPath).getNameCount();
        try {
            // set up the tree (start nodes)
            File demoDir = new File(startPath, "demo"); // create reference to the ./demo directory
            File prog2Dir = new File(demoDir, "PROG2"); // represents the PROG2 directory. Automatically uses separator

            // traversing upwards --> parent
            File prog2Parent = prog2Dir.getParentFile();   // get parent of prog2 (should be similar as demoDir)
            printInfo("PROG2-Parent: %s%n", prog2Parent, "");

            // traversing downwards --> children
            File[] prog2Children = prog2Dir.listFiles();      // get all children of PROG2
            System.out.println("PROG2-Children:");
            for (File prog2Child : prog2Children) {
                System.out.printf(" - %-10s %c%c%c%c%c modified %tF %<tT size %d bytes%n",
                    prog2Child.getName(),
                    prog2Child.isDirectory() ? 'd':'-',
                    prog2Child.canRead()     ? 'r':'-',
                    prog2Child.canWrite()    ? 'w':'-',
                    prog2Child.canExecute()  ? 'x':'-',
                    prog2Child.isHidden()    ? 'h':'-',
                    millisToLocalDateTime(prog2Child.lastModified()),
                    prog2Child.length()
                );
            }
            System.out.println();

            // create a new file
            File readmeFile = new File(prog2Dir, "README.md");  // create abstract file (does not yet exist)
            printInfo("File %s exists: %s%n",  readmeFile, readmeFile.exists());
            boolean created = readmeFile.createNewFile();    // create file, returns true if successful
            printInfo("File %s created: %s%n", readmeFile, created);
            printInfo("File %s exists: %s%n",  readmeFile, readmeFile.exists());

            // create a new directory / directories
            File srcDir = new File(prog2Dir, "src"); // create abstract file for directory (does not yet exist)
            printInfo("Directory %s exists: %s%n",  srcDir, srcDir.exists());
            boolean createdDir = srcDir.mkdir();       // create directory, returns true if successful
            printInfo("Directory %s created: %s%n", srcDir, createdDir);
            printInfo("Directory %s exists: %s%n",  srcDir, srcDir.exists());

            File appSrcDir = new File(srcDir, "java/ch/zhaw/prog2/app");
            boolean createdDirs = appSrcDir.mkdirs();
            printInfo("Created %s: %s%n", appSrcDir, createdDirs);

            // deleting a file or directory
            printInfo("File %s exists: %s%n",  readmeFile, readmeFile.exists());
            boolean deleted = readmeFile.delete();           // delete file/directory, returns true if successful
            printInfo("File %s deleted: %s%n", readmeFile, deleted);
            printInfo("File %s exists: %s%n",  readmeFile, readmeFile.exists());

        } catch (IOException e) {
            System.err.printf("Error accessing file: %s%n", e.getMessage());
        }

    }

    private static void printInfo(String format, File file, boolean arg) {
        printInfo(format,file, String.valueOf(arg));
    }

    private static void printInfo(String format, File file, String arg) {
        Path filePath = file.toPath();
        String filePathString = (USE_RELATIVE_PATHS ? "./" : "/") +
            filePath.subpath(USE_RELATIVE_PATHS? startDepth : 0, filePath.getNameCount()).toString();
        System.out.printf(format, filePathString, arg);
    }

    private static LocalDateTime millisToLocalDateTime(long millis) {
        return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }


}
