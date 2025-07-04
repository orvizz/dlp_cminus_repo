package ast.types;

import ast.Type;
import semantic.Visitor;

public class Char extends AbstractType {

    @Override
    public String toString() {
        return "Char";
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
        return v.visit(this, param);
    }

    public String typeExpression() {
        return "Char";
    }

    @Override
    public Type arithmetic(Type type, int line, int column) {
        if(type instanceof ErrorType) return type;
        else if(type instanceof Char) return new Int();
        return new ErrorType(line, column, "Arithmetic operation not supported between " + typeExpression() + " and "
                + type.typeExpression());
    }

    @Override
    public Type modulus(Type type, int line, int column) {
        if(type instanceof ErrorType) return type;
        else if(type instanceof Char) return new Int();
        return new ErrorType(line, column, "Modulus operation not supported between "
                + typeExpression() + " and " + type.typeExpression());
    }

    @Override
    public Type relational(Type type, int line, int column) {
        if(type instanceof ErrorType) return type;
        else if(type instanceof Char) return new Int();
        return new ErrorType(line, column, "Relational operation not supported between " + typeExpression() + " and "
                + type.typeExpression());
    }

    @Override
    public Type cast(Type type, int line, int column) {
        if(type instanceof ErrorType) return type;
        else if(type instanceof Char) return this;
        else if(type instanceof Int) return new Int();
        else if(type instanceof DoubleType) return new DoubleType();
        return new ErrorType(line, column, "Cast operation not supported for " + typeExpression() + " into "
                + type.typeExpression());
    }

    @Override
    public Type unaryMinus(int line, int column) {
        return new Int();
    }

    @Override
    public Type assignment(Type type, int line, int column) {
        if(type instanceof ErrorType) return type;
        else if(type instanceof Char) return this;
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
    public Type mustBeReturnable(Type type, int line, int column) {
        if(type instanceof ErrorType) return type;
        else if(type instanceof Char) return this;
        return new ErrorType(line, column, "A type of " + typeExpression() + " cannot be returned in a type of "
                + type.typeExpression() + " function ");
    }

    public int getBytes() {
        return 1;
    }

    public char getSuffix(){
        return 'b';
    }

    @Override
    public String convertTo(Type type) {
        if(type instanceof DoubleType) return "b2i\ni2f";
        else if(type instanceof Int) return "b2i";
        else return "";
    }

    @Override
    public Type supertype(Type type) {
        return type;
    }

}
