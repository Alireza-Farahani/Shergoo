import org.hamcrest.core.StringContains;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.apache.commons.io.FileUtils.readFileToString;
import static org.junit.Assert.*;

public class ShergooTest {

    @Test(expected = FileNotFoundException.class)
    public void testNotExistingFile() throws Exception {
        Shergoo shergoo = new Shergoo(new File("beyond the horizon of the place we lived.txt"));

        shergoo.generatePoems();
    }

    @Test(expected = IllegalArgumentException.class) // TODO: parametrized
    public void testInvalidFormat() throws Exception {
        Shergoo shergoo = new Shergoo(TestUtils.getFileFromResources("BadMeterWord.txt"));

        shergoo.generatePoems();
    }

    @Test
    public void testSimplestVersion() throws Exception {
        Shergoo shergoo = new Shergoo(TestUtils.getFileFromResources("SimpleValidFile.txt"));

        List<String> poems = shergoo.generatePoems();
        assertEquals(1, poems.size());
        assertEquals("درک", poems.get(0));
    }

    @Test
    public void testMultipleVerses() throws Exception {
        Shergoo shergoo = new Shergoo(TestUtils.getFileFromResources("MultipleVerses.txt"));

        List<String> poems = shergoo.generatePoems();
        assertEquals(3, poems.size());

        String solution = readFileToString(
                TestUtils.getFileFromResources("MultipleVersesSolution.txt"), StandardCharsets.UTF_8);

        poems.forEach(poem -> assertThat(solution, StringContains.containsString(poem)));
    }
}