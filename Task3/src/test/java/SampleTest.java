import org.junit.Test;

/**
 * Created by Alexandr on 09.12.15.
 */
public class SampleTest {
    @Test
    public void test(){
        try {
            ExpressionBuilder builder = new ExpressionBuilder("(2*2 + 2.6*x)*x+5*x");
            Expression expr = builder.build();
            double a = expr.solve(2);
        }
        catch(UnexpectedSymbolException e){
            System.out.println(e.getMessage());
        }
    }

}
