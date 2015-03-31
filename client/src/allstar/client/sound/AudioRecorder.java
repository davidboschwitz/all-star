/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allstar.client.sound;

import java.io.IOException;
import java.io.OutputStream;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;
/*
 * This code has parts of it taken from Matthias Pfisterer's SimpleAudioRecorder:
 * Copyright (c) 1999 - 2003 by Matthias Pfisterer
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 *
 * @author davidboschwitz
 */
public class AudioRecorder extends Thread {

    private TargetDataLine dataLine;
    private AudioFileFormat.Type m_targetType;
    private AudioInputStream audioInputStream;
    /**
     * The OutputStream object for the audio buffer.
     */
    private OutputStream outputStream;

    public AudioRecorder(TargetDataLine line, AudioFileFormat.Type targetType, OutputStream out) {
        dataLine = line;
        audioInputStream = new AudioInputStream(line);
        m_targetType = targetType;
        outputStream = out;
    }

    /**
     * Starts the recording. To accomplish this, (i) the line is started and
     * (ii) the thread is started.
     */
    @Override
    public void start() {
        /* Starting the TargetDataLine. It tells the line that
         we now want to read data from it. If this method
         isn't called, we won't
         be able to read data from the line at all.
         */
        dataLine.start();

        /* Starting the thread. This call results in the
         method 'run()' (see below) being called. There, the
         data is actually read from the line.
         */
        super.start();
    }

    /**
     * Stops the recording.
     *
     * Note that stopping the thread explicitely is not necessary. Once no more
     * data can be read from the TargetDataLine, no more data be read from our
     * AudioInputStream. And if there is no more data from the AudioInputStream,
     * the method 'AudioSystem.write()' (called in 'run()' returns. Returning
     * from 'AudioSystem.write()' is followed by returning from 'run()', and
     * thus, the thread is terminated automatically.
     *
     * It's not a good idea to call this method just 'stop()' because stop() is
     * a (deprecated) method of the class 'Thread'. And we don't want to
     * override this method.
     */
    public void stopRecording() {
        dataLine.stop();
    }

    /**
     * We used to close our TargetDataLine with the stopRecording() method, but
     * that caused an error that would only allow one transmission per session.
     *
     * This method closes the TargetDataLine.
     */
    public void closeDataLine() {
        dataLine.close();
    }

    /**
     * Main working method. You may be surprised that here, just
     * 'AudioSystem.write()' is called. But internally, it works like this:
     * AudioSystem.write() contains a loop that is trying to read from the
     * passed AudioInputStream. Since we have a special AudioInputStream that
     * gets its data from a TargetDataLine, reading from the AudioInputStream
     * leads to reading from the TargetDataLine. The data read this way is then
     * written to the passed File. Before writing of audio data starts, a header
     * is written according to the desired audio file type. Reading continues
     * until no more data can be read from the AudioInputStream. In our case,
     * this happens if no more data can be read from the TargetDataLine. This,
     * in turn, happens if the TargetDataLine is stopped or closed (which
     * implies stopping). (Also see the comment above.) Then, the file is closed
     * and 'AudioSystem.write()' returns.
     */
    @Override
    public void run() {
        try {
            AudioSystem.write(audioInputStream, m_targetType, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
