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
     SEP_SCOL = 269,
     SEP_DOT = 270,
     SEP_DOTS = 271,
     SEP_COMMA = 272,
     SEP_CO = 273,
     SEP_CF = 274,
     SEP_DOTDOT = 275,
     OP_PTR = 276,
     OP_SUB = 277,
     TOK_IDENT = 278,
     TOK_INTEGER = 279
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
#define SEP_SCOL 269
#define SEP_DOT 270
#define SEP_DOTS 271
#define SEP_COMMA 272
#define SEP_CO 273
#define SEP_CF 274
#define SEP_DOTDOT 275
#define OP_PTR 276
#define OP_SUB 277
#define TOK_IDENT 278
#define TOK_INTEGER 279




#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef union YYSTYPE
#line 67 "parser.y"
{

    int numero;
    Type* type;
    TypeInterval* typeInterval;
    char* interBase;
    TypeArray* typeArray;


}
/* Line 1489 of yacc.c.  */
#line 108 "parser.h"
	YYSTYPE;
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
# define YYSTYPE_IS_TRIVIAL 1
#endif

extern YYSTYPE yylval;

