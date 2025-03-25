package ast.expressions;

import ast.Expression;
import semantic.Visitor;

public class ModExpression extends AbstractBinaryArithmeticExpression {

    public ModExpression(int line, int column, String operator, Expression leftExpr, Expression rightExpr) {
        super(line, column, operator, leftExpr, rightExpr);
    }

    @Override
    public String toString() {
        return "Mod Expression: " + leftExpr.toString() + " " + operator + " " + rightExpr.toString();
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
        return v.visit(this, param);
    }
}