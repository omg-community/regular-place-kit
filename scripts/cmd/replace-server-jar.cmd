@echo off
setlocal enabledelayedexpansion

REM Exit script immediately on first error.
set errorlevel=0

REM Create the server directory if it doesn't exist
call .\scripts\cmd\create-server-dir.cmd
if %errorlevel% neq 0 exit /b %errorlevel%

REM Get the environment variables and project properties
call .\scripts\cmd\get-env.cmd
if %errorlevel% neq 0 exit /b %errorlevel%

call .\scripts\cmd\project-properties.cmd
if %errorlevel% neq 0 exit /b %errorlevel%

REM Check if the server directory has a %SERVER_JAR_NAME% file
if not exist "%SERVER_DIRECTORY%\%SERVER_JAR_NAME%" (
  REM Check if the server directory has a paper*.jar file
  set FILEFOUND=false
  for %%F in ("%SERVER_DIRECTORY%\paper*.jar") do if exist "%%F" set FILEFOUND=true
  if "!FILEFOUND!"=="true" (
    REM Move all paper*.jar files to the previous_jars directory
    mkdir "%SERVER_DIRECTORY%\previous_jars" 2>nul
    move "%SERVER_DIRECTORY%\paper*.jar" "%SERVER_DIRECTORY%\previous_jars"
  )

  REM Download the Paper JAR without asking for confirmation
  call .\scripts\cmd\enforce-server-jar.cmd --force
  echo Done!
) else (
  echo Server directory already running the proper build of Paper. No need to replace it!
)
