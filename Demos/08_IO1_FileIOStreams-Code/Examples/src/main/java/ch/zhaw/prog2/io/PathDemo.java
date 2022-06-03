package ch.zhaw.prog2.io;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;

class PathDemo {
    // absolut path examples
    private static final String PATH_WINDOWS_NATIVE = "C:\\Users\\demo\\.\\PROG2\\Demo.txt";  // does not work on unix
    private static final String PATH_WINDOWS_POSIX  = "C:/Users/demo/./PROG2/Demo.txt";       // works on windows, unix can parse it but not access
    private static final String PATH_UNIX     = "/Users/demo/./PROG2/Demo.txt";               // works on unix & windows (using current drive)?
    // relative path (when used it is appended to the current work directory System.getProperty("user.dir")
    private static final String PATH_RELATIVE = "demo/PROG2/./Demo.txt";


    public static void main(String[] args) throws IOException {
        String pathname = (args.length >= 1)? args[0] : PATH_UNIX;
        Path path = Path.of(pathname);   // Creates a path from from String vararg

        //Some examples using Path methods
        p("toString: " + path.toString());          // Unix: /Users/demo/./PROG2/Demo.txt | Windows: C:\Users\demo\.\PROG2\Demo.txt
        p("normalize: " + path.normalize());        // Unix: /Users/demo/PROG2/Demo.txt   | Windows: C:\Users\demo\PROG2\Demo.txt
        p("getFileName: " + path.getFileName());    // Unix: Demo.txt                     | Windows: Demo.txt
        p("getName(0): " + path.getName(0));        // Unix: Users                        | Windows: home
        p("getNameCount: " + path.getNameCount());  // Unix: 5                            | Windows: 4
        p("subpath(0,2): " + path.subpath(0,2));    // Unix: Users/demo                   | Windows: home\joe
        p("getParent: " + path.getParent());        // Unix: /Users/demo/./PROG2          | Windows: C:\home\joe\.
        p("getRoot: " + path.getRoot());            // Unix: /                            | Windows: C:\
        p("isAbsolute: " + path.isAbsolute());      // Unix: true                         | Windows: true
        Path otherPath = path.normalize().getParent().resolveSibling("srv/java/ch/zhaw");
        p("relative path from " + path + " to " + otherPath +
                  ": " + path.relativize(otherPath));  // Unix: ../../srv/java/ch/zhaw       | Windows:
        try {
            p("toAbsolutePath: " + path.toAbsolutePath()); // Unix: /Users/demo/./PROG2/Demo.txt | Windows:
            p("toRealPath: " + path.toRealPath(LinkOption.NOFOLLOW_LINKS));  // exception because file does not exist
        } catch (IOException e) {
            p("Failed: " + e);
        }

    }

    static void p(String s) {
        System.out.println(s);
    }
}

