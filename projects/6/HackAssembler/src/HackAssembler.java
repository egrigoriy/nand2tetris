import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Represents Hack assembler main program where the main operations are reading and writing of the files.
 */
public class HackAssembler {
    public static void main(String[] args) {
        validateArgs(args);
        String inputFileName = args[0];
        List<String> asmLines = readFile(inputFileName);
        List<String> binaryLines = translateToBinary(asmLines);
        String outputFileName = buildOutputName(inputFileName);
        saveFile(outputFileName, binaryLines);
    }

    /**
     * Validates the provided arguments
     * @param args
     */
    private static void validateArgs(String[] args) {
        if (args.length != 1) {
            System.out.println("Wrong number of arguments: Required 1, but provided " + args.length);
            System.out.println("Usage: java HackAssembler YourASMFile.asm");
            System.exit(0);
        }
        String fileName = args[0];
        String fileExtension = fileName.substring(fileName.indexOf(".") + 1);
        if (!fileExtension.equals("asm")) {
            System.out.println("Wrong file extension: Required .asm, but provided " + fileExtension);
            System.out.println("File extension must be .asm");
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
    private static List<String> translateToBinary(List<String> lines) {
        ASMProgram asmProgram = new ASMProgram(lines);
        return asmProgram.toBinary();
    }

    /**
     * Returns the file name with extension ".hack"
     * @param inputFileName
     * @return the file name with extension .hack
     */
    private static String buildOutputName(String inputFileName) {
        String inputFileNameWithoutExtension = inputFileName.substring(0, inputFileName.indexOf("."));
        return inputFileNameWithoutExtension + ".hack";
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