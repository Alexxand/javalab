import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Alexandr on 24.12.15.
 */
@RunWith(Parameterized.class)
public class IncorrectExpressionsTest {
    private String expression;

    public IncorrectExpressionsTest(String expression) {
        this.expression = expression;
    }

    private static List<Object[]> getData(String ExpressionsFileName) {
        List<Object[]> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(ExpressionsFileName)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.add(new Object[]{line});
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException("File with test expressions can't be opened", e);
        }
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        List<Object[]> testData = new ArrayList<>();
        testData.addAll(getData("incorrect expressions.txt"));
        return testData;
    }

    @Test(expected = UnexpectedSymbolException.class)
    public void UnexpectedSymbolExceptionTest() throws UnexpectedSymbolException {
        ExpressionBuilder builder = new ExpressionBuilder(expression);
        builder.build();
    }
}
