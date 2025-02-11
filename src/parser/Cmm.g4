grammar Cmm;	

program:
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