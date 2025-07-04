package ast.types;

import ast.Type;

import java.util.List;
import java.util.Optional;

public abstract class AbstractType implements Type {

    @Override
    public Type arithmetic(Type type, int line, int column) {
        return new ErrorType(line, column, "Arithmetic operation not supported for type "
                + typeExpression());
    }

    @Override
    public Type modulus(Type type, int line, int column) {
        return new ErrorType(line, column, "Modulus operation not supported for type "
                + typeExpression());
    }

    @Override
    public Type logical(Type type, int line, int column) {
        return new ErrorType(line, column, "Logical operation not supported for type "
                + typeExpression());
    }

    @Override
    public Type not(int line, int column) {
        return new ErrorType(line, column, "Not operation not supported for type "
                + typeExpression());
    }

    @Override
    public Type relational(Type type, int line, int column) {
        return new ErrorType(line, column, "Relational operation not supported for type "
                + typeExpression());
    }

    @Override
    public Type unaryMinus(int line, int column) {
        return new ErrorType(line, column, "Unary minus operation not supported for type "
                + typeExpression());
    }
    @Override
    public Type cast(Type type, int line, int column) {
        return new ErrorType(line, column, "Cast operation not supported for type "
                + typeExpression());
    }

    @Override
    public Type squareBrackets(Type type, int line, int column) {
        return new ErrorType(line, column, "Square brackets operation not supported for type "
                + typeExpression());
    }

    @Override
    public Type dot(String name, int line, int column) {
        return new ErrorType(line, column, "Dot operation not supported for type "
                + typeExpression());

    }

    @Override
    public Type parenthesis(List<Type> types, int line, int column) {
        return new ErrorType(line, column, "Parenthesis operation not supported for type " + typeExpression());
    }

    @Override
    public Type assignment(Type type, int line, int column) {
        return new ErrorType(line, column, "Assignment statement not supported for type " + typeExpression());
    }

    @Override
    public Type mustBeReadable(int line, int column) {
        return new ErrorType(line, column, "Type " + typeExpression() + " is not readable");
    }

    @Override
    public Type mustBeWritable(int line, int column) {
        return new ErrorType(line, column, "Type " + typeExpression() + " is not writable");
    }

    @Override
    public Type mustBeCondition(int line, int column) {
        return new ErrorType(line, column, "Type " + typeExpression() + " cannot be used as condition");
    }

    @Override
    public Type mustBeReturnable(Type type, int line, int column) {
        return new ErrorType(line, column, "Type " + typeExpression() + " cannot be used as return type");
    }

    public int getBytes() {
        return 0;
    }

    @Override
    public char getSuffix(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String convertTo(Type type){
        return "";
    }

    @Override
    public Type supertype(Type type) {
        return this;
    }
}
