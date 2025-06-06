import java.util.Objects;

/**
 * Represents an Assembler Hack instruction
 */

public class CInstruction implements Instruction {
    public static final String BINARY_PREFIX = "111";
    private final String dst;
    private final String comp;
    private final String jmp;

    public CInstruction(String dst, String comp, String jmp) {
        this.dst = dst;
        this.comp = comp;
        this.jmp = jmp;
    }

    /**
     * Returns true if the given string is an C-Instruction, otherwise false
     * @param instruction
     * @return true if the given string is an C-Instruction, otherwise false
     */
    public static boolean isSuch(String instruction) {
        return !AInstruction.isSuch(instruction);
    }

    /**
     * Returns the binary format of this C-Instruction
     * @return the binary format of this C-Instruction
     */
    @Override
    public String toBinary() {
        return BINARY_PREFIX +  compToBinary() + dstToBinary() + jmpToBinary();
    }

    /**
     * Returns the binary format of dst
     * @return the binary format of dst
     */
    private String dstToBinary() {
        if (dst == null) {
            return "000";
        }
        return switch (dst) {
            case "M" -> "001";
            case "D" -> "010";
            case "DM", "MD" -> "011";
            case "A" -> "100";
            case "AM" -> "101";
            case "AD" -> "110";
            case "ADM" -> "111";
            default -> throw new IllegalStateException("Unexpected value of dst: " + dst);
        };
    }

    /**
     * Returns the binary format of comp
     * @return the binary format of comp
     */
    private String compToBinary() {
        return switch (comp) {
            case "0" -> "0101010";
            case "1" -> "0111111";
            case "-1" -> "0111010";
            case "D" -> "0001100";
            case "A" -> "0110000";
            case "M" -> "1110000";
            case "!D" -> "0001101";
            case "!A" -> "0110001";
            case "!M" -> "1110001";
            case "-D" -> "0001111";
            case "-A" -> "0110011";
            case "-M" -> "1110011";
            case "D+1" -> "0011111";
            case "A+1" -> "0110111";
            case "M+1" -> "1110111";
            case "D-1" -> "0001110";
            case "A-1" -> "0110010";
            case "M-1" -> "1110010";
            case "D+A" -> "0000010";
            case "D+M" -> "1000010";
            case "D-A" -> "0010011";
            case "D-M" -> "1010011";
            case "A-D" -> "0000111";
            case "M-D" -> "1000111";
            case "D&A" -> "0000000";
            case "D&M" -> "1000000";
            case "D|A" -> "0010101";
            case "D|M" -> "1010101";
            default -> throw new IllegalStateException("Unexpected value of comp: " + comp);
        };
    }

    /**
     * Returns the binary format of jmp
     * @return the binary format of jmp
     */
    private String jmpToBinary() {
        if (jmp == null) {
            return "000";
        }
        return switch (jmp) {
            case "JGT" -> "001";
            case "JEQ" -> "010";
            case "JGE" -> "011";
            case "JLT" -> "100";
            case "JNE" -> "101";
            case "JLE" -> "110";
            case "JMP" -> "111";
            default -> throw new IllegalStateException("Unexpected value of jump: " + jmp);
        };
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CInstruction that = (CInstruction) o;
        return Objects.equals(dst, that.dst) && Objects.equals(comp, that.comp) && Objects.equals(jmp, that.jmp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dst, comp, jmp);
    }
}
