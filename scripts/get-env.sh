#!/bin/bash
set -e # Exit script immediately on first error.

# Check if the ENV_FILE variable is set and the file exists
if [ -f "$ENV_FILE" ]; then
  export $(grep -v '^#' "$ENV_FILE" | xargs)
fi
