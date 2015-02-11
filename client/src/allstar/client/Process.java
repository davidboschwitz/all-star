package allstar.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Second thread
 * @author davidboschwitz
 */
public class Process implements Runnable {

    @Override
    public void run() {
        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(Main.client.socket.getInputStream()));
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
            return;
        }
        String next = "";
        try {
            while ((next = in.readLine()) != null) { //wait for next line of input from server
                System.out.println(next);
            }
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
            return;
        }
    }

}
