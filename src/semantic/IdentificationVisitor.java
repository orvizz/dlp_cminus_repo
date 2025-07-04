package semantic;

import ast.Definition;
import ast.expressions.Variable;
import ast.program.FuncDefinition;
import ast.program.Program;
import ast.program.VarDefinition;
import ast.types.ErrorType;
import ast.types.Function;
import symboltable.SymbolTable;

public class IdentificationVisitor extends AbstractVisitor<Void, Void> {
    private SymbolTable symbolTable;

    @Override
    public Void visit(Program p, Void param) {
        symbolTable = new SymbolTable();
        for(Definition def : p.getDefinitions()) {
            def.accept(this, param);
        }
        return null;
    }

    @Override
    public Void visit(Variable v, Void param) {
        Definition def = symbolTable
                .find(v.getName());
        if(def != null) {
            v.setDefinition(def);
        }
        return null;
    }

    @Override
    public Void visit(FuncDefinition funcDefinition, Void param) {
        if(!symbolTable.insert(funcDefinition)){
            new ErrorType(funcDefinition.getLine(), funcDefinition.getColumn(), "Function " + funcDefinition.getName() + " is already defined in the program");
        }
        symbolTable.set();
        funcDefinition.getType().accept(this, param);
        for(VarDefinition var : funcDefinition.getVarDefinitions()) {
            var.accept(this, param);
        }
        funcDefinition.getStatements().forEach(stmt -> stmt.accept(this, param));
        symbolTable.reset();
        return null;
    }

    @Override
    public Void visit(VarDefinition v, Void param) {
        if(!symbolTable.insert(v)){
            new ErrorType(v.getLine(), v.getColumn(), "Variable " + v.getName() + " is already defined in this scope");
        }
        return null;
    }

}
