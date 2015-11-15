public class DoubleFromStringCreator extends FromStringCreator {
    public Object factoryMethod(String s) {
        return new Double(s);
    }
}
