/**
 * Created by Alexandr on 05.12.15.
 */
public class ExpressionBuilder {
    private String expression_s;
    private int offset = 0;

    public ExpressionBuilder(String expression){
        expression_s = expression;
    }


    /*S0 : S1 [ "+" или "-" S1] *
      S1: S2 [ "*" или "/" S2] *
      S2: num | var | (S0) */

    public Expression build() throws UnexpectedSymbolException{
        expression_s = removeAllSpaces(expression_s);
        return build_S0();
    }

    private Expression build_S0() throws UnexpectedSymbolException{
        boolean isInBrackets = false;
        if (offset > 0 && expression_s.charAt(offset - 1) == '(') {
            isInBrackets = true;
        }
        Expression a = build_S1();
        while (offset < expression_s.length()){
            if (expression_s.charAt(offset) == ')'){
                if (!isInBrackets)
                    throw new UnexpectedSymbolException(offset + 1);
                else
                    return a;
            }

            switch (expression_s.charAt(offset)) {
                case '+':
                    offset++;
                    if (offset == expression_s.length())
                        throw new UnexpectedSymbolException(offset + 1);
                    Expression b = build_S1();
                    a = new Expression.Sum(a,b);
                    break;
                case '-':
                    offset++;
                    if (offset == expression_s.length())
                        throw new UnexpectedSymbolException(offset + 1);
                    b = build_S1();
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

    private Expression build_S1() throws UnexpectedSymbolException{
        Expression a = build_S2();
        while (offset < expression_s.length()){
            char ch = expression_s.charAt(offset);
            if (ch == '+' || ch == '-' || ch == ')'){
                return a;
            }



            switch (expression_s.charAt(offset)) {
                case '*':
                    offset++;
                    if (offset == expression_s.length())
                        throw new UnexpectedSymbolException(offset + 1);
                    Expression b = build_S2();
                    a = new Expression.Mult(a,b);
                    break;
                case '/':
                    offset++;
                    if (offset == expression_s.length())
                        throw new UnexpectedSymbolException(offset + 1);
                    b = build_S2();
                    a = new Expression.Quot(a,b);
                    break;
                default:
                    //unexpected symbol if not ')'
                    throw new UnexpectedSymbolException(offset + 1);
            }
        }
        return a;
    }

    private Expression build_S2() throws UnexpectedSymbolException{
        switch(expression_s.charAt(offset)){
            case '(':
                offset++;
                Expression expr =build_S0();
                offset++;
                return expr;
            case 'x':
                offset++;
                return new Expression.Var();
            default:
                int end_of_number = findEndOfNumber(expression_s,offset);
                if(end_of_number == offset)
                    throw new UnexpectedSymbolException(offset + 1);
                int old_offset = offset;
                offset = end_of_number;
                return new Expression.Num(new Double(expression_s.substring(old_offset,end_of_number)));
        }
    }

    private static String removeAllSpaces(String s){
        char source_char_arr[] = s.toCharArray();
        char new_char_arr[] = new char[s.length()];
        int i_new = 0;
        for (int i_source = 0;i_source < s.length(); i_source++) {
            if (source_char_arr[i_source] != ' ') {
                new_char_arr[i_new] = source_char_arr[i_source];
                i_new++;
            }
        }
        return new String(new_char_arr,0,i_new);
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
