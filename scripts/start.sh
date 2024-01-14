#!/bin/bash
if [ ! -f eula.txt ]; then
  echo "eula=true" > eula.txt
  echo "Created eula.txt and accepted EULA."
  sleep 3
fi

echo "Starting server.."

while true
do
  java -Xms3G -Xmx3G -jar paper*.jar nogui
  EXIT_CODE=$?
  # 130 - stopped early
  # 0 - stopped normally FK FK FK FK
  echo $EXIT_CODE
  echo "Restarting server.."
  sleep 3
  exit
done
