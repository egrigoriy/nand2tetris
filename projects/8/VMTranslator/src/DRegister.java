import java.util.List;
import java.util.Random;

public class DRegister {
    public static String push() {
        List<String> result = List.of(
                storeToAddressPointedBy("SP"),
                StackPointer.inc()
        );
        return String.join(System.lineSeparator(), result);
    }
    public static String pop() {
        List<String> result = List.of(
                StackPointer.dec(),
                loadAddressPointedBy("SP")
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String storeToAddressPointedBy(String register) {
        List<String> result = List.of(
                "@" + register,
                "A=M",
                "M=D"
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String loadAddressPointedBy(String register) {
        List<String> result = List.of(
                "@" + register,
                "A=M",
                "D=M"
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String loadAddress(String address) {
        List<String> result = List.of(
                "@" + address,
                "D=M"
        );
        return String.join(System.lineSeparator(), result);
    }
    public static String storeToAddress(String address) {
        List<String> result = List.of(
                "@" + address,
                "M=D"
        );
        return String.join(System.lineSeparator(), result);
    }
    public static String loadValue(String value) {
        List<String> result = List.of(
                "@" + value,
                "D=A"
        );
        return String.join(System.lineSeparator(), result);
    }
    public static String setTrueFalseIf(String condition) {
        int random = new Random().nextInt((int) (Math.pow(2, 16) + 1));
        String labelTRUE = "TRUE$" + random;
        String labelEND = "END$" + random;
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
