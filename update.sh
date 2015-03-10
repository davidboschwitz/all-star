#!/bin/bash
# all-star IRLP Node RaspberryPi Update Script
# Made by David Boschwitz
# Updated 3/10/2015

echo "Starting Update for RaspberryPi all-star client..."

git pull
cd client
ant jar

echo "Update completed."
