package semantic;

import ast.Definition;
import ast.Expression;
import ast.Statement;
import ast.Type;
import ast.expressions.*;
import ast.program.FuncDefinition;
import ast.program.Program;
import ast.program.VarDefinition;
import ast.statements.*;
import ast.types.*;

public class TypeCheckingVisitor extends AbstractVisitor<Type, Void> {

    @Override
    public Void visit(Variable v, Type returnType) {
        v.setLvalue(true);
        if(v.getDefinition() == null){
            v.setType(new ErrorType(v.getLine(), v.getColumn(), "Variable " + v.getName() + " is not defined in the program"));
        } else {
            v.setType(v.getDefinition().getType());
        }
        return null;
    }

    @Override
    public Void visit(ReadStatement readStatement, Type returnType) {
        readStatement.getExpr().accept(this, returnType);
        if(!readStatement.getExpr().getLvalue()){
            readStatement.getExpr().setType(new ErrorType(readStatement.getExpr().getLine(),
                    readStatement.getExpr().getColumn(), "LValue required"));
        }
        /*
        * ReadStatement: statement -> expression
        * expression.type.mustBeReadable();
         */
        readStatement.setType(readStatement.getExpr().getType().mustBeReadable(readStatement.getLine(),
                readStatement.getColumn()));
        return null;
    }

    @Override
    public Void visit(WriteStatement writeStatement, Type returnType) {
        writeStatement.getExpr().accept(this, returnType);
        /*
         * WriteStatement: statement -> expression
         * expression.type.mustBeWritable();
         */
        writeStatement.setType(writeStatement.getExpr().getType().mustBeWritable(writeStatement.getLine(),
                writeStatement.getColumn()));
        return null;
    }


    @Override
    public Void visit(Assignment assignment, Type returnType) {
        assignment.getLeftSide().accept(this, returnType);
        assignment.getRightSide().accept(this, returnType);
        if( !assignment.getLeftSide().getLvalue()){
            assignment.getLeftSide().setType(new ErrorType(assignment.getLeftSide().getLine(),
                    assignment.getLeftSide().getColumn(), "LValue required"));
        }
        /*
         * P: Assignment: statement -> expression1 expression2
         * R: expression1.type.assign(expression2.type)
         */
        assignment.setType(assignment.getLeftSide().getType().assignment(assignment.getRightSide().getType(),
                assignment.getLine(), assignment.getColumn()));
        return null;
    }

    @Override
    public Void visit(UnaryMinus unaryMinus, Type returnType) {
        unaryMinus.getExpr().accept(this, returnType);
        unaryMinus.setLvalue(false);
        /*
         * P: UnaryMinus: expression1 -> expression2
         * R: expression1.type = expression2.type.unaryMinus()
         */
        unaryMinus.setType(unaryMinus.getExpr().getType().unaryMinus(unaryMinus.getLine(), unaryMinus.getColumn()));
        return null;
    }

    @Override
    public Void visit(RelationalExpression relationalExpression, Type returnType) {
        relationalExpression.getLeftExpr().accept(this, returnType);
        relationalExpression.getRightExpr().accept(this, returnType);
        relationalExpression.setLvalue(false);
        /*
         * P: RelationalExpression: expression1 -> expression2 expression3
         * R: expression1.type = expression2.type.relational(expression3.type)
         */
        Expression leftExpr = relationalExpression.getLeftExpr();
        Expression rightExpr = relationalExpression.getRightExpr();
        relationalExpression.setType(leftExpr.getType().relational(rightExpr.getType(),
                relationalExpression.getLine(), relationalExpression.getColumn()));
        return null;
    }

    @Override
    public Void visit(RealLiteral realLiteral, Type returnType) {
        realLiteral.setLvalue(false);
        realLiteral.setType(new DoubleType());
        return null;
    }

    @Override
    public Void visit(NotExpression notExpression, Type returnType) {
        notExpression.getExpr().accept(this, returnType);
        notExpression.setLvalue(false);
        /*
         * P: NotExpression: expression1 -> expression2
         * R: expression1.type = expression2.type.mustBeNot()
         */
        notExpression.setType(notExpression.getExpr().getType().not(notExpression.getLine(), notExpression.getColumn()));
        return null;
    }

    @Override
    public Void visit(ModExpression modExpression, Type returnType) {
        modExpression.getLeftExpr().accept(this, returnType);
        modExpression.getRightExpr().accept(this, returnType);
        modExpression.setLvalue(false);
        /*
         * P: ModExpression: expression1 -> expression2 expression3
         * R: expression1.type = expression2.type.modulus(expression3.type)
         */
        modExpression.setType(modExpression.getLeftExpr().getType().modulus(modExpression.getRightExpr().getType(),
                modExpression.getLine(), modExpression.getColumn()));
        return null;
    }

