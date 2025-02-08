import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

public class SymbolTable {

    HashMap<String, Integer> map = new HashMap<>();

    public SymbolTable() {
        map.putAll(PredefinedSymbols.MAP);
    }
    public void addEntry(String symbol, int address) {
        map.put(symbol, address);
    }
    public boolean contains(String symbol) {
        return map.containsKey(symbol);
    }
    public int getAddress(String symbol) {
        return map.get(symbol);
    }
}
