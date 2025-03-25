package ast.expressions;

import ast.Expression;
import semantic.Visitor;

public class FieldAccessExpression extends AbstractExpression {

	final String fieldName;
	final Expression expr;

	public FieldAccessExpression(int line, int column, String fieldName, Expression expr) {
		super(line, column);
		this.fieldName = fieldName;
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "FieldAccessExpression [fieldName=" + fieldName + ", expr=" + expr + "]";
	}

	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
		return v.visit(this, param);
	}

	public Expression getExpr() {
		return expr;
	}
}
