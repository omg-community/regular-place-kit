@echo off
if not exist eula.txt (
  echo eula=true> eula.txt
  echo Created eula.txt and accepted EULA.
  timeout /t 3 /nobreak
)

echo Starting server..

:restart
java -Xms3G -Xmx3G -jar paper*.jar nogui
timeout /t 3 /nobreak
echo Restarting server..
goto restart