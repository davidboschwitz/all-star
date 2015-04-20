package all_star.all_star_chat;


import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

//import
import java.net.Socket;
import java.net.URI;

import static android.media.AudioFormat.Builder;


public class Chat extends ActionBarActivity {
    final Intent i = new Intent(this, ServerHandler.class);










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ImageButton btn = (ImageButton) findViewById(R.id.imageButton);




        btn.setOnTouchListener( new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        startStreamingAudio();

                        break;

                    case MotionEvent.ACTION_UP:
                        stopStreamingAudio();
                        break;
                }

                return false;
            }
        });
    }

    private void startStreamingAudio() {
        i.putExtra("currentlySendingAudio",true);
        sendBroadcast(i);

//        ServerHandler.startStreaming();
    }

    private void stopStreamingAudio() {
        i.putExtra("currentlySendingAudio",false);
        sendBroadcast(i);
//        recorder.release();
    }




    public void onStartCommand (Intent intent, int flags, int startId){

//        address = intent.getStringExtra("ip_address");

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
