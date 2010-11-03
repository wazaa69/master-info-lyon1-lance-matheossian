/* A Bison parser, made by GNU Bison 2.3.  */

/* Skeleton interface for Bison's Yacc-like parsers in C

   Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2, or (at your option)
   any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor,
   Boston, MA 02110-1301, USA.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     KW_PROGRAM = 258,
     KW_VAR = 259,
     KW_BEGIN = 260,
     KW_END = 261,
     KW_INTEGER = 262,
     KW_REAL = 263,
     KW_BOOLEAN = 264,
     KW_CHAR = 265,
     KW_STRING = 266,
     KW_ARRAY = 267,
     KW_OF = 268,
     KW_TYPE = 269,
     KW_RECORD = 270,
     KW_FUNC = 271,
     KW_PROC = 272,
     KW_CONST = 273,
     KW_DIV = 274,
     KW_MOD = 275,
     KW_AND = 276,
     KW_OR = 277,
     KW_XOR = 278,
     KW_NOT = 279,
     SEP_SCOL = 280,
     SEP_DOT = 281,
     SEP_DOTS = 282,
     SEP_COMMA = 283,
     SEP_CO = 284,
     SEP_CF = 285,
     SEP_DOTDOT = 286,
     SEP_PO = 287,
     SEP_PF = 288,
     OP_PTR = 289,
     OP_SUB = 290,
     OP_EQ = 291,
     OP_NEQ = 292,
     OP_LTE = 293,
     OP_MUL = 294,
     OP_GT = 295,
     OP_GTE = 296,
     OP_ADD = 297,
     OP_LT = 298,
     OP_SLASH = 299,
     TOK_IDENT = 300,
     TOK_INTEGER = 301,
     TOK_REAL = 302,
     TOK_STRING = 303,
     TOK_PTR = 304,
     TOK_BOOLEAN = 305
   };
#endif
/* Tokens.  */
#define KW_PROGRAM 258
#define KW_VAR 259
#define KW_BEGIN 260
#define KW_END 261
#define KW_INTEGER 262
#define KW_REAL 263
#define KW_BOOLEAN 264
#define KW_CHAR 265
#define KW_STRING 266
#define KW_ARRAY 267
#define KW_OF 268
#define KW_TYPE 269
#define KW_RECORD 270
#define KW_FUNC 271
#define KW_PROC 272
#define KW_CONST 273
#define KW_DIV 274
#define KW_MOD 275
#define KW_AND 276
#define KW_OR 277
#define KW_XOR 278
#define KW_NOT 279
#define SEP_SCOL 280
#define SEP_DOT 281
#define SEP_DOTS 282
#define SEP_COMMA 283
#define SEP_CO 284
#define SEP_CF 285
#define SEP_DOTDOT 286
#define SEP_PO 287
#define SEP_PF 288
#define OP_PTR 289
#define OP_SUB 290
#define OP_EQ 291
#define OP_NEQ 292
#define OP_LTE 293
#define OP_MUL 294
#define OP_GT 295
#define OP_GTE 296
#define OP_ADD 297
#define OP_LT 298
#define OP_SLASH 299
#define TOK_IDENT 300
#define TOK_INTEGER 301
#define TOK_REAL 302
#define TOK_STRING 303
#define TOK_PTR 304
#define TOK_BOOLEAN 305




#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef union YYSTYPE
#line 160 "parser.y"
{

	int numeroIdent;
	int numeroInteger;
	int numeroReal;
	int numeroString;
	int numeroPtr;
	int numeroBoolean;
	
	Type* type;
	TypeInterval* typeInterval;
	int interBase;
	TypeArray* typeArray;
	TypePointeur* typePointeur;
	TypeRecord* typeRecord;
    
	bool boolE;
	Expression* expression;

}
/* Line 1489 of yacc.c.  */
#line 170 "parser.h"
	YYSTYPE;
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
# define YYSTYPE_IS_TRIVIAL 1
#endif

extern YYSTYPE yylval;

