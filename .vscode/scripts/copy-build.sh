#!/bin/bash

# If there's an env file defined, load the environment variables from it
if [ -f "$ENV_FILE" ]; then
  export $(cat "$ENV_FILE" | xargs) >/dev/null 2>&1
fi

# Make sure a JAR file is specified
if [ ! -f "$JAR_FILE" ]; then
  echo "No JAR file specified. $JAR_FILE"
  exit 1
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

# Copy JAR file to server directory
echo "Copying $JAR_FILE to $SERVER_DIRECTORY/plugins"
mkdir -p "$SERVER_DIRECTORY/plugins"
cp "$JAR_FILE" "$SERVER_DIRECTORY/plugins"