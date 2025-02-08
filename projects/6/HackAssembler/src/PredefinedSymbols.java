import java.util.Map;

import static java.util.Map.entry;

public class PredefinedSymbols {
    public static final Map<String, Integer> MAP = Map.ofEntries(
            entry("R0", 0),
            entry("R1", 1),
            entry("R2", 2),
            entry("R3", 3),
            entry("R4", 4),
            entry("R5", 5),
            entry("R6", 6),
            entry("R7", 7),
            entry("R8", 8),
            entry("R9", 9),
            entry("R10", 10),
            entry("R11", 11),
            entry("R12", 12),
            entry("R13", 13),
            entry("R14", 14),
            entry("R15", 15),
            entry("SCREEN", 16384),
            entry("KBD", 24576),
            entry("SP", 0),
            entry("LCL", 1),
            entry("ARG", 2),
            entry("THIS", 3),
            entry("THAT", 4)
    ) ;
}
