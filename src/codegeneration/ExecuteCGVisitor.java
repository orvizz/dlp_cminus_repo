package codegeneration;

import ast.Type;
import ast.expressions.*;
import ast.program.FuncDefinition;
import ast.program.Program;
import ast.program.VarDefinition;
import ast.statements.*;
import ast.types.Function;
import ast.types.VoidType;

import java.io.FileWriter;

public class ExecuteCGVisitor extends AbstractCGVisitor<ExecuteCGVisitor.RetBytesDTO, Void> {

    CodeGenerator cg;
    ValueCGVisitor valueCGVisitor;
    AddressCGVisitor addressCGVisitor;

    public ExecuteCGVisitor(FileWriter fileWriter, String nameIn) {
        this.cg = CodeGenerator.getInstance(fileWriter, nameIn);
        this.valueCGVisitor = new ValueCGVisitor(fileWriter, nameIn);
        this.addressCGVisitor = new AddressCGVisitor(fileWriter, valueCGVisitor, nameIn);
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
     *
     *  ModExpression:
     * execute[[ArithmeticExpression: expression1 ->
     *      expression2 (+|-|*|/) expression3]] =
     *      value[[expression2]]
     *      value[[expression3]]
     *      <modi>
     *
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
     *
     *  NotExpression:
     *  execute[[NotExpression: expression1 ->
     *      !expression2]] =
     *      value[[expression2]]
     *      <not>
     *
     * Cast:
     * execute[[Cast: expression1 ->
     *      type expression2]] =
     *      value[[expression2]]
     *      expression2.type.convertTo(type)
     *
     * ReadStatement:
     * execute[[ReadStatement: statement -> expression]] =
     *      address[[expression]]
     *      <in> expression.type.suffix
     *      <store> expression.type.suffix
     *
     * WriteStatement:
     * execute[[WriteStatement: statement -> expression]] =
     *      value[[expression]]
     *      <out> expression.type.suffix
     *
     * Assignment:
     * execute[[Assignment: statement -> expression1 = expression2]] =
     *      address[[expression1]]
     *      value[[expression2]]
     *      <store>
     *          expression1.type.suffix
     *
     * VarDefinition:
     * execute[[VarDefinition: definition -> type ID]] =
     *      <\t\t' * > + type.typeExpression  + ID + <(offset > + definition.offset + <)>
     *
     * FuncDefinition:
     * execute[[FuncDefinition: definition ->
     *      type ID varDefinition* statement*]] =
     *      ID<:>
     *      <\t\t' * Parameters>
     *      execute[[type]]
     *      <\t\t' * Local variables>
     *      varDefinition*.forEach(vd -> execute[[vd]])
     *      <enter > varDefinition*.isEmpty() ? 0 :
     *          -varDefinition*.get(varDefinition*.size()  - 1).offset
     *      statement*.forEach(s -> execute[[s]])
     *
     *
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
     *
     */




    /**
     * ReadStatement:
     * execute[[ReadStatement: statement -> expression]] =
     *      address[[expression]]
     *      <in> expression.type.suffix
     *      <store> expression.type.suffix
     */
    @Override
    public Void visit(ReadStatement read, RetBytesDTO param) {
        cg.line(read.getLine());
        read.getExpr().accept(addressCGVisitor, null);
        cg.in(read.getType().getSuffix());
        cg.store(read.getExpr().getType().getSuffix());
        return null;
    }

    /**
     * WriteStatement:
     * execute[[WriteStatement: statement -> expression]] =
     *      value[[expression]]
     *      <out> expression.type.suffix
     */
    @Override
    public Void visit(WriteStatement write, RetBytesDTO param) {
        cg.line(write.getLine());
        write.getExpr().accept(valueCGVisitor, null);
        cg.out(write.getExpr().getType().getSuffix());
        return null;
    }

    /**
     * Assignment:
     * execute[[Assignment: statement -> expression1 = expression2]] =
     *      address[[expression1]]
     *      value[[expression2]]
     *      <store> expression1.type.suffix
     */
    @Override
    public Void visit(Assignment assignment, RetBytesDTO param) {
        cg.line(assignment.getLine());
        assignment.getLeftSide().accept(addressCGVisitor, null);
        assignment.getRightSide().accept(valueCGVisitor, null);
        cg.store(assignment.getLeftSide().getType().getSuffix());
        return null;
    }

    /**
     * VarDefinition:
     * execute[[VarDefinition: definition -> type ID]] =
     *      <\t\t' * > + type.typeExpression  + ID + <(offset > + definition.offset + <)>
     */
    @Override
    public Void visit(VarDefinition varDef, RetBytesDTO param) {
        cg.line(varDef.getLine());
        cg.variable(varDef.getType().typeExpression(),
                varDef.getName(),varDef.getOffset());
        return null;
    }


    /**
     * FuncDefinition:
     * execute[[FuncDefinition: definition ->
     *      type ID varDefinition* statement*]] =
     *      ID<:>
     *      <\t\t' * Parameters>
     *      execute[[type]]
     *      <\t\t' * Local variables>
     *      varDefinition*.forEach(vd -> execute[[vd]])
     *      int bytesLocals =
     *      <enter > varDefinition*.isEmpty() ? 0 :
     *          -varDefinition*.get(varDefinition*.size()  - 1).offset
     *      int bytesParams =
     *      int bytesReturn =
     *      statement*.forEach(s -> execute[[s]](bytesLocals,bytesParams,bytesReturn))
     *      if(bytesReturn==0) {
     *          <ret > bytesReturn, bytesLocals, bytesParams
     *      }
     */
    @Override
    public Void visit(FuncDefinition funcDef, RetBytesDTO param) {
        cg.line(funcDef.getLine());
        cg.label(funcDef.getName());
        cg.writeCode("\t\t' * Parameters");
        funcDef.getType().accept(this, null);
        cg.writeCode("\t\t' * Local variables");
        funcDef.getVarDefinitions().forEach(stmt -> stmt.accept(this, param));
        int bytesLocals = funcDef.getVarDefinitions().isEmpty() ? 0 : -funcDef.getVarDefinitions()
                .getLast().getOffset();
        cg.enter(bytesLocals);
        Function funcType = (Function) funcDef.getType();
        int bytesParams = funcType.getParams().isEmpty() ? 0 :
                funcType.getParams().stream().mapToInt(p->p.getType().getBytes()).sum();
        int bytesReturn = funcType.getReturnType().getBytes();
        RetBytesDTO retBytes = new RetBytesDTO(bytesReturn, bytesLocals, bytesParams);
        funcDef.getStatements().forEach(stmt -> stmt.accept(this, retBytes));
        if(bytesReturn==0) {
            cg.ret(bytesReturn,bytesLocals,bytesParams);
        }
        return null;
    }


    /**
     * WhileStatement
     * execute[[WhileStatement: statement ->
     *          expression statement*]] =
     *     String conditionLabel = cg.nextLabel();
     *     String exitLabel = cg.nextLabel();
     *
     *     conditionLabel <:>
     *     value[[expression]]
     *     <jz > exitLabel
     *     statement*.forEach(stmt -> execute[[stmt]])
     *     <jmp > conditionLabel
     *     exitLabel <:>
     */
    @Override
    public Void visit(WhileStatement whileStmt, RetBytesDTO param) {
       cg.line(whileStmt.getLine());
       String conditionLabel = cg.nextLabel();
       String exitLabel = cg.nextLabel();
       cg.label(conditionLabel);
       whileStmt.getStopCondition().accept(valueCGVisitor, null);
       cg.jz(exitLabel);
       whileStmt.getWhileBlock().forEach(stmt -> stmt.accept(this, param));
       cg.jmp(conditionLabel);
       cg.label(exitLabel);
       return null;
    }

    /**
     * IfStatement
     * execute[[IfStatement: statement ->
     *      expression statement1* statement2*]] =
     *      String elseLabel = cg.nextLabel();
     *      String endIfLabel = cg.nextLabel();
     *
     *      value[[expression]]
     *      <jz > elseLabel
     *      statement1*.forEach(stmt -> execute[[stmt]]);
     *      <jmp > exitLabel:
     *      elseLabel <:>
     *      statement2*forEach(stmt -> execute[[stmt]]);
     *      endIfLabel <:>
     */
    @Override
    public Void visit(IfStatement ifStmt, RetBytesDTO param) {
        cg.line(ifStmt.getLine());
        String elseLabel = cg.nextLabel();
        String endIfLabel = cg.nextLabel();
        ifStmt.getCondition().accept(valueCGVisitor, null);
        cg.jz(elseLabel);
        ifStmt.getIfBlock().forEach(stmt -> stmt.accept(this, param));
        cg.jmp(endIfLabel);
        cg.label(elseLabel);
        ifStmt.getElseBlock().forEach(stmt -> stmt.accept(this, param));
        cg.label(endIfLabel);
        return null;
    }

    /**
     * Program
     * execute[[Program: astnode ->
     *      definition*]] =
     *      definition*.stream().filter(def -> def instanceof VarDefinition).forEach(vd -> execute[[vd]]);
     *      <call main>
     *      <halt>
     *      definition*.stream().filter(def -> def instanceof FuncDefinition).forEach(fd -> execute[[fd]]);
     */
    @Override
    public Void visit(Program program, RetBytesDTO param) {
        cg.printSource();
        cg.writeCode("\t' * Global variables");
        program.getDefinitions().stream().filter(def -> def instanceof VarDefinition)
                .forEach(vd -> vd.accept(this, null));
        cg.call("main");
        cg.halt();
        program.getDefinitions().stream().filter(def -> def instanceof FuncDefinition)
                .forEach(fd -> fd.accept(this, null));
        return null;
    }

    /**
     * FuncInvocation
     * execute[[FuncInvocation: statement -> expression1 expression*]] =
     *      expression*.forEach(expr -> value[[expr]])
     *      <call > expression1.name
     *      if(!(expression1.type.returnType instanceof Void)){
     *          <pop> expression1.type.returnType.suffix()
     *      }
     */
     @Override
    public Void visit(FuncInvocation func, RetBytesDTO param) {
         cg.line(func.getLine());
         func.getExpressions().forEach(stmt -> stmt.accept(valueCGVisitor, null));
         cg.call(func.getVariable().getName());
         Function funcType = (Function) func.getVariable().getType();
         if(!(funcType.getReturnType() instanceof VoidType)) {
             cg.pop(funcType.getReturnType().getSuffix());
         }
         return null;
     }

    /**
     * ReturnStatement
     * execute[[ReturnStatement: statement -> expression]] =
     *      value[[expression]]
     *      <ret > bytesReturn , bytesLocals, bytesParams
     */
    public Void visit(ReturnStatement returnStmt, RetBytesDTO param) {
        cg.line(returnStmt.getLine());
        returnStmt.getReturnValue().accept(valueCGVisitor, null);
        cg.ret(param.bytesReturn, param.bytesLocals, param.bytesParams);
        return null;
    }

    public record RetBytesDTO(int bytesReturn, int bytesLocals, int bytesParams) {}
}
