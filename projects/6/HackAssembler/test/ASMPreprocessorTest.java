import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void testHandleLabel() {
        SymbolTable st = new SymbolTable();
        List<String> program = new ArrayList<>();
        // line 0
        program.add("@sum");
        // line 1
        program.add("M=0");
        program.add("(LOOP)");
        // line 2
        program.add("@i");
        // line 3
        program.add("D=M");
        // line 4
        program.add("@R0");
        // line 5
        program.add("D=D-M");
        // line 6
        program.add("@END");
        // line 7
        program.add("D;JGT");
        // line 8
        program.add("@LOOP");
        // line 9
        program.add("D=D+M;JMP");
        program.add("(END)");
        // line 10
        program.add("@END");
        // line 11
        program.add("0;JMP");

        ASMPreprocessor preprocessor = new ASMPreprocessor();
        List<String> result = preprocessor.handleLabels(program, st);
        assertTrue(st.contains("LOOP"));
        assertEquals(2, st.getAddress("LOOP"));

        assertTrue(st.contains("END"));
        assertEquals(10, st.getAddress("END"));

        List<String> expected = new ArrayList<>();
        // line 0
        expected.add("@sum");
        // line 1
        expected.add("M=0");
        // line 2
        expected.add("@i");
        // line 3
        expected.add("D=M");
        // line 4
        expected.add("@R0");
        // line 5
        expected.add("D=D-M");
        // line 6
        expected.add("@END");
        // line 7
        expected.add("D;JGT");
        // line 8
        expected.add("@LOOP");
        // line 9
        expected.add("D=D+M;JMP");
        // line 10
        expected.add("@END");
        // line 11
        expected.add("0;JMP");

        assertEquals(expected, result);
    }
}
