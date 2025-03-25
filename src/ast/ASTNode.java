package ast;

import semantic.Visitor;

public interface ASTNode {
    <TP,TR> TR accept(Visitor<TP,TR> v, TP param);
}
