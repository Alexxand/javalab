/**
 * Created by Alexandr on 20.12.15.
 */
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

@RunWith(Parameterized.class)
public class CalcTest<T> {

    private String expression;
    private Double value;
    private String trueResult;
    public CalcTest(String expression, Double value, String trueResult) {
        this.expression = expression;
        this.value = value;
        this.trueResult = trueResult;
    }

    private static <E> List<Object[]> getData(String ExpressionsFileName, String ValuesFileName, String ResultsFileName) {
        List<String> ExpressionsList;
        List<Double> ValuesList;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(ExpressionsFileName)))) {
            ExpressionsList = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                ExpressionsList.add(line);
            }

        } catch (IOException e) {
            throw new RuntimeException("File with test expressions can't be opened", e);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(ValuesFileName)))) {
            ValuesList = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                ValuesList.add(new Double(line));
            }

        } catch (IOException e) {
            throw new RuntimeException("File with test values can't be opened", e);
        }
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(ResultsFileName)))){
        List<Object[]> result = new ArrayList<>();
        for (String expression: ExpressionsList){
            for(double value: ValuesList){
                String trueResultString = reader.readLine();
                Object[] resultElem = new Object[]{expression, value, trueResultString};
                result.add(resultElem);
            }
        }
        return result;
        }
        catch(IOException e){
            throw new RuntimeException("File with test values can't be opened", e);
        }
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        List<Object[]> testData = new ArrayList<>();
        testData.addAll(getData("expressions.txt","values.txt","results.txt"));
        return testData;
    }

    @Test
    public void Test() {
        try {
            ExpressionBuilder builder = new ExpressionBuilder(expression);
            Expression expr = builder.build();
            double result = expr.solve(value);
            Assert.assertEquals("Incorrect result", new Double(result), new Double(trueResult));
        }
        catch(UnexpectedSymbolException | ArithmeticException e){
            Assert.assertEquals("Incorrect result", e.getMessage(), trueResult);
        }
    }

}

