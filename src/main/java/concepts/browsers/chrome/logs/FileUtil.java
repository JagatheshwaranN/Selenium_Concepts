package concepts.browsers.chrome.logs;

import java.io.File;
import java.io.IOException;

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
}
