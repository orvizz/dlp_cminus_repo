package ast.statements;

import java.util.ArrayList;
import java.util.List;

import ast.Expression;
import ast.Statement;
import semantic.Visitor;

public class IfStatement extends AbstractStatement {

	final Expression condition;
	final List<Statement> ifBlock;
	final List<Statement> elseBlock;

	public IfStatement(int line, int column, Expression condition, List<Statement> ifBlock, List<Statement> elseBlock) {
		super(line, column);
		this.condition = condition;
		this.ifBlock = new ArrayList<>(ifBlock);
		this.elseBlock = new ArrayList<>(elseBlock);
	}

	public IfStatement(int line, int column, Expression condition, List<Statement> ifBlock) {
		this(line, column, condition, ifBlock, new ArrayList<>());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("IfStatement [condition=" + condition + ", ifBlock=");
		ifBlock.forEach(s -> sb.append("\n\t- "+s.toString()));
		sb.append("\nelseBlock=");
		elseBlock.forEach(s -> sb.append("\n\t- "+s.toString()));
		sb.append("]");
		return sb.toString();
	}

	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
		return v.visit(this, param);
	}

	public Expression getCondition() {
		return condition;
	}

	public List<Statement> getElseBlock() {
		return elseBlock;
	}

	public List<Statement> getIfBlock() {
		return ifBlock;
	}
}
