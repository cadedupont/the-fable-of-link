:: @echo off

:: Compile Java files in src folder
javac ../src/*.java -d ../bin

:: If compilation was successful, run the program inside bin folder with given arguments for window size
if %ERRORLEVEL% EQU 0 (
	echo "Compilation successful; running game."
	java -cp bin Game 716 539
) else (
	echo "Compilation failed; exiting now."
)