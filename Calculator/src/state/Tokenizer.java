package state;

import token.Token;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private String expr;
    private int pos;
    private StringBuilder acc = new StringBuilder();
    private State state = new StartState(this);
    private List<Token> tokens = new ArrayList<>();

    public Tokenizer(String expr) {
        this.expr = expr;
    }

    public boolean isEnd() {
        return pos >= expr.length();
    }

    public char safeGetChar() {
        return pos < expr.length() ? expr.charAt(pos) : 0;
    }

    public void nextPos() {
        pos++;
    }

    public List<Token> parseTokens() throws TokenizerException {
        while (!(state instanceof EndState || state instanceof ErrorState)) {
            state.nextState();
        }
        if (state instanceof ErrorState) {
            throw new TokenizerException("Failed to parse token near position " + pos);
        }
        return tokens;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void appendToAcc(char ch) {
        acc.append(ch);
    }

    public String pollAcc() {
        String ans = acc.toString();
        acc = new StringBuilder();
        return ans;
    }

    public void addToken (Token token) {
        tokens.add(token);
    }
}
