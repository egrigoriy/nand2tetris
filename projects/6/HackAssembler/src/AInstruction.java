import java.util.Objects;

/**
 * Represents an Assembler Hack A-Instruction
 */
public class AInstruction implements Instruction {
    public static final String ASM_PREFIX = "@";
    private static final String BINARY_PREFIX = "0";
    private final String address;

    public AInstruction(String address) {
        this.address = address;
    }

    /**
     * Returns true if the given string is an A-Instruction, otherwise false
     * @param instruction
     * @return true if the given string is an A-Instruction, otherwise false
     */
    public static boolean isSuch(String instruction) {
        return instruction.startsWith(ASM_PREFIX);
    }

    /**
     * Returns the binary format of this A-Instruction
     * @return the binary format of this A-Instruction
     */
    @Override
    public String toBinary() {
        return BINARY_PREFIX + addressToBinary();
    }

    /**
     * Returns the binary representation of this address string
     * @return the binary representation of this address string
     */
    private String addressToBinary() {
        String result = "";
        int toConvert = Integer.parseInt(address);
        for (int i=0; i<15; i++) {
            int reminder = toConvert % 2;
            result = reminder + result;
            toConvert = toConvert / 2;
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AInstruction that = (AInstruction) o;
        return Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
