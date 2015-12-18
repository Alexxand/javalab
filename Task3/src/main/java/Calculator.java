/**
 * Created by Alexandr on 16.12.15.
 */
public class Calculator {
    public static void main(String[] args){
        if (args.length < 2) {
            System.out.println("Too few arguments");
        }
        else {
            String expression = args[0];
            double variableValue = new Double(args[1]);
            try {
                ExpressionBuilder builder = new ExpressionBuilder(expression);
                Expression expr = builder.build();
                double result = expr.solve(variableValue);
                System.out.println(result);
            }
            catch (UnexpectedSymbolException | ArithmeticException e){
                System.out.println(e.getMessage());
            }
        }
    }

}
