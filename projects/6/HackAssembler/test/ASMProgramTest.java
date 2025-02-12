import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ASMProgramTest {
    @Test
    public void testToBinary() {
        List<String> program = new ArrayList<>();
        program.add("// This file is part of www.nand2tetris.org");
        program.add("// and the book \"The Elements of Computing Systems\"");
        program.add("// by Nisan and Schocken, MIT Press.");
        program.add("// File name: projects/6/rect/Rect.asm");
        program.add("");
        program.add("// Draws a rectangle at the top-left corner of the screen.");
        program.add("// The rectangle is 16 pixels wide and R0 pixels high.");
        program.add("// Usage: Before executing, put a value in R0.");
        program.add("");
        program.add("   // If (R0 <= 0) goto END else n = R0");
        program.add("   @R0");
        program.add("   D=M");
        program.add("   @END");
        program.add("   D;JLE ");
        program.add("   @n");
        program.add("   M=D");
        program.add("   // addr = base address of first screen row");
        program.add("   @SCREEN");
        program.add("   D=A");
        program.add("   @addr");
        program.add("   M=D");
        program.add("(LOOP)");
        program.add("   // RAM[addr] = -1");
        program.add("   @addr");
        program.add("   A=M");
        program.add("   M=-1");
        program.add("   // addr = base address of next screen row");
        program.add("   @addr");
        program.add("   D=M");
        program.add("   @32");
        program.add("   D=D+A");
        program.add("   @addr");
        program.add("   M=D");
        program.add("   // decrements n and loops");
        program.add("   @n");
        program.add("   MD=M-1");
        program.add("   @LOOP");
        program.add("   D;JGT");
        program.add("(END)");
        program.add("   @END");
        program.add("   0;JMP");

        List<String> expected = new ArrayList<>();
        expected.add("0000000000000000");
        expected.add("1111110000010000");
        expected.add("0000000000010111");
        expected.add("1110001100000110");
        expected.add("0000000000010000");
        expected.add("1110001100001000");
        expected.add("0100000000000000");
        expected.add("1110110000010000");
        expected.add("0000000000010001");
        expected.add("1110001100001000");
        expected.add("0000000000010001");
        expected.add("1111110000100000");
        expected.add("1110111010001000");
        expected.add("0000000000010001");
        expected.add("1111110000010000");
        expected.add("0000000000100000");
        expected.add("1110000010010000");
        expected.add("0000000000010001");
        expected.add("1110001100001000");
        expected.add("0000000000010000");
        expected.add("1111110010011000");
        expected.add("0000000000001010");
        expected.add("1110001100000001");
        expected.add("0000000000010111");
        expected.add("1110101010000111");

        ASMProgram asmProgram = new ASMProgram(program);
        assertEquals(expected, asmProgram.toBinary());
    }
}
