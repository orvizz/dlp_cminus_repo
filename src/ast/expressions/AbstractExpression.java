package ast.expressions;

import ast.AbstractLocatable;
import ast.Expression;

public abstract class AbstractExpression extends AbstractLocatable implements Expression {

	private boolean lValue;

	public AbstractExpression(int line, int column) {
		super(line, column);
	}

	public void setLvalue(boolean lValue) {
		this.lValue = lValue;
	}

	public boolean getLvalue() {
		return lValue;
	}
}
