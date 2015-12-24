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
    private Long executionTime;


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
                if(!result.isCellHaveValue(resRowIndex,resColIndex)){
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

/*        final List<Future<Long>> taskFutures = new ArrayList<>();
        final ThreadFactory factory = Executors.defaultThreadFactory();
        for (int i = 0; i < threadNumber; i++) {
            Callable<Long> task = new OneTask();
            taskFutures.add(new FutureTask<>(task));
            factory.newThread(task);
        }*/

        final List<Future<Long>> taskFutures = new ArrayList<>();
        final ExecutorService executor = Executors.newFixedThreadPool(threadNumber);
        for (int i = 0; i < threadNumber; i++) {
            final Future<Long> future = executor.submit(new OneTask());
            taskFutures.add(future);
        }



        /*try{
            executor.awaitTermination(7,TimeUnit.SECONDS);
        }catch(InterruptedException ignore){
        }*/

        Long multiplicationTime = 0L;
        try {
            for (Future<Long> future: taskFutures) {
                final Long executionTime = future.get();
                multiplicationTime += executionTime;
            }
        } catch (InterruptedException | ExecutionException ignore) {
        }
        executor.shutdown();
        return multiplicationTime;
    }

    Matrix<Double> returnMultiplicationResult(){
        return result;
    }
}
