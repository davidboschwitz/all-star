#!/bin/bash
# all-star IRLP Node RaspberryPi Update Script
# Made by David Boschwitz
# Updated 3/10/2015

cd client/dist
java -cp All-Star_Client.jar:lib/All-Star_Utilities.jar allstar.client.Main $1
