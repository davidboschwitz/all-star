import RPi.GPIO as GPIO
import time

# Get GPIO pins ready
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

# Set pin 4 to output
led = 4
GPIO.setup(led, GPIO.OUT);

# Turn LED on
print("on")
GPIO.output(led, 0)
# wait for input
ina = raw_input('press a key to continue')
# Turn LED off
GPIO.output(led, 1)
print("off")
# Clean up ports
GPIO.cleanup()
