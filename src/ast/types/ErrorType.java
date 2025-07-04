package ast.types;

import ast.AbstractLocatable;
import ast.Locatable;
import ast.Type;
import ast.errorhandler.ErrorHandler;
import semantic.Visitor;

import java.util.List;

public class ErrorType extends AbstractType implements Locatable {

    final String message;
    final int line;
    final int column;

    public ErrorType(int line, int column, String message) {
        this.line = line;
        this.column = column;
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

    public String typeExpression() {
        return "ErrorType";
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public Type arithmetic(Type type, int line, int column) {
        return this;
    }

    @Override
    public Type modulus(Type type, int line, int column) {
        return this;
    }

    @Override
    public Type logical(Type type, int line, int column) {
        return this;
    }

    @Override
    public Type not(int line, int column) {
        return this;
    }

    @Override
    public Type unaryMinus(int line, int column) {
        return this;
    }

    @Override
    public Type relational(Type type, int line, int column) {
        return this;
    }

    @Override
    public Type cast(Type type, int line, int column) {
        return this;
    }

    @Override
    public Type squareBrackets(Type type, int line, int column) {
        return this;
    }

    @Override
    public Type dot(String name, int line, int column) {
        return this;
    }

    @Override
    public Type parenthesis(List<Type> types, int line, int column) {
        return this;
    }

    @Override
    public Type assignment(Type type, int line, int column) {
        return this;
    }

    @Override
    public Type mustBeReadable(int line, int column) {
        return this;
    }

    @Override
    public Type mustBeWritable(int line, int column) {
        return this;
    }

    @Override
    public Type mustBeCondition(int line, int column) {
        return this;
    }

    @Override
    public Type mustBeReturnable(Type type, int line, int column) {
        return this;
    }
}
