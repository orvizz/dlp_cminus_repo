package ast.expressions;

import ast.Expression;
import semantic.Visitor;

public class ArithmeticExpression extends AbstractBinaryArithmeticExpression {

	public ArithmeticExpression(int line, int column, String operator, Expression leftExpr, Expression rightExpr) {
		super(line, column, operator, leftExpr, rightExpr);
	}


	@Override
	public String toString() {
		return "Arithmetic Expression: " + leftExpr.toString() + " " + operator + " " + rightExpr.toString();
	}

	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
		return v.visit(this, param);
	}
}
