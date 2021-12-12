package token;

import visitor.CalcException;
import visitor.ParserException;
import visitor.TokenVisitor;

public class BraceToken implements Token {

    Brace brace;

    public BraceToken(Brace brace) {
        this.brace = brace;
    }

    public Brace getBrace() {
        return brace;
    }

    @Override
    public void accept(TokenVisitor tokenVisitor) throws ParserException, CalcException {
        tokenVisitor.visit(this);
    }
}
