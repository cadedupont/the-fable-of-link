#! /bin/bash

# Declare variables for storing window size
width=700
height=525

# Exit on error
set -ue

# Attempt to compile, send compiled files to bin
javac -d bin src/*.java

# Run the program inside bin with arguments for window size
java -cp bin Game $width $height