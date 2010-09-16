# user dirs
SRC_DIR         = ./src/
OBJ_DIR         = ./obj/
DEP_DIR         = ./dep/
BIN_DIR         = ./

# bin name
BIN             = ${shell basename `pwd`}

# additionnal lib and includes dir
LIB_DIR         = ./
INC_DIR         = ./

# compile commands
CC      = g++
CPP     = g++
LD      = g++
FLEX    = flex
BISON   = bison

# flags and libs
CPPFLAGS        = -I$(INC_DIR) -g -Wall -pipe
CFLAGS          = -I$(INC_DIR) -g -Wall -pipe
LDFLAGS = -lfl #pour flex

SRCS_CPP        = $(wildcard $(SRC_DIR)*.cpp)
SRCS_C          = $(wildcard $(SRC_DIR)*.c)
SRCS_L          = $(wildcard $(SRC_DIR)*.l)
SRCS_Y          = $(wildcard $(SRC_DIR)*.y)

#Dépendances pour les .cpp et .c
DEPS    = $(SRCS_CPP:$(SRC_DIR)%.cpp=$(DEP_DIR)%.d) $(SRCS_C:$(SRC_DIR)%.c=$(DEP_DIR)%.d)
#OBJS   = $(SRCS_CPP:$(SRC_DIR)%.cpp=$(OBJ_DIR)%.o) $(SRCS_C:$(SRC_DIR)%.c=$(OBJ_DIR)%.o)

OBJS    = $(SRCS_CPP:$(SRC_DIR)%.cpp=$(OBJ_DIR)%.o) $(SRCS_C:$(SRC_DIR)%.c=$(OBJ_DIR)%.o) \
          $(SRCS_Y:$(SRC_DIR)%.y=$(OBJ_DIR)%.o) $(SRCS_L:$(SRC_DIR)%.l=$(OBJ_DIR)%.o)




all: $(BIN_DIR)/$(BIN)

#Exécutable
$(BIN_DIR)/$(BIN): $(OBJS)
	$(LD) $+ -o $@ $(LDFLAGS)

#.C --> .o
$(OBJ_DIR)%.o: $(SRC_DIR)%.c
	$(CC) $(CFLAGS) -o $@ -c $<

$(DEP_DIR)%.d: $(SRC_DIR)%.c
	$(CC) $(CFLAGS) -MM -MD -o $@ $<


#Flex et Bison --> .c
$(SRC_DIR)%.c : $(SRC_DIR)lexer.l
	$(FLEX) -o $@ $< 

$(SRC_DIR)%.c : $(SRC_DIR)%.y
	$(BISON) -d -o $@ $<


#.CPP --> .o
$(OBJ_DIR)%.o: $(SRC_DIR)%.cpp
	$(CPP) $(CPPFLAGS) -o $@ -c $<

$(DEP_DIR)%.d: $(SRC_DIR)%.cpp
	$(CPP) $(CPPFLAGS) -MM -MD -o $@ $<


-include $(DEPS)




.PHONY: clean distclean


run: $(BIN_DIR)/$(BIN)
	$(BIN_DIR)/$(BIN)

gdb: $(BIN_DIR)/$(BIN)
	gdb $(BIN_DIR)/$(BIN)

valgrind: $(BIN_DIR)/$(BIN)
	valgrind $(BIN_DIR)/$(BIN)

clean:
	rm -f $(OBJ_DIR)*.o $(SRC_DIR)*~ $(DEP_DIR)*.d *~

distclean: clean
	rm -f $(BIN_DIR)/$(BIN)

tar: clean
	tar -cvzf ../${shell basename `pwd`}.tgz ../${shell basename `pwd`}
