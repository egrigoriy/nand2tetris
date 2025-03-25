import java.util.List;
import java.util.Map;
import java.util.Random;

public class ASMMacro {
    public static final Map<String, String> map = Map.ofEntries(
            Map.entry("local", "1"),
            Map.entry("argument", "2"),
            Map.entry("this", "3"),
            Map.entry("that", "4"),
            Map.entry("temp", "5")
    );

    public static String loadAddressToD(String address) {
        List<String> result = List.of(
                "@" + address,
                "D=M"
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String storeDToAddress(String address) {
        List<String> result = List.of(
                "@" + address,
                "M=D"
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String storeSumToR13(String register, String index) {
        List<String> result = List.of(
                loadAddressToD(register),
                "@" + index,
                "D=D+A",
                storeDToAddress("R13")
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String loadToDAddressPointedBy(String register) {
        List<String> result = List.of(
                "@" + register,
                "A=M",
                "D=M"
        );
        return String.join(System.lineSeparator(), result);
    }


    public static String storeDToAddressPointedBy(String register) {
        List<String> result = List.of(
                "@" + register,
                "A=M",
                "M=D"
        );
        return String.join(System.lineSeparator(), result);
    }
    public static String loadValueToD(String value) {
        List<String> result = List.of(
                "@" + value,
                "D=A"
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
                loadAddressToD(register),
                "@" + index,
                "D=D+A",
                storeDToAddress("R13")
        );
        return String.join(System.lineSeparator(), result);
    }

    private static String pushD() {
        List<String> result = List.of(
                storeDToAddressPointedBy("SP"),
                increment("SP")
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String pushValue(String value) {
        List<String> result = List.of(
                loadValueToD(value),
                pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String pushPointedMemory(String segment, String index) {
        List<String> result = List.of(
                storeSumToR13(map.get(segment), index),
                loadToDAddressPointedBy("R13"),
                pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String pushTemp(String index) {
        int address = Integer.parseInt(map.get("temp")) + Integer.parseInt(index);
        List<String> result = List.of(
                loadAddressToD(String.valueOf(address)),
                pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String popD() {
        List<String> result = List.of(
                decrement("SP"),
                loadToDAddressPointedBy("SP")
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Pop stack top element to A-register.
     * IMPORTANT: D-register is not affected!!!!
     *
     * @return
     */
    public static String popA() {
        List<String> result = List.of(
                decrement("SP"),
                "@SP",
                "A=M",
                "A=M"
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String popMemory(String register, String index) {
        List<String> result = List.of(
                calculateAbsAddressToR13(map.get(register), index),
                popD(),
                storeDToAddressPointedBy("R13")
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String popTemp(String index) {
        int address = Integer.parseInt(map.get("temp")) + Integer.parseInt(index);
        List<String> result = List.of(
                popD(),
                storeDToAddress(String.valueOf(address))
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String increment(String register) {
        List<String> result = List.of(
                "@" + register,
                "M=M+1"
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String decrement(String register) {
        List<String> result = List.of(
                "@" + register,
                "M=M-1"
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String add() {
        List<String> result = List.of(
                popD(),
                popA(),
                "D=D+A",
                pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String sub() {
        List<String> result = List.of(
                popD(),
                popA(),
                "D=A-D",
                pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String neg() {
        List<String> result = List.of(
                popD(),
                "D=-D",
                pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String not() {
        List<String> result = List.of(
                popD(),
                "D=!D",
                pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String and() {
        List<String> result = List.of(
                popD(),
                popA(),
                "D=D&A",
                pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String or() {
        List<String> result = List.of(
                popD(),
                popA(),
                "D=D|A",
                pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String lt() {
        List<String> result = List.of(
                popD(),
                popA(),
                "D=A-D",
                putToDTrueFalseIf("LT"),
                pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String gt() {
        List<String> result = List.of(
                popD(),
                popA(),
                "D=A-D",
                putToDTrueFalseIf("GT"),
                pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String eq() {
        List<String> result = List.of(
                popD(),
                popA(),
                "D=A-D",
                putToDTrueFalseIf("EQ"),
                pushD()
        );
        return String.join(System.lineSeparator(), result);
    }
    private static String putToDTrueFalseIf(String condition) {
        int random = new Random().nextInt((int) (Math.pow(2, 16) + 1));
        String labelTRUE = "TRUE" + random;
        String labelEND = "END" + random;
        List<String> result = List.of(
                "@" + labelTRUE,
                "D;J" + condition.toUpperCase(),
                "D=0",
                "@" + labelEND,
                "0;JMP",
                "(" + labelTRUE + ")",
                "D=-1",
                "(" + labelEND + ")"
        );
        return String.join(System.lineSeparator(), result);
    }
}
