import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ASMMacro {
    public static final Map<String, String> map = Map.ofEntries(
            Map.entry("local", "1"),
            Map.entry("argument", "2"),
            Map.entry("this", "3"),
            Map.entry("that", "4"),
            Map.entry("temp", "5"),
            Map.entry("static", "16")
    );


    public static String storeSumToR13(String register, String index) {
        List<String> result = List.of(
                DRegister.loadAddress(register),
                "@" + index,
                "D=D+A",
                DRegister.storeToAddress("R13")
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Calculate absolute address from given register pointer and index.
     * Put the result in general purpose register R13
     *
     * @param register
     * @param index
     * @return
     */
    public static String calculateAbsAddressToR13(String register, String index) {
        List<String> result = List.of(
                DRegister.loadAddress(register),
                "@" + index,
                "D=D+A",
                DRegister.storeToAddress("R13")
        );
        return String.join(System.lineSeparator(), result);
    }


    public static String pushValue(String value) {
        List<String> result = List.of(
                DRegister.loadValue(value),
                DRegister.push()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String pushPointedMemory(String segment, String index) {
        List<String> result = List.of(
                storeSumToR13(map.get(segment), index),
                DRegister.loadAddressPointedBy("R13"),
                DRegister.push()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String pushTemp(String index) {
        int address = Integer.parseInt(map.get("temp")) + Integer.parseInt(index);
        List<String> result = List.of(
                DRegister.loadAddress(String.valueOf(address)),
                DRegister.push()
        );
        return String.join(System.lineSeparator(), result);
    }


    public static String popToMemory(String register, String index) {
        List<String> result = List.of(
                calculateAbsAddressToR13(map.get(register), index),
                DRegister.pop(),
                DRegister.storeToAddressPointedBy("R13")
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String popTemp(String index) {
        int address = Integer.parseInt(map.get("temp")) + Integer.parseInt(index);
        List<String> result = List.of(
                DRegister.pop(),
                DRegister.storeToAddress(String.valueOf(address))
        );
        return String.join(System.lineSeparator(), result);
    }

    private static String increment(String register) {
        List<String> result = List.of(
                "@" + register,
                "M=M+1"
        );
        return String.join(System.lineSeparator(), result);
    }

    private static String decrement(String register) {
        List<String> result = List.of(
                "@" + register,
                "M=M-1"
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String add() {
        List<String> result = List.of(
                DRegister.pop(),
                ARegister.pop(),
                "D=D+A",
                DRegister.push()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String sub() {
        List<String> result = List.of(
                DRegister.pop(),
                ARegister.pop(),
                "D=A-D",
                DRegister.push()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String neg() {
        List<String> result = List.of(
                DRegister.pop(),
                "D=-D",
                DRegister.push()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String not() {
        List<String> result = List.of(
                DRegister.pop(),
                "D=!D",
                DRegister.push()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String and() {
        List<String> result = List.of(
                DRegister.pop(),
                ARegister.pop(),
                "D=D&A",
                DRegister.push()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String or() {
        List<String> result = List.of(
                DRegister.pop(),
                ARegister.pop(),
                "D=D|A",
                DRegister.push()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String lt() {
        List<String> result = List.of(
                DRegister.pop(),
                ARegister.pop(),
                "D=A-D",
                DRegister.setTrueFalseIf("LT"),
                DRegister.push()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String gt() {
        List<String> result = List.of(
                DRegister.pop(),
                ARegister.pop(),
                "D=A-D",
                DRegister.setTrueFalseIf("GT"),
                DRegister.push()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String eq() {
        List<String> result = List.of(
                DRegister.pop(),
                ARegister.pop(),
                "D=A-D",
                DRegister.setTrueFalseIf("EQ"),
                DRegister.push()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String pushThis() {
        List<String> result = List.of(
               DRegister.loadAddress(map.get("this")),
                DRegister.push()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String pushThat() {
        List<String> result = List.of(
                DRegister.loadAddress(map.get("that")),
                DRegister.push()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String popThis() {
        List<String> result = List.of(
                DRegister.pop(),
                DRegister.storeToAddress(map.get("this"))
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String popThat() {
        List<String> result = List.of(
                DRegister.pop(),
                DRegister.storeToAddress(map.get("that"))
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String popStatic(String index) {
        int address = Integer.parseInt(map.get("static")) + Integer.parseInt(index);
        List<String> result = List.of(
                DRegister.pop(),
                DRegister.storeToAddress(String.valueOf(address))
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String pushStatic(String index) {
        int address = Integer.parseInt(map.get("static")) + Integer.parseInt(index);
        List<String> result = List.of(
                DRegister.loadAddress(String.valueOf(address)),
                DRegister.push()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String label(String labelName) {
        return "(" + labelName + ")";
    }

    public static String asmgoto(String labelName) {
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
                DRegister.pop(),
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
            result.add(StackPointer.inc());
        }
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Use R14 as pointer to caller frame
     *
     * @return
     */
    public static String asmreturn() {
        List<String> result = List.of(
                // set returned value
                popToMemory("argument", "0"),
                // reposition SP just after the address of returned value
                moveFromAddressToAddress(map.get("argument"), "SP"),
                increment("SP"),
                // store caller end frame address to R14
                moveFromAddressToAddress(map.get("local"), "R14"),
                decrement("R14"),
                // restore THAT
                DRegister.loadAddressPointedBy("R14"),
                DRegister.storeToAddress(map.get("that")),
                decrement("R14"),
                // restore THIS
                DRegister.loadAddressPointedBy("R14"),
                DRegister.storeToAddress(map.get("this")),
                decrement("R14"),
                // restore ARGS
                DRegister.loadAddressPointedBy("R14"),
                DRegister.storeToAddress(map.get("argument")),
                decrement("R14"),
                // restore LCL
                DRegister.loadAddressPointedBy("R14"),
                DRegister.storeToAddress(map.get("local")),
                decrement("R14"),
                DRegister.loadAddress("R14"),
                "A=D",
                "0;JMP"
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String moveFromAddressToAddress(String fromAddress, String toAddress) {
        List<String> result = List.of(
                DRegister.loadAddress(fromAddress),
                DRegister.storeToAddress(toAddress)
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
                DRegister.loadAddress(map.get("local")),
                DRegister.push(),
                // push ARG
                DRegister.loadAddress(map.get("argument")),
                DRegister.push(),
                // push THIS
                pushThis(),
                // push THAT
                pushThat(),
                // reposition for callee ARG = SP – 5 – nArgs
                DRegister.loadAddress("SP"),
                "@" + "5",
                "D=D-A",
                "@" + nArgs,
                "D=D-A",
                DRegister.storeToAddress(map.get("argument")),
                // reposition for callee LCL = SP
                moveFromAddressToAddress("SP", map.get("local")),
                // inject return address label
                "(" + retAddressLabel + ")"
        );
        return String.join(System.lineSeparator(), result);
    }
}
