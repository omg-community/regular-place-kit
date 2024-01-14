#!/bin/bash

if [ ! -f "$ENV_FILE" ]; then
  echo ".env file not found at $ENV_FILE"
  exit 1
fi

# Load environment variables from .env file
export $(cat "$ENV_FILE" | xargs) >/dev/null 2>&1

# Check if SERVER_DIRECTORY is set
if [ -z "$SERVER_DIRECTORY" ]; then
  echo "SERVER_DIRECTORY not set in .env file."
  exit 1
fi

# Check if SERVER_ARGS is set, otherwise set it to "-Xmx1024M -Xms512M"
if [ -z "$SERVER_ARGS" ]; then
  echo "SERVER_ARGS not set in .env file. Setting it to \"-Xmx1024M -Xms512M\""
  export SERVER_ARGS="-Xmx1024M -Xms512M"
fi

# Start the server
echo "Starting server.."
cd "$SERVER_DIRECTORY"
java $SERVER_ARGS -Dcom.mojang.eula.agree=true -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar "$SERVER_DIRECTORY"/paper*.jar nogui