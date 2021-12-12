package token;

import visitor.CalcException;
import visitor.ParserException;
import visitor.TokenVisitor;

public interface Token {
    void accept(TokenVisitor tokenVisitor) throws ParserException, CalcException;
}
