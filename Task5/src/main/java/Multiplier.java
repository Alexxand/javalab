/**
 * Created by Alexandr on 23.12.15.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Multiplier {
    private Matrix<Double> first;
    private Matrix<Double> second;
    private Matrix<Double> result;

    public class CouldNotMultiplyException extends Exception{
        CouldNotMultiplyException(){
            super("Number of first matrix columns must be equals to number of second matrix rows");
        }
    }


    Multiplier(Matrix<Double> first,Matrix<Double> second){
        this.first = first;
        this.second = second;
        result = new Matrix<>(first.getRowNumber(),second.getColNumber());
    }

    private void calculateOneCell(int rowIndex,int colIndex){
        Double res = 0.;
        for (int k = 0; k < first.getColNumber(); ++k ){
            res += first.get(rowIndex,k)*second.get(k,colIndex);
        }
        result.set(rowIndex,colIndex,res);
    }

    private class OneTask implements Callable<Long> {
        public Long call(){
            final long startTime = System.currentTimeMillis();
            int resRowIndex = 0;
            int resColIndex = 0;
            while(!result.isIndexOutOfBound(resRowIndex,resColIndex)){
                if(result.isCellHaveValue(resRowIndex,resColIndex)){
                    result.set(resRowIndex,resColIndex,0.);
                    calculateOneCell(resRowIndex,resColIndex);
                }
                if (resColIndex + 1 < result.getColNumber()){
                    ++resColIndex;
                }
                else{
                    ++resRowIndex;
                    resColIndex = 0;
                }
            }
            return System.currentTimeMillis() - startTime;
        }
    }

    public Long multiply(int threadNumber) throws CouldNotMultiplyException{
        if (first.getColNumber() != second.getRowNumber())
            throw new CouldNotMultiplyException();
        final ExecutorService executor = Executors.newFixedThreadPool(threadNumber);
        final List<Future<Long>> taskFutures = new ArrayList<>();
        for (int i = 0; i < threadNumber; i++) {
            final Future<Long> future = executor.submit(new OneTask());
            taskFutures.add(future);
        }
        Long multiplicationTime = 0L;
        for (final Future<Long> future: taskFutures) {
            try {
                final long executionTime = future.get();
                multiplicationTime += executionTime;
            } catch (InterruptedException | ExecutionException ignore) {
            }
        }
        executor.shutdown();
        return multiplicationTime;
    }

    Matrix<Double> returnMultiplicationResult(){
        for (int rowIndex = 0; rowIndex < result.getRowNumber(); ++rowIndex) {
            for (int colIndex = 0; colIndex < result.getColNumber(); ++colIndex) {
                if (!result.isCellHaveValue(rowIndex,colIndex))
                    return null;
            }
        }
        return result;
    }
}
