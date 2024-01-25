@echo off

REM Enforce that the built JAR file is specified in the environment
call .\scripts\cmd\enforce-env-jar.cmd
if ERRORLEVEL 1 exit /b 1

REM Create the server directory if it doesn't exist
call .\scripts\cmd\create-server-dir.cmd
if ERRORLEVEL 1 exit /b 1

REM Get the environment variables
call .\scripts\cmd\get-env.cmd
if ERRORLEVEL 1 exit /b 1

REM Copy JAR file to server directory
echo Copying %JAR_FILE% to %SERVER_DIRECTORY%\plugins
mkdir "%SERVER_DIRECTORY%\plugins" 2>nul
copy "%JAR_FILE%" "%SERVER_DIRECTORY%\plugins"
if ERRORLEVEL 1 exit /b 1

echo Done!
