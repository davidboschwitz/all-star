package allstar.server;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Listens for new connections and connects Clients when they initiate a connection.
 * @author davidboschwitz
 */
public class ConnectionListener implements Runnable {
    
    private static ServerSocket listener;

    @Override
    public void run() {
        if (listener != null) {
            println("Already initialized.");
            return;
        }
        try {
            listener = new ServerSocket(Server.port);
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
            println("Error initializing ServerSocket!");
            return;
        }
        println("Initialized.");
        try {
            while(Processing.isRunning()) {
                int lastID = -1;
                Socket socket = listener.accept();
                println("New Connection from: " + socket.getRemoteSocketAddress());
                try {
                    lastID = Server.clientHandler.addClient(socket);
                } catch (java.io.IOException ioe) {
                    ioe.printStackTrace();
                    lastID = -1;
                }
                if (lastID == -1) {
                    socket.close();
                }
            }
        }catch(java.io.IOException ioe){
            ioe.printStackTrace();
        } finally {
            try{
            listener.close();
            }catch(java.io.IOException ioe){
                ioe.printStackTrace();
            }
        }

    }

    private void println(String s) {
        Server.out.println("[ConnectionListener]: " + s);
    }
}
