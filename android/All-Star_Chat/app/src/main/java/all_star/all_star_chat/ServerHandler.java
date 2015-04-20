package all_star.all_star_chat;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.IBinder;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class ServerHandler extends Service {

    private static final int RECORDING_RATE = 44100;
    private static final int CHANNEL = AudioFormat.CHANNEL_IN_STEREO;
    private static final int FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private AudioRecord recorder = null;
    boolean cSA = false;
    private static int BUFFER_SIZE = AudioRecord.getMinBufferSize(RECORDING_RATE, CHANNEL, FORMAT);
    String address;
    OutputStream out;
    Socket socket;
    Intent i;
    BroadcastReceiver checker;


    public int onStartCommand (Intent intent, int flags, int startId){
        address = intent.getStringExtra("ip_address");
        init();
        i = intent;
        checker = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                cSA = intent.getBooleanExtra("currentlySendingAudio", false);
                if(cSA) {
                    startStreaming();
                }
                else recorder.release();
            }
        };



        return START_STICKY;
    }

    private void init(){
        try {
            socket = new Socket(address, 2727);
            out = socket.getOutputStream();

        }catch(UnknownHostException uhe){

        }catch(java.io.IOException ioe){
            ioe.printStackTrace();
        }
    }


    public ServerHandler() {

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
                        int read = recorder.read(buffer, 0, buffer.length);



//                        // place contents of buffer into the packet
//                        packet = new DatagramPacket(buffer, read,
//                                serverAddress, 2727);

                        // send the packet
                        //out.send(packet);


                        out.write(buffer);
                        cSA = i.getBooleanExtra("currentlySendingAudio",cSA);
                    }



                } catch (Exception e) {

                }
            }
        });

        // start the thread
        streamThread.start();
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }



}
