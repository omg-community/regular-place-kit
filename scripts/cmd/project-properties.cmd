@echo off

REM Extracting MINECRAFT_VERSION and PAPER_BUILD from project.properties
for /f "tokens=1,* delims==" %%a in ('findstr "^MINECRAFT_VERSION=" project.properties') do set MINECRAFT_VERSION=%%b
for /f "tokens=1,* delims==" %%a in ('findstr "^PAPER_BUILD=" project.properties') do set PAPER_BUILD=%%b
set SERVER_JAR_NAME=paper-%MINECRAFT_VERSION%-%PAPER_BUILD%.jar

REM Enforce that MINECRAFT_VERSION and PAPER_BUILD are set
if "%MINECRAFT_VERSION%"=="" or "%PAPER_BUILD%"=="" (
  echo MINECRAFT_VERSION and PAPER_BUILD must be set in project.properties.
  exit /b 1
)
