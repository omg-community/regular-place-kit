#!/bin/bash
set -e # Exit script immediately on first error.

# Check for the auto-download flag
AUTO_DOWNLOAD=false

for arg in "$@"; do
  case $arg in
    --force|-f)
      AUTO_DOWNLOAD=true
      shift # Remove --force or -f from processing
      ;;
    *)
      # Unknown option
      ;;
  esac
done

# Create the server directory if it doesn't exist
./scripts/create-server-dir.sh

# Get the environment variables and project properties
source ./scripts/get-env.sh
source ./scripts/project-properties.sh

# Check that the server directory has a paper*.jar
if [ ! -f "$SERVER_DIRECTORY"/paper*.jar ]; then
  DOWNLOAD=false

  if [ "$AUTO_DOWNLOAD" = true ]; then
    DOWNLOAD=true
  else
    echo "No Paper JAR found in $SERVER_DIRECTORY."
    # Ask if we should download the Paper JAR for Minecraft $MINECRAFT_VERSION build $PAPER_BUILD
    read -p "Would you like to download it (Minecraft $MINECRAFT_VERSION, build $PAPER_BUILD)? [Y/n] " -n 1 -r
    echo # Move to a new line

    if [[ $REPLY =~ ^[Yy]$ ]] || [[ -z $REPLY ]]; then
      DOWNLOAD=true
    fi
  fi

  if [ "$DOWNLOAD" = true ]; then
    echo "Downloading the Paper JAR.."
    curl -o "$SERVER_DIRECTORY/$SERVER_JAR_NAME" "https://papermc.io/api/v2/projects/paper/versions/$MINECRAFT_VERSION/builds/$PAPER_BUILD/downloads/$SERVER_JAR_NAME"
    echo "Downloaded the Paper JAR. Proceeding.."
  else
    echo "Please download the Paper JAR and try again."
    exit 1
  fi
fi
