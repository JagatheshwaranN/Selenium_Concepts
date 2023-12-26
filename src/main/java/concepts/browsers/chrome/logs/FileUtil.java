package concepts.browsers.chrome.logs;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    public static File getTempFile(String prefix, String suffix){
        File logFileLocation = null;
        try{
            logFileLocation = File.createTempFile(prefix, suffix);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logFileLocation.deleteOnExit();
        return logFileLocation;
    }
}
