package ast.expressions;

import ast.AbstractLocatable;
import ast.Expression;
import ast.Type;

public abstract class AbstractExpression extends AbstractLocatable implements Expression {

	private boolean lValue;
	private Type type;

	public AbstractExpression(int line, int column) {
		super(line, column);
	}

	public void setLvalue(boolean lValue) {
		this.lValue = lValue;
	}

	public boolean getLvalue() {
		return lValue;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
