import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Represents a VM program
 */
public class VMProgram {

    /**
     * Translates all given files with their content to assembler.
     * If it's a single file, them assembler code of infinite loop is appended.
     * If more than one file, them assembler code for bootstrap is prepended.
     *
     * @param vmFiles
     * @return assembler code corresponding to given vm files
     */
    public static List<String> toASM(Map<String, List<String>> vmFiles) {
        List<String> result = translateAll(vmFiles);
        if (vmFiles.keySet().size() == 1) {
            result.addAll(endInfiniteLoop());
        } else {
            result.addAll(0, bootstrap());
        }
        return result;
    }

    /**
     * Translates the given map of file name and file content to assembler
     *
     * @param vmFiles
     * @return assembler code corresponding to given vm files
     */
    private static List<String> translateAll(Map<String, List<String>> vmFiles) {
        List<String> result = new ArrayList<>();
        for (String fileName : vmFiles.keySet()) {
            List<String> vmLines = vmFiles.get(fileName);
            result.addAll(VMParser.parse(fileName, vmLines));
        }
        return result;
    }

    /**
     * Provides assembly code for bootstrap
     * @return assembly code for bootstrap
     */
    private static List<String> bootstrap() {
        return ASMWriter.bootstrap();
    }

    /**
     * Provides assembly code for end of file infinite loop
     * @return assembly code for end of file infinite loop
     */
    private static List<String> endInfiniteLoop() {
        return ASMWriter.endInfiniteLoop();
    }
}
