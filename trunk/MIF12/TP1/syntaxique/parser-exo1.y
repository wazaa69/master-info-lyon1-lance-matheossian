%start E
%token plus
%token fois
%token cste
%%
E : E plus E
| E fois E
| cste
;
%%
int main ( int argc, char** argv ) {
yyparse ();
}
