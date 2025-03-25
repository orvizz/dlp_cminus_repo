package ast.expressions;

import ast.Expression;
import semantic.Visitor;

public class RelationalExpression extends AbstractExpression {

	final String operator;
	final Expression leftExpr;
	final Expression rightExpr;

	public RelationalExpression(int line, int column, String operator, Expression leftExpr, Expression rightExpr) {
		super(line, column);
		this.operator = operator;
		this.leftExpr = leftExpr;
		this.rightExpr = rightExpr;
	}

	@Override
	public String toString() {
		return "Relational Expression: " + leftExpr.toString() + " " + operator + " " + rightExpr.toString();
	}


	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
		return v.visit(this, param);
	}

	public Expression getLeftExpr() {
		return leftExpr;
	}

	public Expression getRightExpr() {
		return rightExpr;
	}
}
