@echo off
setlocal enabledelayedexpansion

REM Exit script immediately on first error.
set errorlevel=0

REM Extract the current MINECRAFT_VERSION from project.properties
for /f "tokens=1,* delims==" %%a in ('findstr "^MINECRAFT_VERSION=" project.properties') do set PREVIOUS_VERSION=%%b

REM Extract version group (major.minor)
for /f %%i in ('echo !PREVIOUS_VERSION! ^| powershell -command "$input -match '^[0-9]+\.[0-9]+'; $matches[0]"') do set VERSION_GROUP=%%i

REM Check if version group was successfully extracted
if "!VERSION_GROUP!"=="" (
  echo Failed to determine the version group from MINECRAFT_VERSION in project.properties.
  exit /b 1
)

REM Fetch the JSON data from the PaperMC API
for /f %%i in ('powershell -command "(Invoke-WebRequest -Uri 'https://api.papermc.io/v2/projects/paper/version_group/!VERSION_GROUP!').Content"') do set API_RESPONSE=%%i

REM Parse the JSON to get the version
for /f %%a in ('powershell -command "$obj = ConvertFrom-Json '!API_RESPONSE!'; $obj.versions[-1]"') do set VERSION=%%a

REM Check if the version was successfully retrieved
if "!VERSION!"=="" (
  echo No version found in the API response from PaperMC.
  exit /b 1
)

REM Replace MINECRAFT_VERSION in project.properties with the version
powershell -command "(Get-Content project.properties) -replace '^MINECRAFT_VERSION=.*', 'MINECRAFT_VERSION=!VERSION!' | Set-Content project.properties"
echo Updated MINECRAFT_VERSION to !VERSION! in project.properties
