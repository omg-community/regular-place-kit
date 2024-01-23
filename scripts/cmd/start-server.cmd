@echo off

REM Exit script immediately on first error.
set errorlevel=0

REM Create the server directory if it doesn't exist
call .\scripts\cmd\create-server-dir.cmd
if %errorlevel% neq 0 exit /b %errorlevel%

REM Enforce that the server directory has a Paper JAR
call .\scripts\cmd\enforce-server-jar.cmd
if %errorlevel% neq 0 exit /b %errorlevel%

REM Check if SERVER_ARGS is set, otherwise set it to "-Xmx1024M -Xms512M"
if "%SERVER_ARGS%"=="" (
  set SERVER_ARGS=-Xmx1024M -Xms512M
  echo Using %SERVER_ARGS% as SERVER_ARGS.
)

REM Start the server
echo Starting server..
cd /d "%SERVER_DIRECTORY%"
for %%F in ("%SERVER_DIRECTORY%\paper*.jar") do (
  java %SERVER_ARGS% -Dcom.mojang.eula.agree=true -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar "%%F" nogui
)
