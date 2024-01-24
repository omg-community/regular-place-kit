#!/bin/bash
set -e # Exit script immediately on first error.

MINECRAFT_VERSION=$(grep "^MINECRAFT_VERSION=" project.properties | cut -d'=' -f2)
PAPER_BUILD=$(grep "^PAPER_BUILD=" project.properties | cut -d'=' -f2)
SERVER_JAR_NAME="paper-$MINECRAFT_VERSION-$PAPER_BUILD.jar"

# Enforce that MINECRAFT_VERSION and PAPER_BUILD are set
if [ -z "$MINECRAFT_VERSION" ] || [ -z "$PAPER_BUILD" ]; then
  echo "MINECRAFT_VERSION and PAPER_BUILD must be set in project.properties."
  exit 1
fi