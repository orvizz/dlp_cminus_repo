grammar Cmm;

@header {
    import ast.*;
    import ast.expressions.*;
    import ast.program.*;
    import ast.statements.*;
    import ast.types.*;
    import ast.factories.*;
}

program returns [Program ast]
        locals [List<Definition> programBody = new ArrayList<>()]:
        d=definitions { $programBody.addAll($d.ast); }
        m=mainFuncDefinition { $programBody.add($m.ast); } EOF
        { $ast = new Program($programBody); }
       ;

definitions returns [List<Definition> ast = new ArrayList<>()]:
    (v=variableDefinition { $ast.addAll($v.ast); } | f=funcDefinition { $ast.add($f.ast); })*
    ;

mainFuncDefinition returns [FuncDefinition ast]:
            v='void' m='main' '(' ')' '{' fv=funcBodyVariables fs=funcBodyStatements '}'
            { $ast = new FuncDefinition($v.getLine(), $v.getCharPositionInLine()+1, $m.text, new Function(new VoidType()), $fv.ast, $fs.ast);}
            ;

variableDefinition returns [List<VarDefinition> ast = new ArrayList<>()]:
            t=type i1=ID { $ast.add(new VarDefinition($i1.getLine(), $i1.getCharPositionInLine()+1, $i1.text, $t.ast));}
            (',' i2=ID{ $ast.add(new VarDefinition($i2.getLine(), $i2.getCharPositionInLine()+1, $i2.text, $t.ast));})* ';'
        ;
funcDefinition returns [FuncDefinition ast]
                locals [Type funcType]:
        (t=builtInType { $funcType = $t.ast; }|v='void' {$funcType = new VoidType();}) i1=ID '('  p=paramDeclaration ')' '{' fv=funcBodyVariables fs=funcBodyStatements '}'
        {$ast = new FuncDefinition($i1.getLine(), $i1.getCharPositionInLine()+1, $i1.text, new Function($funcType, $p.ast), $fv.ast, $fs.ast);}
        ;

funcBodyVariables returns [List<VarDefinition> ast = new ArrayList<>()]:
        | (v=variableDefinition { $ast.addAll($v.ast); })+
        ;

funcBodyStatements returns [List<Statement> ast = new ArrayList<>()]:
        | (s=statement { $ast.addAll($s.ast); })+
        ;

paramDeclaration returns [List<VarDefinition> ast = new ArrayList<>()]:
    | t1=builtInType i1=ID { $ast.add(new VarDefinition($i1.getLine(), $i1.getCharPositionInLine()+1, $i1.text, $t1.ast)); }
        (',' t2=builtInType i2=ID { $ast.add(new VarDefinition($i2.getLine(), $i2.getCharPositionInLine()+1, $i2.text, $t2.ast)); })*
    ;


expression returns [Expression ast]:
        ID { $ast = new Variable( $ID.getLine(), $ID.getCharPositionInLine()+1 ,$ID.getText()); }
    | ic=INT_CONSTANT { $ast = new IntLiteral( $ic.getLine(), $ic.getCharPositionInLine()+1,
            LexerHelper.lexemeToInt($ic.text));}
    | rc=REAL_CONSTANT { $ast = new RealLiteral( $rc.getLine(), $rc.getCharPositionInLine()+1,
            LexerHelper.lexemeToReal($rc.text));}
    | cc=CHAR_CONSTANT { $ast = new CharLiteral( $cc.getLine(), $cc.getCharPositionInLine()+1,
            LexerHelper.lexemeToChar($cc.text));}
    | fi=funcInvocation { $ast = $fi.ast;}
    | '(' e1=expression ')' { $ast = $e1.ast; }
    | e1=expression '[' e2=expression ']' { $ast = new IndexExpression($e1.ast.getLine(), $e1.ast.getColumn(),
            $e1.ast, $e2.ast);}
    | e1=expression '.' ID { $ast = new FieldAccessExpression($e1.ast.getLine(), $e1.ast.getColumn(), $ID.text, $e1.ast);}
    | op='(' type ')' e1=expression { $ast = new Cast($op.getLine(), $op.getCharPositionInLine()+1, $type.ast, $e1.ast);}
    | op='-' e1=expression { $ast = new UnaryMinus($op.getLine(), $op.getCharPositionInLine()+1, $e1.ast);}
    | op='!' e1=expression { $ast = new NotExpression($op.getLine(), $op.getCharPositionInLine()+1, $e1.ast);}
    | e1=expression op=('*'|'/'|'%') e2=expression { $ast = ExpressionFactory.createArithmeticExpression($e1.ast.getLine(),
            $e1.ast.getColumn(), $op.text, $e1.ast, $e2.ast);}
    | e1=expression op=('+'|'-') e2=expression { $ast = new ArithmeticExpression($e1.ast.getLine(),
            $e1.ast.getColumn(), $op.text, $e1.ast, $e2.ast);}
    | e1=expression op=('>'|'>='|'<'|'<='|'!='|'==') e2=expression { $ast = new RelationalExpression($e1.ast.getLine(),
            $e1.ast.getColumn(), $op.text, $e1.ast, $e2.ast);}
    | e1=expression op=('&&'|'||') e2=expression {$ast = new LogicalExpression($e1.ast.getLine(),
            $e1.ast.getColumn(), $op.text, $e1.ast, $e2.ast);}
    ;

