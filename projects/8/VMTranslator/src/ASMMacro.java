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
        result.add("A=A+D");
        return String.join(System.lineSeparator(), result);

    }
    public static String pushD() {
        List<String> result = new ArrayList<>();
        result.add("@0");
        result.add("A=M");
        result.add("M=D");
        result.add(incSP());
        return String.join(System.lineSeparator(), result);
    }

    public static String pushValue(int value) {
        List<String> result = new ArrayList<>();
        result.add("@" + value);
        result.add("D=A");
        result.add(pushD());
        return String.join(System.lineSeparator(), result);
    }

    public static String pushMemory(String segment, String index) {
        List<String> result = new ArrayList<>();
        result.add(loadAddressToA(map.get(segment), index));
        result.add("D=M");
        result.add(pushD());
        return String.join(System.lineSeparator(), result);
    }

    public static String popD() {
        List<String> result = new ArrayList<>();
        result.add(decSP());
        result.add("@0");
        result.add("A=M");
        result.add("D=M");
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Pop stack top element to A-register.
     * IMPORTANT: D-register is not affected!!!!
     * @return
     */
    public static String popA() {
        List<String> result = new ArrayList<>();
        result.add(decSP());
        result.add("@0");
        result.add("A=M");
        result.add("A=M");
        return String.join(System.lineSeparator(), result);
    }

    public static String popMemory(String register, String index) {
        List<String> result = new ArrayList<>();
        result.add(popD());
        result.add(loadAddressToA(register, index));
        result.add("M=D");
        return String.join(System.lineSeparator(), result);
    }

    public static String incSP() {
        List<String> result = new ArrayList<>();
        result.add("@0");
        result.add("M=M+1");
        return String.join(System.lineSeparator(), result);
    }

    public static String decSP() {
        List<String> result = new ArrayList<>();
        result.add("@0");
        result.add("M=M-1");
        return String.join(System.lineSeparator(), result);
    }
}
