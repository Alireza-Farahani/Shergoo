import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestUtils {
    public static final Pattern NEW_LINE_PATTERN = Pattern.compile("\r\n|\r|\n");

    public static File getFileFromResources(String fileName) throws IOException {
        /*return IOUtils.toString(
                TestUtils.class.getResourceAsStream(fileName),
                "UTF-8"
        );*/
        ClassLoader classLoader = TestUtils.class.getClassLoader();
        URL url = classLoader.getResource(fileName);
        if (url == null) {
            throw new IOException("resource url was null");
        }
        return new File(url.getFile());
    }

    public static int countStringLines(String input) {
        if (input == null) {
            return -1;
        }

        Matcher matcher = NEW_LINE_PATTERN.matcher(input);
        int lines = 1;
        while (matcher.find()) {
            lines++;
        }
        return lines;
    }
}
