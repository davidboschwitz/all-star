package allstar.client;

import allstar.util.Defaults;
import java.net.Socket;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

/**
 * Holds most of the data for the client and connecting to the server.
 *
 * @author davidboschwitz
 */
public class Client implements LineListener {

    /**
     * The socket connection to the client
     */
    public Socket socket;
    /**
     * Audio output clip?
     */
    private Clip clip;

    public Client(Socket s) {
        this.socket = s;
    }

    public void audioOut() {
        boolean stopped = false;
        TargetDataLine line;
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, Defaults.AudioFormat); // format is an AudioFormat object
        if (!AudioSystem.isLineSupported(info)) {
            // Handle the error ... 
            System.out.println("Audio System not supported.");
            throw new UnsupportedOperationException("Audio System not supported (FUCK)");
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
        try {
            System.out.println("Talk Start.");
            while (!Main.stopped) {
                // Read the next chunk of data from the TargetDataLine.
                numBytesRead = line.read(data, 0, data.length);
            // Save this chunk of data.

                socket.getOutputStream().write(data, 0, numBytesRead);

            }
            System.out.println("Talk flush()");
            socket.getOutputStream().flush();
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        }

    }

    public void audioIn(InputStream in) {
        //JFileChooser chooser = new JFileChooser();
        //chooser.showOpenDialog(null);
        //soundFile = chooser.getSelectedFile();

        //System.out.println("Playing " + soundFile.getName());
        Line.Info linfo = new Line.Info(Clip.class);
        Line line;
        try {
            line = AudioSystem.getLine(linfo);
        } catch (javax.sound.sampled.LineUnavailableException lue) {
            lue.printStackTrace();
            return;
        }

        clip = (Clip) line;
        clip.addLineListener(this);
        AudioInputStream ais;
        try {
            ais = AudioSystem.getAudioInputStream(in);
        } catch (javax.sound.sampled.UnsupportedAudioFileException uafe) {
            uafe.printStackTrace();
            return;
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
            return;
        }
        try {
            clip.open(ais);
        } catch (javax.sound.sampled.LineUnavailableException lue) {
            lue.printStackTrace();
            return;
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
            return;
        }
        clip.start();
    }

    @Override
    public void update(LineEvent le) {
        LineEvent.Type type = le.getType();
        if (type == LineEvent.Type.OPEN) {
            System.out.println("OPEN");
        } else if (type == LineEvent.Type.CLOSE) {
            System.out.println("CLOSE");
            System.exit(0);
        } else if (type == LineEvent.Type.START) {
            System.out.println("START");
            //playingDialog.setVisible(true);
        } else if (type == LineEvent.Type.STOP) {
            System.out.println("STOP");
            //playingDialog.setVisible(false);
            clip.close();
        }
    }
}
