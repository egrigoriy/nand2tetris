import java.util.List;
import java.util.Random;

/**
 * Represents Assembly language for HAck platform
 */
public class ASM {
    private static final String effectiveAddressRegister = "R13";
    private static final int tempBaseAddress = 5;

    /**
     * Returns assembly code for moving given value to A-register
     *
     * @param value
     * @return assembly code for moving given value to A-register
     */
    public static String moveValueToA(String value) {
        return "@" + value;
    }

    /**
     * Returns assembly code for moving given value to D-register
     *
     * @param value
     * @return assembly code for moving given value to D-register
     */
    public static String moveValueToD(String value) {
        List<String> result = List.of(
                moveValueToA(value),
                moveAToD()
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for moving data from A-register to D-register
     *
     * @return assembly code for moving data from A-register to D-register
     */
    public static String moveAToD() {
        return "D=A";
    }

    /**
     * Returns assembly code for moving data from D-register to A-register
     *
     * @return assembly code for moving data from D-register to A-register
     */
    public static String moveDToA() {
        return "A=D";
    }


    /**
     * Returns assembly code for ADD operation of A-register from D-register.
     * The result is put in D-register.
     *
     * @return assembly code for ADD operation of A-register from D-register.
     */
    public static String addAToD() {
        return "D=D+A";
    }

    /**
     * Returns assembly code for ADD operation of A-register from D-register.
     * The result is put in A-register.
     *
     * @return assembly code for ADD operation of A-register from D-register.
     */
    public static String addDToA() {
        return "A=A+D";
    }

    /**
     * Returns assembly code for SUB operation of A-register from D-register.
     * The result is put in D-register.
     *
     * @return assembly code for SUB operation of A-register from D-register.
     */
    public static String subAFromD() {
        return "D=D-A";
    }

    /**
     * Returns assembly code for NEG operation of D-register.
     * The result is put in D-register.
     *
     * @return assembly code for NEG operation of D-register.
     */
    public static String negD() {
        return "D=-D";
    }

    /**
     * Returns assembly code for NOT operation of D-register.
     * The result is put in D-register.
     *
     * @return assembly code for NOT operation of D-register.
     */
    public static String notD() {
        return "D=!D";
    }

    /**
     * Returns assembly code for AND operation between D-register and A-register values.
     * The result is put in D-register.
     *
     * @return assembly code for AND operation between D-register and A-register values.
     */
    public static String andAD() {
        return "D=D&A";
    }

    public static String orAD() {
        return "D=D|A";
    }

    /**
     * Returns assembly code for setting true or false in D-register in case of matching the given condition
     * True is represented as -1, false as 0.
     *
     * @param condition
     * @return assembly code for setting true or false in D-register in case of matching the given condition
     */
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

    /**
     * Returns assembly code for label with given name
     *
     * @param labelName
     * @return assembly code for label with given name
     */
    public static String label(String labelName) {
        return "(" + labelName + ")";
    }

    /**
     * Returns assembly code for unconditional jump
     *
     * @return assembly code for unconditional jump
     */
    public static String jmp() {
        return "0;JMP";
    }

    /**
     * Returns assembly code for jump if D-register value is not equal to 0
     *
     * @return assembly code for jump if D-register value is not equal to 0
     */
    public static String jne() {
        return "D;JNE";
    }

    /**
     * Returns assembly code for jump to given label
     *
     * @param label
     * @return assembly code for jump to given label
     */
    public static String jumpTo(String label) {
        List<String> result = List.of(
                ASM.moveValueToA(label),
                ASM.jmp()
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for storing data from D-register to memory pointed by a given reference
     *
     * @param reference
     * @return assembly code for storing data from D-register to memory pointed by a given reference
     */
    public static String storeDToDereference(String reference) {
        List<String> result = List.of(
                loadMemoryToA(reference),
                "M=D"
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for loading data from memory pointed by a given reference to D-register
     *
     * @param reference
     * @return assembly code for loading data from memory pointed by a given reference to D-register
     */
    public static String loadDereferenceToD(String reference) {
        List<String> result = List.of(
                loadMemoryToA(reference),
                "D=M"
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for loading data from memory pointed by a given reference to A-register
     *
     * @param reference
     * @return assembly code for loading data from memory pointed by a given reference to A-register
     */
    public static String loadDereferenceToA(String reference) {
        List<String> result = List.of(
                loadMemoryToA(reference),
                "A=M"
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for loading data from given address to D-register
     *
     * @param address
     * @return assembly code for loading data from given address to D-register
     */
    public static String loadMemoryToD(String address) {
        List<String> result = List.of(
                moveValueToA(address),
                "D=M"
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for loading data from given address to A-register
     *
     * @param address
     * @return assembly code for loading data from given address to A-register
     */
    public static String loadMemoryToA(String address) {
        List<String> result = List.of(
                moveValueToA(address),
                "A=M"
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for storing data from D-register to given memory address
     *
     * @param address
     * @returns assembly code for storing data from D-register to given memory address
     */
    public static String storeDToMemory(String address) {
        List<String> result = List.of(
                moveValueToA(address),
                "M=D"
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for storing given value to given memory address
     *
     * @param value
     * @param address
     * @return assembly code for storing given value to given memory address
     */
    public static String storeValueToMemory(String value, String address) {
        List<String> result = List.of(
                ASM.moveValueToD(value),
                ASM.storeDToMemory(address)
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for moving data from one given address to another given address.
     *
     * @param fromAddress
     * @param toAddress
     * @return assembly code for moving data from one given address to another given address.
     */
    public static String moveFromMemoryToMemory(String fromAddress, String toAddress) {
        List<String> result = List.of(
                ASM.loadMemoryToD(fromAddress),
                ASM.storeDToMemory(toAddress)
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for pushing from D-register to address SP is pointing to and then incrementing SP.
     *
     * @return assembly code for pushing from D-register to address SP is pointing to and then incrementing SP
     */
    public static String pushD() {
        List<String> result = List.of(
                storeDToDereference("SP"),
                increment("SP")
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for poping to D-register from address SP is pointing to and then incrementing SP
     *
     * @return assembly code for poping to D-register from address SP is pointing to and then incrementing SP
     */
    public static String popD() {
        List<String> result = List.of(
                decrement("SP"),
                loadDereferenceToD("SP")
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for poping to A-register from address SP is pointing to and then incrementing SP
     *
     * @return assembly code for poping to A-register from address SP is pointing to and then incrementing SP
     */
    public static String popA() {
        List<String> result = List.of(
                decrement("SP"),
                loadDereferenceToA("SP")
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for pushing given value to address  SP is pointing to and incrementing SP
     *
     * @param value
     * @return assembly code for pushing given value to address  SP is pointing to and incrementing SP
     */
    public static String pushValue(String value) {
        List<String> result = List.of(
                ASM.moveValueToD(value),
                ASM.pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for push operation from given referenced base and index to SP referenced address.
     * SP is incremented.
     *
     * @param reference
     * @param index
     * @return assembly code for push operation from given referenced base and index to SP referenced address.
     */
    public static String pushFromPointedSegment(String reference, String index) {
        List<String> result = List.of(
                storeEffectiveAddress(reference, index),
                ASM.loadDereferenceToD(effectiveAddressRegister),
                ASM.pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for pop operation from SP referenced address to given referenced base and index.
     * SP is decremented.
     *
     * @param reference
     * @param index
     * @return assembly code for pop operation from SP referenced address to given referenced base and index.
     */
    public static String popToPointedSegment(String reference, String index) {
        List<String> result = List.of(
                storeEffectiveAddress(reference, index),
                ASM.popD(),
                ASM.storeDToDereference(effectiveAddressRegister)
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for storing effective address from given base address and index.
     *
     * @param address
     * @param index
     * @return assembly code for storing effective address from given base address and index.
     */
    private static String storeEffectiveAddress(String address, String index) {
        List<String> result = List.of(
                ASM.loadMemoryToD(address),
                ASM.moveValueToA(index),
                ASM.addAToD(),
                ASM.storeDToMemory(effectiveAddressRegister)
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for push operation from given memory address to SP referenced address.
     * SP is incremented.
     *
     * @param address
     * @return
     */
    public static String pushFromMemory(String address) {
        List<String> result = List.of(
                ASM.loadMemoryToD(address),
                ASM.pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for pop operation from SP referenced address to given memory address.
     * SP is decremented.
     *
     * @param address
     * @return assembly code for pop operation from SP referenced address to given memory address.
     */
    public static String popToMemory(String address) {
        List<String> result = List.of(
                ASM.popD(),
                ASM.storeDToMemory(address)
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for push operation from temp segment base address plus index to SP referenced address.
     * SP is incremented.
     *
     * @param index
     * @return assembly code for push operation from temp segment base address plus index to SP referenced address.
     */
    public static String pushFromTempSegment(String index) {
        String address = String.valueOf(tempBaseAddress + Integer.parseInt(index));
        List<String> result = List.of(
                ASM.loadMemoryToD(address),
                ASM.pushD()
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for pop operation from SP referenced address to temp segment base address plus index.
     * SP is decremented.
     *
     * @param index
     * @return assembly code for pop operation from SP referenced address to temp segment base address plus index.
     */
    public static String popToTempSegment(String index) {
        String address = String.valueOf(tempBaseAddress + Integer.parseInt(index));
        List<String> result = List.of(
                ASM.popD(),
                ASM.storeDToMemory(address)
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for incrementing memory value at given address by 1
     *
     * @param address
     * @return assembly code for incrementing memory value at given address by 1
     */
    public static String increment(String address) {
        List<String> result = List.of(
                moveValueToA(address),
                "M=M+1"
        );
        return String.join(System.lineSeparator(), result);
    }

    /**
     * Returns assembly code for decrementing  memory value at given address by 1
     *
     * @param address
     * @return assembly code for decrementing  memory value at given address by 1
     */
    public static String decrement(String address) {
        List<String> result = List.of(
                moveValueToA(address),
                "M=M-1"
        );
        return String.join(System.lineSeparator(), result);
    }
}
