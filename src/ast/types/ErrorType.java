package ast.types;

import ast.AbstractLocatable;
import ast.Type;
import ast.errorhandler.ErrorHandler;
import semantic.Visitor;

public class ErrorType extends AbstractLocatable implements Type {

    final String message;

    public ErrorType(int line, int column, String message) {
        super(line, column);
        this.message = message;
        ErrorHandler.getInstance().addError(this);
    }

    public String toString() {
        return "Error found in line " + getLine() + " and column " + getColumn() + ": " + message;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
        return v.visit(this,param);
    }
}
