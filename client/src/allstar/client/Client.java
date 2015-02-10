/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allstar.client;

import java.net.Socket;

/**
 *
 * @author davidboschwitz
 */
public class Client {

    /**
     * The socket connection to the client
     */
    Socket socket;

    public Client(Socket s) {
        this.socket = s;
    }
}
