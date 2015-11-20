public class DoubleFromStringCreator implements FromStringCreator<Double> {
    public Double create(String s) {
        return new Double(s);
    }
}
