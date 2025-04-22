import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ASMTest {

    @Before
    public void setUp() {
    }

    @Test
    public void testMoveAToD() {
        assertEquals("D=A", ASM.moveAToD());
    }

    @Test
    public void testMoveDtoA() {
        assertEquals("A=D", ASM.moveDToA());
    }

    @Test
    public void testMoveValueToA() {
        assertEquals("@1234", ASM.moveValueToA("1234"));
    }

    @Test
    public void testMoveValueToD() {
        String c = "1234";
        List<String> expectedList = List.of(
                "@" + c,
                "D=A"
        );
        String expected = String.join(System.lineSeparator(), expectedList);
        assertEquals(expected, ASM.moveValueToD(c));
    }

    @Test
    public void testLoadDereferenceToD() {
        String reference = "SP";
        List<String> expectedList = List.of(
                "@" + reference,
                "A=M",
                "D=M"
        );
        String expected = String.join(System.lineSeparator(), expectedList);
        assertEquals(expected, ASM.loadDereferenceToD(reference));
    }

    @Test
    public void testLoadDereferenceToA() {
        String reference = "SP";
        List<String> expectedList = List.of(
                "@" + reference,
                "A=M",
                "A=M"
        );
        String expected = String.join(System.lineSeparator(), expectedList);
        assertEquals(expected, ASM.loadDereferenceToA(reference));
    }

    @Test
    public void testStoreToPointedAddressFromDRegister() {
        String reference = "SP";
        List<String> expectedList = List.of(
                "@" + reference,
                "A=M",
                "M=D"
        );
        String expected = String.join(System.lineSeparator(), expectedList);
        assertEquals(expected, ASM.storeDToDereference(reference));
    }

    @Test
    public void testLoadToDRegisterFromAddress() {
        String address = "1234";
        List<String> expectedList = List.of(
                "@" + address,
                "D=M"
        );
        String expected = String.join(System.lineSeparator(), expectedList);
        assertEquals(expected, ASM.loadMemoryToD(address));
    }

    @Test
    public void testStoreToAddressFromDRegister() {
        String address = "1234";
        List<String> expectedList = List.of(
                "@" + address,
                "M=D"
        );
        String expected = String.join(System.lineSeparator(), expectedList);
        assertEquals(expected, ASM.storeDToMemory(address));
    }

    @Test
    public void testIncrement() {
        String address = "1234";
        List<String> expectedList = List.of(
                "@" + address,
                "M=M+1"
        );
        String expected = String.join(System.lineSeparator(), expectedList);
        assertEquals(expected, ASM.increment(address));
    }

    @Test
    public void testDecrement() {
        String address = "1234";
        List<String> expectedList = List.of(
                "@" + address,
                "M=M-1"
        );
        String expected = String.join(System.lineSeparator(), expectedList);
        assertEquals(expected, ASM.decrement(address));
    }

    @Test
    public void testPushD() {
        List<String> expectedList = List.of(
                "@SP",
                "A=M",
                "M=D",
                "@SP",
                "M=M+1"
        );
        String expected = String.join(System.lineSeparator(), expectedList);
        assertEquals(expected, ASM.pushD());
    }

    @Test
    public void testPopD() {
        List<String> expectedList = List.of(
                "@SP",
                "M=M-1",
                "@SP",
                "A=M",
                "D=M"
        );
        String expected = String.join(System.lineSeparator(), expectedList);
        assertEquals(expected, ASM.popD());
    }

    @Test
    public void testPopA() {
        List<String> expectedList = List.of(
                "@SP",
                "M=M-1",
                "@SP",
                "A=M",
                "A=M"
        );
        String expected = String.join(System.lineSeparator(), expectedList);
        assertEquals(expected, ASM.popA());
    }

    @Test
    public void testAddAtoD() {
        assertEquals("D=D+A", ASM.addAToD());
    }

    @Test
    public void testAddDtoA() {
        assertEquals("A=A+D", ASM.addDToA());
    }
    @Test
    public void testSub() {
        assertEquals("D=D-A", ASM.subAFromD());
    }

    @Test
    public void testNeg() {
        assertEquals("D=-D", ASM.negD());
    }

    @Test
    public void testNot() {
        assertEquals("D=!D", ASM.notD());
    }

    @Test
    public void testAnd() {
        assertEquals("D=D&A", ASM.andAD());
    }

    @Test
    public void testOr() {
        assertEquals("D=D|A", ASM.orAD());
    }

    @Test
    public void testLabel() {
        String myLabel = "MyLabel";
        assertEquals("(" + myLabel + ")", ASM.label(myLabel));
    }

    @Test
    public void testJMP() {
        assertEquals("0;JMP", ASM.jmp());
    }
    @Test
    public void testJNE() {
        assertEquals("D;JNE", ASM.jne());
    }

    @Test
    public void testPushValue() {
        String value = "1234";
        List<String> expectedList = List.of(
                "@" + value,
                "D=A",
                "@SP",
                "A=M",
                "M=D",
                "@SP",
                "M=M+1"
        );
        String expected = String.join(System.lineSeparator(), expectedList);
        assertEquals(expected, ASM.pushValue(value));
    }
    @Test
    public void testStoreValueToMemory() {
        String value = "1234";
        String address = "257";
        List<String> expectedList = List.of(
                "@" + value,
                "D=A",
                "@" + address,
                "M=D"
        );
        String expected = String.join(System.lineSeparator(), expectedList);
        assertEquals(expected, ASM.storeValueToMemory(value, address));
    }

    @Test
    public void testJumpTo() {
        String label = "jumpLabel";
        List<String> expectedList = List.of(
                "@" + label,
                "0;JMP"
        );
        String expected = String.join(System.lineSeparator(), expectedList);
        assertEquals(expected, ASM.jumpTo(label));
    }

    @Test
    public void testMoveFromMemoryToMemory() {
        String srcAddress = "1234";
        String dstAddress = "5678";
        List<String> expectedList = List.of(
                "@" + srcAddress,
                "D=M",
                "@" + dstAddress,
                "M=D"
        );
        String expected = String.join(System.lineSeparator(), expectedList);
        assertEquals(expected, ASM.moveFromMemoryToMemory(srcAddress, dstAddress));
    }


    @Test
    public void testPushFromMemory() {
        String address = "1234";
        List<String> expectedList = List.of(
                "@" + address,
                "D=M",
                "@SP",
                "A=M",
                "M=D",
                "@SP",
                "M=M+1"
        );
        String expected = String.join(System.lineSeparator(), expectedList);
        assertEquals(expected, ASM.pushFromMemory(address));
    }
    @Test
    public void testPopToMemory() {
        String address = "1234";
        List<String> expectedList = List.of(
                "@SP",
                "M=M-1",
                "@SP",
                "A=M",
                "D=M",
                "@" + address,
                "M=D"
        );
        String expected = String.join(System.lineSeparator(), expectedList);
        assertEquals(expected, ASM.popToMemory(address));
    }
    @Test
    public void testPushFromTempSegment() {
        String index = "2";
        String address = String.valueOf(5 + Integer.parseInt(index));
        List<String> expectedList = List.of(
                "@" + address ,
                "D=M",
                "@SP",
                "A=M",
                "M=D",
                "@SP",
                "M=M+1"
        );
        String expected = String.join(System.lineSeparator(), expectedList);
        assertEquals(expected, ASM.pushFromTempSegment(index));
    }
    @Test
    public void testPopToTempSegment() {
        String index = "3";
        String address = String.valueOf(5 + Integer.parseInt(index));
        List<String> expectedList = List.of(
                "@SP",
                "M=M-1",
                "@SP",
                "A=M",
                "D=M",
                "@" + address,
                "M=D"
        );
        String expected = String.join(System.lineSeparator(), expectedList);
        assertEquals(expected, ASM.popToTempSegment(index));
    }
    @Test
    public void testPushFromPointedSegment() {
        String reference = "ARG";
        String index = "2";
        String address = String.valueOf(5 + Integer.parseInt(index));
        List<String> expectedList = List.of(
                "@" + reference,
                "D=M",
                "@" + index,
                "D=D+A",
                "@R13",
                "M=D",
                "@R13",
                "A=M",
                "D=M",
                "@SP",
                "A=M",
                "M=D",
                "@SP",
                "M=M+1"
        );
        String expected = String.join(System.lineSeparator(), expectedList);
        assertEquals(expected, ASM.pushFromPointedSegment(reference, index));
    }
    @Test
    public void testPopToPointedSegment() {
        String reference = "LCL";
        String index = "3";
        List<String> expectedList = List.of(
                "@" + reference,
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
        String expected = String.join(System.lineSeparator(), expectedList);
        assertEquals(expected, ASM.popToPointedSegment(reference, index));
    }
}
