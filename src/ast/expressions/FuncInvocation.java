package ast.expressions;

import java.util.ArrayList;
import java.util.List;

import ast.Expression;
import ast.Statement;
import semantic.Visitor;

public class FuncInvocation extends AbstractExpression implements Statement {

	final Variable variable;
	final List<Expression> expressions;

	public FuncInvocation(int line, int column, Variable variable, List<Expression> expressions) {
		super(line, column);
		this.variable = variable;
		this.expressions = new ArrayList<>(expressions);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("FuncInvocation [variable=" + variable + ", expressions=");
		expressions.forEach(e -> sb.append("\n\t- "+e.toString()));
		sb.append("]");
		return sb.toString();

	}

	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
		return v.visit(this, param);
	}

	public List<Expression> getExpressions() {
		return expressions;
	}

	public Variable getVariable() {
		return variable;
	}
}
