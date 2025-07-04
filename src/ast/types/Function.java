package ast.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ast.Type;
import ast.program.VarDefinition;
import semantic.Visitor;

public class Function extends AbstractType {
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

	public String typeExpression() {
		StringBuilder sb = new StringBuilder();
		sb.append("Function<" + returnType.typeExpression());
		params.forEach(p -> sb.append(", " + p.getType().typeExpression()));
		sb.append(">");
		return sb.toString();
	}

	@Override
	public Type parenthesis(List<Type> types, int line, int column) {
		Optional<Type> epErr = types.stream().filter(t -> t instanceof ErrorType).findFirst();
		if (epErr.isPresent()) { return epErr.get(); }
		if (types.size() != params.size()) {
			return new ErrorType(line, column, "Parameters length doesn't match (Expected " + params.size()
					+ ", found " + types.size() + ")");
		}
		for(int i = 0; i < params.size(); i++) {
			if (types.get(i).getClass() != params.get(i).getType().getClass()) {
				return new ErrorType(line, column, "Parameter mismatch in parameter " + i+1 + " (Expected "
						+ params.get(i).getType().typeExpression() + ", found " + types.get(i).typeExpression() + ")");
			}
		}
		return returnType;
	}

	public int getBytes() {
		return returnType.getBytes()+params.stream().mapToInt(vd -> vd.getType().getBytes()).sum();
	}
}
