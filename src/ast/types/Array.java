package ast.types;

import ast.Type;
import semantic.Visitor;

public class Array extends AbstractType {
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

	public String typeExpression() {
		return "Array<" + type.typeExpression() + ">";
	}

	@Override
	public Type squareBrackets(Type type, int line, int column) {
		if(type instanceof ErrorType) return type;
		else if(type instanceof Int) return this.getType();
		return new ErrorType(line, column, "Type " + type.typeExpression() + " cannot be used as index");
	}

	public int getBytes() {
		return size*type.getBytes();
	}
}
