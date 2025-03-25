package ast.statements;

import java.util.ArrayList;
import java.util.List;

import ast.Expression;
import ast.Statement;
import semantic.Visitor;

public class WhileStatement extends AbstractStatement {

	final Expression stopCondition;
	final List<Statement> whileBlock;

	public WhileStatement(int line, int column, Expression stopCondition, List<Statement> whileBlock) {
		super(line, column);
		this.stopCondition = stopCondition;
		this.whileBlock = new ArrayList<>(whileBlock);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("WhileStatement [stopCondition=" + stopCondition + ", whileBlock=");
		whileBlock.forEach(s -> sb.append("\n\t- "+s.toString()));
		sb.append("]");

		return sb.toString();
	}

	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
		return v.visit(this, param);
	}

	public Expression getStopCondition() {
		return stopCondition;
	}

	public List<Statement> getWhileBlock() {
		return whileBlock;
	}
}
