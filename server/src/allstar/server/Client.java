package allstar.server;

import allstar.util.Defaults;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author davidboschwitz
 */
public class Client {

    private int UID;
    public final int SESSION_ID;
    //protected String name;
    /**/
    /**
     * InputStream from the client.
     */
    protected final InputStream in;
    /**
     * OuputStream to the client.
     */
    protected final OutputStream out;
    /**
     * The socket object for the connection to the client.
     */
    private final Socket socket;
    //private final byte[] buffer;

    /**
     * Should we remove the client?
     */
    private boolean removeMe = false;
    
    /**
     * Is the person transmitting/talking right meow?
     */
    public boolean isTalking = false;
    
    /**
     * Initialize the Server-side Client object
     *
     * @param SESSION_ID
     * @param sock
     * @throws java.io.IOException
     */
    public Client(int SESSION_ID, Socket sock) throws java.io.IOException {
        this.SESSION_ID = SESSION_ID;
        this.in = sock.getInputStream();
        this.out = sock.getOutputStream();
        this.socket = sock;
        //this.buffer = new byte[128];
        //this.inStream = new Stream(256);
        //this.outStream = new Stream(256);
        this.initialize();
    }

    private void initialize() throws java.io.IOException {
        if (Defaults.TextEnabled) {
            out.write(("Connection Accepted - [" + SESSION_ID + "]\n").getBytes());
        }
        //inStream.size = in.read(inStream.buffer);
        //inStream.currentPos = 0;
        //name = inStream.readString();
        //println("Name = "+name);
    }

    protected void disconnect() {
        try {
            in.close();
            out.flush();
//            socket.shutdownOutput();
//            socket.shutdownInput();
            socket.close();
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * This method is run every time the server 'ticks' so general runtime tasks
     * should be in this "process".
     */
    public void process() {
        if (!socket.isConnected()) {
            removeMe = true;
        }
        try {
            if (in.available() > 0) {
                Server.clientHandler.sendToAll(this);
                //println("MSG");
            }
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /*public void writeOut() {
     try{
     out.write(outStream.buffer, 0, outStream.currentPos);
     out.flush();
     }catch(java.io.IOException ioe){
     ioe.printStackTrace();
     }
     outStream.currentPos = 0;
     }
    
     public void sendMessage(String msg) {
     outStream.writeString(msg);
     writeOut();
     }*/
    /**
     * Indicates if the client should be removed from the ClientHandler's array
     * for disconnect
     *
     * @return removeMe
     */
    public boolean remove() {
        if (!socket.isConnected()) {
            removeMe = true;
        }
        return removeMe;
    }

    private void println(String s) {
        Server.out.println(this + ": " + s);
    }

    @Override
    public String toString() {
        return "[Client-" + SESSION_ID + "]";
    }

}
