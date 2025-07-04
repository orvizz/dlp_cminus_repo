package codegeneration;

import ast.Type;
import ast.expressions.*;
import ast.types.Int;

import java.io.FileWriter;
import java.lang.reflect.Field;

public class ValueCGVisitor extends AbstractCGVisitor<Void, Void> {

    CodeGenerator cg;
    AddressCGVisitor addressCGVisitor;

    public ValueCGVisitor(FileWriter fileWriter, String nameIn) {
        this.cg = CodeGenerator.getInstance(fileWriter, nameIn);
        this.addressCGVisitor = new AddressCGVisitor(fileWriter, this, nameIn);
    }
    /**
     * IntLiteral:
     * value[[IntLiteral: expression -> INT_CONSTANT]] =
     *      <pushi > expression.value
     *
     * CharLiteral:
     * value[[CharLiteral: expression -> CHAR_CONSTANT]] =
     *      <pushb > (int) expression.value
     *
     * RealLiteral:
     * value[[RealLiteral: expression -> REAL_CONSTANT]] =
     *      <pushf > expression.value
     *
     * Variable:
     * value[[Variable: expression -> ID]] =
     *      address[[expression]]
     *      <load> expression.type.suffix
     */

    /**
     * IntLiteral:
     * value[[IntLiteral: expression -> INT_CONSTANT]] =
     *      <pushi > expression.value
     */
    @Override
    public Void visit(IntLiteral intLiteral, Void param) {
        cg.pushi(intLiteral.getValue());
        return null;
    }

    /**
     * CharLiteral:
     * value[[CharLiteral: expression -> CHAR_CONSTANT]] =
     *      <pushb > (int) expression.value
     */
    @Override
    public Void visit(CharLiteral charLiteral, Void param) {
        cg.pushb(charLiteral.getValue());
        return null;
    }

    /**
     * RealLiteral:
     * value[[RealLiteral: expression -> REAL_CONSTANT]] =
     *      <pushf > expression.value
     */
    @Override
    public Void visit(RealLiteral realLiteral, Void param) {
        cg.pushf(realLiteral.getValue());
        return null;
    }

    /**
     * Variable:
     * value[[Variable: expression -> ID]] =
     *      address[[expression]]
     *      <load> expression.type.suffix
     */
    @Override
    public Void visit(Variable var, Void param) {
        var.accept(addressCGVisitor, null);
        cg.load(var.getDefinition().getType().getSuffix());
        return null;
    }

    /**
     * IndexExpression
     * value[[IndexExpression: expression1 ->
     *          expression2 expression3]] =
     *          address[[expression1]]
     *          <load> expression1.type.suffix()
     */
    @Override
    public Void visit(IndexExpression indexExpression, Void param) {
        indexExpression.accept(addressCGVisitor, null);
        cg.load(indexExpression.getType().getSuffix());
        return null;
    }

    /**
     * FieldAccessExpression
     * value[[FieldAccessExpression: expression1 ->
     *          expression2 ID]] =
     *          address[[expression1]]
     *          <load> expression1.type.suffix()
     */
    @Override
    public Void visit(FieldAccessExpression fieldAccessExpression, Void param) {
        fieldAccessExpression.accept(addressCGVisitor, null);
        cg.load(fieldAccessExpression.getType().getSuffix());
        return null;
    }

    /**
     * ArithmeticExpression:
     * execute[[ArithmeticExpression: expression1 ->
     *      expression2 (+|-|*|/) expression3]] =
     *      value[[expression2]]
     *      expression2.type.convertTo(expression1.type)
     *      value[[expression3]]
     *      expression3.type.convertTo(expression1.type)
     *      switch(expression1.operator){
     *          case "+":
     *              <add> expression1.type.suffix
     *              break;
     *          case "-":
     *              <sub> expression1.type.suffix
     *              break;
     *          case "*":
     *              <mul> expression1.type.suffix
     *              break;
     *          case "/":
     *              <div> expression1.type.suffix
     *              break;
     *          default:
     *              assert(false);
     *      }
     */
    @Override
    public Void visit(ArithmeticExpression arith, Void param) {
        arith.getLeftExpr().accept(this, null);
        cg.writeCode(arith.getLeftExpr().getType().convertTo(arith.getType()));
        arith.getRightExpr().accept(this, null);
        cg.writeCode(arith.getRightExpr().getType().convertTo(arith.getType()));
        switch (arith.getOperator()) {
            case "+":
                cg.add(arith.getType().getSuffix());
                break;
            case "-":
                cg.sub(arith.getType().getSuffix());
                break;
            case "*":
                cg.mul(arith.getType().getSuffix());
                break;
            case "/":
                cg.div(arith.getType().getSuffix());
                break;
            default:
                assert false;
        }
        return null;
    }

