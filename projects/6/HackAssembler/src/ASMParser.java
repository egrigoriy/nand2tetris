import java.util.ArrayList;
import java.util.List;

public class ASMParser {
    public List<Instruction> parse(List<String> instructions) {
        List<Instruction> result = new ArrayList<>();
        for (String inst : instructions) {
            result.add(parse(inst));
        }
        return result;
    }

    private Instruction parse(String inst) {
        if (inst.startsWith("@")) {
            return handleAInstruction(inst);
        } else {
            return handleCInstruction(inst);
        }
    }

    private Instruction handleCInstruction(String inst) {
        int indexOfAssign = inst.indexOf("=");
        int indexOfSemicolon = inst.indexOf(";");
        if ((indexOfAssign == -1) && (indexOfSemicolon == -1)) {
            return new CInstruction(null, inst, null);
        }
        if ((indexOfAssign == -1) && (indexOfSemicolon != -1)) {
            String comp = getComp(inst);
            String jmp = getJmp(inst);
            return new CInstruction(null, comp, jmp);
        }
        if ((indexOfAssign != -1) && (indexOfSemicolon == -1)) {
            String dst = getDst(inst);
            String comp = getComp(inst);
            return new CInstruction(dst, comp, null);
        }
        String dst = getDst(inst);
        String comp = getComp(inst);
        String jmp = getJmp(inst);
        return new CInstruction(dst, comp, jmp);
    }

    private Instruction handleAInstruction(String inst) {
        return new AInstruction(inst.substring(1));
    }

    private String getJmp(String inst) {
        int indexOfSemicolon = inst.indexOf(";");
        return inst.substring(indexOfSemicolon + 1);
    }

    private String getComp(String inst) {
        int indexOfAssign = inst.indexOf("=");
        int indexOfSemicolon = inst.indexOf(";");
        if (indexOfSemicolon == -1) {
            return inst.substring(indexOfAssign + 1);
        }
        return inst.substring(indexOfAssign + 1, indexOfSemicolon);
    }

    private String getDst(String inst) {
        int indexOfAssign = inst.indexOf("=");
        return inst.substring(0, indexOfAssign);
    }
}
