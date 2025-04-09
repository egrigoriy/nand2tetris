import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * Represents a translator from VM stack-machine program to HACK assembly program
 * Usage: java VMTranslator source
 * where source is either a path to a VM file ("xxx.vm") or a folder containing one or more VM files
 */
public class VMTranslator {
    public static void main(String[] args) {
        validateArgs(args);
        Path providedPath = Paths.get(args[0]);
        Map<String, List<String>> vmFiles = readVMFiles(providedPath);
        List<String> asmLines = VMProgram.toASM(vmFiles);
        Path outputFilePath = buildOutputFilePath(providedPath);
        saveFile(outputFilePath, asmLines);
    }

    /**
     * Validates the provided arguments
     *
     * @param args
     */
    private static void validateArgs(String[] args) {
        if (args.length != 1) {
            System.out.println("Wrong number of arguments: Required 1, but provided " + args.length);
            printUsage();
            System.exit(0);
        }
        Path inputPath = Path.of(args[0]);
        if (Files.isRegularFile(inputPath)) {
            if (!inputPath.getFileName().toString().endsWith(".vm")) {
                System.out.println("Wrong file extension: Required \"vm\"");
                System.out.println("Provided path was: " + inputPath);
                System.out.println("File path must be in the form of \"Path\\to\\xxx.vm\"");
                printUsage();
                System.exit(0);
            }
        }
        if (Files.isDirectory(inputPath)) {
            try (Stream<Path> paths = Files.walk(inputPath)) {
                int vmFilesCount = (int) paths.filter(path -> path.getFileName().toString().endsWith(".vm")).count();
                if (vmFilesCount == 0) {
                    System.out.println("No VM files within provided directory.");
                    printUsage();
                    System.exit(0);
                }
            } catch (IOException e) {
                printUsage();
                System.exit(0);
            }
        }
    }

    /**
     * Prints to the console usage information
     */
    private static void printUsage() {
        System.out.println("Usage: java VMTranslator source" + System.lineSeparator());
        System.out.println("where source is either a path to a VM file (\"xxx.vm\") or a folder containing one or more VM files");
    }

    /**
     * Reads VM files from a given path and returns a map with file name and corresponding content
     * If path is a file, not directory, then the result is a single entry map.
     *
     * @param inputPath
     * @return a map of file name and corresponding content
     */
    private static Map<String, List<String>> readVMFiles(Path inputPath) {
        if (Files.isRegularFile(inputPath)) {
            return readVMFile(inputPath);
        }
        return readVMFilesInDir(inputPath);
    }

    /**
     * Reads VM file from a given path and returns a map with file name and corresponding content
     *
     * @param inputPath
     * @return a map of file name and corresponding content
     */
    private static Map<String, List<String>> readVMFile(Path inputPath) {
        try {
            List<String> vmLines = Files.readAllLines(inputPath);
            String fileName = getFileNameWithoutExtension(inputPath);
            return Map.of(fileName, vmLines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads VM files from a given path and returns a map with file name and corresponding content
     *
     * @param inputDirPath
     * @return a map of file name and corresponding content
     */
    public static Map<String, List<String>> readVMFilesInDir(Path inputDirPath) {
        Map<String, List<String>> result = new HashMap<>();
        try (Stream<Path> paths = Files.walk(inputDirPath)) {
            Stream<Path> vmFilesPaths = paths.filter(path -> path.getFileName().toString().endsWith(".vm"));
            vmFilesPaths.forEach((vmFilePath) -> {
                result.putAll(readVMFile(vmFilePath));
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Returns the ASM file path, i.e. path with file extension ".asm"
     *
     * @return the ASM file path, i.e. path with file extension ".asm"
     */
    private static Path buildOutputFilePath(Path inputFilePath) {
        if (Files.isRegularFile(inputFilePath)) {
            String outputFileName = getFileNameWithoutExtension(inputFilePath) + ".asm";
            return Path.of(inputFilePath.getParent().toString(), outputFileName);
        }
        return Path.of(inputFilePath.toString(), inputFilePath + ".asm");
    }

    /**
     * Returns the file's name from the given path without the extension
     *
     * @param path
     * @return
     */
    private static String getFileNameWithoutExtension(Path path) {
        String fullFileName = path.getFileName().toString();
        int dotIndex = fullFileName.lastIndexOf(".");
        if (dotIndex > 0) {
            return fullFileName.substring(0, dotIndex);
        }
        return fullFileName;
    }

    /**
     * Saves the provided list of lines to a file of given path
     *
     * @param filePath
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
