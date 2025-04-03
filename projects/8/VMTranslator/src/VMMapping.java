import java.util.Map;

public class VMMapping {
    public static final Map<String, String> address = java.util.Map.ofEntries(
            Map.entry("SP", "0"),
            Map.entry("local", "1"),
            Map.entry("argument", "2"),
            Map.entry("this", "3"),
            Map.entry("that", "4"),
            Map.entry("temp", "5"),
            Map.entry("static", "16")
            );

}
