package concepts.browsers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtil {

    public static File getTempFile(String fileName, String fileExtension){
        File logFileLocation;
        try{
            logFileLocation = File.createTempFile(fileName, fileExtension);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logFileLocation.deleteOnExit();
        return logFileLocation;
    }

    public static File getTempDirectory(String dirName) {
        File tempDirectoryLocation;
        try {
            tempDirectoryLocation = Files.createTempDirectory(dirName).toFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        tempDirectoryLocation.deleteOnExit();

        return tempDirectoryLocation;
    }

}
