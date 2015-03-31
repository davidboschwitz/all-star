package allstar.client;

import allstar.client.sound.AudioPlayer;
import allstar.client.sound.AudioRecorder;
import java.net.Socket;

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
    protected AudioPlayer player = new AudioPlayer();
    protected AudioRecorder recorder;
    
    public Client(Socket s) {
        this.socket = s;
    }
}
