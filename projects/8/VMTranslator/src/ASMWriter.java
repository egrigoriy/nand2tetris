import java.util.ArrayList;
import java.util.List;

/**
 * Represents assembly writer
 */
public class ASMWriter {
    private static int callCounter = 1;

    /**
     * Returns assembly code for PUSH a value to the stack
     *
     * @param value
     * @return assembly code for PUSH a value to the stack
     */
    public static String pushValue(String value) {
        return ASM.pushValue(value);
    }

    /**
     * Returns assembly code for PUSH from LOCAL segment index
     *
     * @param index
     * @return assembly code for PUSH from LOCAL segment index
     */
    public static String pushLocal(String index) {
        return ASM.pushFromPointedSegment("LCL", index);
    }

    /**
     * Returns assembly code for POP to LOCAL segment index
     *
     * @param index
     * @return assembly code for POP to LOCAL segment index
     */
    public static String popLocal(String index) {
        return ASM.popToPointedSegment("LCL", index);
    }

    /**
     * Returns assembly code for PUSH from ARGUMENT segment index
     *
     * @param index
     * @return assembly code for PUSH from ARGUMENT segment index
     */
    public static String pushArgument(String index) {
        return ASM.pushFromPointedSegment("ARG", index);
    }

    /**
     * Returns assembly code for POP to ARGUMENT segment index
     *
     * @param index
     * @return assembly code for POP to ARGUMENT segment index
     */
    public static String popArgument(String index) {
        return ASM.popToPointedSegment("ARG", index);
    }

    /**
     * Returns assembly code for PUSH from THIS segment index
     *
     * @param index
     * @return assembly code for PUSH from THIS segment index
     */
    public static String pushThis(String index) {
        return ASM.pushFromPointedSegment("THIS", index);
    }

    /**
     * Returns assembly code for POP to THIS segment index
     *
     * @param index
     * @return assembly code for POP to THIS segment index
     */
    public static String popThis(String index) {
        return ASM.popToPointedSegment("THIS", index);
    }

    /**
     * Returns assembly code for PUSH from THAT segment index
     *
     * @param index
     * @return assembly code for PUSH from THAT segment index
     */
    public static String pushThat(String index) {
        return ASM.pushFromPointedSegment("THAT", index);
    }

    /**
     * Returns assembly code for POP to THAT segment index
     *
     * @param index
     * @return assembly code for POP to THAT segment index
     */
    public static String popThat(String index) {
        return ASM.popToPointedSegment("THAT", index);
    }

    /**
     * Returns assembly code for PUSH from TEMP segment index
     *
     * @param index
     * @return assembly code for PUSH from TEMP segment index
     */
    public static String pushTemp(String index) {
        return ASM.pushFromTempSegment(index);
    }

    /**
     * Returns assembly code for POP to TEMP segment index
     *
     * @param index
     * @return assembly code for POP to TEMP segment index
     */
    public static String popTemp(String index) {
        return ASM.popToTempSegment(index);
    }

    /**
     * Returns assembly code for PUSH from STATIC segment index
     *
     * @param address
     * @return assembly code for PUSH from STATIC segment index
     */
    public static String pushStatic(String address) {
        return ASM.pushFromMemory(address);
    }

    /**
     * Returns assembly code for POP to STATIC segment index
     *
     * @param address
     * @return assembly code for POP to STATIC segment index
     */
    public static String popStatic(String address) {
        return ASM.popToMemory(address);
    }


    /**
     * Returns assembly code for PUSH from POINTER index.
     * It's equivalent to PUSH THIS memory content if index is 0,
     * and to THAT memory content if index is 1.
     *
     * @param index
     * @return assembly code for PUSH from argument segment index
     */

    public static String pushPointer(String index) {
        String register = index.equals("0") ? "THIS" : "THAT";
        return ASM.pushFromMemory(register);
    }

