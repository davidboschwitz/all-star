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
public class Processing implements Runnable {

    private Client client;
    private boolean pttStarted = false;

    Processing(Client c) {
        this.client = c;
    }

    @Override
    public void run() {
        while (true) {
            if(pttStarted && client.player.lastTalk + 1000 < System.currentTimeMillis()){
                GPIO.OUTPUT_STOP();
            }
            if(!pttStarted && client.player.lastTalk + 1000 > System.currentTimeMillis()){
                GPIO.OUTPUT_START();
            }
        }
    }

}
