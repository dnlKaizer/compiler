package br.com.comcet.tp1.token;

public enum TokenType {
    // Palavras-chave
    IF, ELSE, WHILE,
    BEGIN, END, VAR, PRINT,

    // Operadores
    SUM, SUB, MULT, DIV,
    ASSIGN, LT, GT, EQUALS,

    // Símbolos
    LPAREN, RPAREN, SEMICOLON, COMMA,

    // Identificadores e Literais
    ID, STRING, BOOLEAN, INTEGER;
}
