import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ASMMacro {
    public static final Map<String, String> map = Map.ofEntries(
            Map.entry("local", "1"),
            Map.entry("argument", "2"),
            Map.entry("this", "3"),
            Map.entry("that", "4"),
            Map.entry("temp", "5")
    );

    public static String loadAddressToA(String register, String index) {
        List<String> result = new ArrayList<>();
        result.add("@" + register);
        result.add("D=M");
        result.add("@" + index);
        result.add("A=D+A");
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
                "@" + register,
                "D=M",
                "@" + index,
                "D=D+A",
                "@R13",
                "M=D"
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String pushD() {
        List<String> result = List.of(
                "@SP",
                "A=M",
                "M=D",
                incSP()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String pushValue(String value) {
        List<String> result = List.of(
                "@" + value,
                "D=A",
                pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String pushMemory(String segment, String index) {
        List<String> result = List.of(
                loadAddressToA(map.get(segment), index),
                "D=M",
                pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String pushTemp(String index) {
        int address = Integer.parseInt(map.get("temp")) + Integer.parseInt(index);
        List<String> result = List.of(
                "@" + address,
                "D=M",
                pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String popD() {
        List<String> result = List.of(
                decSP(),
                "@SP",
                "A=M",
                "D=M"
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
        List<String> result = new ArrayList<>();
        result.add(decSP());
        result.add("@SP");
        result.add("A=M");
        result.add("A=M");
        return String.join(System.lineSeparator(), result);
    }

    public static String popMemory(String register, String index) {
        List<String> result = List.of(
                calculateAbsAddressToR13(map.get(register), index),
                popD(),
                "@R13",
                "A=M",
                "M=D"
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String popTemp(String index) {
        int address = Integer.parseInt(map.get("temp")) + Integer.parseInt(index);
        List<String> result = List.of(
                popD(),
                "@" + address,
                "M=D"
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String incSP() {
        List<String> result = List.of(
                "@SP",
                "M=M+1"
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String decSP() {
        List<String> result = List.of(
                "@SP",
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

}
