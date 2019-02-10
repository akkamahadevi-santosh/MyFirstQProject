#!/bin/bash
echo "$USER"
cd /Users/$USER/Downloads/zip
cp *.zip /Users/$USER/Documents/zip
cd /Users/$USER/Documents/zip
unzip *.zip
cd agent-installer-0.1
./bin/agent.sh
var=$(pwd)
echo "The current working directory $var."
#EOF