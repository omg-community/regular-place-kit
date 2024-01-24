@echo off

REM Fetch the JSON data and parse it to get the last version using PowerShell
for /f %%a in ('powershell -command "try { $response = Invoke-WebRequest -Uri 'https://api.papermc.io/v2/projects/paper'; $obj = ConvertFrom-Json $response.Content; $obj.versions[-1] } catch { '' }"') do set VERSION=%%a

REM Check if the last version was successfully retrieved
if "%VERSION%"=="" (
  echo No version found in the API response from PaperMC.
  exit /b 1
)

REM Replace MINECRAFT_VERSION in project.properties with the retrieved version using PowerShell
powershell -command "(Get-Content project.properties) -replace '^MINECRAFT_VERSION=.*', 'MINECRAFT_VERSION=%VERSION%' | Set-Content project.properties"
echo Updated project.properties MINECRAFT_VERSION to %VERSION%.