    @Override
    public Void visit(LogicalExpression logicalExpression, Type returnType) {
        logicalExpression.getLeftExpr().accept(this, returnType);
        logicalExpression.getRightExpr().accept(this, returnType);
        logicalExpression.setLvalue(false);
        /*
         * P: LogicalExpression: expression1 -> expression2 expression3
         * R: expression1.type = expression2.type.logical(expression3.type)
         */
        Expression leftExpr = logicalExpression.getLeftExpr();
        Expression rightExpr = logicalExpression.getRightExpr();
        logicalExpression.setType(leftExpr.getType().logical(rightExpr.getType(),
                logicalExpression.getLine(), logicalExpression.getColumn()));
        return null;
    }

    @Override
    public Void visit(IntLiteral intLiteral, Type returnType) {
        intLiteral.setLvalue(false);
        intLiteral.setType(new Int());
        return null;
    }

    @Override
    public Void visit(IndexExpression indexExpression, Type returnType) {
        indexExpression.getFirstExpr().accept(this, returnType);
        indexExpression.getSecExpr().accept(this, returnType);
        indexExpression.setLvalue(true);
        /*
        * P: IndexExpression: expression1 -> expression2 expression3
        * R: expression1.type = expression2.type.squareBrackets(expression3.type)
        * */
        Expression firstExpr = indexExpression.getFirstExpr();
        Expression secondExpr = indexExpression.getSecExpr();
        indexExpression.setType(firstExpr.getType().squareBrackets(secondExpr.getType(),
                indexExpression.getLine(), indexExpression.getColumn()));
        return null;
    }

    @Override
    public Void visit(FuncInvocation funcInvocation, Type returnType) {
        funcInvocation.getVariable().accept(this, returnType);
        funcInvocation.getExpressions().forEach(expr -> expr.accept(this, returnType));
        funcInvocation.setLvalue(false);
        /*
        * P: FunctionInvocation: expression1 -> expression2 expression*
        * R: expression1.type = expression2.type.parenthesis(
        *                           expression*.stream().map(expr -> expr.type).toList()
        *                           );
        * P: FunctionInvocation: statement -> expression expression*
        * R: expression.type.parenthesis(
        *               expression*.stream().map(expr -> expr.type).toList()
        *               );
        * */
        funcInvocation.setType(funcInvocation.getVariable().getType().parenthesis(funcInvocation.getExpressions()
                .stream().map(Expression::getType).toList(),
                funcInvocation.getLine(), funcInvocation.getColumn()));
        return null;
    }

    @Override
    public Void visit(FieldAccessExpression fieldAccessExpression, Type returnType) {
        fieldAccessExpression.getExpr().accept(this, returnType);
        fieldAccessExpression.setLvalue(true);
        /*
         * P: FieldAccessExpression: expression1 -> expression2 expression3
         * R: expression1.type = expression2.type.accessField(expression3.type)
         */
        fieldAccessExpression.setType(fieldAccessExpression.getExpr().getType().dot(fieldAccessExpression.getFieldName(), fieldAccessExpression.getLine(), fieldAccessExpression.getColumn()));
        return null;
    }

    @Override
    public Void visit(CharLiteral charLiteral, Type returnType) {
        charLiteral.setLvalue(false);
        charLiteral.setType(new Char());
        return null;
    }

    @Override
    public Void visit(Cast cast, Type returnType) {
        cast.getExpr().accept(this, returnType);
        cast.getType().accept(this, returnType);
        cast.setLvalue(false);
        /*
         * P: Cast: expression1 -> type expression2
         * R: expression1.type = expression2.type.castTo(type);
         */
        cast.setType(cast.getExpr().getType().cast(cast.getType(), cast.getLine(), cast.getColumn()));
        return null;
    }

    @Override
    public Void visit(ArithmeticExpression arithmeticExpression, Type returnType) {
        arithmeticExpression.getLeftExpr().accept(this, returnType);
        arithmeticExpression.getRightExpr().accept(this, returnType);
        arithmeticExpression.setLvalue(false);
        /*
        * P: Arithmetic: expression1 -> expression2 expression3
        * R: expression1.type = expression2.type.arithmetic(expression3.type)
        */
        arithmeticExpression.setType(arithmeticExpression.getLeftExpr().getType().arithmetic(
                arithmeticExpression.getRightExpr().getType(),
                arithmeticExpression.getLine(), arithmeticExpression.getColumn()));
        return null;
    }

    @Override
    public Void visit(WhileStatement whileStatement, Type returnType) {
        /*
         * P: WhileStmt: statement1 -> expression statement2*
         * R: expression.type.mustBeCondition()
         */
        whileStatement.getStopCondition().accept(this, returnType);
        for( Statement st : whileStatement.getWhileBlock()){
            st.accept(this, returnType);
        }
        whileStatement.setType(whileStatement.getStopCondition().getType().mustBeCondition(whileStatement.getLine(),
                whileStatement.getColumn()));
        return null;
    }

