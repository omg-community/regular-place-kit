@echo off

set POM_FILE=./pom.xml
set README_FILE=./README.md
set GUIDE_FILE=./guides/Beginners.md

REM Check if the pom.xml file exists
if not exist "%POM_FILE%" (
  echo pom.xml file at %POM_FILE% not found.
  exit /b 1
)

REM Check if the README.md file exists
if not exist "%README_FILE%" (
  echo README.md file at %README_FILE% not found.
  exit /b 1
)

REM Check if the guides/Beginners.md file exists
if not exist "%GUIDE_FILE%" (
  echo Beginners.md file at %GUIDE_FILE% not found.
  exit /b 1
)

REM Get the project properties
call .\scripts\cmd\project-properties.cmd

REM Update the paper-api version in pom.xml
powershell -command "$xml = [xml](Get-Content '%POM_FILE%'); $xml.project.dependencies.dependency | where { $_.artifactId -eq 'paper-api' } | ForEach-Object { $_.version = '%MINECRAFT_VERSION%-R0.1-SNAPSHOT' }; $xml.Save('%POM_FILE%')"
echo Updated paper-api version to %MINECRAFT_VERSION%-R0.1-SNAPSHOT in pom.xml

REM Update the .md files
powershell -command "(Get-Content '%README_FILE%') -replace '<!--mcversion-->.*<!--mcversion-->', '<!--mcversion-->%MINECRAFT_VERSION%<!--mcversion-->' | Set-Content '%README_FILE%'"
powershell -command "(Get-Content '%GUIDE_FILE%') -replace '<!--mcversion-->.*<!--mcversion-->', '<!--mcversion-->%MINECRAFT_VERSION%<!--mcversion-->' | Set-Content '%GUIDE_FILE%'"
echo Updated .md files with Minecraft version %MINECRAFT_VERSION%
