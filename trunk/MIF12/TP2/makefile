
BIN = main
OBJS = lexer.o parser.o TableDesIdentificateur.o
LEX=flex
SRC = lexer.cpp parser.cpp TableDesIdentificateur.cpp

$(BIN): $(OBJS)
	g++ -o $@ $< -lfl

# Génération des fichiers objets
%.o: %.c
	g++ -o $@ -c $< 

%.c: %.y
	bison -d -o $@ $<

%.c: %.l
	$(LEX) $(LFLAGS) -o $@ $^

TableDesIdentificateur.cpp:TableDesIdentificateur.hpp

clean:
	rm -rf $(OBJS)
	rm -rf $(SRC)

distclean: clean
	rm -rf $(BIN)
