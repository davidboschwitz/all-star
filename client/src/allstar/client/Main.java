package allstar.client;

import allstar.util.Defaults;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Does most of the work for the client.
 *
 * @author davidboschwitz
 */
public class Main {

    /**
     * The ip-address to attempt to connect to (Default
     * <code>davidboschwitz.student.iastate.edu</code>.
     */
    public static String ip = Defaults.ServerIP;
    /**
     * The port to attempt to connect to (Default <code>2727</code>).
     */
    public static int port = Defaults.port;
    /**
     * The Client object that holds most of the data for the client.
     */
    protected static Client client;

    
    static boolean stopped = true;
    
    /**
     * Runs the program//main thread
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            ip = args[0];
        }
         OutputStream out;
        try {
            /* init client */
            client = new Client(new Socket(ip, port));
            /* init the output stream to the server */
            out = client.socket.getOutputStream();
        } catch (java.net.UnknownHostException uhe) {
            uhe.printStackTrace();
            return;
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
            return;
        }
        
        /* The reader for System.in */
        BufferedReader line = new BufferedReader(new InputStreamReader(System.in));
        /* The next line */
        String next = "";
       
        /* Start the Process class */
        new Thread(new Process()).start();
        try {
            /* waits for the next line of input into the prompt */
            while ((next = line.readLine()) != null) {
                //System.out.println("[sent]: "+next);
                if(next.equals("start")){
                    stopped = false;
                    Main.client.audioOut();
                }
                if(next.equals("stop")){
                    stopped = true;
                }
                //out.write(next.getBytes());
                //out.flush();
            }
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
