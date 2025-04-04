import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VMTranslator {
    public static void main(String[] args) {
        validateArgs(args);
        Path providedPath = Paths.get(args[0]);
        if (Files.isRegularFile(providedPath)) {
            List<String> vmLines = readFile(providedPath);
            List<String> asmLines = translateToAssembly(vmLines);
            String outputFileName = buildOutputName(providedPath);
            saveFile(Paths.get(outputFileName), asmLines);
        }
        if (Files.isDirectory(providedPath)) {
            List<String> allLines = new ArrayList<>();
//            allLines.addAll(translateToAssembly(List.of("call Sys.init 0")));
//            allLines.add("@256");
//            allLines.add("D=A");
//            allLines.add("@0");
//            allLines.add("M=D");
//            allLines.addAll(translateToAssembly(List.of("call Sys.init 0")));
            try (Stream<Path> stream = Files.walk(providedPath)) {
                allLines.addAll(stream
                        .filter(path -> path.getFileName().toString().endsWith(".vm"))
                        .map(path -> {
                            List<String> vmLines = readFile(path);
                            List<String> asm = translateToAssembly(vmLines);
                            return String.join(System.lineSeparator(), asm);
                        })
                        .collect(Collectors.toList()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            saveFile( Paths.get(providedPath.toString(), args[0] + ".asm"), allLines);
        }
    }

    /**
     * Validates the provided arguments
     *
     * @param args
     */
    private static void validateArgs(String[] args) {
        if (args.length != 1) {
            System.out.println("Wrong number of arguments: Required 1, but provided " + args.length);
            System.out.println("Usage: java VMTranslator YourVMFile.vm");
            System.exit(0);
        }
        File dirOrFile = new File(args[0]);
        if (dirOrFile.isFile()) {
            String fileName = args[0];
            String fileExtension = fileName.substring(fileName.indexOf(".") + 1);
            if (!fileExtension.equals("vm")) {
                System.out.println("Wrong file extension: Required .vm, but provided " + fileExtension);
                System.out.println("File extension must be .vm");
                System.exit(0);
            }
        }
    }


    /**
     * Returns the content of a given file as list of strings, where each string is a line
     *
     * @param filePath
     * @return file content as list of strings
     */
    private static List<String> readFile(Path filePath) {
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Translate provided lines from Hack machine language to binary format
     *
     * @param lines
     * @return program translated to binary format
     */
    private static List<String> translateToAssembly(List<String> lines) {
        return VMProgram.toASM(lines);
    }

    /**
     * Returns the file name with extension ".asm"
     *
     * @return the file name with extension .asm
     */
    private static String buildOutputName(Path inputFilePath) {
        String fileName = inputFilePath.getFileName().toString();
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0) {
            return  fileName.substring(0, dotIndex) + ".asm";
        }
        return "";
    }

    /**
     * Saves the provided list of lines to a file with given name
     *
     * @param fileName
     * @param lines
     */
    private static void saveFile(Path filePath, List<String> lines) {
        try {
            String content = String.join("\n", lines);
            Files.writeString(filePath, content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
