import java.util.List;

public class ARegister {
    /**
     * Pop stack top element to A-register.
     * IMPORTANT: D-register is not affected!!!!
     *
     * @return
     */
    public static String pop() {
        List<String> result = List.of(
                StackPointer.dec(),
                "@SP",
                "A=M",
                "A=M"
        );
        return String.join(System.lineSeparator(), result);
    }
}
