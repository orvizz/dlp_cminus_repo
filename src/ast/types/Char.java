package ast.types;

import ast.Type;
import semantic.Visitor;

public class Char implements Type {

    @Override
    public String toString() {
        return "Char";
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
        return v.visit(this, param);
    }
}
