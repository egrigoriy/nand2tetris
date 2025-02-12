import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class VariablesHandlerTest {
    @Test
    public void testHandle() {
        List<String> program = new ArrayList<>();
        program.add("M=1");
        program.add("@i");
        program.add("M=1");
        program.add("@sum");
        program.add("M=0");
        program.add("@1234");
        program.add("M=D");
        program.add("@i");
        program.add("D=D+M");
        program.add("@sum");
        program.add("M=D");

        VariablesHandler handler = new VariablesHandler();
        List<String> result = handler.handle(program);

        List<String> expected = new ArrayList<>();
        expected.add("M=1");
        expected.add("@16");
        expected.add("M=1");
        expected.add("@17");
        expected.add("M=0");
        expected.add("@1234");
        expected.add("M=D");
        expected.add("@16");
        expected.add("D=D+M");
        expected.add("@17");
        expected.add("M=D");

        assertEquals(expected, result);
    }
}
