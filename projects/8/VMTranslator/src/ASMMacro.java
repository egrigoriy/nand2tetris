import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ASMMacro {
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

    public static String pushMemory(int address) {
        List<String> result = new ArrayList<>();
        result.add("@" + address);
        result.add("D=M");
        result.add(pushD());
        return String.join(System.lineSeparator(), result);
    }

    public static String popD() {
        List<String> result = new ArrayList<>();
        result.add(decSP());
        result.add("@0");
        result.add("A=M");
        result.add("M=D");
        return String.join(System.lineSeparator(), result);
    }

    public static String popA() {
        List<String> result = new ArrayList<>();
        result.add(decSP());
        result.add("@0");
        result.add("A=M");
        result.add("A=M");
        return String.join(System.lineSeparator(), result);
    }

    public static String popMemory(int address) {
        List<String> result = new ArrayList<>();
        result.add(popD());
        result.add("@" + address);
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
