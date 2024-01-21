#!/bin/bash

# If there's an env file defined, load the environment variables from it
if [ -f "$ENV_FILE" ]; then
  export $(cat "$ENV_FILE" | xargs) >/dev/null 2>&1
fi

# Make sure SERVER_DIRECTORY is set
if [ -z "$SERVER_DIRECTORY" ]; then
  echo "SERVER_DIRECTORY is not set. Please set it via an environment variable."
  exit 1
fi

# Make sure SERVER_DIRECTORY exists
if [ ! -d "$SERVER_DIRECTORY" ]; then
  echo "The server directory at $SERVER_DIRECTORY does not exist. Please create one and try again."
  exit 1
fi

# Check that the server directory has a paper*.jar
if [ ! -f "$SERVER_DIRECTORY"/paper*.jar ]; then
  echo "No Paper JAR found in $SERVER_DIRECTORY. Please place a Paper JAR in the directory and try again."
  exit 1
fi

# Check if SERVER_ARGS is set, otherwise set it to "-Xmx1024M -Xms512M"
if [ -z "$SERVER_ARGS" ]; then
  export SERVER_ARGS="-Xmx1024M -Xms512M"
  echo "Using $SERVER_ARGS as SERVER_ARGS."
fi

# Start the server
echo "Starting server.."
cd "$SERVER_DIRECTORY"
java $SERVER_ARGS -Dcom.mojang.eula.agree=true -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar "$SERVER_DIRECTORY"/paper*.jar nogui