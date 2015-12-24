/**
 * Created by Alexandr on 21.12.15.
 */
public class DoubleFromStringCreator implements FromStringCreator<Double> {
    public Double create(String s) {
        return new Double(s);
    }
}
