package allstar.client;

import java.net.Socket;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import allstar.util.Defaults;
import java.io.ByteArrayOutputStream;

/**
 * Holds most of the data for the client and connecting to the server.
 *
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

    public void audio() {
        boolean stopped = false;
        TargetDataLine line;
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, Defaults.AudioFormat); // format is an AudioFormat object
        if (!AudioSystem.isLineSupported(info)) {
            // Handle the error ... 
            throw new UnsupportedOperationException("FUCK IDK WHAT TO DO");
        }
        // Obtain and open the line.
        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(Defaults.AudioFormat);
        } catch (LineUnavailableException ex) {
            // Handle the error ... 
            ex.printStackTrace();
            return;
        }

        // Assume that the TargetDataLine, line, has already
        // been obtained and opened.
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int numBytesRead;
        byte[] data = new byte[line.getBufferSize() / 5];

        // Begin audio capture.
        line.start();

        // Here, stopped is a global boolean set by another thread.
        while (!stopped) {
            // Read the next chunk of data from the TargetDataLine.
            numBytesRead = line.read(data, 0, data.length);
            // Save this chunk of data.
            out.write(data, 0, numBytesRead);
        }

    }
}
