#!/bin/bash
set -e # Exit script immediately on first error.

# Fetch the JSON data from the PaperMC API
RESPONSE=$(curl -s "https://api.papermc.io/v2/projects/paper")

# Parse the JSON to get the last version
VERSION=$(echo $RESPONSE | jq -r '.versions[-1]')

# Check if the last version was successfully retrieved
if [ -z "$VERSION" ]; then
  echo "No version found in the API response from PaperMC."
  exit 1
fi

# Replace MINECRAFT_VERSION in project.properties with the retrieved version
sed -i "s/^MINECRAFT_VERSION=.*/MINECRAFT_VERSION=$VERSION/" project.properties
echo "Updated project.properties MINECRAFT_VERSION to $VERSION."
