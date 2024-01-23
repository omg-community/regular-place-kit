@echo off
setlocal enabledelayedexpansion

REM Exit script immediately on first error.
set errorlevel=0

REM Extract the current MINECRAFT_VERSION from project.properties
for /f "tokens=1,* delims==" %%a in ('findstr "^MINECRAFT_VERSION=" project.properties') do set PREVIOUS_VERSION=%%b

REM Fetch the JSON data from the PaperMC API
for /f %%i in ('powershell -command "(Invoke-WebRequest -Uri 'https://api.papermc.io/v2/projects/paper/versions/!PREVIOUS_VERSION!').Content"') do set RESPONSE=%%i

REM Parse the JSON to get the latest build
for /f %%a in ('powershell -command "$obj = ConvertFrom-Json '!RESPONSE!'; $obj.builds[-1]"') do set BUILD=%%a

REM Check if the build was successfully retrieved
if "!BUILD!"=="" (
  echo No build found in the API response from PaperMC.
  exit /b 1
)

REM Replace PAPER_BUILD in project.properties with the build
powershell -command "(Get-Content project.properties) -replace '^PAPER_BUILD=.*', 'PAPER_BUILD=!BUILD!' | Set-Content project.properties"
echo Updated PAPER_BUILD to !BUILD! in project.properties
