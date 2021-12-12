import state.Tokenizer;
import state.TokenizerException;
import token.Token;
import visitor.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main (String [] args) throws TokenizerException, ParserException, CalcException {
        Scanner scan = new Scanner(System.in);
        String expr = scan.nextLine();
        Tokenizer tokenizer = new Tokenizer(expr);
        List<Token> ans = tokenizer.parseTokens();

        ParserVisitor parserVisitor = new ParserVisitor();
        for (Token token : ans) {
            token.accept(parserVisitor);
        }
        parserVisitor.finishPolishOrdering();
        ans = parserVisitor.getPolishOrder();

        PrintVisitor printVisitor = new PrintVisitor(System.out);
        for (Token token : ans) {
            token.accept(printVisitor);
        }
        printVisitor.flush();

        CalcVisitor calcVisitor = new CalcVisitor();
        for (Token token : ans) {
            token.accept(calcVisitor);
        }
        calcVisitor.finishCalc();
        System.out.println(calcVisitor.getResult());
    }
}
