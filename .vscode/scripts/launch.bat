@echo off
setlocal

:: If there's an env file defined, load the environment variables from it
if exist "%ENV_FILE%" (
  for /f "delims=" %%i in (%ENV_FILE%) do set %%i
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

:: Check if SERVER_ARGS is set, otherwise set it to "-Xmx1024M -Xms512M"
if "%SERVER_ARGS%"=="" (
  set "SERVER_ARGS=-Xmx1024M -Xms512M"
  echo Using %SERVER_ARGS% as SERVER_ARGS.
)

:: Start the server
echo Starting server..
cd /d "%SERVER_DIRECTORY%"
java %SERVER_ARGS% -Dcom.mojang.eula.agree=true -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar "%SERVER_DIRECTORY%\paper*.jar" nogui
