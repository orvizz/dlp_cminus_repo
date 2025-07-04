package ast;

import java.util.List;

public interface Type extends ASTNode {

    String typeExpression();
    Type arithmetic(Type type, int line, int column);
    Type modulus(Type type, int line, int column);
    Type logical(Type type, int line, int column);
    Type not(int line, int column);
    Type relational(Type type, int line, int column);
    Type unaryMinus(int line, int column);
    Type cast(Type type, int line, int column);
    Type squareBrackets(Type type, int line, int column);
    Type dot(String name, int line, int column);
    Type parenthesis(List<Type> types, int line, int column);
    Type assignment(Type type, int line, int column);
    Type mustBeReadable(int line, int column);
    Type mustBeWritable(int line, int column);
    Type mustBeCondition(int line, int column);
    Type mustBeReturnable(Type type, int line, int column);
    int getBytes();
    char getSuffix();
    String convertTo(Type type);

    Type supertype(Type type);
}
