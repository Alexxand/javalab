import java.io.IOException;

/**
 * Created by Alexandr on 23.12.15.
 */
public class MatrixMultiplier {
    public static void main(String[] args){
        String firstFileName = args[0];
        String secondFileName = args[1];
        String resultFileName = args[2];
        int threadNumber = new Integer(args[3]);
        try {
            Matrix<Double> first = new Matrix<>(firstFileName, new DoubleFromStringCreator());
            Matrix<Double> second = new Matrix<>(secondFileName, new DoubleFromStringCreator());
            Multiplier multiplier = new Multiplier(first,second);
            final Long executionTime = multiplier.multiply(threadNumber);
            multiplier.returnMultiplicationResult().print(resultFileName);
            System.out.println("Execution time for " + threadNumber + " threads: " + executionTime + " ms");

        }catch(IOException | CouldNotMultiplyException | IncorrectMatrixFileException e){
            System.out.println(e.getMessage());
        }
    }
}
