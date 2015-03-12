#!/bin/bash
# all-star IRLP Node RaspberryPi Startup Runtime Script
# Made by David Boschwitz
# Updated 3/10/2015

echo "Starting all-star for RaspberryPi..\n”

sh update.sh

cd client/dist

java -jar All-Star_Client.jar

echo “Run completed."
