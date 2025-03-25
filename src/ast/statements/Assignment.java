package ast.statements;

import ast.Expression;
import semantic.Visitor;

public class Assignment extends AbstractStatement {

	final Expression leftSide;
	final Expression rightSide;

	public Assignment(int line, int column, Expression leftSide, Expression rightSide) {
		super(line, column);
		this.leftSide = leftSide;
		this.rightSide = rightSide;
	}

	@Override
	public String toString() {
		return "Assignment [leftSide=" + leftSide.toString() + ", rightSide=" + rightSide.toString() + "]";
	}

	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
		return v.visit(this, param);
	}

	public Expression getLeftSide() {
		return leftSide;
	}

	public Expression getRightSide() {
		return rightSide;
	}
}
