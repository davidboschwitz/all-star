package allstar.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author davidboschwitz
 */
public class Main {

    static String ip = "davidboschwitz.student.iastate.edu";
    static int port = 2727;
    static Client client;

    /**
     * Runs the program//main thread
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length > 0){
            ip = args[0];
        }
        BufferedReader line = new BufferedReader(new InputStreamReader(System.in));
        String next = "";
        OutputStream out;
        try {
            /** init client */
            client = new Client(new Socket(ip, port));
            /** init the output stream to the server */
            out = client.socket.getOutputStream();
        } catch (java.net.UnknownHostException uhe) {
            uhe.printStackTrace();
            return;
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
            return;
        }
        new Thread(new Process()).start();
        try {
            while ((next = line.readLine()) != null) {
                //System.out.println("[sent]: "+next);
                out.write(next.getBytes());
                out.flush();
            }
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
