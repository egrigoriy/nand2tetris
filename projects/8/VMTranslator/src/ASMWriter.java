import java.util.ArrayList;
import java.util.List;

public class ASMWriter {
    private static int callCounter = 1;

    public static String storeSumToAddress(String address, String index, String toAddress) {
        List<String> result = List.of(
                ASM.loadAddressToD(address),
                ASM.moveValueToA(index),
                ASM.addAToD(),
                ASM.storeDToAddress(toAddress)
        );
        return String.join(System.lineSeparator(), result);
    }


    public static String pushValue(String value) {
        List<String> result = List.of(
                ASM.moveValueToD(value),
                ASM.pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String pushDereference(String reference, String index) {
        String pointerAddress = "R13";
        List<String> result = List.of(
                storeSumToAddress(reference, index, pointerAddress),
                ASM.loadDereferenceToD(pointerAddress),
                ASM.pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String popDereference(String reference, String index) {
        String pointerAddress = "R13";
        List<String> result = List.of(
                storeSumToAddress(reference, index, pointerAddress),
                ASM.popD(),
                ASM.storeDToDereference(pointerAddress)
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String pushAddress(String address) {
        List<String> result = List.of(
                ASM.loadAddressToD(address),
                ASM.pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String popAddress(String address) {
        List<String> result = List.of(
                ASM.popD(),
                ASM.storeDToAddress(address)
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String pushEffectiveAddress(String base, String index) {
        String address = String.valueOf(Integer.parseInt(base) + Integer.parseInt(index));
        return pushAddress(address);
    }

    public static String popEffectiveAddress(String base, String index) {
        String address = String.valueOf(Integer.parseInt(base) + Integer.parseInt(index));
        return popAddress(address);
    }

    public static String pushLocal(String index) {
        return pushDereference("LCL", index);
    }

    public static String popLocal(String index) {
        return popDereference("LCL", index);
    }

    public static String pushArgument(String index) {
        return pushDereference("ARG", index);
    }

    public static String popArgument(String index) {
        return popDereference("ARG", index);
    }

    public static String pushThis(String index) {
        return pushDereference("THIS", index);
    }

    public static String popThis(String index) {
        return popDereference("THIS", index);
    }

    public static String pushThat(String index) {
        return pushDereference("THAT", index);
    }

    public static String popThat(String index) {
        return popDereference("THAT", index);
    }

    public static String pushTemp(String index) {
        String tempBase = "5";
        return pushEffectiveAddress(tempBase, index);
    }

    public static String popTemp(String index) {
        String tempBase = "5";
        return popEffectiveAddress(tempBase, index);
    }

    public static String popStatic(String address) {
        return popAddress(address);
    }

    public static String pushStatic(String address) {
        return pushAddress(address);
    }

    public static String pushPointer(String index) {
        String address = index.equals("0") ? "THIS" : "THAT";
        return pushAddress(address);
    }

    public static String popPointer(String index) {
        String address = index.equals("0") ? "THIS" : "THAT";
        return popAddress(address);
    }

    public static String add() {
        List<String> result = List.of(
                ASM.popD(),
                ASM.popA(),
                ASM.addAToD(),
                ASM.pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

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

    public static String neg() {
        List<String> result = List.of(
                ASM.popD(),
                ASM.negD(),
                ASM.pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String not() {
        List<String> result = List.of(
                ASM.popD(),
                ASM.notD(),
                ASM.pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String and() {
        List<String> result = List.of(
                ASM.popD(),
                ASM.popA(),
                ASM.andAD(),
                ASM.pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String or() {
        List<String> result = List.of(
                ASM.popD(),
                ASM.popA(),
                ASM.orAD(),
                ASM.pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

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


    public static String label(String labelName) {
        return ASM.label(labelName);
    }

    public static String goTo(String labelName) {
        List<String> result = List.of(
                ASM.moveValueToA(labelName),
                ASM.jmp()
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * True = 1111111 => if contains True then !D must be 00000.
     *
     * @param labelName
     * @return
     */
    public static String ifGoto(String labelName) {
        List<String> result = List.of(
                ASM.popD(),
                ASM.moveValueToA(labelName),
                ASM.jne()
        );
        return String.join(System.lineSeparator(), result);
    }

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

    private static String initLocalSegment(String nVars) {
        int n = Integer.parseInt(nVars);
        List<String> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(pushValue("0"));
        }
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Use R14 as pointer to caller frame
     *
     * @return
     */
    public static String ret() {
        List<String> result = List.of(
                // store LCL - 5 to R15 as retAddress to jump
                ASM.loadAddressToD("LCL"),
                ASM.moveValueToA("5"),
                ASM.subAFromD(),
                ASM.storeDToAddress("R15"),
                ASM.loadDereferenceToD("R15"),
                ASM.storeDToAddress("R15"),
                // store ARG to R14
                moveFromAddressToAddress("ARG", "R14"),
                // pop returnValue to reference from R14
                ASM.popD(),
                ASM.storeDToDereference("R14"),
                // SP = LCL coz need to skip all locals
                moveFromAddressToAddress("LCL", "SP"),
                // pop THAT
                ASM.popD(),
                ASM.storeDToAddress("THAT"),
                // pop THIS
                ASM.popD(),
                ASM.storeDToAddress("THIS"),
                // pop ARG
                ASM.popD(),
                ASM.storeDToAddress("ARG"),
                // pop LCL
                ASM.popD(),
                ASM.storeDToAddress("LCL"),
                // SP = R14
                ASM.loadAddressToD("R14"),
                ASM.storeDToAddress("SP"),
                // SP++
                ASM.increment("SP"),
                // jmp to address in R15
                ASM.loadAddressToD("R15"),
                ASM.moveDToA(),
                ASM.jmp()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String moveFromAddressToAddress(String fromAddress, String toAddress) {
        List<String> result = List.of(
                ASM.loadAddressToD(fromAddress),
                ASM.storeDToAddress(toAddress)
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Return address label must be unique, cause call can be made from any place
     *
     * @param functionName
     * @param nArgs
     * @return
     */
    public static String call(String functionName, String nArgs) {
        String retAddressLabelName = functionName + "$ret." + callCounter;
        callCounter++;
        List<String> result = List.of(
                // push retAddressLabel
                pushValue(retAddressLabelName),
                // push LCL
                pushAddress("LCL"),
                // push ARG
                pushAddress("ARG"),
                // push THIS
                pushAddress("THIS"),
                // push THAT
                pushAddress("THAT"),
                // reposition for callee ARG = SP – 5 – nArgs
                ASM.loadAddressToD("SP"),
                ASM.moveValueToA("5"),
                ASM.subAFromD(),
                ASM.moveValueToA(nArgs),
                ASM.subAFromD(),
                ASM.storeDToAddress("ARG"),
                // reposition for callee LCL = SP
                moveFromAddressToAddress("SP", "LCL"),
                // transfer control to callee
                goTo(functionName),
                // inject return address label
                label(retAddressLabelName)
        );
        return String.join(System.lineSeparator(), result);
    }

}
