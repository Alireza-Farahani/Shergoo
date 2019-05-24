import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class Shergoo {
    private File file;
    private final static String NEWLINE_PATTERN = "\\R+";

    public Shergoo(File file) {
        this.file = file;
        /*if (!file.exists())
            throw new IllegalArgumentException(file.getAbsolutePath() + " doesn't exist");*/
    }

    public List<String> generatePoems() throws IOException {
        String input = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        String[] lines = input.split(NEWLINE_PATTERN); // ignores empty lines
        verifyInputFormat(lines);

        String poemMeter = lines[0];
        List<String> wordMetersLines = stream(lines)
                .skip(1)
//                .map(line -> Arrays.asList(line.split(" ")))
                .collect(Collectors.toList());
        Map<String, String> wordToMeter = createWordToMeterMap(wordMetersLines);
        return generatePoemsByMeter(poemMeter, wordToMeter);
    }

    private Map<String, String> createWordToMeterMap(List<String> wordMetersLines) {
        Map<String, String> result = new HashMap<>(wordMetersLines.size());
        wordMetersLines.forEach(line -> {
            int firstSpace = line.trim().indexOf(" ");
            String word = line.substring(0, firstSpace);
            String meter = line.substring(firstSpace).trim();
            result.put(word, meter);
        });
        return result;
    }

    private List<String> generatePoemsByMeter(String poemMeter, Map<String, String> wordsToMeters) {
        if (poemMeter.trim().isEmpty())
            return Collections.singletonList("");

        List<String> result = new ArrayList<>();
        wordsToMeters.forEach((word, meter) -> {
            if (poemMeter.startsWith(meter)) {
                List<String> tempPoems = generatePoemsByMeter(
                        poemMeter.replaceFirst(meter, "").trim(),
                        wordsToMeters);
                tempPoems = tempPoems.stream().map(poem -> (word + " " + poem).trim()).collect(Collectors.toList());
                result.addAll(tempPoems);
            }
        });
        return result;
    }

    private void verifyInputFormat(String[] lines) {
        if (lines.length == 0)
            throw new IllegalArgumentException("Empty input");

        for (String meter : lines[0].split(" "))
            if (isWrongPoemMeter(meter)) {
                throw new IllegalArgumentException("Bad poem meter format -> " + meter);
            }

        stream(lines).skip(1).forEach(line ->
                stream(line.split(" ")).skip(1).forEach(meter -> {
                    if (isWrongPoemMeter(meter))
                        throw new IllegalArgumentException("Bad poem meter format -> " + meter);
                }));
    }

    private boolean isWrongPoemMeter(String meter) {
        return !meter.equals("تن") && !meter.equals("ت");
    }
}
