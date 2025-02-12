import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents an ASM Parser for Hack Assembly Language
 */
public class ASMParser {

    /**
     * Returns list of instructions corresponding to a given list of strings
     * @param instructions
     * @return list of instructions corresponding to a given list of strings
     */
    public static List<Instruction> parse(List<String> instructions) {
       return instructions.stream()
               .map(ASMParser::parse)
               .collect(Collectors.toList());
    }

    /**
     * Returns the instruction corresponding to a given string
     * @param instruction
     * @return the instruction corresponding to a given string
     */
    private static Instruction parse(String instruction) {
        if (AInstruction.isSuch(instruction)) {
            return handleAInstruction(instruction);
        } else {
            return handleCInstruction(instruction);
        }
    }

    /**
     * Returns the AInstruction corresponding to a given string
     * @param instruction
     * @return AInstruction corresponding to a given string
     */
    private static Instruction handleAInstruction(String instruction) {
        return new AInstruction(instruction.substring(1));
    }

    /**
     * Returns the CInstruction corresponding to a given string
     * @param instruction
     * @return CInstruction corresponding to a given string
     */
    private static Instruction handleCInstruction(String instruction) {
        String dst = getDst(instruction);
        String comp = getComp(instruction);
        String jmp = getJmp(instruction);
        return new CInstruction(dst, comp, jmp);
    }

    /**
     * Returns dst field of given instruction if present, otherwise null
     * @param instruction
     * @return dst field of given instruction if present, otherwise null
     */
    private static String getDst(String instruction) {
        int indexOfAssign = instruction.indexOf("=");
        if (indexOfAssign == -1) return null;
        return instruction.substring(0, indexOfAssign);
    }

    /**
     * Returns comp field of given instruction if present, otherwise null
     * @param instruction
     * @return comp field of given instruction if present, otherwise null
     */
    private static String getComp(String instruction) {
        int indexOfAssign = instruction.indexOf("=");
        int indexOfSemicolon = instruction.indexOf(";");
        if (indexOfSemicolon == -1) {
            return instruction.substring(indexOfAssign + 1);
        }
        return instruction.substring(indexOfAssign + 1, indexOfSemicolon);
    }

    /**
     * Returns jmp field of given instruction if present, otherwise null
     * @param instruction
     * @return jmp field of given instruction if present, otherwise null
     */
    private static String getJmp(String instruction) {
        int indexOfSemicolon = instruction.indexOf(";");
        if (indexOfSemicolon == -1) return null;
        return instruction.substring(indexOfSemicolon + 1);
    }

}
