import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a VM parser
 */
public class VMParser {
    private static String currentFileName = "defaultFileName";
    private static String functionName = "defaultFunction";

    /**
     * Returns assembly code corresponding to given file name and content.
     * Each original vm line is included as comment just before corresponding assembly code
     *
     * @param fileName
     * @param vmLines
     * @return assembler code corresponding to given file name and content
     */
    public static List<String> parse(String fileName, List<String> vmLines) {
        currentFileName = fileName;
        return VMPreprocessor.process(vmLines).stream()
                .map((vmLine) -> {
                    String comment = "// " + vmLine + System.lineSeparator();
                    return comment + parse(vmLine);
                })
                .collect(Collectors.toList());
    }

    /**
     * Returns assembly code corresponding to given vm line
     *
     * @param vmLine
     * @return assembly code corresponding to given vm line
     */
    private static String parse(String vmLine) {
        String[] vmCommand = vmLine.split(" ");
        String operation = vmCommand[0];
        switch (operation) {
            case "push":
                return handlePush(vmCommand);
            case "pop":
                return handlePop(vmCommand);
            case "add":
                return handleAdd();
            case "sub":
                return handleSub();
            case "neg":
                return handleNeg();
            case "not":
                return handleNot();
            case "or":
                return handleOr();
            case "and":
                return handleAnd();
            case "eq":
                return handleEq();
            case "gt":
                return handleGt();
            case "lt":
                return handleLt();
            case "label":
                return handleLabel(vmCommand);
            case "goto":
                return handleGoto(vmCommand);
            case "if-goto":
                return handleIfGoto(vmCommand);
            case "function":
                return handleFunction(vmCommand);
            case "return":
                return handleReturn();
            case "call":
                return handleCall(vmCommand);
        }
        return null;
    }

    /**
     * Returns assembly code corresponding to push command
     *
     * @param command
     * @return assembly code corresponding to push command
     */
    private static String handlePush(String[] command) {
        String segment = command[1];
        String index = command[2];
        switch (segment) {
            case "constant":
                return ASMWriter.pushValue(index);
            case "local":
                return ASMWriter.pushLocal(index);
            case "argument":
                return ASMWriter.pushArgument(index);
            case "this":
                return ASMWriter.pushThis(index);
            case "that":
                return ASMWriter.pushThat(index);
            case "static":
                String address = currentFileName + "." + index;
                return ASMWriter.pushStatic(address);
            case "temp":
                return ASMWriter.pushTemp(index);
            case "pointer":
                return ASMWriter.pushPointer(index);
        }
        return null;
    }

    /**
     * Returns assembly code corresponding to pop command
     *
     * @param command
     * @return assembly code corresponding to pop command
     */
    private static String handlePop(String[] command) {
        String segment = command[1];
        String index = command[2];
        switch (segment) {
            case "local":
                return ASMWriter.popLocal(index);
            case "argument":
                return ASMWriter.popArgument(index);
            case "this":
                return ASMWriter.popThis(index);
            case "that":
                return ASMWriter.popThat(index);
            case "static":
                String address = currentFileName + "." + index;
                return ASMWriter.popStatic(address);
            case "temp":
                return ASMWriter.popTemp(index);
            case "pointer":
                return ASMWriter.popPointer(index);
        }
        return null;
    }

    /**
     * Returns assembly code corresponding to add operation
     *
     * @return assembly code corresponding to add operation
     */
    private static String handleAdd() {
        return ASMWriter.add();
    }

    /**
     * Returns assembly code corresponding to sub operation
     *
     * @return assembly code corresponding to sub operation
     */
    private static String handleSub() {
        return ASMWriter.sub();
    }

    /**
     * Returns assembly code corresponding to lt operation
     *
     * @return assembly code corresponding to lt operation
     */
    private static String handleLt() {
        return ASMWriter.lt();
    }

    /**
     * Returns assembly code corresponding to gt operation
     *
     * @return assembly code corresponding to gt operation
     */
    private static String handleGt() {
        return ASMWriter.gt();
    }

    /**
     * Returns assembly code corresponding to eq operation
     *
     * @return assembly code corresponding to eq operation
     */
    private static String handleEq() {
        return ASMWriter.eq();
    }

    /**
     * Returns assembly code corresponding to and operation
     *
     * @return assembly code corresponding to and operation
     */
    private static String handleAnd() {
        return ASMWriter.and();
    }

    /**
     * Returns assembly code corresponding to or operation
     *
     * @return assembly code corresponding to or operation
     */
    private static String handleOr() {
        return ASMWriter.or();
    }

    /**
     * Returns assembly code corresponding to not operation
     *
     * @return assembly code corresponding to not operation
     */
    private static String handleNot() {
        return ASMWriter.not();
    }

    /**
     * Returns assembly code corresponding to neg operation
     *
     * @return assembly code corresponding to neg operation
     */
    private static String handleNeg() {
        return ASMWriter.neg();
    }

    /**
     * Returns assembly code corresponding to call operation
     *
     * @param command
     * @return assembly code corresponding to call operation
     */
    private static String handleCall(String[] command) {
        String calleeName = command[1];
        String nArgs = command[2];
        return ASMWriter.call(calleeName, nArgs);
    }

    /**
     * Returns assembly code corresponding to return keyword
     *
     * @return assembly code corresponding to return keyword
     */
    private static String handleReturn() {
        return ASMWriter.ret();
    }

    /**
     * Returns assembly code corresponding to function operation
     *
     * @return assembly code corresponding to function operation
     */
    private static String handleFunction(String[] command) {
        functionName = command[1];
        String nVars = command[2];
        return ASMWriter.function(functionName, nVars);
    }

    /**
     * Returns assembly code corresponding to if-goto operation
     *
     * @param command
     * @return assembly code corresponding to if-goto operation
     */
    private static String handleIfGoto(String[] command) {
        String labelName = functionName + "$" + command[1];
        return ASMWriter.ifGoto(labelName);
    }

    /**
     * Returns assembly code corresponding to goto keyword
     *
     * @param command
     * @return assembly code corresponding to goto keyword
     */
    private static String handleGoto(String[] command) {
        String labelName = functionName + "$" + command[1];
        return ASMWriter.goTo(labelName);
    }

    /**
     * Returns assembly code corresponding to label operation
     *
     * @param command
     * @return assembly code corresponding to label operation
     */
    private static String handleLabel(String[] command) {
        String labelName = functionName + "$" + command[1];
        return ASMWriter.label(labelName);
    }
}
