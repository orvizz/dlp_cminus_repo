package ast.expressions;

import ast.Expression;
import semantic.Visitor;

public class IndexExpression extends AbstractExpression {

	final Expression firstExpr;
	final Expression secExpr;

	public IndexExpression(int line, int column, Expression firstExpr, Expression secExpr) {
		super(line, column);
		this.firstExpr = firstExpr;
		this.secExpr = secExpr;
	}

	@Override
	public String toString() {
		return "IndexExpression [variable=" + firstExpr.toString() + ", expression=" + secExpr.toString() + "]";
	}

	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
		return v.visit(this, param);
	}

	public Expression getFirstExpr() {
		return firstExpr;
	}

	public Expression getSecExpr() {
		return secExpr;
	}
}
