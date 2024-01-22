#!/bin/bash
set -e # Exit script immediately on first error.

# Create the server directory if it doesn't exist
./scripts/create-server-dir.sh

# Get the environment variables and project properties
source ./scripts/get-env.sh
source ./scripts/project-properties.sh

# Check if the server directory has a $SERVER_JAR_NAME file
if [ ! -f "$SERVER_DIRECTORY/$SERVER_JAR_NAME" ]; then
  # Check if the server directory has a paper*.jar file
  if [ -f "$SERVER_DIRECTORY/paper*.jar" ]; then
    # Move all paper*.jar files to the previous_jars directory
    mkdir -p "$SERVER_DIRECTORY/previous_jars"
    mv "$SERVER_DIRECTORY"/paper*.jar "$SERVER_DIRECTORY/previous_jars"
  fi

  # Download the Paper JAR without asking for confirmation
  ./scripts/enforce-server-jar.sh --force
  echo "Done!"
else
  echo "Server directory already running the proper build of Paper. No need to replace it!"
fi
