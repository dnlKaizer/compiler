grammar MiniPascal;
program: 'program' ID ';' varDeclarations? subroutineDeclarations? blockCommand '.' EOF;

varDeclarations: 'var' (varDeclaration ';')+;
varDeclaration: ID (',' ID)* ':' VAR_TYPE;

subroutineDeclarations: subroutineDeclaration+;
subroutineDeclaration: 
    'function' ID '(' parameters? ')' ':' returnType=VAR_TYPE ';' varDeclarations? blockCommand ';'
    | 'procedure' ID '(' parameters? ')' ';' varDeclarations? blockCommand ';';

parameters: varDeclaration (';' varDeclaration)*;

expression:
	// Nível mais alto: Unário e parênteses
	'(' expression ')'		# ParenExpr
	| op = 'not' expression	# UnaryExpr

	// Nível 2: Multiplicação, divisão e and lógico
	| left = expression op = ('*' | '/' | 'and') right = expression # BinaryExpr

	// Nível 3: Adição, subtração e or lógico
	| left = expression op = ('+' | '-' | 'or') right = expression # BinaryExpr

	// Nível 4: Expressões relacionais e de igualdade
	| left = expression op = ('=' | '<>' | '<' | '>' | '<=' | '>=') right = expression # BinaryExpr

	// Termos base (Folhas da AST)
	| INTEGER	                # LitInt
	| BOOLEAN	                # LitBool
	| STRING	                # LitStr
    | ID '(' arguments? ')'	    # FunctionCall
	| ID		                # Identifier;

command:
	assignmentCommand
	| ifCommand
	| whileCommand
	| repeatCommand
	| blockCommand
	| callCommand;

assignmentCommand: ID ':=' expression;
blockCommand: 'begin' command (';' command)* ';'? 'end';
ifCommand: 'if' expression 'then' command ('else' command)?;
whileCommand: 'while' expression 'do' command;
repeatCommand: 'repeat' command 'until' expression;
callCommand: ID '(' arguments? ')';

arguments: expression (',' expression)*;

// Lexer
VAR_TYPE: 'integer' | 'boolean' | 'string';
BOOLEAN: 'true' | 'false';
ID: [a-zA-Z][a-zA-Z0-9]*;
INTEGER: [0-9]+;
WHITESPACE: [ \t\r\n]+ -> skip;

STRING: '\'' ( ESCAPE | ~('\\' | '\''))* '\'';
fragment ESCAPE: '\\' [ntr\\"];