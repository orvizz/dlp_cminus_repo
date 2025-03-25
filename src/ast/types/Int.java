package ast.types;

import ast.Type;
import semantic.Visitor;

public class Int implements Type {

    @Override
    public String toString() {
        return "Int";
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
        return v.visit(this,param);
    }
}
