import java.util.List;
import java.util.Random;

public class ASM {
    public static String moveValueToA(String value) {
        return "@" + value;
    }

    public static String moveValueToD(String c) {
        List<String> result = List.of(
                moveValueToA(c),
                moveAToD()
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String moveAToD() {
        return "D=A";
    }

    public static String moveDToA() {
        return "A=D";
    }

    public static String storeDToDereference(String reference) {
        List<String> result = List.of(
                moveValueToA(reference),
                "A=M",
                "M=D"
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String loadDereferenceToD(String reference) {
        List<String> result = List.of(
                moveValueToA(reference),
                "A=M",
                "D=M"
        );
        return String.join(System.lineSeparator(), result);
    }
    public static String loadDereferenceToA(String reference) {
        List<String> result = List.of(
                moveValueToA(reference),
                "A=M",
                "A=M"
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String storeDToReferencePlusIndex(String reference, String index) {
        List<String> result = List.of(
                moveValueToA(reference),
                "A=M",
                "M=D"
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String loadReferenceToD(String reference) {
        List<String> result = List.of(
                moveValueToA(reference),
                "A=M",
                "D=M"
        );
        return String.join(System.lineSeparator(), result);
    }
    public static String loadAddressToD(String address) {
        List<String> result = List.of(
                moveValueToA(address),
                "D=M"
        );
        return String.join(System.lineSeparator(), result);
    }
    public static String storeDToAddress(String address) {
        List<String> result = List.of(
                moveValueToA(address),
                "M=D"
        );
        return String.join(System.lineSeparator(), result);
    }


    public static String increment(String address) {
        List<String> result = List.of(
                moveValueToA(address),
                "M=M+1"
        );
        return String.join(System.lineSeparator(), result);
    }
    public static String decrement(String address) {
        List<String> result = List.of(
                moveValueToA(address),
                "M=M-1"
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String pushD() {
        List<String> result = List.of(
                storeDToDereference("SP"),
                increment("SP")
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String popD() {
        List<String> result = List.of(
                decrement("SP"),
                loadDereferenceToD("SP")
        );
        return String.join(System.lineSeparator(), result);
    }

    public static String popA() {
        List<String> result = List.of(
                decrement("SP"),
                loadDereferenceToA("SP")
        );
        return String.join(System.lineSeparator(), result);
    }
    public static String addAToD() {
        return "D=D+A";
    }

    public static String addDToA() {
        return "A=A+D";
    }
    public static String subAFromD() {
        return "D=D-A";
    }

    public static String negD() {
        return "D=-D";
    }
    public static String notD() {
        return "D=!D";
    }

    public static String andAD() {
        return "D=D&A";
    }

    public static String orAD() {
        return "D=D|A";
    }

    public static String setDTrueFalseIf(String condition) {
        int random = new Random().nextInt((int) (Math.pow(2, 16) + 1));
        String labelNameTRUE = "TRUE$" + random;
        String labelNameEND = "END$" + random;
        List<String> result = List.of(
                "@" + labelNameTRUE,
                "D;J" + condition.toUpperCase(),
                "D=0",
                "@" + labelNameEND,
                "0;JMP",
                label(labelNameTRUE),
                "D=-1",
                label(labelNameEND)
        );
        return String.join(System.lineSeparator(), result);
    }
    public static String label(String labelName) {
        return "(" + labelName + ")";
    }

    public static String jmp() {
        return "0;JMP";
    }
    public static String jne() {
        return "D;JNE";
    }
}
