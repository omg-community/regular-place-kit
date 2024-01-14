@echo off
setlocal

REM Check if JAR_FILE variable is set
if "%JAR_FILE%"=="" (
  echo No JAR file specified.
  exit /b 1
)

REM Function to check SERVER_DIRECTORY variable
call :check_directory
if %errorlevel% neq 0 (
  echo Checking for .env file..

  REM Check if .env file exists
  if not exist "%ENV_FILE%" (
    echo .env file with a defined SERVER_DIRECTORY not found at %ENV_FILE%
    exit /b 1
  )

  REM Load environment variables from .env file
  for /f "delims=" %%i in (%ENV_FILE%) do set %%i

  call :check_directory
  if %errorlevel% neq 0 (
    echo SERVER_DIRECTORY must be set in your .env file at %ENV_FILE%
    exit /b 1
  )
)

REM Copy JAR file to server directory
echo Copying %JAR_FILE% to %SERVER_DIRECTORY%/plugins
copy "%JAR_FILE%" "%SERVER_DIRECTORY%/plugins"

REM End of script
exit /b 0

REM Check directory function
:check_directory
if "%SERVER_DIRECTORY%"=="" (
  exit /b 1
)
exit /b 0
