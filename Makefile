# Variables
JC = javac
JVM = java
BIN = bin
LIB = lib/mysql-connector-j-9.3.0.jar
RESOURCES = resources
SRC = src/model/*.java src/presenter/*.java src/view/*.java src/App.java
MAIN = App

# Default target
all: compile

# Target to compile the program
compile:
	@echo "Compiling the program..."
	if not exist $(BIN) mkdir $(BIN)
	$(JC) -d $(BIN) -cp "$(LIB)" $(SRC)

# Target to run the program
run:
	@echo "Running the program..."
	$(JVM) -cp "$(BIN);$(LIB);$(RESOURCES)" $(MAIN)

# Target to clean the bin folder
clean:
	@echo "Cleaning up..."
	rd /s /q $(BIN)

# Target to re-compile and run
re: clean compile run