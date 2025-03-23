import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class VMTranslator {
    public static void main(String[] args) {
        validateArgs(args);
        String inputFileName = args[0];
        List<String> vmLines = readFile(inputFileName);
        List<String> asmLines = translateToAssembly( vmLines);
        String outputFileName = buildOutputName(inputFileName);
        saveFile(outputFileName, asmLines);
    }

    /**
     * Validates the provided arguments
     * @param args
     */
    private static void validateArgs(String[] args) {
        if (args.length != 1) {
            System.out.println("Wrong number of arguments: Required 1, but provided " + args.length);
            System.out.println("Usage: java VMTranslator YourVMFile.vm");
            System.exit(0);
        }
        String fileName = args[0];
        String fileExtension = fileName.substring(fileName.indexOf(".") + 1);
        if (!fileExtension.equals("vm")) {
            System.out.println("Wrong file extension: Required .vm, but provided " + fileExtension);
            System.out.println("File extension must be .vm");
            System.exit(0);
        }
    }


    /**
     * Returns the content of a given file as list of strings, where each string is a line
     * @param filePath
     * @return file content as list of strings
     */
    private static List<String> readFile(String filePath) {
        try {
            Path file = Paths.get(filePath);
            return Files.readAllLines(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Translate provided lines from Hack machine language to binary format
     * @param lines
     * @return program translated to binary format
     */
    private static List<String> translateToAssembly(List<String> lines) {
        VMProgram vmProgram = new VMProgram(lines);
        return vmProgram.toASM();
    }

    /**
     * Returns the file name with extension ".asm"
     * @param inputFileName
     * @return the file name with extension .asm
     */
    private static String buildOutputName(String inputFileName) {
        String inputFileNameWithoutExtension = inputFileName.substring(0, inputFileName.indexOf("."));
        return inputFileNameWithoutExtension + ".asm";
    }

    /**
     * Saves the provided list of lines to a file with given name
     * @param fileName
     * @param lines
     */
    private static void saveFile(String fileName, List<String> lines) {
        try {
            String content = String.join("\n", lines);
            Files.writeString(Paths.get(fileName), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
