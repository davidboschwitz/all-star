/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allstar.client.sound;

import allstar.client.Client;
import allstar.client.Main;
import allstar.util.Defaults;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author davidboschwitz
 */
public class AudioPlayerThread implements Runnable {

    Client client;

    public AudioPlayerThread(Client c) {
        this.client = c;
    }

    @Override
    public void run() {
        InputStream in;
        try {
            in = client.socket.getInputStream();
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
            return;
        }
        if (Defaults.AUDIO_ENABLED) {
            try {
                while (true) {
                    if (in.available() > 0) {
                        client.player.play(in);
                        //GPIO.GPIO_OUTPUT_STOP();
                    } else {
                    }
                }
            } catch (java.io.IOException ioe) {
                ioe.printStackTrace();
                return;
            } catch (Exception e) {
                e.printStackTrace();
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
