package ast.types;

import ast.AbstractLocatable;
import ast.Type;
import ast.ASTNode;
import semantic.Visitor;

public class RecordField extends AbstractLocatable {
	final String name;
	final Type type;

	public RecordField(int line, int column, String name, Type type) {
		super(line, column);
		this.type = type;
		this.name = name;
	}

	@Override
	public String toString() {
		return "RecordField [name=" + name + ", type=" + type.toString() + "]";
	}


	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
		return null;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}
}
