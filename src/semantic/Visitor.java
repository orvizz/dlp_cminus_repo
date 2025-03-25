package semantic;

import ast.expressions.*;
import ast.program.FuncDefinition;
import ast.program.Program;
import ast.program.VarDefinition;
import ast.statements.*;
import ast.types.*;

public interface Visitor <TP,TR> {

    TR visit(Program p, TP param);

    TR visit(Variable v, TP param);

    TR visit(WhileStatement whileStatement, TP param);

    TR visit(FuncDefinition funcDefinition, TP param);

    TR visit(VarDefinition varDefinition, TP param);

    TR visit(WriteStatement writeStatement, TP param);

    TR visit(ReturnStatement returnStatement, TP param);

    TR visit(ReadStatement readStatement, TP param);

    TR visit(IfStatement ifStatement, TP param);

    TR visit(Assignment assignment, TP param);

    TR visit(UnaryMinus unaryMinus, TP param);

    TR visit(RelationalExpression relationalExpression, TP param);

    TR visit(RealLiteral realLiteral, TP param);

    TR visit(NotExpression notExpression, TP param);

    TR visit(ModExpression modExpression, TP param);

    TR visit(LogicalExpression logicalExpression, TP param);

    TR visit(IntLiteral intLiteral, TP param);

    TR visit(IndexExpression indexExpression, TP param);

    TR visit(FuncInvocation funcInvocation, TP param);

    TR visit(FieldAccessExpression fieldAccessExpression, TP param);

    TR visit(CharLiteral charLiteral, TP param);

    TR visit(Cast cast, TP param);

    TR visit(ArithmeticExpression arithmeticExpression, TP param);

    TR visit(ErrorType errorType, TP param);

    TR visit(Array array, TP param);

    TR visit(Char aChar, TP param);

    TR visit(DoubleType doubleType, TP param);

    TR visit(Function function, TP param);

    TR visit(Int anInt, TP param);

    TR visit(Struct struct, TP param);

    TR visit(VoidType voidType, TP param);
}
