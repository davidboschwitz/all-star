package allstar.client;

import java.io.InputStream;

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
        try{
        while (true) {
            if (in.available() > 0) {
                Main.client.audioIn(in);
            }
        }
        }catch(java.io.IOException ioe){
            ioe.printStackTrace();
            return;
        }
        /*
         BufferedReader reader;
         try {
         reader = new BufferedReader(new InputStreamReader(in));
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
         }*/
    }

}
