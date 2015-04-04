package allstar.server;

import allstar.util.Defaults;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 *
 * @author davidboschwitz
 */
public class Server {

    /**
     * Just in case we don't want to use System.out
     */
    public static final PrintStream out = System.out;
    public static ConnectionListener listener;
    protected static Thread listenerThread;
    public static ClientHandler clientHandler;
    public static Processing process;
    protected static Thread processThread;
    public static int port = 2727;
    protected static String password = "all-star";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* init max clients/clienthandler */
        clientHandler = new ClientHandler(Defaults.MAX_CLIENTS);

        /* start threads */
        (processThread = new Thread(process = new Processing())).start();
        (listenerThread = new Thread(listener = new ConnectionListener())).start();

        /* Server-Side Commands to keep it running here  */
        BufferedReader line = new BufferedReader(new InputStreamReader(System.in));
        String cmd = "";
        try {
            while ((cmd = line.readLine()) != null) {
                if (cmd.equalsIgnoreCase("close") || cmd.equalsIgnoreCase("stop")) {
                    process.Stop();
                    try{
                    processThread.join();
                    }catch(InterruptedException ie){
                        ie.printStackTrace();
                    }
                    return;
                }
            }
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        }
        System.exit(0);
    }

}
