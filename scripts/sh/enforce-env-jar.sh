#!/bin/bash
set -e # Exit script immediately on first error.

source ./scripts/sh/get-env.sh

# Make sure a JAR file is specified
if [ ! -f "$JAR_FILE" ]; then
  echo "No JAR file specified. $JAR_FILE"
  exit 1
fi