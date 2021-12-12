package visitor;

import token.Brace;
import token.BraceToken;
import token.NumberToken;
import token.OperationToken;

import java.io.OutputStream;
import java.io.PrintWriter;

public class PrintVisitor implements TokenVisitor {

    PrintWriter printWriter;

    public PrintVisitor(OutputStream outputStream) {
        printWriter = new PrintWriter(outputStream);
    }

    @Override
    public void visit(NumberToken token) {
        printWriter.print(token.getNumber() + " ");
    }

    @Override
    public void visit(BraceToken token) {
        printWriter.print(token.getBrace() == Brace.LEFT ? "( " : ") ");
    }

    @Override
    public void visit(OperationToken token) {
        printWriter.print(switch (token.getOperation()) {
            case PLUS -> "+ ";
            case MINUS -> "- ";
            case MUL -> "* ";
            case DIV -> "/ ";
        });
    }

    public void flush() {
        printWriter.println();
        printWriter.flush();
    }
}
