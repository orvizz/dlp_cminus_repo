package ast.expressions;

import ast.Expression;
import semantic.Visitor;

public class NotExpression extends AbstractExpression {

	final Expression expr;

	public NotExpression(int line, int column, Expression expr) {
		super(line, column);
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "NotExpression [expr=" + expr.toString() + "]";
	}

	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
		return v.visit(this, param);
	}

	public Expression getExpr() {
		return expr;
	}
}
