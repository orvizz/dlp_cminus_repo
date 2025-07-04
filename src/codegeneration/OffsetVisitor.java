package codegeneration;

import ast.program.FuncDefinition;
import ast.program.VarDefinition;
import ast.types.Function;
import ast.types.RecordField;
import ast.types.Struct;
import semantic.AbstractVisitor;

public class OffsetVisitor extends AbstractVisitor<Void, Void> {

    /**
     * P:
     * (1) VarDefinition: definition -> type ID
     * (2) FuncDefinition: definition -> type ID definition* statement*
     * (3) Function: type -> type definition*
     * (4) Struct: type -> recordField*
     *
     * R:
     * (1)  if(definition.scope == 0){ // globalsBytesSum is a global variable initialized to zero
     *          definition.setOffset(globalBytesSum);
     *          globalBytesSum+=definition.getType().getBytes();
     *      }
     *
     * (2) int localsBytesSum=0;
     *      for( Definition def : definition*) {
     *          localsBytesSum+=def.getType().getBytes();
     *          def.setOffset(-localsBytesSum);
     *      }
     *
     * (3) int paramsBytesSum=0;
     *      for(int i = definition*.size(); i >= 0; i--){
     *          definition.get(i).setOffset(paramsBytesSum+4);
     *          paramsBytesSum+=definition.get(i).getType().getBytes();
     *      }
     *
     * (4) int fieldsBytesSum=0;
     *      for(RecordField rf : recordField*) {
     *          rf.setOffset(fieldsBytesSum);
     *          fieldsBytesSum+=rf.getType().getBytes();
     *      }
     */

    int globalBytesSum = 0;

    public Void visit(VarDefinition vd, Void param) {
        vd.getType().accept(this, param);
        if(vd.getScope() == 0){ // globalsBytesSum is a global variable initialized to zero
            vd.setOffset(globalBytesSum);
            globalBytesSum+=vd.getType().getBytes();
        }
        return null;
    }

    public Void visit(FuncDefinition fd, Void param) {
        int localsBytesSum = 0;
        fd.getType().accept(this, param);
        for(VarDefinition var : fd.getVarDefinitions()) {
            var.accept(this, param);
            localsBytesSum+=var.getType().getBytes();
            var.setOffset(-localsBytesSum);
        }
        fd.getStatements().forEach(s -> s.accept(this, param));
        return null;
    }

    public Void visit(Function f, Void param) {
        int paramsBytesSum=0;
        f.getReturnType().accept(this, param);
        for(int i = f.getParams().size()-1; i >= 0; i--) {
            f.getParams().get(i).accept(this, param);
            f.getParams().get(i).setOffset(paramsBytesSum+4);
            paramsBytesSum+=f.getParams().get(i).getType().getBytes();
        }
        return null;
    }

    public Void visit(Struct s, Void param) {
        int fieldsBytesSum=0;
           for(RecordField rf : s.getFields()) {
               rf.getType().accept(this, param);
               rf.setOffset(fieldsBytesSum);
               fieldsBytesSum+=rf.getType().getBytes();
           }
        return null;
    }

}