    @Override
    public Void visit(IfStatement ifStatement, Type returnType) {
        /*
         * P: IfStatement: statement1 -> expression statement2*
         * R: expression.type.mustBeCondition()
         */
        ifStatement.getCondition().accept(this, returnType);
        for (Statement st : ifStatement.getIfBlock()) {
            st.accept(this, returnType);
        }
        for (Statement st : ifStatement.getElseBlock()) {
            st.accept(this, returnType);
        }
        ifStatement.setType(ifStatement.getCondition().getType().mustBeCondition(ifStatement.getLine(),
                ifStatement.getColumn()));
        return null;
    }

    @Override
    public Void visit(ReturnStatement returnStatement, Type returnType) {
        /*
         * P: ReturnStatement: statement1 -> expression statement2*
         * R: expression.type.mustBeCondition()
         */
        returnStatement.getReturnValue().accept(this, returnType);
        returnStatement.setType(returnStatement.getReturnValue().getType().mustBeReturnable(returnType,
                returnStatement.getLine(), returnStatement.getColumn()));
        return null;
    }

    @Override
    public Void visit(FuncDefinition funcDefinition, Type returnType) {
        funcDefinition.getType().accept(this, returnType);
        for(VarDefinition var : funcDefinition.getVarDefinitions()) {
            var.accept(this, returnType);
        }
        funcDefinition.getStatements().forEach(stmt -> stmt.accept(this, ((Function) funcDefinition.getType()).getReturnType()));
        return null;
    }

    /**
     * Productions
     * (1) Arithmetic: expression1 -> expression2 expression3
     * (2) ModExpression: expression1 -> expression2 expression3
     * (3) LogicalExpression: expression1 -> expression2 expression3
     * (4) NotExpression: expression1 -> expression2
     * (5) RelationalExpression: expression1 -> expression2 expression3
     * (6) UnaryMinus: expression1 -> expression2
     * (7) Cast: expression1 -> type expression2
     * (8) IndexExpression: expression1 -> expression2 expression3
     * (9) FieldAccessExpression: expression1 -> expression2 expression3
     * (10) FuncInvocation: expression1 -> expression2 expression*
     * (11) FuncInvocation: statement -> expression expression*
     * (12) Assignment: statement -> expression1 expression2
     * (13) ReadStatement: statement -> expression
     * (14) WriteStatement: statement -> expression
     * (15) WhileStmt: statement1 -> expression statement2*
     * (16) IfStatement: statement1 -> expression statement2*
     * (17) FuncDefinition: definition -> type ID definition* statement*
     * (18) ReturnStatement: statement -> expression
     * (19) Variable: expression -> ID
     * (20) CharLiteral: expression -> CHAR_CONSTANT
     * (21) IntLiteral: expression -> INT_CONSTANT
     * (22) RealLiteral: expression -> REAL_CONSTANT
     * --------------------------------------------------------------------
     * Rules
     * (1) expression1.type = expression2.type.arithmetic(expression3.type) D
     * (2) expression1.type = expression2.type.modulus(expression3.type) D
     * (3) expression1.type = expression2.type.logical(expression3.type) D
     * (4) expression1.type = expression2.type.not() D
     * (5) expression1.type = expression2.type.relational(expression3.type) D
     * (6) expression1.type = expression2.type.unary() D
     * (7) expression1.type = expression2.type.cast(type); D
     * (8) expression1.type = expression2.type.squareBrackets(expression3.type) D
     * (9) expression1.type = expression2.type.dot(expression3.type) D
     * (10) expression1.type = expression2.type.parenthesis(
     *              expression*.stream().map(expr -> expr.type).toList()
     *          ); D
     * (11)  expression.type.parenthesis(
     *              expression*.stream().map(expr -> expr.type).toList()
     *          ); D
     * (12) expression1.type.assignment(expression2.type) D
     * (13) expression.type.mustBeReadable(); D
     * (14) expression.type.mustBeWritable(); D
     * (15) expression.type.mustBeCondition();
     *      statement2*.forEach(stmt -> stmt.returnType=statement1.returnType);
     * (16) expression.type.mustBeCondition();
     *      statement2*.forEach(stmt -> stmt.returnType=statement1.returnType);
     * (17) statement*.forEach(stmt -> stmt.returnType=type.returnType);
     * (18) expression.type.mustBeReturnable(stmt.returnType);
     * (19) expression.type = expression.definition != null ? expression.definition.type : new ErrorType(
     *                          expression.getLine(), expression.getColumn(), ""); D
     * (20) expression.type = new Char(); D
     * (21) expression.type = new Int(); D
     * (22) expression.type = new DoubleType(); D
     */
}
