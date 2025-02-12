import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RemoveEmptyLinesHandlerTest {
    @Test
    public void testHandle() {
        List<String> program = new ArrayList<>();
        program.add("M=1");
        program.add("     ");
        program.add("");
        program.add("// Computes R1=1 + ... + R0");

        RemoveEmptyLinesHandler handler = new RemoveEmptyLinesHandler();
        List<String> result = handler.handle(program);

        List<String> expected = new ArrayList<>();
        expected.add("M=1");
        expected.add("// Computes R1=1 + ... + R0");

        assertEquals(expected, result);
    }
}
