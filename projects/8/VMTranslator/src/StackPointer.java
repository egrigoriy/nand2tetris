import java.util.List;

public class StackPointer {
    private static String register = "SP";
    public static String inc() {
        List<String> result = List.of(
                "@" + register,
                "M=M+1"
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String dec() {
        List<String> result = List.of(
                "@" + register,
                "M=M-1"
        );
        return String.join(System.lineSeparator(), result);
    }
}
