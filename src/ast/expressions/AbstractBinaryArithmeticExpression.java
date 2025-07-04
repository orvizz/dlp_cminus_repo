package ast.expressions;

import ast.Expression;

public abstract class AbstractBinaryArithmeticExpression extends AbstractExpression {

    final String operator;
    final Expression leftExpr;
    final Expression rightExpr;

    public AbstractBinaryArithmeticExpression(int line, int column, String operator, Expression leftExpr, Expression rightExpr) {
        super(line, column);
        this.operator = operator;
        this.leftExpr = leftExpr;
        this.rightExpr = rightExpr;
    }

    public Expression getLeftExpr() {
        return leftExpr;
    }

    public Expression getRightExpr() {
        return rightExpr;
    }

    public String getOperator() {
        return operator;
    }
}
