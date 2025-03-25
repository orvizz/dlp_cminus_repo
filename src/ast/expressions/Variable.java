package ast.expressions;

import ast.Definition;
import semantic.Visitor;

public class Variable extends AbstractExpression {

	final String name;
	Definition definition;

	public Variable(int line, int column, String name) {
		super(line, column);
		this.name = name;
	}

	@Override
	public String toString() {
		return "Variable [name=" + name + "]";
	}

	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
		return v.visit(this, param);
	}

	public Definition getDefinition() {
		return definition;
	}

	public void setDefinition(Definition definition) {
		this.definition = definition;
	}

	public String getName() {
		return name;
	}
}
