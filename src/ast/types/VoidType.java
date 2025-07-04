package ast.types;

import ast.Type;
import ast.statements.AbstractStatement;
import semantic.Visitor;

public class VoidType extends AbstractType {
    @Override
    public String toString() {
        return "Void";
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
        return v.visit(this, param);
    }


    public String typeExpression() {
        return "Void";
    }
}
