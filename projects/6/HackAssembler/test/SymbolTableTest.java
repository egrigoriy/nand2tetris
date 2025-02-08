import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SymbolTableTest {
    @Test
    public void testPredefinedSymbols() {
        SymbolTable st = new SymbolTable();
        for (String symbol : PredefinedSymbols.MAP.keySet()) {
            assertTrue(st.contains(symbol));
            assertEquals(PredefinedSymbols.MAP.get(symbol), (Integer)st.getAddress(symbol));
        }
    }

    @Test
    public void testNotContainIfNotAdded() {
        SymbolTable st = new SymbolTable();
        assertFalse(st.contains("foo"));
    }

    @Test
    public void testContainIfAdded() {
        SymbolTable st = new SymbolTable();
        String symbol = "foo";
        int address = 1234;
        st.addEntry(symbol, address);
        assertTrue(st.contains(symbol));
        assertEquals(address, st.getAddress(symbol));
    }
}
