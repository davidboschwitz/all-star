package allstar.util;

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
     * You should never create an instance of this class. It is for static
     * resources only.
     */
    private Defaults() {
    }
}
