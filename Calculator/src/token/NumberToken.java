package token;

import visitor.TokenVisitor;

public class NumberToken implements Token {

    long number;

    public NumberToken(long number) {
        this.number = number;
    }

    @Override
    public void accept(TokenVisitor tokenVisitor) {
        tokenVisitor.visit(this);
    }

    public long getNumber() {
        return number;
    }
}
