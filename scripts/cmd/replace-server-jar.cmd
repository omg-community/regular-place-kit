@echo off

REM Create the server directory if it doesn't exist
call .\scripts\cmd\create-server-dir.cmd
if ERRORLEVEL 1 exit /b 1

REM Get the environment variables and project properties
call .\scripts\cmd\get-env.cmd
if ERRORLEVEL 1 exit /b 1

call .\scripts\cmd\project-properties.cmd
if ERRORLEVEL 1 exit /b 1

REM Check if the server directory has a %SERVER_JAR_NAME% file
if not exist "%SERVER_DIRECTORY%\%SERVER_JAR_NAME%" (
  REM Check if the server directory has a paper*.jar file
  set PAPER_JAR_FOUND=0
  for %%F in ("%SERVER_DIRECTORY%\paper*.jar") do if exist "%%F" (
    set PAPER_JAR_FOUND=1
    REM Move all paper*.jar files to the previous_jars directory
    if not exist "%SERVER_DIRECTORY%\previous_jars" mkdir "%SERVER_DIRECTORY%\previous_jars"
    move "%SERVER_DIRECTORY%\paper*.jar" "%SERVER_DIRECTORY%\previous_jars"
  )

  REM Download the Paper JAR without asking for confirmation
  call .\scripts\cmd\enforce-server-jar.cmd --force
  echo Done!
) else (
  echo Server directory already running the proper build of Paper. No need to replace it!
)
