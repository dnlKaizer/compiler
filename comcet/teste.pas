program Teste;

var
  s, nome: string;
  n, total, i: integer;
  p, achou: boolean;

function soma(a, b: integer): integer;
begin
  soma := a + b
end;

function saudacao(nome: string; idade: integer): string;
var
  prefixo: string;
begin
  prefixo := 'Ola, ';
  saudacao := prefixo + nome
end;

function ehPar(x: integer): boolean;
begin
  ehPar := (x = (x / 2) * 2)
end;

procedure linha();
begin
  writeln('--------------------')
end;

(* main *)
begin
  linha();

  { Variaveis globais: STRING, INTEGER, BOOLEAN }
  s := 'Testando o compilador';
  writeln(s);

  { Function com 2 parametros e retorno INTEGER }
  total := soma(7, 5);
  write('soma(7, 5) = ');
  writeln(total);

  { Function com parametros mistos (string, integer) e retorno STRING,
    com variavel local fazendo shadow do nome global "nome" }
  nome := 'Mundo';
  s := saudacao(nome, 10);
  writeln(s);

  { If-then-else }
  if total > 10 then
    writeln('total e maior que 10')
  else
    writeln('total nao e maior que 10');

  { If-then SEM else }
  if total = 12 then
    writeln('total e exatamente 12');

  { Operadores logicos: and, or, xor, not }
  p := true;
  achou := false;
  if (p and (not achou)) then
    writeln('p verdadeiro e achou falso');

  if (p or achou) then
    writeln('p ou achou e verdadeiro');

  if (p xor achou) then
    writeln('p xor achou e verdadeiro');

  { Operadores relacionais variados }
  if (total >= 12) and (total <= 20) then
    writeln('total esta entre 12 e 20');

  if (total <> 0) then
    writeln('total e diferente de zero');

  { While }
  i := 0;
  total := 0;
  while i < 5 do
  begin
    total := total + i;
    i := i + 1
  end;
  write('soma de 0 a 4 (while) = ');
  writeln(total);

  { Repeat-until }
  i := 0;
  repeat
    begin
      write('i = ');
      writeln(i);
      i := i + 1
    end
  until i = 3;

  { Procedure chamada como CallCommand }
  linha();

  { Function booleana usada em if }
  n := 8;
  if ehPar(n) then
    writeln('n e par')
  else
    writeln('n e impar');

  { Write com multiplos argumentos de tipos diferentes (forca convertToStringIfNeeded) }
  write('Resumo: total=');
  write(total);
  write(', i=');
  write(i);
  write(', p=');
  writeln(p);

  { Read e readln }
  write('Digite seu nome: ');
  readln(nome);
  write('Digite sua idade (numero inteiro): ');
  readln(n);
  write('Voce gosta de Pascal? (true/false): ');
  readln(achou);

  write('Ola ');
  write(nome);
  write(', voce tem ');
  write(n);
  if achou then
    writeln(' anos e gosta de Pascal!')
  else
    writeln(' anos e nao gosta de Pascal.');

  linha();
  writeln('Fim do teste.')
end.