@echo off

REM Extract the current MINECRAFT_VERSION from project.properties
for /f "tokens=1,* delims==" %%a in ('findstr "^MINECRAFT_VERSION=" project.properties') do set PREVIOUS_VERSION=%%b

REM Extract version group (major.minor) using PowerShell
for /f "tokens=1,2 delims=." %%a in ("%PREVIOUS_VERSION%") do set VERSION_GROUP=%%a.%%b

REM Check if version group was successfully extracted
if "%VERSION_GROUP%"=="" (
  echo Failed to determine the version group from MINECRAFT_VERSION in project.properties.
  exit /b 1
)

REM Fetch the JSON data and parse it to get the version using PowerShell
for /f %%a in ('powershell -command "try { $response = Invoke-WebRequest -Uri 'https://api.papermc.io/v2/projects/paper/version_group/%VERSION_GROUP%'; $obj = ConvertFrom-Json $response.Content; $obj.versions[-1] } catch { '' }"') do set VERSION=%%a

REM Check if the version was successfully retrieved
if "%VERSION%"=="" (
  echo No version found in the API response from PaperMC.
  exit /b 1
)

REM Replace MINECRAFT_VERSION in project.properties with the version using PowerShell
powershell -command "(Get-Content project.properties) -replace '^MINECRAFT_VERSION=.*', 'MINECRAFT_VERSION=%VERSION%' | Set-Content project.properties"
echo Updated MINECRAFT_VERSION to %VERSION% in project.properties
