package allstar.andriod;


import java.net.Socket;

/**
 * Holds most of the data for the client and connecting to the server.
 * @author davidboschwitz
 */
public class Client {

    /**
     * The IP-Address to connect to (default <code>davidboschwitz.iastate.edu</code>.
     */
    public static String ServerIP = "davidboschwitz.iastate.edu";
    /**
     * The port to connect to (default <code>2727</code>).
     */
    public static int port = 2727;
    public static Client tempInstance;

    /**
     * The socket connection to the client
     */
    public Socket socket;

    public Client(Socket s) {
        this.socket = s;
    }

    public void pttPressed(){
        throw new UnsupportedOperationException("FUCK ME IN THE ASS.");
    }
}