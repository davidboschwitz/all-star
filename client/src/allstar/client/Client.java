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
     * Audio output object.
     */
    public AudioPlayer player = new AudioPlayer();
    /**
     * Audio input object.
     */
    protected AudioRecorder recorder;
    
    public Client(Socket s) {
        this.socket = s;
    }
}
