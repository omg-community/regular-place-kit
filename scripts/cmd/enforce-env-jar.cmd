@echo off
setlocal EnableDelayedExpansion

REM Exit script immediately on first error.
set errorlevel=0

call .\scripts\cmd\get-env.cmd
if %errorlevel% neq 0 exit /b %errorlevel%

REM Make sure a JAR file is specified
if not exist "%JAR_FILE%" (
  echo No JAR file specified. %JAR_FILE%
  exit /b 1
)
