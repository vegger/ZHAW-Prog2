package ch.zhaw.prog2.io;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;


public class LoadResources {

    public static void main(String[] args) throws Exception {
        new LoadResources().loadResources();
    }

    public void loadResources() throws IOException, URISyntaxException {
        // getting the classloader
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        ClassLoader classLoader = this.getClass().getClassLoader();

        printPath("URL-Form", classLoader.getResource("config.properties").toString());

        // resource using classloader
        printPath("URL-Form", classLoader.getResource("config.properties").toString());
        printPath("Resource root", classLoader.getResource("").getPath());
        printPath("config.properties", classLoader.getResource("config.properties").getPath());
        printPath("Demo.txt", classLoader.getResource("ch/zhaw/prog2/io/Demo.txt").getPath());

        // resource using class-Instance
        Class<LoadResources> staticClass = LoadResources.class;
        Class<? extends LoadResources> instanceClass = this.getClass();
        printPath(instanceClass.getSimpleName(), instanceClass.getResource("").getPath());
        printPath("Demo.txt", instanceClass.getResource("Demo.txt").getPath());
        printPath("config.properties", instanceClass.getResource("/config.properties").getPath());

        // Create File / Path objects
        File demoFile = new File(this.getClass().getResource("Demo.txt").getPath());
        Path demoPath = Path.of(this.getClass().getResource("Demo.txt").getPath());

        // Open InputStream from resource
        InputStream demoStream = this.getClass().getResourceAsStream("Demo.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(demoStream, StandardCharsets.UTF_8));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

    }

    private void printPath(String prefix, String path) {
        System.out.printf("%s path: %s%n", prefix, path);
    }

}
