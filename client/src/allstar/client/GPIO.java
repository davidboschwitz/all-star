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

    public static void GPIO_OUTPUT_START() {
        if (out != null) {
            println("Cannot start output: already started!");
            return;
        }
        println("Start 1.");
        try {
            out = Runtime.getRuntime().exec("sh ~/all-star/gpio/key.sh");
            println("Start exec");
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void GPIO_OUTPUT_STOP() {
        if (out == null) {
            return;
        }
        try {
            println("Stop 1");
            out.getOutputStream().write("\n".getBytes());
            println("Stop write");
            out.getOutputStream().flush();
            println("Stop flush");
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        }
        out = null;
    }

    private static void println(String s) {
        System.out.println("[GPIO]: " + s);
    }
}
