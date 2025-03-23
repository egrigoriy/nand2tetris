import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ASMMacroTest {
    @Test
    public void testPushValue() {
        int value = 11;
        List<String> expectedAsList = List.of(
                "@" + value,
                "D=A",
                "@0",
                "A=M",
                "M=D",
                "@0",
                "M=M+1"
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.pushValue(value));
    }
}