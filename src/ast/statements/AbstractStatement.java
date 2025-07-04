package ast.statements;

import ast.AbstractLocatable;
import ast.Statement;
import ast.Type;

public abstract class AbstractStatement extends AbstractLocatable implements Statement {

	private Type type;
	public AbstractStatement(int line, int column) {
		super(line, column);
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
