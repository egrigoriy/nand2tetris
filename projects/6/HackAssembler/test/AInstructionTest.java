import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AInstructionTest {
    @Test
    public void testAInstructionToBinary() {
        String address = "0";
        AInstruction aInstruction = new AInstruction(address);
        assertEquals("0000000000000000", aInstruction.toBinary());

        address = "12345";
        aInstruction = new AInstruction(address);
        assertEquals("0011000000111001", aInstruction.toBinary());
    }

    @Test
    public void testAInstructionEquals() {
        String address = "12345";
        AInstruction aInstruction1 = new AInstruction(address);
        AInstruction aInstruction2 = new AInstruction(address);
        assertEquals(aInstruction1, aInstruction2);
    }
}
