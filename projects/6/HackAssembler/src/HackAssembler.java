import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HackAssembler {
    public static void main(String[] args) {
        validateArgs(args);
        String inputFileName = args[0];
        List<String> lines = readFile(inputFileName);
        String binary = translateToBinary(lines);
        String outputFileName = buildOutputName(inputFileName);
        saveFile(outputFileName, binary);
    }


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
    private static List<String> readFile(String filePath) {
        try {
            Path file = Paths.get(filePath);
            return Files.readAllLines(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String translateToBinary(List<String> lines) {
        ASMProgram asmProgram = new ASMProgram(lines);
        return asmProgram.toBinary();
    }

    private static String buildOutputName(String inputFileName) {
        String inputFileNameWithoutExtension = inputFileName.substring(0, inputFileName.indexOf("."));
        return inputFileNameWithoutExtension + ".hack";
    }

    private static void saveFile(String fileName, String content) {
        try {
            Files.writeString(Paths.get(fileName), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    
    
}