package ast.program;

import java.util.ArrayList;
import java.util.List;

import ast.ASTNode;
import ast.Definition;
import semantic.Visitor;

public class Program implements ASTNode {
	final List<Definition> definitions;

	public Program(List<Definition> definitions) {
		this.definitions = new ArrayList<>(definitions);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Program [definitions=");
		definitions.forEach(definition -> sb.append("\n\t- "+definition.toString()));
		sb.append("]");
		return sb.toString();
	}

	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
		return v.visit(this, param);
	}

	public List<Definition> getDefinitions() {
		return definitions;
	}
}
