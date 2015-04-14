package all_star.all_star_chat;

import java.net.Socket;

public class Client {

    private Socket socket;
    
    public Client(Socket s){
        this.socket = s;
    }

    public Socket getSocket(){
        return socket;
    }
}
