/**
 * Created by Alexandr on 16.12.15.
 */
public class UnexpectedSymbolException extends Exception {
    final int unexpectedSymbolIndex;
    UnexpectedSymbolException(int index){
        super("Unexpected symbol in "+ Integer.toString(index) + " position");
        unexpectedSymbolIndex = index;
    }
}
