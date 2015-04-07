package allstar.client;

import allstar.util.Defaults;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Second thread
 *
 * @author davidboschwitz
 */
public class Process implements Runnable {

    @Override
    public void run() {
        InputStream in;
        try {
            in = Main.client.socket.getInputStream();
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
            return;
        }
        if (Defaults.AUDIO_ENABLED) {
            try {
                while (true) {
                    if (in.available() > 0) {
                        GPIO.GPIO_OUTPUT_START();
                        Main.client.player.play(in);
                    } else {
                        GPIO.GPIO_OUTPUT_STOP();
                    }
                }
            } catch (java.io.IOException ioe) {
                ioe.printStackTrace();
                return;
            }
        }
        if (Defaults.TEXT_ENABLED) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String next = "";
            try {
                while ((next = reader.readLine()) != null) { //wait for next line of input from server
                    System.out.println(next);
                }
            } catch (java.io.IOException ioe) {
                ioe.printStackTrace();
                return;
            }
        }
    }

}
