public class MemorySegment {
    public static int getBase(String segment) {
        switch (segment) {
            case "local": return VMProgram.LCL;
            case "argument": return VMProgram.ARG;
            case "this": return VMProgram.THIS;
            case "that": return VMProgram.THAT;
            default:
                return 999;
        }
    }
}
