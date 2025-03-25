package ast.types;

import ast.Type;
import semantic.Visitor;

public class Array implements Type {
	final Type type;
	final int size;
	
	public Array(Type type, int size) {
		this.type = type;
		this.size = size;
	}

	public Type getType() {
		return type;
	}

	public int getSize() {
		return size;
	}

	@Override
	public String toString() {
		return "Array [type=" + type.toString() + ", size=" + size + "]";
	}

	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
		return v.visit(this, param);
	}
}
