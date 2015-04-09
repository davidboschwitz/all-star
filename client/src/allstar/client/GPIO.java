/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allstar.client;

/**
 * Deals with everything GPIO.
 *
 * @author davidboschwitz
 */
public class GPIO {

    public static final int outPin = 4;
    private static java.lang.Process out = null;

    /**
     * Never initialize.
     */
    private GPIO() {

    }

    /**
     * Cue the GPIO Pins to simulate pressing the PTT button on the radio.
     */
    public static void OUTPUT_START() {
        
        println("Start 1.");
        try {
            out = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "sudo python ~/all-star/gpio/PTT_ON.py"});
            println("Start exec");
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public static void OUTPUT_STOP() {
        
        println("stop 1.");
        try {
            out = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "sudo python ~/all-star/gpio/PTT_OFF.py"});
            println("stop exec");
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static void println(String s) {
        System.out.println("[GPIO]: " + s);
    }
}
