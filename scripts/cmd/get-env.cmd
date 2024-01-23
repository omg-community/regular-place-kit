@echo off

REM Check if the ENV_FILE variable is set and the file exists
if exist "%ENV_FILE%" (
  for /f "usebackq tokens=*" %%a in (`findstr /v /b "#" "%ENV_FILE%"`) do (
    REM Setting environment variable
    for /f "tokens=1,* delims==" %%b in ("%%a") do (
      set "%%b=%%c"
    )
  )
)