    /**
     *  ModExpression:
     * execute[[ArithmeticExpression: expression1 ->
     *      expression2 (+|-|*|/) expression3]] =
     *      value[[expression2]]
     *      value[[expression3]]
     *      <modi>
     */
    @Override
    public Void visit(ModExpression mod, Void param) {
        mod.getLeftExpr().accept(this, null);
        mod.getRightExpr().accept(this, null);
        cg.modi();
        return null;
    }

    /**
     *  LogicalExpression:
     *  execute[[LogicalExpression: expression1 ->
     *      expression2 (&& | ||) expression3]] =
     *      value[[expression2]]
     *      value[[expression3]]
     *      switch(expression1.operand) {
     *          case("&&"):
     *              <and>
     *              break;
     *          case("||"):
     *              <or>
     *              break;
     *          default:
     *              assert(false);
     *      }
     */
    @Override
    public Void visit(LogicalExpression le, Void param) {
        le.getLeftExpr().accept(this, null);
        le.getRightExpr().accept(this, null);
        switch(le.getOperator()) {
            case "&&":
                cg.and();
                break;
            case "||":
                cg.or();
                break;
            default:
                assert false;
        }
        return null;
    }

    /**
     *  NotExpression:
     *  execute[[NotExpression: expression1 ->
     *      !expression2]] =
     *      value[[expression2]]
     *      <not>
     */
    @Override
    public Void visit(NotExpression ne, Void param) {
        ne.getExpr().accept(this, null);
        cg.not();
        return null;
    }

    /**
     * Cast:
     * execute[[Cast: expression1 ->
     *      type expression2]] =
     *      value[[expression2]]
     *      expression2.type.convertTo(type)
     */
    @Override
    public Void visit(Cast cast, Void param) {
        cast.getExpr().accept(this, null);
        cg.writeCode(cast.getExpr().getType().convertTo(cast.getType()));
        return null;
    }


    /**
     * RelationalExpression:
     * execute[[RelationalExpression: expression1 ->
     *      expression2 (<|>|<=|>=|==|!=) expression3]] =
     *      Type supertype = exp2.type.supertype(exp1.type);
     *      value[[expression2]]
     *      expression2.type.convertTo(supertype)
     *      value[[expression3]]
     *      expression3.type.convertTo(supertype)
     *      switch(expression1.operator) {
     *          case "<":
     *              <lt> supertype.suffix
     *              break;
     *          case ">":
     *              <gt> supertype.suffix
     *              break;
     *          case "<=":
     *              <le> supertype.suffix
     *              break;
     *          case ">=":
     *              <ge> supertype.suffix
     *              break;
     *          case "==":
     *              <eq> supertype.suffix
     *              break;
     *          case "!=":
     *              <ne> supertype.suffix
     *              break;
     *          default:
     *              assert(false)
     *      }
     */
    @Override
    public Void visit(RelationalExpression expr, Void param) {
        Type supertype = expr.getLeftExpr().getType().supertype(expr.getType());
        expr.getLeftExpr().accept(this, null);
        cg.writeCode(expr.getLeftExpr().getType().convertTo(supertype));
        expr.getRightExpr().accept(this, null);
        cg.writeCode(expr.getRightExpr().getType().convertTo(supertype));
        switch(expr.getOperator()) {
            case "<":
                cg.lt(supertype.getSuffix());
                break;
            case ">":
                cg.gt(supertype.getSuffix());
                break;
            case "<=":
                cg.le(supertype.getSuffix());
                break;
            case ">=":
                cg.ge(supertype.getSuffix());
                break;
            case"==":
                cg.eq(supertype.getSuffix());
                break;
            case "!=":
                cg.ne(supertype.getSuffix());
                break;
            default:
                assert false;
        }
        return null;
    }

    /**
     * FuncInvocation
     * value[[FuncInvocation: expression1 -> expression2 expression*]] =
     *      expression*.forEach(expr -> value[[expr]])
     *      <call > expression2.name
     */
    public Void visit(FuncInvocation func, Void param) {
        func.getExpressions().forEach(expr -> expr.accept(this, null));
        cg.call( func.getVariable().getName() );
        return null;
    }
}

