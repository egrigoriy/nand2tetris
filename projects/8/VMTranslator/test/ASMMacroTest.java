import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ASMMacroTest {
    @Test
    public void testPushValue() {
        String value = "99";
        List<String> expectedAsList = List.of(
                "@" + value,
                "D=A",
                "@SP",
                "A=M",
                "M=D",
                "@SP",
                "M=M+1"
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.pushValue(value));
    }

    @Test
    public void testPushLocal() {
        String index = "11";
        String segment = "local";
        String segmentRegister = "1";
        List<String> expectedAsList = List.of(
                "@" + segmentRegister,
                "D=M",
                "@" + index,
                "A=D+A",
                "D=M",
                "@SP",
                "A=M",
                "M=D",
                "@SP",
                "M=M+1"
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.pushMemory(segment, index));
    }

    @Test
    public void testPushArgument() {
        String index = "22";
        String segment = "argument";
        String baseRegister = "2";
        List<String> expectedAsList = List.of(
                "@" + baseRegister,
                "D=M",
                "@" + index,
                "A=D+A",
                "D=M",
                "@SP",
                "A=M",
                "M=D",
                "@SP",
                "M=M+1"
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.pushMemory(segment, index));
    }

    @Test
    public void testPushThis() {
        String index = "33";
        String segment = "this";
        String segmentRegister = "3";
        List<String> expectedAsList = List.of(
                "@" + segmentRegister,
                "D=M",
                "@" + index,
                "A=D+A",
                "D=M",
                "@SP",
                "A=M",
                "M=D",
                "@SP",
                "M=M+1"
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.pushMemory(segment, index));
    }

    @Test
    public void testPushThat() {
        String index = "44";
        String segment = "that";
        String segmentRegister = "4";
        List<String> expectedAsList = List.of(
                "@" + segmentRegister,
                "D=M",
                "@" + index,
                "A=D+A",
                "D=M",
                "@SP",
                "A=M",
                "M=D",
                "@SP",
                "M=M+1"
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.pushMemory(segment, index));
    }

    @Test
    public void testPushTemp() {
        String index = "55";
        String segment = "temp";
        String segmentRegister = "5";
        List<String> expectedAsList = List.of(
                "@" + segmentRegister,
                "D=M",
                "@" + index,
                "A=D+A",
                "D=M",
                "@SP",
                "A=M",
                "M=D",
                "@SP",
                "M=M+1"
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.pushMemory(segment, index));
    }


    @Test
    public void testPopLocal() {
        String index = "11";
        String segment = "local";
        String segmentRegister = "1";
        List<String> expectedAsList = List.of(
                "@" + segmentRegister,
                "D=M",
                "@" + index,
                "D=D+A",
                "@R13",
                "M=D",
                "@SP",
                "M=M-1",
                "@SP",
                "A=M",
                "D=M",
                "@R13",
                "A=M",
                "M=D"
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.popMemory(segment, index));
    }
    @Test
    public void testPopArgument() {
        String index = "22";
        String segment = "argument";
        String segmentRegister = "2";
        List<String> expectedAsList = List.of(
                "@" + segmentRegister,
                "D=M",
                "@" + index,
                "D=D+A",
                "@R13",
                "M=D",
                "@SP",
                "M=M-1",
                "@SP",
                "A=M",
                "D=M",
                "@R13",
                "A=M",
                "M=D"
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.popMemory(segment, index));
    }
    @Test
    public void testPopThis() {
        String index = "33";
        String segment = "this";
        String segmentRegister = "3";
        List<String> expectedAsList = List.of(
                "@" + segmentRegister,
                "D=M",
                "@" + index,
                "D=D+A",
                "@R13",
                "M=D",
                "@SP",
                "M=M-1",
                "@SP",
                "A=M",
                "D=M",
                "@R13",
                "A=M",
                "M=D"
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.popMemory(segment, index));
    }
    @Test
    public void testPopThat() {
        String index = "44";
        String segment = "that";
        String segmentRegister = "4";
        List<String> expectedAsList = List.of(
                "@" + segmentRegister,
                "D=M",
                "@" + index,
                "D=D+A",
                "@R13",
                "M=D",
                "@SP",
                "M=M-1",
                "@SP",
                "A=M",
                "D=M",
                "@R13",
                "A=M",
                "M=D"
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.popMemory(segment, index));
    }
    @Test
    public void testPopTemp() {
        String index = "55";
        String segment = "temp";
        String segmentRegister = "5";
        List<String> expectedAsList = List.of(
                "@" + segmentRegister,
                "D=M",
                "@" + index,
                "D=D+A",
                "@R13",
                "M=D",
                "@SP",
                "M=M-1",
                "@SP",
                "A=M",
                "D=M",
                "@R13",
                "A=M",
                "M=D"
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.popMemory(segment, index));
    }
}