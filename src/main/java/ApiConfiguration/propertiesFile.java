package ApiConfiguration;
import utility.restExtension;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class propertiesFile {
    static Properties properties = new Properties();
    public  void getApiBaseUrl() {
        try {
            InputStream inputStream = Files.newInputStream(Paths.get(System.getProperty("user.dir") + "/src/main/java/ApiConfiguration/cofig.properties"));
            properties.load(inputStream);
           // restExtension.url = properties.getProperty("apiBaseURL");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

}
