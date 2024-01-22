#!/bin/bash
set -e # Exit script immediately on first error.

# Create the server directory if it doesn't exist
./scripts/create-server-dir.sh

# Enforce that the server directory has a Paper JAR
./scripts/enforce-server-jar.sh

# Check if SERVER_ARGS is set, otherwise set it to "-Xmx1024M -Xms512M"
if [ -z "$SERVER_ARGS" ]; then
  export SERVER_ARGS="-Xmx1024M -Xms512M"
  echo "Using $SERVER_ARGS as SERVER_ARGS."
fi

# Start the server
echo "Starting server.."
cd "$SERVER_DIRECTORY"
java $SERVER_ARGS -Dcom.mojang.eula.agree=true -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar "$SERVER_DIRECTORY"/paper*.jar nogui