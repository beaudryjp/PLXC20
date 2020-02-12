import java_cup.runtime.*;
%%
//%debug
%cup

Int = 0|[1-9][0-9]*
Quotes = \'[^']*\'
Ini_str = \"[^\"]*\"
Rexp = [eE][+-]?[0-9]+
Real = (0|[0-9])+\.[0-9]*{Rexp}?|[0-9]*\.(0|[0-9])+{Rexp}?|[0-9]+{Rexp}
Variable = [a-zA-Z_][a-zA-Z0-9]*

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*
//Comment =  {TraditionalComment} | {EndOfLineComment} | {DocumentationComment} | \/\*[^]*\*\/
Comment =  {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

%%

/* Reserved words */
"int"                   { return new Symbol(sym.INT); }
"float"                 { return new Symbol(sym.FLOAT); }
"char"                  { return new Symbol(sym.CHAR); }
"string"                { return new Symbol(sym.STRING); }
"to"                    { return new Symbol(sym.TO); }
"in"                    { return new Symbol(sym.IN); }
"downto"                { return new Symbol(sym.DOWNTO); }
"step"                  { return new Symbol(sym.STEP); }
"if"                    { return new Symbol(sym.IF); }
"else"                  { return new Symbol(sym.ELSE); }
"while"                 { return new Symbol(sym.WHILE); }
"do"                    { return new Symbol(sym.DO); }
"for"                   { return new Symbol(sym.FOR); }
"print"                 { return new Symbol(sym.PRINT); }
"void"                  { return new Symbol(sym.VOID); }
"return"                { return new Symbol(sym.RETURN); }
".length"               { return new Symbol(sym.LENGTH); }

/* Separators */
"("                     { return new Symbol(sym.OP); }
")"                     { return new Symbol(sym.CP); }
"{"                     { return new Symbol(sym.OB); }
"}"                     { return new Symbol(sym.CB); }
"["                     { return new Symbol(sym.OSB); }
"]"                     { return new Symbol(sym.CSB); }
";"                     { return new Symbol(sym.SEMICOLON); }
","                     { return new Symbol(sym.COMMA); }
":"                     { return new Symbol(sym.COLON); }

/* Operators */
"++"                    { return new Symbol(sym.INC); }
"--"                    { return new Symbol(sym.DEC); }
"%"                     { return new Symbol(sym.MOD); }
"="                     { return new Symbol(sym.ASIG); }
"+"                     { return new Symbol(sym.ADD); }
"-"                     { return new Symbol(sym.SUB); }
"*"                     { return new Symbol(sym.MUL); }
"/"                     { return new Symbol(sym.DIV); }
"&"                     { return new Symbol(sym.AMP); }
"~"                     { return new Symbol(sym.TILDE); }

/* Conditions */
"<"                     { return new Symbol(sym.LT); }
">"                     { return new Symbol(sym.GT); }
"<="                    { return new Symbol(sym.LTOREQ); }
">="                    { return new Symbol(sym.GTOREQ); }
"=="                    { return new Symbol(sym.EQ); }
"!="                    { return new Symbol(sym.NOTEQ); }
"&&"                    { return new Symbol(sym.AND); }
"||"                    { return new Symbol(sym.OR); }
"!"                     { return new Symbol(sym.NOT); }

/* Numbers, chars 6 variables */
{Int}                   { return new Symbol(sym.NUM, yytext()); }
{Real}                  { return new Symbol(sym.REAL, yytext());}  
{Quotes}                { return new Symbol(sym.QUOTES, yytext()); }
{Ini_str}               { return new Symbol(sym.INISTRING, yytext()); }
{Variable}              { return new Symbol(sym.VAR, yytext()); }

/* Variables */



/* Everything else */
{Comment}               {}
\s                      {}
[^]                     { throw new Error("# ERROR: Invalid argument <" + yytext() + ">."); }
