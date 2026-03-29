package br.com.comcet.tp1.token;

public enum TokenType {
    // Palavras-chave
    IF, THEN, ELSE,
    WHILE, DO,
    BEGIN, END, VAR,
    WRITELN, READLN,
    REPEAT, UNTIL,

    // Operadores
    SUM, SUB, MULT, DIV,
    LESS_THAN, GREATER_THAN, GREATER_EQUAL, 
    LESS_EQUAL, NOT_EQUAL, EQUAL,
    ASSIGN, COLON,

    // Símbolos
    LPAREN, RPAREN, SEMICOLON, COMMA,

    // Identificadores e Literais
    ID, STRING_TYPE, BOOLEAN_TYPE, INTEGER_TYPE,
    STRING_LITERAL, BOOLEAN_LITERAL, INTEGER_LITERAL,
    
    // Fim de arquivo
    EOF;
}
