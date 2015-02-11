package allstar.client;

import java.net.Socket;

/**
 * Holds most of the data for the client and connecting to the server.
 * @author davidboschwitz
 */
public class Client {

    /**
     * The socket connection to the client
     */
    public Socket socket;

    public Client(Socket s) {
        this.socket = s;
    }
}
