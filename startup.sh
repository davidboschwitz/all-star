#!/bin/bash
# all-star IRLP Node RaspberryPi Startup Runtime Script
# Made by David Boschwitz
# Updated 3/10/2015

echo "Starting all-star for RaspberryPi..."

sh update.sh

cd client/dist

java -cp All-Star_Client.jar:lib/All-Star_Utilties.jar allstar.client.Main

echo "Run completed."
