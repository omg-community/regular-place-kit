@echo off

REM Extracting MINECRAFT_VERSION and PAPER_BUILD from project.properties
for /f "tokens=1,* delims==" %%a in ('findstr "^MINECRAFT_VERSION=" project.properties') do set %%a=%%b
for /f "tokens=1,* delims==" %%a in ('findstr "^PAPER_BUILD=" project.properties') do set %%a=%%b

set SERVER_JAR_NAME=paper-%MINECRAFT_VERSION%-%PAPER_BUILD%.jar

REM Enforce that MINECRAFT_VERSION and PAPER_BUILD are set
if not defined MINECRAFT_VERSION (
  echo MINECRAFT_VERSION not set in project.properties.
  exit /b 1
)
if not defined PAPER_BUILD (
  echo PAPER_BUILD not set in project.properties.
  exit /b 1
)