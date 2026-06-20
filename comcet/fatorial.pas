program Fatorial;

var
  n : integer;

{ Função Fatorial }
function fat(num: integer): integer;
begin
  if (num = 0) then
    fat := 1
  else
    fat := num * fat(num - 1)
end;

(* main *)
begin
  write('Digite um número para calcular o fatorial: ');
  read(n);
  writeln('O resultado de fatorial de ', n, ' é ', fat(n));
end.