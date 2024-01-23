@echo off

REM Fetch the JSON data from the PaperMC API using PowerShell
for /f %%i in ('powershell -command "(Invoke-WebRequest -Uri 'https://api.papermc.io/v2/projects/paper').Content"') do set RESPONSE=%%i

REM Parse the JSON to get the last version using PowerShell
for /f %%a in ('powershell -command "$obj = ConvertFrom-Json '%RESPONSE%'; $obj.versions[-1]"') do set VERSION=%%a

REM Check if the last version was successfully retrieved
if "%VERSION%"=="" (
  echo No version found in the API response from PaperMC.
  exit /b 1
)

REM Replace MINECRAFT_VERSION in project.properties with the retrieved version using PowerShell
powershell -command "(Get-Content project.properties) -replace '^MINECRAFT_VERSION=.*', 'MINECRAFT_VERSION=%VERSION%' | Set-Content project.properties"
echo Updated project.properties MINECRAFT_VERSION to %VERSION%.