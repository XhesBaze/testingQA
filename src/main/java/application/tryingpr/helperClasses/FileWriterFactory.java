package application.tryingpr.helperClasses;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public interface FileWriterFactory {
    static FileWriter create(File file) throws IOException {
        return new FileWriter(file);
    }

}
