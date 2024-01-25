@echo off

REM Call get-env.cmd and check if it was successful
call .\scripts\cmd\get-env.cmd
if ERRORLEVEL 1 exit /b 1

REM Make sure a JAR file is specified
if not exist "%JAR_FILE%" (
  echo No JAR file specified. %JAR_FILE%
  exit /b 1
)