    /**
     * Returns assembly code for POP to POINTER index.
     * It's equivalent to POP to THIS memory content if index is 0,
     * and to THAT memory content if index is 1.
     *
     * @param index
     * @return assembly code for POP argument segment index
     */
    public static String popPointer(String index) {
        String register = index.equals("0") ? "THIS" : "THAT";
        return ASM.popToMemory(register);
    }

    /**
     * Returns assembly code for VM command ADD
     *
     * @return assembly code for VM command ADD
     */
    public static String add() {
        List<String> result = List.of(
                ASM.popD(),
                ASM.popA(),
                ASM.addAToD(),
                ASM.pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for VM command SUB
     *
     * @return assembly code for VM command SUB
     */
    public static String sub() {
        List<String> result = List.of(
                ASM.popD(),
                ASM.popA(),
                ASM.subAFromD(),
                ASM.negD(),
                ASM.pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for VM command NEG
     *
     * @return assembly code for VM command NEG
     */
    public static String neg() {
        List<String> result = List.of(
                ASM.popD(),
                ASM.negD(),
                ASM.pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for VM command NOT
     *
     * @return assembly code for VM command NOT
     */
    public static String not() {
        List<String> result = List.of(
                ASM.popD(),
                ASM.notD(),
                ASM.pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for VM command AND
     *
     * @return assembly code for VM command AND
     */
    public static String and() {
        List<String> result = List.of(
                ASM.popD(),
                ASM.popA(),
                ASM.andAD(),
                ASM.pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for VM command OR
     *
     * @return assembly code for VM command OR
     */
    public static String or() {
        List<String> result = List.of(
                ASM.popD(),
                ASM.popA(),
                ASM.orAD(),
                ASM.pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for VM command LT
     *
     * @return assembly code for VM command LT
     */
    public static String lt() {
        List<String> result = List.of(
                ASM.popD(),
                ASM.popA(),
                ASM.subAFromD(),
                ASM.negD(),
                ASM.setDTrueFalseIf("LT"),
                ASM.pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for VM command GT
     *
     * @return assembly code for VM command GT
     */
    public static String gt() {
        List<String> result = List.of(
                ASM.popD(),
                ASM.popA(),
                ASM.subAFromD(),
                ASM.negD(),
                ASM.setDTrueFalseIf("GT"),
                ASM.pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for VM command EQ
     *
     * @return assembly code for VM command EQ
     */
    public static String eq() {
        List<String> result = List.of(
                ASM.popD(),
                ASM.popA(),
                ASM.subAFromD(),
                ASM.setDTrueFalseIf("EQ"),
                ASM.pushD()
        );
        return String.join(System.lineSeparator(), result);
    }


    /**
     * Returns assembly code for VM command LABEL
     *
     * @param labelName
     * @return assembly code for VM command LABEL
     */
    public static String label(String labelName) {
        return ASM.label(labelName);
    }

    /**
     * Returns assembly code for VM command GOTO
     *
     * @param labelName
     * @return assembly code for VM command GOTO
     */
    public static String goTo(String labelName) {
        return ASM.jumpTo(labelName);
    }

    /**
     * Returns assembly code for VM command IF-GOTO
     *
     * @param labelName
     * @return assembly code for VM command IF-GOTO
     */
    public static String ifGoto(String labelName) {
        List<String> result = List.of(
                ASM.popD(),
                ASM.moveValueToA(labelName),
                ASM.jne()
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for VM command FUNCTION
     *
     * @param functionName
     * @param nVars
     * @return assembly code for VM command FUNCTION
     */
    public static String function(String functionName, String nVars) {
        if (nVars.equals("0")) {
            return label(functionName);
        }
        List<String> result = List.of(
                // put label for function block
                label(functionName),
                // initialize locals
                initLocalSegment(nVars)
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for setting all LOCAL segment to 0
     *
     * @param nVars
     * @return assembly code for setting all LOCAL segment to 0
     */
    private static String initLocalSegment(String nVars) {
        int n = Integer.parseInt(nVars);
        List<String> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(pushValue("0"));
        }
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for VM command RETURN.
     * Address R14 is used as temporary storage of RETURN VALUE.
     * Address R15 is used as temporary storage of RETURN ADDRESS (address to jump).
     *
     * @return assembly code for VM command RETURN
     */
    public static String ret() {
        List<String> result = List.of(
                // store LCL - 5 to R15 as retAddress to jump
                ASM.loadMemoryToD("LCL"),
                ASM.moveValueToA("5"),
                ASM.subAFromD(),
                ASM.storeDToMemory("R15"),
                ASM.loadDereferenceToD("R15"),
                ASM.storeDToMemory("R15"),
                // store ARG to R14
                ASM.moveFromMemoryToMemory("ARG", "R14"),
                // pop returnValue to reference from R14
                ASM.popD(),
                ASM.storeDToDereference("R14"),
                // SP = LCL coz need to skip all locals
                ASM.moveFromMemoryToMemory("LCL", "SP"),
                // pop THAT
                ASM.popD(),
                ASM.storeDToMemory("THAT"),
                // pop THIS
                ASM.popD(),
                ASM.storeDToMemory("THIS"),
                // pop ARG
                ASM.popD(),
                ASM.storeDToMemory("ARG"),
                // pop LCL
                ASM.popD(),
                ASM.storeDToMemory("LCL"),
                // SP = R14
                ASM.loadMemoryToD("R14"),
                ASM.storeDToMemory("SP"),
                // SP++
                ASM.increment("SP"),
                // jmp to address in R15
                ASM.loadMemoryToA("R15"),
                ASM.jmp()
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for VM command CALL.
     * Injected return address label must be unique,
     * cause call can be made from any place of the assembly file.
     *
     * @param functionName
     * @param nArgs
     * @return assembly code for VM command CALL.
     */
    public static String call(String functionName, String nArgs) {
        String retAddressLabelName = functionName + "$ret." + callCounter;
        callCounter++;
        List<String> result = List.of(
                // push retAddressLabel
                pushValue(retAddressLabelName),
                // push LCL
                ASM.pushFromMemory("LCL"),
                // push ARG
                ASM.pushFromMemory("ARG"),
                // push THIS
                ASM.pushFromMemory("THIS"),
                // push THAT
                ASM.pushFromMemory("THAT"),
                // reposition for callee ARG = SP – 5 – nArgs
                ASM.loadMemoryToD("SP"),
                ASM.moveValueToA("5"),
                ASM.subAFromD(),
                ASM.moveValueToA(nArgs),
                ASM.subAFromD(),
                ASM.storeDToMemory("ARG"),
                // reposition for callee LCL = SP
                ASM.moveFromMemoryToMemory("SP", "LCL"),
                // transfer control to callee
                goTo(functionName),
                // inject return address label
                label(retAddressLabelName)
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembler code for setting SP and calling Sys.init function
     *
     * @return assembler code for setting SP and calling Sys.init function
     */
    public static String bootstrap() {
        List<String> result = List.of(
                initSP(),
                callSysInit()
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembler code for setting SP to 256
     *
     * @return assembler code for setting SP to 256
     */
    private static String initSP() {
        return ASM.storeValueToMemory("256", "SP");
    }

    /**
     * Returns assembler code for calling Sys.init function
     *
     * @return assembler code for calling Sys.init function
     */
    private static String callSysInit() {
        return call("Sys.init", "0");
    }

    /**
     * Returns assembler code for empty infinite loop
     *
     * @return returns assembler code for empty infinite loop
     */
    public static String endInfiniteLoop() {
        String endLabelName = "END";
        List<String> result = List.of(
                ASM.label(endLabelName),
                ASM.jumpTo(endLabelName)
        );
        return String.join(System.lineSeparator(), result);
    }
}
