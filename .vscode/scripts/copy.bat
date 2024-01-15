@echo off
setlocal

:: If there's an env file defined, load the environment variables from it
if exist "%ENV_FILE%" (
  for /f "delims=" %%i in (%ENV_FILE%) do set %%i
)

:: Make sure a JAR file is specified
if not exist "%JAR_FILE%" (
  echo No JAR file specified. %JAR_FILE%
  exit /b 1
)

:: Make sure SERVER_DIRECTORY is set
if "%SERVER_DIRECTORY%"=="" (
  echo SERVER_DIRECTORY is not set. Please set it via an environment variable.
  exit /b 1
)

:: Make sure SERVER_DIRECTORY exists
if not exist "%SERVER_DIRECTORY%" (
  echo The server directory at %SERVER_DIRECTORY% does not exist. Please create one and try again.
  exit /b 1
)

:: Check that the server directory has a paper*.jar
if not exist "%SERVER_DIRECTORY%\paper*.jar" (
  echo No Paper JAR found in %SERVER_DIRECTORY%. Please place a Paper JAR in the directory and try again.
  exit /b 1
)

:: Copy JAR file to server directory
echo Copying %JAR_FILE% to %SERVER_DIRECTORY%/plugins
mkdir "%SERVER_DIRECTORY%\plugins"
copy /Y "%JAR_FILE%" "%SERVER_DIRECTORY%\plugins"
