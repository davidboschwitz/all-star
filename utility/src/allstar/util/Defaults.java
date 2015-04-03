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
    public final static AudioFileFormat.Type FileFormatType = AudioFileFormat.Type.AU;
    
    /**
     * The Default Server IP-Address to connect to.
     */
    public static final String SERVER_IP = "davidboschwitz.student.iastate.edu";

    /**
     * The Default Server port number to connect to.
     */
    public static final int SERVER_PORT = 2727;

    /**
     * The buffer size for audio.
     */
    public static final int EXTERNAL_BUFFER_SIZE = 128000;
   
    /**
     * Is the text version of the client enabled (audio must be disabled for
     * this to work). Disabled by default.
     */
    public static final boolean TEXT_ENABLED = false;

    /**
     * Is the audio version of the client enabled (this must be disabled for
     * text to work). Enabled by default.
     */
    public static final boolean AUDIO_ENABLED = true;

    /**
     * The maximum number of clients that will be allowed to connect.
     */
    public static final int MAX_CLIENTS = 20;
    
    /**
     * You should never create an instance of this class. It is for static
     * resources only.
     */
    private Defaults() {
    }
}
