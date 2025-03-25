package ast.expressions;

import ast.Expression;
import ast.Type;
import semantic.Visitor;

public class Cast extends AbstractExpression {

	final Type type;
	final Expression expr;

	public Cast(int line, int column, Type type, Expression expr) {
		super(line, column);
		this.type = type;
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "Cast [type=" + type + ", expr=" + expr + "]";
	}

	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
		return v.visit(this, param);
	}

	public Expression getExpr() {
		return expr;
	}

	public Type getType() {
		return type;
	}
}
