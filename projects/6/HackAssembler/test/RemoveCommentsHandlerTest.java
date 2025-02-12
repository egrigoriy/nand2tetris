import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RemoveCommentsHandlerTest {
    @Test
    public void testRemoveFullLineComments() {
        List<String> program = new ArrayList<>();
        program.add("M=1");
        program.add("// Computes R1=1 + ... + R0");
        program.add("// i = 1");
        program.add("@i");
        program.add("M=1");
        program.add("// sum = 0");

        RemoveCommentsHandler handler = new RemoveCommentsHandler();
        List<String> result = handler.handle(program);

        List<String> expected = new ArrayList<>();
        expected.add("M=1");
        expected.add("@i");
        expected.add("M=1");
        assertEquals(expected, result);
    }

    @Test
    public void testRemoveEndLineComments() {
        List<String> program = new ArrayList<>();
        program.add("M=1     // assign 1 to memory");
        program.add("// Computes R1=1 + ... + R0");
        program.add("// i = 1");
        program.add("@i//index");
        program.add("M=1 // this is inline comment");
        program.add("// sum = 0");

        RemoveCommentsHandler handler = new RemoveCommentsHandler();
        List<String> result = handler.handle(program);

        List<String> expected = new ArrayList<>();
        expected.add("M=1");
        expected.add("@i");
        expected.add("M=1");

        assertEquals(expected, result);
    }

    @Test
    public void removeMixedComments() {
        List<String> program = new ArrayList<>();
        program.add("M=1");
        program.add("// Computes R1=1 + ... + R0");
        program.add("// i = 1");
        program.add("@i");
        program.add("M=1");
        program.add("// sum = 0");
        program.add("M=D     // assign 1 to memory");
        program.add("// Computes R1=1 + ... + R0");
        program.add("// i = 1");
        program.add("@i//index");
        program.add("D=M // this is inline comment");
        program.add("// sum = 0");

        RemoveCommentsHandler handler = new RemoveCommentsHandler();
        List<String> result = handler.handle(program);

        List<String> expected = new ArrayList<>();
        expected.add("M=1");
        expected.add("@i");
        expected.add("M=1");
        expected.add("M=D");
        expected.add("@i");
        expected.add("D=M");

        assertEquals(expected, result);
    }
}
