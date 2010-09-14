
BIN = main3
OBJS = lexer-Exo3.o
LEX=flex

$(BIN): $(OBJS)
	gcc -o $@ $< -lfl

%.o: %.c
	gcc -o $@ -c $<

%.c: %.
	$(LEX) $(LFLAGS) -o $@ $^

clean:
	rm -rf $(OBJS)

distclean: clean
	rm -rf $(BIN)


