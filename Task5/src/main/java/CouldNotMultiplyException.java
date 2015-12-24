/**
 * Created by Alexandr on 24.12.15.
 */
public class CouldNotMultiplyException extends Exception {
    CouldNotMultiplyException() {
        super("Number of first matrix columns must be equals to number of second matrix rows");
    }
}
