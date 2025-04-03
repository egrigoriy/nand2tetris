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
    public void testMoveConstantToA() {
        assertEquals("@1234", ASM.moveValueToA("1234"));
    }

    @Test
    public void testMoveConstantToD() {
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
                "@" + reference ,
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
        assertEquals(expected, ASM.loadAddressToD(address));
    }

    @Test
    public void testStoreToAddressFromDRegister() {
        String address = "1234";
        List<String> expectedList = List.of(
                "@" + address,
                "M=D"
        );
        String expected = String.join(System.lineSeparator(), expectedList);
        assertEquals(expected, ASM.storeDToAddress(address));
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
    public void testAdd() {
        assertEquals("D=D+A", ASM.addAToD());
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
}
