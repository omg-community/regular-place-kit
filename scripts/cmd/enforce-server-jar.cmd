@echo off
setlocal EnableDelayedExpansion

REM Exit script immediately on first error.
set errorlevel=0

REM Check for the auto-download flag
set AUTO_DOWNLOAD=false

REM Parse command line arguments
:arg_loop
if "%1"=="" goto end_arg_loop
if "%1"=="--force" set AUTO_DOWNLOAD=true & shift & goto arg_loop
if "%1"=="-f" set AUTO_DOWNLOAD=true & shift & goto arg_loop
shift
goto arg_loop
:end_arg_loop

REM Create the server directory if it doesn't exist
call .\scripts\cmd\create-server-dir.cmd
if %errorlevel% neq 0 exit /b %errorlevel%

REM Get the environment variables and project properties
call .\scripts\cmd\get-env.cmd
if %errorlevel% neq 0 exit /b %errorlevel%

call .\scripts\cmd\project-properties.cmd
if %errorlevel% neq 0 exit /b %errorlevel%

REM Check that the server directory has a paper*.jar
set DOWNLOAD=false
set FILEFOUND=false

for %%F in ("%SERVER_DIRECTORY%\paper*.jar") do if exist "%%F" set FILEFOUND=true
if "!FILEFOUND!"=="false" (
  if "!AUTO_DOWNLOAD!"=="true" (
    set DOWNLOAD=true
  ) else (
    echo No Paper JAR found in %SERVER_DIRECTORY%.
    set /p user_input="Would you like to download it (Minecraft %MINECRAFT_VERSION%, build %PAPER_BUILD%)? [Y/n] "

    if /i "!user_input!"=="Y" set DOWNLOAD=true
    if /i "!user_input!"=="" set DOWNLOAD=true
  )

  if "!DOWNLOAD!"=="true" (
    echo Downloading the Paper JAR..
    powershell -command "Invoke-WebRequest -Uri 'https://papermc.io/api/v2/projects/paper/versions/%MINECRAFT_VERSION%/builds/%PAPER_BUILD%/downloads/%SERVER_JAR_NAME%' -OutFile '%SERVER_DIRECTORY%\%SERVER_JAR_NAME%'"
    echo Downloaded the Paper JAR. Proceeding..
  ) else (
    echo Please download the Paper JAR and try again.
    exit /b 1
  )
)
