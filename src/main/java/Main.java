import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String filePath;
        if (args.length > 0)
            filePath = args[0];
        else filePath = "input.txt";

        File wordsAndMeters = new File(filePath);

        try {
            final List<String> poems;
            Shergoo shergoo = new Shergoo(wordsAndMeters);
            poems = shergoo.generatePoems();
            System.out.println(String.join("\n", poems));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
