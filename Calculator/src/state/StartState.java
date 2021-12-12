package state;

import token.Brace;
import token.BraceToken;
import token.Operation;
import token.OperationToken;

public class StartState extends State {

    public StartState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public void nextState() {
        if (tokenizer.isEnd()) {
            tokenizer.setState(new EndState(tokenizer));
        } else {
            char ch = tokenizer.safeGetChar();
            if (Character.isDigit(ch)) {
                tokenizer.appendToAcc(ch);
                tokenizer.setState(new NumberState(tokenizer));
            } else if (ch == '+') {
                tokenizer.addToken(new OperationToken(Operation.PLUS));
            } else if (ch == '-') {
                tokenizer.addToken(new OperationToken(Operation.MINUS));
            } else if (ch == '*') {
                tokenizer.addToken(new OperationToken(Operation.MUL));
            } else if (ch == '/') {
                tokenizer.addToken(new OperationToken(Operation.DIV));
            } else if (ch == '(') {
                tokenizer.addToken(new BraceToken(Brace.LEFT));
            } else if (ch == ')') {
                tokenizer.addToken(new BraceToken(Brace.RIGHT));
            } else if (!Character.isWhitespace(ch)) {
                tokenizer.setState(new ErrorState(tokenizer));
            }
            tokenizer.nextPos();
        }
    }
}
