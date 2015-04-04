package allstar.client;

import allstar.client.sound.AudioRecorder;
import allstar.util.Defaults;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

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
    public static String ip = Defaults.SERVER_IP;
    /**
     * The port to attempt to connect to (Default <code>2727</code>).
     */
    public static int port = Defaults.SERVER_PORT;
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
            if (args[0].contains(":")) {
                port = Integer.parseInt(args[0].substring(args[0].indexOf(":") + 1));
                ip = args[0].substring(0, args[0].indexOf(":"));
            } else {
                ip = args[0];
            }
        }
        /* For simplicity, the audio data format used for recording
         is hardcoded here. We use PCM 44.1 kHz, 16 bit signed,
         stereo.
         */

        AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0F, 16, 2, 4, 44100.0F, false);

        /* Now, we are trying to get a TargetDataLine. The
         TargetDataLine is used later to read audio data from it.
         If requesting the line was successful, we are opening
         it (important!).
         */
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
        TargetDataLine targetDataLine = null;
        try {
            targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
            targetDataLine.open(audioFormat);
        } catch (LineUnavailableException e) {
            System.out.println("unable to get a recording line");
            e.printStackTrace();
            System.exit(1);
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

        /* Now, we are creating an AudioRecorder object. It
         contains the logic of starting and stopping the
         recording, reading audio data from the TargetDataLine
         and writing the data to a file.
         */
        //client.recorder = new AudioRecorder(targetDataLine, Defaults.FileFormatType, out);
        /* Start the Process class */
        new Thread(new Process()).start();
        try {
            /* waits for the next line of input into the prompt */
            while ((next = line.readLine()) != null) {
                //System.out.println("[sent]: "+next);
                if (Defaults.AUDIO_ENABLED) {
                    if (next.equals("start")) {
                        client.recorder = new AudioRecorder(targetDataLine, Defaults.FileFormatType, out);
                        System.out.println("Started Recording.");
                        stopped = false;
                        //Main.client.audio.audioOut();
                        client.recorder.start();
                    }
                    if (next.equals("stop")) {
                        System.out.println("Stopped Recording.");
                        client.recorder.stopRecording();
                        stopped = true;
                    }
                    if (next.equals("close")) {
                        close();
                        break;
                    }
                }
                if (Defaults.TEXT_ENABLED) {
                    out.write(next.getBytes());
                    out.flush();
                }
            }
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        }
        if (client.recorder != null) {
            client.recorder.stopRecording();
            client.recorder.closeDataLine();
        }
        System.exit(1);
    }

    private static void close() {
        System.out.println("Closing...");
        if (client.recorder != null) {
            client.recorder.stopRecording();
        }
        stopped = true;
    }
}
