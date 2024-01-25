#!/bin/bash
set -e # Exit script immediately on first error.

# Extract the current MINECRAFT_VERSION from project.properties
PREVIOUS_VERSION=$(grep "^MINECRAFT_VERSION=" project.properties | cut -d'=' -f2)

# Extract version group (major.minor)
VERSION_GROUP=$(echo $PREVIOUS_VERSION | grep -oE '^[0-9]+\.[0-9]+')

# Check if version group was successfully extracted
if [ -z "$VERSION_GROUP" ]; then
  echo "Failed to determine the version group from MINECRAFT_VERSION in project.properties."
  exit 1
fi

# Fetch the JSON data from the PaperMC API
API_RESPONSE=$(curl -s "https://api.papermc.io/v2/projects/paper/version_group/$VERSION_GROUP")

# Parse the JSON to get the version
VERSION=$(echo $API_RESPONSE | jq -r '.versions[-1]')

# Check if the version was successfully retrieved
if [ -z "$VERSION" ]; then
  echo "No version found in the API response from PaperMC."
  exit 1
fi

# Replace MINECRAFT_VERSION in project.properties with the version
if [[ "$OSTYPE" == "darwin"* ]]; then
  # macOS
  sed -i '' "s/^MINECRAFT_VERSION=.*/MINECRAFT_VERSION=$VERSION/" project.properties
else
  # Linux
  sed -i "s/^MINECRAFT_VERSION=.*/MINECRAFT_VERSION=$VERSION/" project.properties
fi
echo "Updated MINECRAFT_VERSION to $VERSION in project.properties"
