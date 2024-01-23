@echo off
REM Exit script immediately on first error.
setlocal EnableDelayedExpansion
set errorlevel=0

REM Enforce that the built JAR file is specified in the environment
call .\scripts\cmd\enforce-env-jar.cmd
if %errorlevel% neq 0 exit /b %errorlevel%

REM Create the server directory if it doesn't exist
call .\scripts\cmd\create-server-dir.cmd
if %errorlevel% neq 0 exit /b %errorlevel%

REM Get the environment variables
call .\scripts\cmd\get-env.cmd
if %errorlevel% neq 0 exit /b %errorlevel%

REM Copy JAR file to server directory
echo Copying %JAR_FILE% to %SERVER_DIRECTORY%\plugins
mkdir "%SERVER_DIRECTORY%\plugins" 2>nul
copy "%JAR_FILE%" "%SERVER_DIRECTORY%\plugins"
if %errorlevel% neq 0 (
  echo Error occurred during copying.
  exit /b %errorlevel%
)
echo Done!
