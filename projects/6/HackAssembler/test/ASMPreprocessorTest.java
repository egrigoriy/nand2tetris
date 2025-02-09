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
    public void testPutLabelsInSymbolTableWhenPresent() {
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
        preprocessor.putLabelsInSymbolTable(program, st);
        assertTrue(st.contains("LOOP"));
        assertEquals(2, st.getAddress("LOOP"));

        assertTrue(st.contains("END"));
        assertEquals(10, st.getAddress("END"));
    }

    public void testNotPutLabelsInSymbolTableWhenAbsent() {
        SymbolTable st = new SymbolTable();
        List<String> program = new ArrayList<>();
        // line 0
        program.add("@sum");
        // line 1
        program.add("M=0");
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
        // line 10
        program.add("@END");
        // line 11
        program.add("0;JMP");

        ASMPreprocessor preprocessor = new ASMPreprocessor();
        preprocessor.putLabelsInSymbolTable(program, st);
        assertTrue(st.contains("LOOP"));
        assertEquals(2, st.getAddress("LOOP"));

        assertTrue(st.contains("END"));
        assertEquals(10, st.getAddress("END"));
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
        program.add("@LOOP");
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
        // line 10
        program.add("@1234");
        // line 11
        program.add("M=D");
        program.add("(END)");
        // line 12
        program.add("@END");
        // line 13
        program.add("0;JMP");

        ASMPreprocessor preprocessor = new ASMPreprocessor();
        List<String> result = preprocessor.handleLabels(program, st);
        assertTrue(st.contains("LOOP"));
        assertEquals(2, st.getAddress("LOOP"));

        assertTrue(st.contains("END"));
        assertEquals(12, st.getAddress("END"));

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
        expected.add("@2");
        // line 5
        expected.add("D=D-M");
        // line 6
        expected.add("@12");
        // line 7
        expected.add("D;JGT");
        // line 8
        expected.add("@2");
        // line 9
        expected.add("D=D+M;JMP");
        // line 10
        expected.add("@1234");
        // line 11
        expected.add("M=D");
        // line 12
        expected.add("@12");
        // line 13
        expected.add("0;JMP");

        assertEquals(expected, result);
    }


    @Test
    public void testRemoveLabelPseudoInstructionsWhenPresent() {
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
        List<String> result = preprocessor.removeLabelPseudoInstructions(program);

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

    @Test
    public void testNotRemoveLabelPseudoInstructionsWhenAbsent() {
        List<String> program = new ArrayList<>();
        // line 0
        program.add("@sum");
        // line 1
        program.add("M=0");
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
        List<String> result = preprocessor.removeLabelPseudoInstructions(program);

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

    @Test
    public void testHandleVariables() {
        ASMPreprocessor preprocessor = new ASMPreprocessor();
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

        SymbolTable st = new SymbolTable();
        List<String> result = preprocessor.handleVariables(program, st);
        assertEquals(expected, result);
    }

    @Test
    public void testProcess() {
        List<String> program = new ArrayList<>();
        // line 0
        program.add("@sum");
        // line 1
        program.add("M=0");
        program.add("(LOOP)");
        program.add("");
        program.add("// Computes R1=1 + ... + R0");
        program.add("// i = 1");
        // line 2
        program.add("@i // create index");
        // line 3
        program.add("D=M");
        // line 4
        program.add("@R0");
        // line 5
        program.add("D = D - M");
        // line 6
        program.add("@END");
        // line 7
        program.add("D;JGT");
        // line 8
        program.add("@LOOP");
        // line 9
        program.add("D=D+M;JMP");
        // line 10
        program.add("@1234");
        // line 11
        program.add("M=D");
        program.add("(END)");
        // line 12
        program.add("@END");
        // line 13
        program.add("0;JMP");

        ASMPreprocessor preprocessor = new ASMPreprocessor();
        List<String> result = preprocessor.process(program);

        List<String> expected = new ArrayList<>();
        // line 0
        expected.add("@16");
        // line 1
        expected.add("M=0");
        // line 2
        expected.add("@17");
        // line 3
        expected.add("D=M");
        // line 4
        expected.add("@0");
        // line 5
        expected.add("D=D-M");
        // line 6
        expected.add("@12");
        // line 7
        expected.add("D;JGT");
        // line 8
        expected.add("@2");
        // line 9
        expected.add("D=D+M;JMP");
        // line 10
        expected.add("@1234");
        // line 11
        expected.add("M=D");
        // line 12
        expected.add("@12");
        // line 13
        expected.add("0;JMP");
        assertEquals(expected, result);
    }
}
