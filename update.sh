#!/bin/bash
# all-star IRLP Node RaspberryPi Update Script
# Made by David Boschwitz
# Updated 3/10/2015

echo "Starting Update for RaspberryPi all-star client..."
echo ""

echo "[Git]"
git pull

echo ""
echo "[Build Utilities]"

cd utility
ant jar

echo ""
echo "[Build Client]"

cd ..
cd client
ant jar

mkdir dist/lib 
cp ../utility/dist/All-Star_Utilities.jar dist/lib

echo "Update completed."

