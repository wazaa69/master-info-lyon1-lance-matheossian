
BIN = main
OBJS = lexer.o parser.o
LEX=flex
SRC = lexer.c parser.c

$(BIN): $(OBJS)
	gcc -o $@ $< -lfl

# Génération des fichiers objets
%.o: %.c
	gcc -o $@ -c $< 

%.c: %.y
	bison -d -o $@ $<

%.c: %.l
	$(LEX) $(LFLAGS) -o $@ $^

clean:
	rm -rf $(OBJS)
	rm -rf $(SRC)

distclean: clean
	rm -rf $(BIN)




