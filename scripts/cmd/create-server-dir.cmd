@echo off

call .\scripts\cmd\get-env.cmd
if ERRORLEVEL 1 exit /b 1

REM Make sure SERVER_DIRECTORY is set
if "%SERVER_DIRECTORY%"=="" (
  echo SERVER_DIRECTORY is not set. Please set it via an environment variable.
  exit /b 1
)

REM Make sure SERVER_DIRECTORY exists
if not exist "%SERVER_DIRECTORY%" (
  echo The server directory at %SERVER_DIRECTORY% does not exist.
  set /p user_input="Would you like to create it? [Y/n] "

  if /i "%user_input%"=="N" (
    echo Please create the directory and try again.
    exit /b 1
  ) else (
    mkdir "%SERVER_DIRECTORY%"
    echo Created the Minecraft server directory. Proceeding..
  )
)
