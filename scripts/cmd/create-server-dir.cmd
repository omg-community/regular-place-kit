@echo off

call .\scripts\cmd\get-env.cmd
if ERRORLEVEL 1 exit /b 1

REM Make sure SERVER_DIRECTORY is set
if not "%SERVER_DIRECTORY%"=="" goto check_dir_exists
echo SERVER_DIRECTORY is not set. Please set it via an environment variable.
exit /b 1

:check_dir_exists
if exist "%SERVER_DIRECTORY%" goto end
echo The server directory at %SERVER_DIRECTORY% does not exist.
set /p USER_INPUT="Would you like to create it? [Y/n] "

if /i "%USER_INPUT%"=="Y" goto create_dir
if not defined USER_INPUT goto create_dir
goto dir_not_created

:create_dir
mkdir "%SERVER_DIRECTORY%"
echo Created the Minecraft server directory. Proceeding..
goto end

:dir_not_created
echo Please create the directory and try again.
exit /b 1

:end
