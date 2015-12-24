/**
 * Created by Alexandr on 24.12.15.
 */
public class IncorrectMatrixFileException extends Exception {
    IncorrectMatrixFileException(String fileName) {
        super("In file \"" + fileName + "\": Rows in matrix should has the same length");
    }
}
