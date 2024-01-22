#!/bin/bash
set -e # Exit script immediately on first error.

POM_FILE="./pom.xml"

# Check if the pom.xml file exists
if [ ! -f "$POM_FILE" ]; then
  echo "pom.xml file at $POM_FILE not found."
  exit 1
fi

# Get the project properties
source ./scripts/project-properties.sh

# Update the paper-api version
sed -i '' "/<artifactId>paper-api<\/artifactId>/{n;s/<version>.*<\/version>/<version>${MINECRAFT_VERSION}-R0.1-SNAPSHOT<\/version>/;}" "$POM_FILE"
echo "Updated paper-api version to ${MINECRAFT_VERSION}-R0.1-SNAPSHOT in pom.xml"