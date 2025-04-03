import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ASMWriter {
    public static final Map<String, String> map = Map.ofEntries(
            Map.entry("local", "1"),
            Map.entry("argument", "2"),
            Map.entry("this", "3"),
            Map.entry("that", "4"),
            Map.entry("temp", "5"),
            Map.entry("static", "16")
    );

    public static String storeSumToR13(String address, String index) {
        List<String> result = List.of(
                ASM.loadAddressToD(address),
                ASM.moveValueToA(index),
                ASM.addAToD(),
                ASM.storeDToAddress("R13")
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
        List<String> result = List.of(
                storeSumToR13(reference, index),
                ASM.loadDereferenceToD("R13"),
                ASM.pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String popDereference(String reference, String index) {
        List<String> result = List.of(
                storeSumToR13(reference, index),
                ASM.popD(),
                ASM.storeDToDereference("R13")
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
        return pushDereference(map.get("local"), index);
    }

    public static String popLocal(String index) {
        return popDereference(map.get("local"), index);
    }

    public static String pushArgument(String index) {
        return pushDereference(map.get("argument"), index);
    }

    public static String popArgument(String index) {
        return popDereference(map.get("argument"), index);
    }
    public static String pushThis(String index) {
        return pushDereference(map.get("this"), index);
    }

    public static String popThis(String index) {
        return popDereference(map.get("this"), index);
    }

    public static String pushThat(String index) {
        return pushDereference(map.get("that"), index);
    }

    public static String popThat(String index) {
        return popDereference(map.get("that"), index);
    }

    public static String pushTemp(String index) {
        return pushEffectiveAddress(map.get("temp"), index);
    }

    public static String popTemp(String index) {
        return popEffectiveAddress(map.get("temp"), index);
    }
    public static String popStatic(String index) {
        return popEffectiveAddress(map.get("static"), index);
    }

    public static String pushStatic(String index) {
        return pushEffectiveAddress(map.get("static"), index);
    }

    public static String pushPointer(String index) {
        String address = index.equals("0") ? map.get("this") : map.get("that");
        return pushAddress(address);
    }

    public static String popPointer(String index) {
        String address = index.equals("0") ? map.get("this") : map.get("that");
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
        return "(" + labelName + ")";
    }

    public static String goTo(String labelName) {
        List<String> result = List.of(
                "@" + labelName,
                "0; JMP"
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
                "@" + labelName,
                "D;JNE"
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String function(String functionName, String nVars) {
        List<String> result = List.of(
                "(" + functionName + ")",
                // initialize locals
                initLCLSegment(nVars)
        );
        return String.join(System.lineSeparator(), result);
    }

    private static String initLCLSegment(String nVars) {
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
                // set returned value
                popEffectiveAddress(map.get("argument"), "0"),
                // reposition SP just after the address of returned value
                moveFromAddressToAddress(map.get("argument"), "SP"),
                ASM.increment("SP"),
                // store caller end frame address to R14
                moveFromAddressToAddress(map.get("local"), "R14"),
                ASM.decrement("R14"),
                // restore THAT
                ASM.loadDereferenceToD("R14"),
                ASM.storeDToAddress(map.get("that")),
                ASM.decrement("R14"),
                // restore THIS
                ASM.loadDereferenceToD("R14"),
                ASM.storeDToAddress(map.get("this")),
                ASM.decrement("R14"),
                // restore ARGS
                ASM.loadDereferenceToD("R14"),
                ASM.storeDToAddress(map.get("argument")),
                ASM.decrement("R14"),
                // restore LCL
                ASM.loadDereferenceToD("R14"),
                ASM.storeDToAddress(map.get("local")),
                ASM.decrement("R14"),
                ASM.loadAddressToD("R14"),
                "A=D",
                "0;JMP"
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

    public static String call(String functionName, String nArgs) {
        int random = new Random().nextInt((int) (Math.pow(2, 16) + 1));
        String retAddressLabel = "retAddressLabel$" + random;
        List<String> result = List.of(
                // push retAddressLabel
                pushValue(retAddressLabel),
                // push LCL
                ASM.loadAddressToD(map.get("local")),
                ASM.pushD(),
                // push ARG
                ASM.loadAddressToD(map.get("argument")),
                ASM.pushD(),
                // push THIS
                pushLocal("0"),
                // push THAT
                pushLocal("1"),
                // reposition for callee ARG = SP – 5 – nArgs
                ASM.loadAddressToD("SP"),
                "@" + "5",
                "D=D-A",
                "@" + nArgs,
                "D=D-A",
                ASM.storeDToAddress(map.get("argument")),
                // reposition for callee LCL = SP
                moveFromAddressToAddress("SP", map.get("local")),
                // inject return address label
                "(" + retAddressLabel + ")"
        );
        return String.join(System.lineSeparator(), result);
    }

}
