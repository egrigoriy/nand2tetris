import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ASMParserTest {
    @Test
    public void testParseSingleAInstruction() {
        List<String> instructions = List.of("@12345");
        List<Instruction> expected = new ArrayList<>();
        expected.add(new AInstruction("12345"));
        assertEquals(expected, ASMParser.parse(instructions));
    }
    @Test
    public void testParseSingleCInstructionFull() {
        List<String> instructions = List.of("DM=D+A;JNE");
        List<Instruction> expected = new ArrayList<>();
        expected.add(new CInstruction("DM", "D+A", "JNE"));
        assertEquals(expected, ASMParser.parse(instructions));
    }

    @Test
    public void testParseSingleCInstructionNoDestination() {
        List<String> instructions = List.of("D+M;JLT");
        List<Instruction> expected = new ArrayList<>();
        expected.add(new CInstruction(null, "D+M", "JLT"));
        assertEquals(expected, ASMParser.parse(instructions));
    }

    @Test
    public void testParseSingleCInstructionNoJump() {
        List<String> instructions = List.of("AD=D+M");
        List<Instruction> expected = new ArrayList<>();
        expected.add(new CInstruction("AD", "D+M", null));
        assertEquals(expected, ASMParser.parse(instructions));
    }

    @Test
    public void testParseMultipleInstructions() {
        List<String> instructions = new ArrayList<>();
        instructions.add("@123");
        instructions.add("MD=M-1");
        instructions.add("@456");
        instructions.add("D;JGT");

        List<Instruction> expected = new ArrayList<>();
        expected.add(new AInstruction("123"));
        expected.add(new CInstruction("MD", "M-1", null));
        expected.add(new AInstruction("456"));
        expected.add(new CInstruction(null, "D", "JGT"));

        assertEquals(expected, ASMParser.parse(instructions));
    }
}
