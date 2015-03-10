package allstar.client;

import java.net.Socket;
import javax.sound.sampled.Clip;

/**
 * Holds most of the data for the client and connecting to the server.
 *
 * @author davidboschwitz and omar
 */
public class Client {

    /**
     * The socket connection to the client
     */
    public Socket socket;
    /**
     * Audio output clip?
     */
    protected AudioClient audio;

    public Client(Socket s) {
        this.socket = s;
    }
}
