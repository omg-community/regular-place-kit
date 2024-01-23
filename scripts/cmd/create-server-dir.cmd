@echo off
setlocal enabledelayedexpansion

REM Exit script immediately on first error.
set errorlevel=0

call .\scripts\cmd\get-env.cmd
if %errorlevel% neq 0 exit /b %errorlevel%

REM Make sure SERVER_DIRECTORY is set
if "%SERVER_DIRECTORY%"=="" (
  echo SERVER_DIRECTORY is not set. Please set it via an environment variable.
  exit /b 1
)

REM Make sure SERVER_DIRECTORY exists
if not exist "%SERVER_DIRECTORY%" (
  echo The server directory at %SERVER_DIRECTORY% does not exist.
  set /p user_input="Would you like to create it? [Y/n] "

  if /i "!user_input!"=="Y" (
    echo Creating the directory..
    mkdir "%SERVER_DIRECTORY%"
    if %errorlevel% neq 0 (
      echo Error occurred during directory creation.
      exit /b %errorlevel%
    )
    echo Created the Minecraft server directory. Proceeding..
  ) else (
    echo Please create the directory and try again.
    exit /b 1
  )
)
