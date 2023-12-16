:: @echo off

:: Set width and height of game window
set WIDTH=716
set HEIGHT=539

:: Attempt to compile game
javac -d bin src/*.java

:: If compilation fails, exit, otherwise run game
if %ERRORLEVEL% EQU 0 (
	java -cp bin Game %WIDTH% %HEIGHT%
)