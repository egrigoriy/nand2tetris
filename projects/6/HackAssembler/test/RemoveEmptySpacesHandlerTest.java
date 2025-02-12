import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RemoveEmptySpacesHandlerTest {
    @Test
    public void testHandle() {
        List<String> program = new ArrayList<>();
        program.add("@sum");
        program.add("M = 0");
        program.add(" ( LOOP ) ");
        program.add("@i");
        program.add("D = M");
        program.add("@R0");
        program.add("D = D - M");
        program.add("@STOP");
        program.add("D ; JGT");
        program.add("@sum");
        program.add("D = D + M ; JMP ");

        RemoveEmptySpacesHandler handler = new RemoveEmptySpacesHandler();
        List<String> result = handler.handle(program);

        List<String> expected = new ArrayList<>();
        expected.add("@sum");
        expected.add("M=0");
        expected.add("(LOOP)");
        expected.add("@i");
        expected.add("D=M");
        expected.add("@R0");
        expected.add("D=D-M");
        expected.add("@STOP");
        expected.add("D;JGT");
        expected.add("@sum");
        expected.add("D=D+M;JMP");

        assertEquals(expected, result);
    }
}
