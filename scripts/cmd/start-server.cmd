@echo off

REM Create the server directory if it doesn't exist
call .\scripts\cmd\create-server-dir.cmd
if ERRORLEVEL 1 exit /b 1

REM Enforce that the server directory has a Paper JAR
call .\scripts\cmd\enforce-server-jar.cmd
if ERRORLEVEL 1 exit /b 1

REM Check if SERVER_ARGS is set, otherwise set it to "-Xmx1024M -Xms512M"
if "%SERVER_ARGS%"=="" (
  set SERVER_ARGS=-Xmx1024M -Xms512M
  echo Using %SERVER_ARGS% as SERVER_ARGS.
)

REM Start the server with the first paper*.jar found
echo Starting server..
cd /d "%SERVER_DIRECTORY%"
set PAPER_JAR_FOUND=
for %%F in ("%SERVER_DIRECTORY%\paper*.jar") do (
  if not defined PAPER_JAR_FOUND (
    set PAPER_JAR_FOUND=1
    java %SERVER_ARGS% -Dcom.mojang.eula.agree=true -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar "%%F" nogui
  )
)
