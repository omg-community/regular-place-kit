@echo off
setlocal

if not exist "%ENV_FILE%" (
  echo .env file not found at %ENV_FILE%
  exit /b 1
)

:: Load environment variables from .env file
for /f "delims=" %%a in ('type "%ENV_FILE%"') do (
  set "%%a"
)

:: Check if SERVER_DIRECTORY is set
if "%SERVER_DIRECTORY%"=="" (
  echo SERVER_DIRECTORY not set in .env file.
  exit /b 1
)

:: Check if SERVER_ARGS is set, otherwise set it to default values
if "%SERVER_ARGS%"=="" (
  echo SERVER_ARGS not set in .env file. Setting it to "-Xmx1024M -Xms512M"
  set SERVER_ARGS=-Xmx1024M -Xms512M
)

:: Start the server
echo Starting server..
cd /d "%SERVER_DIRECTORY%"
java %SERVER_ARGS% -Dcom.mojang.eula.agree=true -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar paper*.jar nogui
