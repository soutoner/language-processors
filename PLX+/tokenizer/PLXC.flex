import java_cup.runtime.*;

%%

// Code (label handling)

%{
	public String newLabel(){
		return Generator.getInstance().newLabel();
	}
%}

//  Declarations

%cup

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

/* Comments */
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment} | {HashComment}

TraditionalComment 	= "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment 	= "//" {InputCharacter}* {LineTerminator}?
DocumentationComment    = "/*" "*"+ [^/*] ~"*/"
HashComment		= "#" {InputCharacter}* {LineTerminator}

%%

/* Expresions and rules */

/* Operators */
    "+"                  		{ return new Symbol(sym.MAS); }
    "-"                  		{ return new Symbol(sym.MENOS); }
    "*"                  		{ return new Symbol(sym.POR); }
    "/"                  		{ return new Symbol(sym.DIV); }
    "%"                  		{ return new Symbol(sym.MOD); }
    "?"                  		{ return new Symbol(sym.ASK, newLabel()); }
    "++"                  		{ return new Symbol(sym.INCR, yytext()); }
    "--"                  		{ return new Symbol(sym.DECR, yytext()); }
    "+="                  		{ return new Symbol(sym.MASEQ); }
    "-="                  		{ return new Symbol(sym.SUBEQ); }
    "*="                  		{ return new Symbol(sym.POREQ); }
    "/="                  		{ return new Symbol(sym.DIVEQ); }
    "(int)"                     { return new Symbol(sym.CASTINT); }
    "(float)"                   { return new Symbol(sym.CASTFLOAT); }
/* Precedence */
    "("                  		{ return new Symbol(sym.AP); }
    ")"                  		{ return new Symbol(sym.CP); }
    "{"                  		{ return new Symbol(sym.ALL); }
    "}"                  		{ return new Symbol(sym.CLL); }
    "["                  		{ return new Symbol(sym.AC); }
    "]"                  		{ return new Symbol(sym.CC); }
/* Delimiter */
    ";"                  		{ return new Symbol(sym.PYC); }
    ","                  		{ return new Symbol(sym.COMA); }
    ":"                  		{ return new Symbol(sym.PP); }
/* Assignations */
    "="                  		{ return new Symbol(sym.ASIG); }
/* Comparisons */
    "=="                 		{ return new Symbol(sym.EQ); }
    "!="                 		{ return new Symbol(sym.NEQ); }
    "<"                  		{ return new Symbol(sym.LOW); }
    "<="                 		{ return new Symbol(sym.LOE); }
    ">"                  		{ return new Symbol(sym.GRE); }
    ">="                 		{ return new Symbol(sym.GOE); }
/* Logical */
    "!"                  		{ return new Symbol(sym.NOT); }
    "&&"                 		{ return new Symbol(sym.AND); }
    "||"                 		{ return new Symbol(sym.OR); }
/* Code */
    "if"                 		{ return new Symbol(sym.IF, newLabel()); }
    "else"               		{ return new Symbol(sym.ELSE); }
    "switch"               		{ return new Symbol(sym.SWITCH, newLabel()); }
    "while"              		{ return new Symbol(sym.WHILE, newLabel()); }
    "do"                 		{ return new Symbol(sym.DO, newLabel()); }
    "for"                		{ return new Symbol(sym.FOR, newLabel()); }
    "print"              		{ return new Symbol(sym.PRINT); }
    "int"              		    { return new Symbol(sym.INT); }
    "float"              		{ return new Symbol(sym.FLOAT); }
    "to"              		    { return new Symbol(sym.TO, newLabel()); }
    "downto"              		{ return new Symbol(sym.DOWNTO, newLabel()); }
    "step"              		{ return new Symbol(sym.STEP); }
    "break"              		{ return new Symbol(sym.BREAK, yytext()); }
    "case"              		{ return new Symbol(sym.CASE, newLabel()); }
    "default"              		{ return new Symbol(sym.DEFAULT, newLabel()); }
    "in"              		    { return new Symbol(sym.IN); }
    "true"              		{ return new Symbol(sym.TRUE); }
    "false"              		{ return new Symbol(sym.FALSE); }
/* Numbers */
    0|[1-9][0-9]*        		        { return new Symbol(sym.ENTERO, new Integer(yytext()) ); }
    [0-9]*\.?[0-9]+([eE][-+]?[0-9]+)?   { return new Symbol(sym.REAL, new Double(yytext())); }
/* Comments */
    {Comment}					{ }
/* Identifiers */
    [_a-zA-Z$][_a-zA-Z0-9$]*    { return new Symbol(sym.IDENT, yytext()); }
/* Others */
    \ |\t\f              		{  }  
    [^]                  		{ /*throw new Error("Illegal character <"+yytext()+">");*/ }
