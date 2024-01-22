#!/bin/bash
set -e # Exit script immediately on first error.

# Extract the current MINECRAFT_VERSION from project.properties
PREVIOUS_VERSION=$(grep "^MINECRAFT_VERSION=" project.properties | cut -d'=' -f2)

# Fetch the JSON data from the PaperMC API
RESPONSE=$(curl -s "https://api.papermc.io/v2/projects/paper/versions/$PREVIOUS_VERSION")

# Parse the JSON to get the latest build
BUILD=$(echo $RESPONSE | jq -r '.builds[-1]')

# Check if the build was successfully retrieved
if [ -z "$BUILD" ]; then
  echo "No build found in the API response from PaperMC."
  exit 1
fi

# Replace PAPER_BUILD in project.properties with the build
sed -i '' "s/^PAPER_BUILD=.*/PAPER_BUILD=$BUILD/" project.properties
echo "Updated PAPER_BUILD to $BUILD in project.properties"