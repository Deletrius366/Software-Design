package visitor;

import token.*;

import java.util.Stack;

public class CalcVisitor implements TokenVisitor {

    private Stack<Long> stack = new Stack<>();

    @Override
    public void visit(NumberToken token) {
        stack.add(token.getNumber());
    }

    @Override
    public void visit(BraceToken token) throws CalcException {
        throw new CalcException("No bracket should be in polish notation");
    }

    @Override
    public void visit(OperationToken token) throws CalcException {
        if (stack.size() < 2) {
            throw new CalcException("Cannot calculate operation, not enough operands");
        }
        long second = stack.pop();
        long first = stack.pop();
        if (token.getOperation() == Operation.MINUS && second == 0) {
            throw new CalcException("Division by zero");
        }
        switch (token.getOperation()) {
            case PLUS -> stack.add(first + second);
            case MINUS -> stack.add(first - second);
            case MUL -> stack.add(first * second);
            case DIV -> stack.add(first / second);
        }
    }

    public void finishCalc() throws CalcException {
        if (stack.size() != 1) {
            throw new CalcException("Polish notation is not valid");
        }
    }

    public long getResult() {
        return stack.lastElement();
    }
}
