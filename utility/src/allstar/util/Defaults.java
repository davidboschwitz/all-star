package allstar.util;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;

/**
 * The class that holds defaults for the Client and Server projects.
 *
 * @author davidboschwitz
 */
public class Defaults {

    /**
     * The Default AudioFormat to use for all audio (to be consistent).
     */
    public final static AudioFormat AudioFormat = new AudioFormat(16000.0F, 8, 2, true, true);
    
    /**
     * The Default AudioFileFormat to use for all audio.
     */
    public final static AudioFileFormat.Type FileFormatType = AudioFileFormat.Type.WAVE;
    /**
     * The Default Server IP-Address to connect to.
     */
    public static final String ServerIP = "davidboschwitz.student.iastate.edu";

    /**
     * The Default Server port number to connect to.
     */
    public static final int port = 2727;

    /**
     *
     */
    public static final boolean TextEnabled = false;

    /**
     *
     */
    public static final boolean AudioEnabled = true;

    /**
     * You should never create an instance of this class. It is for static
     * resources only.
     */
    private Defaults() {
    }
}
