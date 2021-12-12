package token;

import visitor.CalcException;
import visitor.TokenVisitor;

public class OperationToken implements Token, Comparable<OperationToken> {

    private Operation operation;
    private int priority;

    public OperationToken(Operation operation) {
        this.operation = operation;
        if (operation == Operation.PLUS || operation == Operation.MINUS) {
            priority = 1;
        } else {
            priority = 2;
        }
    }

    public Operation getOperation() {
        return operation;
    }

    @Override
    public void accept(TokenVisitor tokenVisitor) throws CalcException {
        tokenVisitor.visit(this);
    }

    @Override
    public int compareTo(OperationToken op) {
        return Integer.compare(priority, op.priority);
    }
}
