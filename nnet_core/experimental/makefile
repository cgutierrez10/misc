CC=g++
CFLAGS=-Wall
FLAGS=
INC=-I./include
SOURCES=$(shell echo *.cpp)
HEADERS = $(shell echo *.h)
OBJECTS=$(SOURCES:.cpp=.o)
EXECUTABLE=../bin/nnet.exe

#all: $(SOURCES) $(EXECUTABLE)
	
all: $(OBJECTS)

$(EXECUTABLE): $(OBJECTS) 
	$(CC) $(FLAGS) $(INC) $(CFLAGS) $(OBJECTS) -o $@

.cpp.o:
	$(CC) $(FLAGS) $(INC) $(CFLAGS) $< -c -o $@

clean:
	rm -f $(OBJECTS) $(EXECUTABLE)

cleanexe:
	rm -f $(EXECUTABLE)

rebuild:	clean	all
test:	cleanexe	all
	(cd ../bin; $(EXECUTABLE))
