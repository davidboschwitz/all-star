import RPi.GPIO as GPIO
import time

# Get GPIO pins ready
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

# Set pin 4 to output
led = 4
GPIO.setup(led, GPIO.OUT);

# Turn LED on
GPIO.output(led, 0)
print("on")
# Clean up ports
