package visitor;

import token.BraceToken;
import token.NumberToken;
import token.OperationToken;

public interface TokenVisitor {
    void visit(NumberToken token);
    void visit(BraceToken token) throws ParserException, CalcException;
    void visit(OperationToken token) throws CalcException;
}
