package ast.statements;

import ast.Expression;
import semantic.Visitor;

public class ReturnStatement extends AbstractStatement {

	final Expression returnValue;

	public ReturnStatement(int line, int column, Expression returnValue) {
		super(line, column);
		this.returnValue = returnValue;
	}

	@Override
	public String toString() {
		return "ReturnStatement [returnValue=" + returnValue.toString() + "]";
	}

	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
		return v.visit(this, param);
	}

	public Expression getReturnValue() {
		return returnValue;
	}
}
