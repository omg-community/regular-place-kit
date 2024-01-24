@echo off

REM Setting default value for AUTO_DOWNLOAD (0 for false, 1 for true)
set AUTO_DOWNLOAD=0

REM Processing command line arguments
:args_loop
if "%~1"=="" goto end_args_loop
if "%~1"=="--force" set AUTO_DOWNLOAD=1 & shift & goto args_loop
if "%~1"=="-f" set AUTO_DOWNLOAD=1 & shift & goto args_loop
shift
goto args_loop
:end_args_loop

REM Create the server directory if it doesn't exist
call .\scripts\cmd\create-server-dir.cmd
if ERRORLEVEL 1 exit /b 1

REM Get the environment variables and project properties
call .\scripts\cmd\get-env.cmd
if ERRORLEVEL 1 exit /b 1

call .\scripts\cmd\project-properties.cmd
if ERRORLEVEL 1 exit /b 1

REM Check that the server directory has a paper*.jar
set PAPER_JAR_FOUND=0
for %%F in ("%SERVER_DIRECTORY%\paper*.jar") do if exist "%%F" set PAPER_JAR_FOUND=1

if "%PAPER_JAR_FOUND%"=="0" goto no_paper_jar
goto end

:no_paper_jar
if "%AUTO_DOWNLOAD%"=="1" goto download_paper_jar

echo No Paper JAR found in %SERVER_DIRECTORY%.
set /p USER_INPUT="Would you like to download it (Minecraft %MINECRAFT_VERSION%, build %PAPER_BUILD%)? [Y/n] "

if /i "%USER_INPUT%"=="Y" goto download_paper_jar
if /i "%USER_INPUT%"=="" goto download_paper_jar
goto no_download

:no_download
echo Please download the Paper JAR and try again.
exit /b 1

:download_paper_jar
echo Downloading the Paper JAR..
powershell -command "Invoke-WebRequest -Uri 'https://papermc.io/api/v2/projects/paper/versions/%MINECRAFT_VERSION%/builds/%PAPER_BUILD%/downloads/%SERVER_JAR_NAME%' -OutFile '%SERVER_DIRECTORY%\%SERVER_JAR_NAME%'"
echo Downloaded the Paper JAR. Proceeding..
goto end

:end
