package ast.factories;

import ast.Expression;
import ast.expressions.AbstractBinaryArithmeticExpression;
import ast.expressions.ArithmeticExpression;
import ast.expressions.ModExpression;

public class ExpressionFactory {

    public static AbstractBinaryArithmeticExpression createArithmeticExpression(int line, int column, String operator, Expression leftExpr, Expression rightExpr) {
        if(operator.equals("%"))
            return new ModExpression(line, column, operator, leftExpr, rightExpr);
        else
            return new ArithmeticExpression(line, column, operator, leftExpr, rightExpr);
    }
}
