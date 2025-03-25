package ast.program;

import ast.Type;
import semantic.Visitor;

public class VarDefinition extends AbstractDefinition {

	public VarDefinition(int line, int column, String name, Type type) {
		super(line, column, name, type);
	}

	@Override
	public String toString() {
		return "VarDefinition [name=" + name + ", type=" + type.toString() + "]" ;
	}

	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
		return v.visit(this, param);
	}
}
