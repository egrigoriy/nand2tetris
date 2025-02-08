import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ASMPreprocessorTest {
    @Test
    public void testRemovesEmptyLines() {
        ASMPreprocessor preprocessor = new ASMPreprocessor();
        List<String> program = new ArrayList<>();
        program.add("M=1");
        program.add("     ");
        program.add("");
        program.add("// Computes R1=1 + ... + R0");
//        program.add("// i = 1");
//        program.add("@i");
//        program.add("@i");
//        program.add("M=1");
//        program.add("     ");
//        program.add("");
//        program.add("// sum = 0");
//        program.add("@sum");
//        program.add("M=0");
//        program.add("(LOOP)");
        List<String> expected = new ArrayList<>();
        expected.add("M=1");
        expected.add("// Computes R1=1 + ... + R0");
       List<String> result = preprocessor.removeEmptyLines(program);
       assertEquals(expected, result);
    }

    @Test
    public void testRemovesEmptySpaces() {
        ASMPreprocessor preprocessor = new ASMPreprocessor();
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
        List<String> result = preprocessor.removeEmptySpaces(program);
        assertEquals(expected, result);
    }

    @Test
    public void testRemoveFullLineComments() {
        ASMPreprocessor preprocessor = new ASMPreprocessor();
        List<String> program = new ArrayList<>();
        program.add("M=1");
        program.add("// Computes R1=1 + ... + R0");
        program.add("// i = 1");
        program.add("@i");
        program.add("M=1");
        program.add("// sum = 0");
        List<String> expected = new ArrayList<>();
        expected.add("M=1");
        expected.add("@i");
        expected.add("M=1");
        List<String> result = preprocessor.removeComments(program);
        assertEquals(expected, result);
    }

    @Test
    public void testRemoveEndLineComments() {
        ASMPreprocessor preprocessor = new ASMPreprocessor();
        List<String> program = new ArrayList<>();
        program.add("M=1     // assign 1 to memory");
        program.add("// Computes R1=1 + ... + R0");
        program.add("// i = 1");
        program.add("@i//index");
        program.add("M=1 // this is inline comment");
        program.add("// sum = 0");
        List<String> expected = new ArrayList<>();
        expected.add("M=1");
        expected.add("@i");
        expected.add("M=1");
        List<String> result = preprocessor.removeComments(program);
        assertEquals(expected, result);
    }
}
