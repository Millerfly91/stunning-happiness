/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

/**
 *
 * @author James
 */
public class TBD {
    public static void main(String[] argv) {
        startServer();
        
        connectClient();
    }

    public static void startServer() {
        NetworkServer testServ
                = new NetworkServer().
                        setAction(conn -> {
                            try {
                                String recievedData = conn.readAsString();
                                System.out.println(recievedData);
                                conn.sendString("TEST Out. Recieved = " + recievedData);
                            } catch (Throwable t) {
                                t.printStackTrace();
                            }
                        }).
                        setPort(1109).
                        start();
    }
    
    public static void connectClient() {
            NetworkClient testInstance = new NetworkClient();
            testInstance.connect("192.168.1.10", "1109");
            testInstance.transmitStream("What up Ernie?");
    }
    
}
