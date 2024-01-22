#!/bin/bash
set -e # Exit script immediately on first error.

# Enforce that the built JAR file is specified in the environment
./scripts/enforce-env-jar.sh

# Create the server directory if it doesn't exist
./scripts/create-server-dir.sh

# Get the environment variables
source ./scripts/get-env.sh

# Copy JAR file to server directory
echo "Copying $JAR_FILE to $SERVER_DIRECTORY/plugins"
mkdir -p "$SERVER_DIRECTORY/plugins"
cp "$JAR_FILE" "$SERVER_DIRECTORY/plugins"
echo "Done!"