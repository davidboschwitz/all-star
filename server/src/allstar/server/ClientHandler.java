package allstar.server;

import allstar.util.Defaults;
import java.net.Socket;

/**
 *
 * @author davidboschwitz
 */
public class ClientHandler {

    public final int MAX_CLIENTS;
    private final Client[] clients;
    private byte[] buffer = new byte[Defaults.EXTERNAL_BUFFER_SIZE];
    private int currentLength = 0;

    ClientHandler(int MAX_CLIENTS) {
        this.MAX_CLIENTS = MAX_CLIENTS;
        clients = new Client[MAX_CLIENTS];
    }

    public int addClient(Socket s) throws java.io.IOException {
        for (int i = 0; i < clients.length; i++) {
            if (clients[i] == null) {
                clients[i] = new Client(i, s);
                return i;
            }
        }
        return -1;
    }

    public Client getClient(int SESSION_ID) {
        return clients[SESSION_ID];
    }

    void disconnect(Client c) {
        if (c == null) {
            return;
        }
        if(c.isTalking){
            return;
        }
        println("Disconnecting " + c.SESSION_ID);
        c.disconnect();
        clients[c.SESSION_ID] = null;
    }

    void sendToAll(Client c) throws java.io.IOException {
        c.isTalking = true;
        currentLength = c.in.read(buffer);
        for (int i = 0; i < MAX_CLIENTS; i++) {
            Client s = clients[i];
            if (s == null) {
                continue;
            }
            if (c.SESSION_ID == s.SESSION_ID) {
                continue;
            }
            if (c.remove()) {
                continue;
            }
            try {
                if (Defaults.TextEnabled) {
                    s.out.write(("[" + c.SESSION_ID + "]: ").getBytes());
                }
                s.out.write(buffer, 0, currentLength);
                if (Defaults.TextEnabled) {
                    s.out.write("\n".getBytes());
                }
                s.out.flush();
            } catch (java.net.SocketException se) {
                s.isTalking = false; //failsafe
                disconnect(s);
            }
        }
        c.isTalking = false;

    }

    /*public void postMessage(String from, String msg){
     for(Client c: clients){
     if(c != null)
     c.sendMessage(from+": "+msg);
     }
     }*/
    public void process() {
        for (Client c : clients) {
            if (c == null) {
                continue;
            }
            c.process();
            if (c.remove()) {
                disconnect(c);
            }

        }
    }

    protected void disconnectAll() {
        for (int i = 0; i < MAX_CLIENTS; i++) {
            disconnect(clients[i]);
        }
    }

    private void println(String s) {
        Server.out.println("[ClientHandler]: " + s);
    }
}
