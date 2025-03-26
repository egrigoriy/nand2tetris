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
                incSP()
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
                storeSumToR13(segmentRegister, index),
                loadToDPointedAddressPointedBy("R13"),
                pushFromD()
        );

        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.pushPointedMemory(segment, index));
    }

    @Test
    public void testPushArgument() {
        String index = "22";
        String segment = "argument";
        String baseRegister = "2";
        List<String> expectedAsList = List.of(
                storeSumToR13(baseRegister, index),
                loadToDPointedAddressPointedBy("R13"),
                pushFromD()
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.pushPointedMemory(segment, index));
    }

    @Test
    public void testPushThis() {
        String index = "33";
        String segment = "this";
        String segmentRegister = "3";
        List<String> expectedAsList = List.of(
                storeSumToR13(segmentRegister, index),
                loadToDPointedAddressPointedBy("R13"),
                pushFromD()
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.pushPointedMemory(segment, index));
    }

    @Test
    public void testPushThat() {
        String index = "44";
        String segment = "that";
        String segmentRegister = "4";
        List<String> expectedAsList = List.of(
                storeSumToR13(segmentRegister, index),
                loadToDPointedAddressPointedBy("R13"),
                pushFromD()
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.pushPointedMemory(segment, index));
    }

    @Test
    public void testPushTemp() {
        String index = "6";
        String segment = "temp";
        String segmentRegister = "5";
        int address = Integer.parseInt(segmentRegister) + Integer.parseInt(index);
        List<String> expectedAsList = List.of(
                "@" + address,
                "D=M",
                "@SP",
                "A=M",
                "M=D",
                incSP()
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.pushTemp(index));
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
                decSP(),
                "@SP",
                "A=M",
                "D=M",
                "@R13",
                "A=M",
                "M=D"
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.popToMemory(segment, index));
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
                decSP(),
                "@SP",
                "A=M",
                "D=M",
                "@R13",
                "A=M",
                "M=D"
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.popToMemory(segment, index));
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
                decSP(),
                "@SP",
                "A=M",
                "D=M",
                "@R13",
                "A=M",
                "M=D"
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.popToMemory(segment, index));
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
                decSP(),
                "@SP",
                "A=M",
                "D=M",
                "@R13",
                "A=M",
                "M=D"
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.popToMemory(segment, index));
    }

    @Test
    public void testPopTemp() {
        String index = "6";
        String segment = "temp";
        String segmentRegister = "5";
        int address = Integer.parseInt(segmentRegister) + Integer.parseInt(index);
        List<String> expectedAsList = List.of(
                popToD(),
                "@" + address,
                "M=D"
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.popTemp(index));
    }

    @Test
    public void testAdd() {
        List<String> expectedAsList = List.of(
                popToD(),
                popToA(),
                "D=D+A",
                pushFromD()
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.add());
    }

    @Test
    public void testSub() {
        List<String> expectedAsList = List.of(
                popToD(),
                popToA(),
                "D=A-D",
                pushFromD()
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.sub());
    }

    @Test
    public void testNeg() {
        List<String> expectedAsList = List.of(
                popToD(),
                "D=-D",
                pushFromD()
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.neg());
    }

    @Test
    public void testNot() {
        List<String> expectedAsList = List.of(
                popToD(),
                "D=!D",
                pushFromD()
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.not());
    }

    @Test
    public void testAnd() {
        List<String> expectedAsList = List.of(
                popToD(),
                popToA(),
                "D=D&A",
                pushFromD()
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.and());
    }

    @Test
    public void testOr() {
        List<String> expectedAsList = List.of(
                popToD(),
                popToA(),
                "D=D|A",
                pushFromD()
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.or());
    }

    @Test
    public void testPushPointer0() {
        String segmentRegister = "3";
        List<String> expectedAsList = List.of(
                "@" + segmentRegister,
                "D=M",
                pushFromD()
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.pushThis());
    }

    @Test
    public void testPushPointer1() {
        String segmentRegister = "4";
        List<String> expectedAsList = List.of(
                "@" + segmentRegister,
                "D=M",
                pushFromD()
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.pushThat());
    }
    @Test
    public void testPopPointer0() {
        String segmentRegister = "3";
        List<String> expectedAsList = List.of(
                popToD(),
                "@" + segmentRegister,
                "M=D"
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.popThis());
    }

    @Test
    public void testPopPointer1() {
        String segmentRegister = "4";
        List<String> expectedAsList = List.of(
                popToD(),
                "@" + segmentRegister,
                "M=D"
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.popThat());
    }

    @Test
    public void testPushStatic() {
        String index = "6";
        String segment = "static";
        String segmentRegister = "16";
        int address = Integer.parseInt(segmentRegister) + Integer.parseInt(index);
        List<String> expectedAsList = List.of(
                "@" + address,
                "D=M",
                "@SP",
                "A=M",
                "M=D",
                incSP()
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.pushStatic(index));
    }
    @Test
    public void testPopStatic() {
        String index = "6";
        String segment = "static";
        String segmentRegister = "16";
        int address = Integer.parseInt(segmentRegister) + Integer.parseInt(index);
        List<String> expectedAsList = List.of(
                popToD(),
                "@" + address,
                "M=D"
        );
        String expected = String.join(System.lineSeparator(), expectedAsList);
        assertEquals(expected, ASMMacro.popStatic(index));
    }
    private String pushFromD() {
        List<String> result = List.of(
                "@SP",
                "A=M",
                "M=D",
                incSP()
        );
        return String.join(System.lineSeparator(), result);
    }

    private String popToA() {
        List<String> result = List.of(
                decSP(),
                "@SP",
                "A=M",
                "A=M"
        );
        return String.join(System.lineSeparator(), result);
    }

    private String popToD() {
        List<String> result = List.of(
                decSP(),
                "@SP",
                "A=M",
                "D=M"
        );
        return String.join(System.lineSeparator(), result);
    }

    private String loadToDPointedAddressPointedBy(String register) {
        List<String> result = List.of(
                "@" + register,
                "A=M",
                "D=M"
        );
        return String.join(System.lineSeparator(), result);
    }
    private String incSP() {
        return "@SP" + System.lineSeparator() +
                "M=M+1";
    }

    private String decSP() {
        return "@SP" + System.lineSeparator() +
                "M=M-1";
    }

    private String storeSumToR13(String register, String index) {
        List<String> result = List.of(
                "@" + register,
                "D=M",
                "@" + index,
                "D=D+A",
                "@R13",
                "M=D"
        );
        return String.join(System.lineSeparator(), result);
    }
}