import RPi.GPIO as GPIO
import time

# Get GPIO pins ready
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

# Set pin 4 to output
led = 8
GPIO.setup(led, GPIO.OUT);

for i in xrange(15):
	# Turn LED on
	GPIO.output(led, 1)
	# wait 5 seconds
	time.sleep(5)
	# Turn LED off
	GPIO.output(led, 0)

# Clean up ports
GPIO.cleanup()