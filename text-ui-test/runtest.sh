#!/usr/bin/env bash

SCRIPT_DIR=$(cd "$(dirname "$0")" && pwd)
PROJECT_ROOT=$(cd "$SCRIPT_DIR/.." && pwd)

cd "$PROJECT_ROOT"

# create bin directory if it doesn't exist
if [ ! -d "bin" ]
then
    mkdir bin
fi

# delete output from previous run
if [ -e "$SCRIPT_DIR/ACTUAL.TXT" ]
then
    rm "$SCRIPT_DIR/ACTUAL.TXT"
fi

# remove previous data file to ensure deterministic test state
if [ -e "data/dooki.txt" ]
then
    rm "data/dooki.txt"
fi

# compile the code into the bin folder, terminates if error occurred
if ! javac -cp src/main/java -Xlint:none -d bin src/main/java/Dooki.java
then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

# run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath bin Dooki < "$SCRIPT_DIR/input.txt" > "$SCRIPT_DIR/ACTUAL.TXT"

# compare the output to the expected output
diff "$SCRIPT_DIR/ACTUAL.TXT" "$SCRIPT_DIR/EXPECTED.TXT"
if [ $? -eq 0 ]
then
    echo "Test result: PASSED"
    exit 0
else
    echo "Test result: FAILED"
    exit 1
fi