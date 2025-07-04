package codegeneration;

import ast.expressions.FieldAccessExpression;
import ast.expressions.IndexExpression;
import ast.expressions.Variable;
import ast.types.Struct;

import java.io.FileWriter;

public class AddressCGVisitor extends AbstractCGVisitor<Void, Void> {

    CodeGenerator cg;
    final ValueCGVisitor valueCGVisitor;

    public AddressCGVisitor(FileWriter fileWriter, ValueCGVisitor valueCGVisitor, String nameIn) {
        this.cg = CodeGenerator.getInstance(fileWriter, nameIn);
        this.valueCGVisitor = valueCGVisitor;
    }

    /**
     * Variable:
     * address[[Variable: expression -> ID]] =
     *      if(expression.definition.scope == 0){
     *          <pusha > expression.definition.offset
     *      } else {
     *          <push bp>
     *          <pushi > expression.definition.offset
     *          <addi>
     *      }
     */
    @Override
    public Void visit(Variable variable, Void param) {
        if(variable.getDefinition().getScope() == 0) {
            cg.pusha(variable.getDefinition().getOffset());
        } else {
            cg.pushbp();
            cg.pushi(variable.getDefinition().getOffset());
            cg.add('i');
        }
        return null;
    }
    /**
     * IndexExpression
     * address[[IndexExpression: expression1 ->
     *          expression2 expression3]] =
     *          address[[expression2]]
     *          <pushi > expression1.type.numberOfBytes();
     *          value[[expression3]]
     *          <muli>
     *          <addi>
     */
    @Override
    public Void visit(IndexExpression indexExpression, Void param) {
        indexExpression.getFirstExpr().accept(this, param);
        cg.pushi(indexExpression.getType().getBytes());
        indexExpression.getSecExpr().accept(valueCGVisitor, param);
        cg.mul('i');
        cg.add('i');
        return null;
    }

    /**
     * FieldAccessExpression
     * address[[FieldAccessExpression: expression1 ->
     *          expression2 ID]] =
     *          address[[expression2]]
     *          <pushi >expression2.type.getField(ID).offset;
     *          <addi>
     */
    @Override
    public Void visit(FieldAccessExpression fieldAccessExpression, Void param) {
        fieldAccessExpression.getExpr().accept(this, param);
        cg.pushi(((Struct)fieldAccessExpression.getExpr().getType())
                .getField(fieldAccessExpression.getFieldName()).getOffset());
        cg.add('i');
        return null;
    }
}
