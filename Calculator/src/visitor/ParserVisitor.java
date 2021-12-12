package visitor;

import token.*;

import java.util.*;

public class ParserVisitor implements TokenVisitor {

    private List<Token> polishOrder = new ArrayList();
    private Stack<Token> stack = new Stack<>();

    @Override
    public void visit(NumberToken token) {
        polishOrder.add(token);
    }

    @Override
    public void visit(BraceToken token) throws ParserException {
        if (token.getBrace() == Brace.LEFT) {
            stack.add(token);
        } else {
            while (!(stack.isEmpty() || stack.lastElement() instanceof BraceToken)) {
                polishOrder.add(stack.pop());
            }
            if (stack.isEmpty()) {
                throw new ParserException("Missing left bracket");
            } else {
                stack.pop();
            }
        }
    }

    @Override
    public void visit(OperationToken token) {
        while (!stack.isEmpty() && (stack.lastElement() instanceof OperationToken && ((OperationToken) stack.lastElement()).compareTo(token) >= 0)) {
            polishOrder.add(stack.pop());
        }
        stack.add(token);
    }

    public void finishPolishOrdering() throws ParserException {
        while (!stack.isEmpty()) {
            if (stack.lastElement() instanceof OperationToken) {
                polishOrder.add(stack.pop());
            } else {
                throw new ParserException("Missing right bracket");
            }
        }
    }

    public List<Token> getPolishOrder() {
        return polishOrder;
    }
}
