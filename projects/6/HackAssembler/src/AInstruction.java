import java.util.Objects;

public class AInstruction implements Instruction {
    private String address;
    public AInstruction(String address) {
        this.address = address;
    }

    @Override
    public String toBinary() {
        return "0" + addressToBinary();
    }

    private String addressToBinary() {
        String result = "";
        int toConvert = Integer.parseInt(address);
        for (int i=0; i<15; i++) {
            int reminder = toConvert % 2;
            result = reminder + result;
            toConvert = toConvert / 2;
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AInstruction that = (AInstruction) o;
        return Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
