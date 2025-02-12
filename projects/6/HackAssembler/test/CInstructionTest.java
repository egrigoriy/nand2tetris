import org.junit.Test;

import static org.junit.Assert.*;

public class CInstructionTest {
    @Test
    public void testCInstructionWithNoDestination() {
        String comp = "D&M";
        String jmp = "JGE";
        CInstruction instruction = new CInstruction(null, comp, jmp);
        assertEquals("1111000000000011", instruction.toBinary());
    }

    @Test
    public void testCInstructionWithNoJump() {
        String dst = "AM";
        String comp = "A-1";
        CInstruction instruction = new CInstruction(dst, comp, null);
        assertEquals("1110110010101000", instruction.toBinary());
    }

    @Test
    public void testCInstructionFull() {
        String dst = "ADM";
        String comp = "D+M";
        String jmp = "JEQ";
        CInstruction instruction = new CInstruction(dst, comp, jmp);
        assertEquals("1111000010111010", instruction.toBinary());
    }

    @Test
    public void testCInstructionEqualityFull() {
        String dst = "ADM";
        String comp = "D+M";
        String jmp = "JEQ";
        CInstruction instruction1 = new CInstruction(dst, comp, jmp);
        CInstruction instruction2 = new CInstruction(dst, comp, jmp);
        assertEquals(instruction1, instruction2);
    }

    @Test
    public void testCInstructionEqualityNoDestination() {
        String comp = "D+M";
        String jmp = "JEQ";
        CInstruction instruction1 = new CInstruction(null, comp, jmp);
        CInstruction instruction2 = new CInstruction(null, comp, jmp);
        assertEquals(instruction1, instruction2);
    }
    @Test
    public void testCInstructionEqualityNoJump() {
        String dst = "AM";
        String comp = "D+M";
        CInstruction instruction1 = new CInstruction(dst, comp, null);
        CInstruction instruction2 = new CInstruction(dst, comp, null);
        assertEquals(instruction1, instruction2);
    }

    @Test
    public void testIsSuchInstruction() {
        assertTrue(CInstruction.isSuch("M=D+M;JMP"));
        assertFalse(CInstruction.isSuch("@1234"));
    }
}
