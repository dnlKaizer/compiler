package br.com.comcet.tp1.token;

public enum TokenType {
    // Palavras-chave
    VAR, PROGRAM,
    IF, THEN, ELSE,
    WHILE, DO,
    BEGIN, END,
    WRITELN, READLN,
    REPEAT, UNTIL,

    // Operadores
    SUM, SUB, MULT, DIV,
    NOT_EQUAL, EQUAL,
    LESS_THAN, GREATER_THAN,
    GREATER_EQUAL, LESS_EQUAL,
    ASSIGN, COLON,
    AND, OR, NOT, XOR,

    // Símbolos
    LPAREN, RPAREN, SEMICOLON, COMMA,

    // Identificadores e Literais
    ID, STRING_TYPE, BOOLEAN_TYPE, INTEGER_TYPE,
    STRING_LITERAL, BOOLEAN_LITERAL, INTEGER_LITERAL,

    // Fim de arquivo
    EOF;

    public boolean isLiteral() {
        return this == STRING_LITERAL || this == BOOLEAN_LITERAL || this == INTEGER_LITERAL;
    }

    public boolean isType() {
        return this == STRING_TYPE || this == BOOLEAN_TYPE || this == INTEGER_TYPE;
    }

    public boolean isOperator() {
        return this == SUM || this == SUB || this == MULT || this == DIV ||
                this == LESS_THAN || this == GREATER_THAN || this == GREATER_EQUAL ||
                this == LESS_EQUAL || this == NOT_EQUAL || this == EQUAL;
    }
}
