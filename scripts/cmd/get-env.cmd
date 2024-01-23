@echo off

REM Exit script immediately on first error.
set errorlevel=0

REM Check if the ENV_FILE variable is set and the file exists
if exist "%ENV_FILE%" (
  for /f "usebackq tokens=*" %%a in (`findstr /v /b "#" "%ENV_FILE%"`) do (
    set "line=%%a"
    REM Use standard expansion to get the first character
    set "var=%line:~0,1%"
    REM Check if the line is not empty and the first character is not a space
    REM Delayed expansion not used, so a different approach is needed
    call :ProcessLine
  )
)
goto :eof

:ProcessLine
if not "%var%"=="" if not "%var%"==" " set %line%
goto :eof
