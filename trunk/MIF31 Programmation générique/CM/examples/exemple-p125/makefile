CPPFLAGS = -O2 -DNDEBUG -Wall 
LDFLAGS  =

CIBLE   = a.out
OBJECTS = module1.o main.o

$(CIBLE): $(OBJECTS)
	$(CXX) $(LDFLAGS) $(OBJECTS) -o $(CIBLE)

module1.o: module1.hh
main.o: module1.hh

.PHONY : clean
clean :
	-rm $(CIBLE) $(OBJECTS)
