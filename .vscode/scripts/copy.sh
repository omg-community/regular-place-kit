#!/bin/bash

if [ -z "$JAR_FILE" ]; then
  echo "No JAR file specified."
  exit 1
fi

check_directory() {
  if [ -z "$SERVER_DIRECTORY" ]; then
    return 1
  fi
  return 0
}

if ! check_directory; then
  # echo "No SERVER_DIRECTORY defined."
  echo "Checking for .env file.."

  if [ ! -f "$ENV_FILE" ]; then
    echo ".env file with a defined SERVER_DIRECTORY not found at $ENV_FILE"
    exit 1
  fi

  # Load environment variables from .env file
  export $(cat "$ENV_FILE" | xargs) >/dev/null 2>&1

  if ! check_directory; then
    echo "SERVER_DIRECTORY must be set in your .env file at $ENV_FILE"
    exit 1
  fi
fi

# Copy JAR file to server directory
echo "Copying $JAR_FILE to $SERVER_DIRECTORY/plugins"
cp "$JAR_FILE" "$SERVER_DIRECTORY/plugins"