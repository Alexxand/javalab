/**
 * Created by Alexandr on 05.12.15.
 */
public class ExpressionBuilder {
    private String expressionString;
    private int offset = 0;

    public ExpressionBuilder(String expression){
        expressionString = expression;
    }


    /*S0 : S1 [ "+" или "-" S1] *
      S1: S2 [ "*" или "/" S2] *
      S2: num | var | (S0) */

    public Expression build() throws UnexpectedSymbolException{
        expressionString = removeAllSpaces(expressionString);
        return buildS0();
    }

    private Expression buildS0() throws UnexpectedSymbolException{
        boolean isInBrackets = false;
        if (offset > 0 && expressionString.charAt(offset - 1) == '(') {
            isInBrackets = true;
        }
        Expression a = buildS1();
        while (offset < expressionString.length()){
            if (expressionString.charAt(offset) == ')'){
                if (!isInBrackets)
                    throw new UnexpectedSymbolException(offset + 1);
                else
                    return a;
            }

            switch (expressionString.charAt(offset)) {
                case '+':
                    offset++;
                    if (offset == expressionString.length())
                        throw new UnexpectedSymbolException(offset + 1);
                    Expression b = buildS1();
                    a = new Expression.Sum(a,b);
                    break;
                case '-':
                    offset++;
                    if (offset == expressionString.length())
                        throw new UnexpectedSymbolException(offset + 1);
                    b = buildS1();
                    a = new Expression.Diff(a,b);
                    break;
                default:
                    throw new UnexpectedSymbolException(offset + 1);
            }
        }
        if (isInBrackets)
          throw new UnexpectedSymbolException(offset + 1);
        else
            return a;
    }

    private Expression buildS1() throws UnexpectedSymbolException{
        Expression a = buildS2();
        while (offset < expressionString.length()){
            char ch = expressionString.charAt(offset);
            if (ch == '+' || ch == '-' || ch == ')'){
                return a;
            }



            switch (expressionString.charAt(offset)) {
                case '*':
                    offset++;
                    if (offset == expressionString.length())
                        throw new UnexpectedSymbolException(offset + 1);
                    Expression b = buildS2();
                    a = new Expression.Mult(a,b);
                    break;
                case '/':
                    offset++;
                    if (offset == expressionString.length())
                        throw new UnexpectedSymbolException(offset + 1);
                    b = buildS2();
                    a = new Expression.Quot(a,b);
                    break;
                default:
                    throw new UnexpectedSymbolException(offset + 1);
            }
        }
        return a;
    }

    private Expression buildS2() throws UnexpectedSymbolException{
        switch(expressionString.charAt(offset)){
            case '(':
                offset++;
                Expression expr = buildS0();
                offset++;
                return expr;
            case 'x':
                offset++;
                return new Expression.Var();
            default:
                int endOfNumber = findEndOfNumber(expressionString,offset);
                if(endOfNumber == offset)
                    throw new UnexpectedSymbolException(offset + 1);
                int oldOffset = offset;
                offset = endOfNumber;
                return new Expression.Num(new Double(expressionString.substring(oldOffset,endOfNumber)));
        }
    }

    private static String removeAllSpaces(String s){
        char sourceCharArr[] = s.toCharArray();
        char newCharArr[] = new char[s.length()];
        int indexNew = 0;
        for (int indexSource = 0;indexSource < s.length(); indexSource++) {
            if (sourceCharArr[indexSource] != ' ') {
                newCharArr[indexNew] = sourceCharArr[indexSource];
                indexNew++;
            }
        }
        return new String(newCharArr,0,indexNew);
    }

    private static int findEndOfNumber(String s,int offset)
    {
        boolean point_flag = false;
        int i;
        for(i = offset;i < s.length();++i){
            if (s.charAt(i) == '.'){
                if (point_flag)
                    break;
                point_flag = true;
            }
            else if (!isDigit(s,i))
                break;
        }
        return i;
    }

    private static boolean isDigit(String s, int index){
        char ch = s.charAt(index);
        return ch >= '0' && ch <= '9';
    }
}
