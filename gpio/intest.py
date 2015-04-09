import RPi.GPIO as GPIO
import time

# Get GPIO pins ready
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

# Set pin 4 to output
led = 3
GPIO.setup(led, GPIO.IN);

while True:
	time.sleep(1)
	# Turn LED on
	print(GPIO.input(led))

# Clean up ports
GPIO.cleanup()