@echo off
echo Compiling Expense Tracker...
javac -source 8 -target 8 -cp lib\mysql-connector-j-8.0.33.jar -d target\classes -sourcepath src\main\java src\main\java\com\expense\*.java src\main\java\com\expense\model\*.java src\main\java\com\expense\util\*.java src\main\java\com\expense\dao\*.java src\main\java\com\expense\gui\*.java

if %ERRORLEVEL% EQU 0 (
    echo Compilation successful!
    echo Running Expense Tracker...
    java -cp "target\classes;lib\mysql-connector-j-8.0.33.jar" com.expense.Main
) else (
    echo Compilation failed!
    pause
)