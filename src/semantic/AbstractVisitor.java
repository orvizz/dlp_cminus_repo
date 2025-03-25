package semantic;

import ast.Definition;
import ast.Statement;
import ast.expressions.*;
import ast.program.FuncDefinition;
import ast.program.Program;
import ast.program.VarDefinition;
import ast.statements.*;
import ast.types.*;

public abstract class AbstractVisitor<TP, TR> implements Visitor<TP, TR> {

    @Override
    public TR visit(Program p, TP param) {
        for(Definition def : p.getDefinitions()) {
            def.accept(this, param);
        }
        return null;
    }

    @Override
    public TR visit(Variable v, TP param) {
        return null;
    }

    @Override
    public TR visit(WhileStatement whileStatement, TP param) {
        whileStatement.getStopCondition().accept(this, param);
        for( Statement st : whileStatement.getWhileBlock()){
            st.accept(this, param);
        }
        return null;
    }

    @Override
    public TR visit(FuncDefinition funcDefinition, TP param) {
        funcDefinition.getType().accept(this, param);
        for(VarDefinition var : funcDefinition.getVarDefinitions()) {
            var.accept(this, param);
        }
        funcDefinition.getStatements().forEach(stmt -> stmt.accept(this, param));
        return null;
    }

    @Override
    public TR visit(VarDefinition varDefinition, TP param) {
        return null;
    }

    @Override
    public TR visit(WriteStatement writeStatement, TP param) {
        writeStatement.getExpr().accept(this, param);
        return null;
    }

    @Override
    public TR visit(ReturnStatement returnStatement, TP param) {
        returnStatement.getReturnValue().accept(this, param);
        return null;
    }

    @Override
    public TR visit(ReadStatement readStatement, TP param) {
        readStatement.getExpr().accept(this, param);
        return null;
    }

    @Override
    public TR visit(IfStatement ifStatement, TP param) {
        ifStatement.getCondition().accept(this, param);
        for (Statement st : ifStatement.getIfBlock()) {
            st.accept(this, param);
        }
        for (Statement st : ifStatement.getElseBlock()) {
            st.accept(this, param);
        }
        return null;
    }

    @Override
    public TR visit(Assignment assignment, TP param) {
        assignment.getLeftSide().accept(this, param);
        assignment.getRightSide().accept(this, param);
        return null;
    }

    @Override
    public TR visit(UnaryMinus unaryMinus, TP param) {
        unaryMinus.getExpr().accept(this, param);
        return null;
    }

    @Override
    public TR visit(RelationalExpression relationalExpression, TP param) {
        relationalExpression.getLeftExpr().accept(this, param);
        relationalExpression.getRightExpr().accept(this, param);
        return null;
    }

    @Override
    public TR visit(RealLiteral realLiteral, TP param) {
        return null;
    }

    @Override
    public TR visit(NotExpression notExpression, TP param) {
        notExpression.getExpr().accept(this, param);
        return null;
    }

    @Override
    public TR visit(ModExpression modExpression, TP param) {
        modExpression.getLeftExpr().accept(this, param);
        modExpression.getRightExpr().accept(this, param);
        return null;
    }

    @Override
    public TR visit(LogicalExpression logicalExpression, TP param) {
        logicalExpression.getLeftExpr().accept(this, param);
        logicalExpression.getRightExpr().accept(this, param);
        return null;
    }

    @Override
    public TR visit(IntLiteral intLiteral, TP param) {
        return null;
    }

    @Override
    public TR visit(IndexExpression indexExpression, TP param) {
        indexExpression.getFirstExpr().accept(this, param);
        indexExpression.getSecExpr().accept(this, param);
        return null;
    }

    @Override
    public TR visit(FuncInvocation funcInvocation, TP param) {
        funcInvocation.getVariable().accept(this, param);
        funcInvocation.getExpressions().forEach(expr -> expr.accept(this, param));
        return null;
    }

    @Override
    public TR visit(FieldAccessExpression fieldAccessExpression, TP param) {
        fieldAccessExpression.getExpr().accept(this, param);
        return null;
    }

    @Override
    public TR visit(CharLiteral charLiteral, TP param) {
        return null;
    }

    @Override
    public TR visit(Cast cast, TP param) {
        cast.getExpr().accept(this, param);
        cast.getType().accept(this, param);
        return null;
    }

    @Override
    public TR visit(ArithmeticExpression arithmeticExpression, TP param) {
        arithmeticExpression.getLeftExpr().accept(this, param);
        arithmeticExpression.getRightExpr().accept(this, param);
        return null;
    }

    @Override
    public TR visit(ErrorType errorType, TP param) {
        return null;
    }

    @Override
    public TR visit(Array array, TP param) {
        array.getType().accept(this, param);
        return null;
    }

    @Override
    public TR visit(Char aChar, TP param) {
        return null;
    }

    @Override
    public TR visit(DoubleType doubleType, TP param) {
        return null;
    }

    @Override
    public TR visit(Function function, TP param) {
        function.getReturnType().accept(this, param);
        function.getParams().forEach(expr -> expr.accept(this, param));
        return null;
    }

    @Override
    public TR visit(Int anInt, TP param) {
        return null;
    }

    @Override
    public TR visit(Struct struct, TP param) {
        struct.getFields().forEach(expr -> expr.accept(this, param));
        return null;
    }

    @Override
    public TR visit(VoidType voidType, TP param) {
        return null;
    }
}
