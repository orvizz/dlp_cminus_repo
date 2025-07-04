package ast.program;

import ast.AbstractLocatable;
import ast.Definition;
import ast.Type;

public abstract class AbstractDefinition extends AbstractLocatable implements Definition {
	
	final String name;
	final Type type;
	int scope;
	int offset;

	public AbstractDefinition(int line, int column, String name, Type type) {
		super(line, column);
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public int getScope() {
		return scope;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

	@Override
	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}
