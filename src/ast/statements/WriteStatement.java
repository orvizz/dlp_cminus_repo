package ast.statements;

import ast.Expression;
import semantic.Visitor;

import java.util.List;

public class WriteStatement extends AbstractStatement {

	final Expression expr;

	public WriteStatement(int line, int column, Expression expr) {
		super(line, column);
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "WriteStatement [expr=" + expr.toString() + "]";
	}

	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
		return v.visit(this, param);
	}

	public Expression getExpr() {
		return expr;
	}
}
