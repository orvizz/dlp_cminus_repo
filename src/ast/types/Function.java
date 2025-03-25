package ast.types;

import java.util.ArrayList;
import java.util.List;

import ast.Type;
import ast.program.VarDefinition;
import semantic.Visitor;

public class Function implements Type {
	final Type returnType;
	final List<VarDefinition> params;
	
	public Function(Type returnType, List<VarDefinition> params) {
		this.returnType = returnType;
		this.params = new ArrayList<>(params);
	}

	public Function(Type returnType) {
		this(returnType, new ArrayList<>());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Function [type=" + returnType.toString() + ", params=");
		params.forEach(p -> sb.append( "\n\t- " + p.toString()));
		sb.append("]");
		return sb.toString();
	}

	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
		return v.visit(this,param);
	}

	public List<VarDefinition> getParams() {
		return params;
	}

	public Type getReturnType() {
		return returnType;
	}
}
