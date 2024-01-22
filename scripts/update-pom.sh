#!/bin/bash
set -e # Exit script immediately on first error.

POM_FILE="./pom.xml"
README_FILE="./README.md"
GUIDE_FILE="./guides/Beginners.md"

# Verbose..
# Check if the pom.xml file exists
if [ ! -f "$POM_FILE" ]; then
  echo "pom.xml file at $POM_FILE not found."
  exit 1
fi

# Check if the README.md file exists
if [ ! -f "$README_FILE" ]; then
  echo "README.md file at $README_FILE not found."
  exit 1
fi

# Check if the guides/Beginners.md file exists
if [ ! -f "$GUIDE_FILE" ]; then
  echo "Beginners.md file at $GUIDE_FILE not found."
  exit 1
fi

# Get the project properties
source ./scripts/project-properties.sh

if [[ "$OSTYPE" == "darwin"* ]]; then
  # macOS

  # Update the paper-api version in pom.xml
  sed -i '' "/<artifactId>paper-api<\/artifactId>/{n;s/<version>.*<\/version>/<version>${MINECRAFT_VERSION}-R0.1-SNAPSHOT<\/version>/;}" "$POM_FILE"
  echo "Updated paper-api version to ${MINECRAFT_VERSION}-R0.1-SNAPSHOT in pom.xml"

  # Update the .md files
  sed -i '' "s/<!--mcversion-->.*<!--mcversion-->/<!--mcversion-->${MINECRAFT_VERSION}<!--mcversion-->/g" "$README_FILE"
  sed -i '' "s/<!--mcversion-->.*<!--mcversion-->/<!--mcversion-->${MINECRAFT_VERSION}<!--mcversion-->/g" "$GUIDE_FILE"
  echo "Updated .md files with Minecraft version ${MINECRAFT_VERSION}"
else
  # Linux

  # Update the paper-api version in pom.xml
  sed -i "/<artifactId>paper-api<\/artifactId>/{n;s/<version>.*<\/version>/<version>${MINECRAFT_VERSION}-R0.1-SNAPSHOT<\/version>/;}" "$POM_FILE"
  echo "Updated paper-api version to ${MINECRAFT_VERSION}-R0.1-SNAPSHOT in pom.xml"

  # Update the .md files
  sed -i "s/<!--mcversion-->.*<!--mcversion-->/<!--mcversion-->${MINECRAFT_VERSION}<!--mcversion-->/g" "$README_FILE"
  sed -i "s/<!--mcversion-->.*<!--mcversion-->/<!--mcversion-->${MINECRAFT_VERSION}<!--mcversion-->/g" "$GUIDE_FILE"
  echo "Updated .md files with Minecraft version ${MINECRAFT_VERSION}"
fi
