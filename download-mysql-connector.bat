@echo off
echo Downloading MySQL Connector/J...
curl -L -o lib\mysql-connector-j-8.0.33.jar "https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar"
if %ERRORLEVEL% EQU 0 (
    echo MySQL Connector downloaded successfully!
) else (
    echo Failed to download MySQL Connector. Please download manually from:
    echo https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar
    echo Save it as lib\mysql-connector-j-8.0.33.jar
)
pause