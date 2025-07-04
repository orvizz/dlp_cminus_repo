package ast.types;

import ast.Type;
import semantic.Visitor;

public class Int extends AbstractType {

    @Override
    public String toString() {
        return "Int";
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
        return v.visit(this,param);
    }

    public String typeExpression() {
        return "Int";
    }

    @Override
    public Type arithmetic(Type type, int line, int column) {
        if(type instanceof ErrorType) return type;
        else if(type instanceof Int) return this;
        return new ErrorType(line, column, "Arithmetic operation not supported between " + typeExpression() + " and "
                + type.typeExpression());
    }

    @Override
    public Type modulus(Type type, int line, int column) {
        if(type instanceof ErrorType) return type;
        else if(type instanceof Int) return this;
        return new ErrorType(line, column, "Modulus operation not supported between "
                + typeExpression() + " and " + type.typeExpression());
    }

    @Override
    public Type logical(Type type, int line, int column) {
        if(type instanceof ErrorType) return type;
        else if(type instanceof Int) return this;
        return new ErrorType(line, column, "Logical operation not supported between "
                + typeExpression() + " and " + type.typeExpression());
    }

    @Override
    public Type not(int line, int column) {
        return this;
    }

    @Override
    public Type relational(Type type, int line, int column) {
        if(type instanceof ErrorType) return type;
        else if(type instanceof Int) return this;
        return new ErrorType(line, column, "Relational operation not supported between " + typeExpression() + " and "
                + type.typeExpression());
    }

    @Override
    public Type cast(Type type, int line, int column) {
        if(type instanceof ErrorType) return type;
        else if(type instanceof Char) return new Char();
        else if(type instanceof Int) return this;
        else if(type instanceof DoubleType) return new DoubleType();
        return new ErrorType(line, column, "Cast operation not supported for " + typeExpression() + " into "
                + type.typeExpression());
    }

    @Override
    public Type unaryMinus(int line, int column) {
        return this;
    }

    @Override
    public Type assignment(Type type, int line, int column) {
        if(type instanceof ErrorType) return type;
        else if(type instanceof Int) return this;
        return new ErrorType(line, column, "Assignment operation not supported for " + type.typeExpression()
                + " into " + typeExpression());
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
        if(type instanceof ErrorType) return type;
        else if(type instanceof Int) return this;
        return new ErrorType(line, column, "A type of " + typeExpression() + " cannot be returned in a type of "
                + type.typeExpression() + " function ");
    }

    public int getBytes() {
        return 2;
    }

    public char getSuffix(){
        return 'i';
    }

    @Override
    public String convertTo(Type type) {
        if(type instanceof DoubleType) return "i2f";
        if(type instanceof Char) return "i2b";
        else return "";
    }

    @Override
    public Type supertype(Type type) {
        if(type instanceof DoubleType) return type;
        return this;
    }
}
