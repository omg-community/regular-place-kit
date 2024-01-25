#!/bin/bash
set -e # Exit script immediately on first error.

source ./scripts/cmd/get-env.sh

# Make sure SERVER_DIRECTORY is set
if [ -z "$SERVER_DIRECTORY" ]; then
  echo "SERVER_DIRECTORY is not set. Please set it via an environment variable."
  exit 1
fi

# Make sure SERVER_DIRECTORY exists
if [ ! -d "$SERVER_DIRECTORY" ]; then
  echo "The server directory at $SERVER_DIRECTORY does not exist."

  # Ask if we should create the directory
  read -p "Would you like to create it? [Y/n] " -n 1 -r
  echo # Move to a new line

  # If the user says yes, create the directory
  if [[ $REPLY =~ ^[Nn]$ ]]; then
    echo "Please create the directory and try again."
    exit 1
  else
    mkdir -p "$SERVER_DIRECTORY"
    echo "Created the Minecraft server directory. Proceeding.."
  fi
fi