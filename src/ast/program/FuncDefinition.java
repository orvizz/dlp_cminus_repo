package ast.program;

import java.util.ArrayList;
import java.util.List;

import ast.Statement;
import ast.Type;
import semantic.Visitor;

public class FuncDefinition extends AbstractDefinition {

	final List<VarDefinition> varDefinitions;
	final List<Statement> statements;

	public FuncDefinition(int line, int column, String name, Type type, List<VarDefinition> varDefinitions, List<Statement> statements) {
		super(line, column, name, type);
		this.varDefinitions = new ArrayList<>(varDefinitions);
		this.statements = new ArrayList<>(statements);

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("FuncDefinition [name=" + name +", Type=" + type.toString() + "\nvarDefinitions=");
		varDefinitions.forEach(var -> sb.append("\n\t- "+var.toString()));
		sb.append("\nstatements=");
		statements.forEach(s -> sb.append("\n\t- "+s.toString()));
		sb.append("]");
		return sb.toString();
	}

	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
		return v.visit(this, param);
	}

	public List<Statement> getStatements() {
		return statements;
	}

	public List<VarDefinition> getVarDefinitions() {
		return varDefinitions;
	}
}
