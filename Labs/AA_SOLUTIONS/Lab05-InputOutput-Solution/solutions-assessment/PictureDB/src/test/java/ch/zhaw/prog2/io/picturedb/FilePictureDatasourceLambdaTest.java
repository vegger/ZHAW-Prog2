package ch.zhaw.prog2.io.picturedb;

import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FilePictureDatasourceLambdaTest extends FilePictureDatasourceTest {

    static {
        // set level for class to test to WARNING (minimize log messages)
        Logger.getLogger(FilePictureLambdaDatasource.class.getCanonicalName()).setLevel(Level.WARNING);
    }

    FilePictureDatasourceLambdaTest() {
        super();
    }

    @BeforeEach
    void setUp() throws IOException {
        Files.copy(dbTemplatePath, dbPath);
        datasource = new FilePictureLambdaDatasource(dbPath.toString());
    }

}
