#!/bin/bash
# all-star IRLP Node RaspberryPi Update Script
# Made by David Boschwitz
# Updated 3/10/2015

echo "Starting Update for RaspberryPi all-star client..."
echo ""

echo "$(tput smso)[Git]$(tput rmso)"
git pull

echo ""
echo "$(tput smso)[Build Utilities]$(tput rmso)"

cd utility
ant jar

echo ""
echo "$(tput smso)[Build Client]$(tput rmso)"

cd ..
cd client
ant jar

mkdir dist/lib 
cp ../utility/dist/All-Star_Utilities.jar dist/lib

echo "Update completed."

