# Compilateur et linker
LEX = flex
YACC = bison -y
CC = gcc
LD = gcc

# Drapeaux
CFLAGS = -ansi -pedantic -I $(INC)
LDFLAGS = -lfl
YFLAGS  = -d

# Dossiers
BIN = ./bin
INC = ./inc
OBJ = ./obj
RES = ./res
SRC = ./src

# Nom du parser et du lexer 
LEXER = lexer
PARSER = parser

# Nom du programme
EXEC = $(PARSER).exe

# Nom des fichiers de test
TEST_FILE1 = tri.pas
TEST_FILE2 = test.c

# Création de la liste des objets
LSRCS = $(wildcard $(SRC)/*.l)
YSRCS = $(wildcard $(SRC)/*.y)
CSRCS = $(YSRCS:$(SRC)/%.y=$(SRC)/%.c) $(LSRCS:$(SRC)/%.l=$(SRC)/%.c)
OBJS = $(CSRCS:$(SRC)/%.c=$(OBJ)/%.o)

all: $(BIN)/$(EXEC)

$(BIN)/%.exe: $(OBJS)
	@mkdir -p $(BIN)
	$(LD) -o $@ $^ $(LDFLAGS) 

$(OBJ)/%.o: $(SRC)/%.c
	@mkdir -p $(OBJ)
	$(CC) $(CFLAGS) -o $@ -c $< 
        
$(SRC)/$(LEXER).c: $(SRC)/$(LEXER).l
	$(LEX) -o $@ $<
        
$(SRC)/$(PARSER).c: $(SRC)/$(PARSER).y
	@mkdir -p $(INC)
	$(YACC) $(YFLAGS) -o $@ $<
	mv $(SRC)/$(PARSER).h  $(INC)/$(PARSER).h

exec: $(BIN)/$(EXEC)
	@echo Test imbrication correcte
	$(BIN)/$(EXEC) $(RES)/$(TEST_FILE1)
	@echo Test imbrication incorrecte
	$(BIN)/$(EXEC) $(RES)/$(TEST_FILE2)
        
clean:
	rm -rf $(OBJ) $(INC) $(SRC)/*.c

distclean: clean
	rm -rf $(BIN)
