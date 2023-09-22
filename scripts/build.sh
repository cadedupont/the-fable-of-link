#! /bin/bash

# Exit on error
set -ue

# Attempt to compile, send compiled files to bin
javac src/*.java -d bin

# Run the program inside bin with arguments for window size
java -cp bin Game 700 525