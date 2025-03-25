package semantic;

import ast.Definition;
import ast.Expression;
import ast.Statement;
import ast.expressions.*;
import ast.program.FuncDefinition;
import ast.program.Program;
import ast.program.VarDefinition;
import ast.statements.*;
import ast.types.*;

public class TypeCheckingVisitor extends AbstractVisitor<Void, Void> {

    @Override
    public Void visit(Variable v, Void param) {
        v.setLvalue(true);
        return null;
    }

    @Override
    public Void visit(ReadStatement readStatement, Void param) {
        readStatement.getExpr().accept(this, param);
        if(!readStatement.getExpr().getLvalue()){
            new ErrorType(readStatement.getExpr().getLine(),
                    readStatement.getExpr().getColumn(), "LValue required");
        }
        return null;
    }


    @Override
    public Void visit(Assignment assignment, Void param) {
        assignment.getLeftSide().accept(this, param);
        assignment.getRightSide().accept(this, param);
        if( !assignment.getLeftSide().getLvalue()){
            new ErrorType(assignment.getLeftSide().getLine(),
                    assignment.getLeftSide().getColumn(), "LValue required");
        }
        return null;
    }

    @Override
    public Void visit(UnaryMinus unaryMinus, Void param) {
        unaryMinus.getExpr().accept(this, param);
        unaryMinus.setLvalue(false);
        return null;
    }

    @Override
    public Void visit(RelationalExpression relationalExpression, Void param) {
        relationalExpression.getLeftExpr().accept(this, param);
        relationalExpression.getRightExpr().accept(this, param);
        relationalExpression.setLvalue(false);
        return null;
    }

    @Override
    public Void visit(RealLiteral realLiteral, Void param) {
        realLiteral.setLvalue(false);
        return null;
    }

    @Override
    public Void visit(NotExpression notExpression, Void param) {
        notExpression.getExpr().accept(this, param);
        notExpression.setLvalue(false);
        return null;
    }

    @Override
    public Void visit(ModExpression modExpression, Void param) {
        modExpression.getLeftExpr().accept(this, param);
        modExpression.getRightExpr().accept(this, param);
        modExpression.setLvalue(false);
        return null;
    }

    @Override
    public Void visit(LogicalExpression logicalExpression, Void param) {
        logicalExpression.getLeftExpr().accept(this, param);
        logicalExpression.getRightExpr().accept(this, param);
        logicalExpression.setLvalue(false);
        return null;
    }

    @Override
    public Void visit(IntLiteral intLiteral, Void param) {
        intLiteral.setLvalue(false);
        return null;
    }

    @Override
    public Void visit(IndexExpression indexExpression, Void param) {
        indexExpression.getFirstExpr().accept(this, param);
        indexExpression.getSecExpr().accept(this, param);
        indexExpression.setLvalue(true);
        return null;
    }

    @Override
    public Void visit(FuncInvocation funcInvocation, Void param) {
        funcInvocation.getVariable().accept(this, param);
        funcInvocation.getExpressions().forEach(expr -> expr.accept(this, param));
        funcInvocation.setLvalue(false);
        return null;
    }

    @Override
    public Void visit(FieldAccessExpression fieldAccessExpression, Void param) {
        fieldAccessExpression.getExpr().accept(this, param);
        fieldAccessExpression.setLvalue(true);
        return null;
    }

    @Override
    public Void visit(CharLiteral charLiteral, Void param) {
        charLiteral.setLvalue(false);
        return null;
    }

    @Override
    public Void visit(Cast cast, Void param) {
        cast.getExpr().accept(this, param);
        cast.getType().accept(this, param);
        cast.setLvalue(false);
        return null;
    }

    @Override
    public Void visit(ArithmeticExpression arithmeticExpression, Void param) {
        arithmeticExpression.getLeftExpr().accept(this, param);
        arithmeticExpression.getRightExpr().accept(this, param);
        arithmeticExpression.setLvalue(false);
        return null;
    }
}