statement returns [List<Statement> ast = new ArrayList<>()]:
    n='while' '(' e1=expression ')' b=block
        {$ast.add(new WhileStatement($n.getLine(), $n.getCharPositionInLine()+1,
            $e1.ast, $b.ast));}
    | ifStmt {$ast.add($ifStmt.ast);}
    | r='return' e1=expression ';'
        {$ast.add(new ReturnStatement($r.getLine(), $r.getCharPositionInLine()+1,
        $e1.ast));}
    | re=readStmt {$ast.addAll($re.ast);} ';'
    | w=writeStmt { $ast.addAll($w.ast);} ';'
    | funcInvocation ';'
        {$ast.add($funcInvocation.ast);}
    | e1=expression '=' e2=expression ';'
        { $ast.add(new Assignment($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast,
        $e2.ast));}
    ;

readStmt returns [List<ReadStatement> ast = new ArrayList<>()]:
        op='read' e1=expression { $ast.add(new ReadStatement($op.getLine(), $op.getCharPositionInLine()+1, $e1.ast)); }
        (',' e2=expression {$ast.add(new ReadStatement($op.getLine(), $op.getCharPositionInLine()+1, $e2.ast));})*
        ;

writeStmt returns [List<WriteStatement> ast = new ArrayList<>()]:
        op='write'  e1=expression { $ast.add(new WriteStatement($op.getLine(), $op.getCharPositionInLine()+1, $e1.ast)); }
        (',' e2=expression {$ast.add(new WriteStatement($op.getLine(), $op.getCharPositionInLine()+1, $e2.ast));})*
        ;

type returns [Type ast]
    locals [List<RecordField> records = new ArrayList<>();]:
    builtInType {$ast = $builtInType.ast;}
    | t=type '[' ic=INT_CONSTANT ']' { $ast = TypesFactory.createArrayType($t.ast, LexerHelper.lexemeToInt($ic.text));}
    | 'struct' '{' ((rec=recordField { $records.addAll($rec.ast); })+ { $ast = new Struct($records); })+ '}'
    ;

builtInType returns [Type ast]:
    'double' {$ast = new DoubleType();}
    |'int' {$ast = new Int();}
    |'char' {$ast = new Char();}
    ;


recordField returns [List<RecordField> ast = new ArrayList<>()]:
    t=type i1=ID { $ast.add(new RecordField($i1.getLine(), $i1.getCharPositionInLine()+1, $i1.text, $t.ast));}
    (',' i2=ID{ $ast.add(new RecordField($i2.getLine(), $i2.getCharPositionInLine()+1, $i2.text, $t.ast));})* ';'
    ;

ifStmt returns [IfStatement ast]:
    i='if' '(' e=expression ')' b=block { $ast = new IfStatement($i.getLine(), $i.getCharPositionInLine()+1, $e.ast, $b.ast);}
    | i='if' '(' e=expression ')' b1=block 'else' b2=block
    { $ast = new IfStatement($i.getLine(), $i.getCharPositionInLine()+1, $e.ast, $b1.ast, $b2.ast);}
    ;

block returns [List<Statement> ast = new ArrayList<>()]:
    '{' (st=statement {$ast.addAll($st.ast);})* '}'
    | st=statement {$ast.addAll($st.ast);}
    ;

funcInvocation returns [FuncInvocation ast]:
     ID '(' p=params ')' { $ast = new FuncInvocation($ID.getLine(), $ID.getCharPositionInLine()+1,
        new Variable($ID.getLine(), $ID.getCharPositionInLine()+1, $ID.text), $p.ast);}
     ;

params returns [List<Expression> ast = new ArrayList<>()]:
    | e1=expression { $ast.add($e1.ast); } (',' e2=expression {$ast.add($e2.ast);})*
    ;


INT_CONSTANT: [1-9] [0-9]*
            | '0'
            ;

REAL_CONSTANT: FLOAT_CONSTANT
             | (INT_CONSTANT|FLOAT_CONSTANT) ('E'|'e') ('-'|'+')? (INT_CONSTANT|FLOAT_CONSTANT)
             ;

fragment FLOAT_CONSTANT: INT_CONSTANT? '.' [0-9]+
                       | INT_CONSTANT '.' [0-9]*
                       ;

CHAR_CONSTANT: '\'\\n\''
             | '\'\\t\''
             |'\'' . '\''
             | '\'\\' INT_CONSTANT '\''
             ;

ID: [a-zA-Z_] [a-zA-Z_0-9]*
  ;

COMMENT: '//' .*? ('\n'|'\r'|EOF) -> skip
        ;

ML_COMMENT: '/*' .*? '*/' -> skip
          ;

BLANK: [ \t\n\r]+ -> skip
    ;