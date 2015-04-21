package all_star.all_star_chat;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


//TODO: Implement a way to stop recording while output is plying or vice-versa
//UPDATE: Should be implemented by server

public class ServerHandler extends Service {

    private static final int RECORDING_RATE = 44100;
    private static final int CHANNEL = AudioFormat.CHANNEL_IN_STEREO;
    private static final int FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private static int BUFFER_SIZE = AudioRecord.getMinBufferSize(RECORDING_RATE, CHANNEL, FORMAT);
    boolean cSA = false;
    private BroadcastReceiver checker = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            cSA = intent.getBooleanExtra("currentlySendingAudio", false);
            if (cSA) {
                startStreaming();
            } else recorder.release();
        }
    };
    String address;
    OutputStream out;
    InputStream in;
    Socket socket;
    Context c;
    Intent i;
    Intent backpls = new Intent(this, Login.class);
    //BroadcastReceiver checker;
    MediaPlayer mp = new MediaPlayer();
    private AudioRecord recorder = null;


    /*
       constructor
    */
    public ServerHandler() {

    }

    /*
    once the service starts -
        initialize server connection
        set intent
        try to pull audio from server
        start a broadcast receiver that will check to see if the button is pushed and broadcast audio to server
*/
    public int onStartCommand(Intent intent, int flags, int startId) {
        address = intent.getStringExtra("ip_address");
        init();
        i = intent;
        getAudio();

        return START_REDELIVER_INTENT;
    }

    /*
        initializes connection to the server
     */
    private void init() {
        try {
            socket = new Socket(address, 2727);
            out = socket.getOutputStream();
            in = socket.getInputStream();
        } catch (UnknownHostException uhe) {
            Toast.makeText(getApplicationContext(), "Error: Incorrect IP; Returning to Login", Toast.LENGTH_LONG);
            startActivity(backpls);
        } catch (java.io.IOException ioe) {
            Toast.makeText(getApplicationContext(), "Error:Could not bind to socket / input / output; \n\nTrying again...", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), ioe.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /*
            takes the audio from the server
         */
    public void getAudio() {
        byte[] buffer = new byte[128000];
        try {
            while (true) {
                in.read(buffer);
                playAudio(buffer);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
        tries to play the audio
     */
    private void playAudio(byte[] mp3SoundByteArr) {
        try {
            File tempMp3 = File.createTempFile("buffer", "mp3", getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(mp3SoundByteArr);
            fos.close();
            FileInputStream fis = new FileInputStream(tempMp3);
            mp.reset();
            mp.setDataSource(fis.getFD());
            mp.prepare();
            mp.start();

        } catch (IOException ioe) {
            return;
        }
    }

    public void startStreaming() {


        Thread streamThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {


                    byte[] buffer = new byte[128000];


//                    final InetAddress serverAddress = InetAddress
//                            .getByName(address);

//                    DatagramPacket packet;


                    recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
                            RECORDING_RATE, CHANNEL, FORMAT, BUFFER_SIZE * 10);


                    recorder.startRecording();

                    while (cSA) {

                        // read the data into the buffer
                        recorder.read(buffer, 0, buffer.length);


//                        // place contents of buffer into the packet
//                        packet = new DatagramPacket(buffer, read,
//                                serverAddress, 2727);

                        // send the packet
                        //out.send(packet);


                        out.write(buffer);
                        cSA = i.getBooleanExtra("currentlySendingAudio", cSA);
                    }


                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error Sending Message", Toast.LENGTH_SHORT).show();

                }
            }
        });

        // start the thread
        streamThread.start();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
