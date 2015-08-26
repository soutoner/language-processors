import java_cup.runtime.*;

%%

/* Code (tag handling) */

%{
	public static int actualTag = 0;

	public static String newTag(){
		return "L"+(actualTag++);	
	}
%}

/*  Declarations */ 
   
%cup 

%%   

/* Expresions and rules */

/* Operators */
    "+"                  		{ return new Symbol(sym.MAS); }
    "-"                  		{ return new Symbol(sym.MENOS); }
    "*"                  		{ return new Symbol(sym.POR); }
    "/"                  		{ return new Symbol(sym.DIV); }
/* Precedence */
    "("                  		{ return new Symbol(sym.AP); }
    ")"                  		{ return new Symbol(sym.CP); }
    "{"                  		{ return new Symbol(sym.ALL); }
    "}"                  		{ return new Symbol(sym.CLL); }
/* Delimiter */
    ";"                  		{ return new Symbol(sym.PYC); }
    ","                  		{ return new Symbol(sym.COMMA); }
/* Assignations */
    "="                  		{ return new Symbol(sym.ASIG); }
/* Comparisons */
    "<"                  		{ return new Symbol(sym.LOW); }
    ">"                  		{ return new Symbol(sym.GRE); }
/* Code */   
    "if"                 		{ return new Symbol(sym.IF, newTag()); }
    "else"               		{ return new Symbol(sym.ELSE); }
    "while"              		{ return new Symbol(sym.WHILE, newTag()); }
    "int"              		    { return new Symbol(sym.INT); }
    "return"              		{ return new Symbol(sym.RETURN); }
/* Numbers */
    0|[1-9][0-9]*        		{ return new Symbol(sym.ENTERO, new Integer(yytext()) ); }
/* Identifiers */
    [_a-zA-Z$][_a-zA-Z0-9$]*	{ return new Symbol(sym.IDENT, yytext()); }
/* Others */
    \ |\t\f              		{  }  
    [^]                  		{ /*throw new Error("Illegal character <"+yytext()+">");*/ }
