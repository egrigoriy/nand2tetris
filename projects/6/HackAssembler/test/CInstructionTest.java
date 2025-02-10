import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
}
