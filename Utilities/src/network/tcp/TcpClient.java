/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.tcp;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jacob
 */
public class TcpClient {

    private TcpConnection conn;

    public static void main(String argv[]) throws Exception {
        TcpClient testInstance = new TcpClient();
        testInstance.connect("192.168.1.16", "1109");
        testInstance.sendString("What up Ernie?");
        System.out.println(testInstance.waitUntilRecieve());
        testInstance.sendString("Second sending from client.");
    }

    private String waitUntilRecieve() {
        while (true) {
            try {
                Thread.sleep(100); 
               String response = conn.readAsString();
                
                if (response != null && response.length() > 0) {
                    return response;
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

    }

    public static void testMethod() {
        for (int i = 0; i < 4; i++) {
            TcpClient testInstance = new TcpClient();
            testInstance.connect("192.168.1.10", "1109");
            testInstance.sendString("What up Ernie?");
        }
    }
    
    public TcpConnection getTcpConnection(){
        return this.conn;
    }

    public void sendString(String out) {
        conn.sendString(out);       
    }

    public String recieveString() {
        String response = null;
        try {
            response = conn.readAsString();
            System.out.println("Local Port for client: " + conn.getSocket().getLocalPort());
            System.out.println("Remote \"SocketAddress\": " + conn.getSocket().getRemoteSocketAddress().toString());
            System.out.println("Remote Response: " + response);
        } catch (Throwable x) {
            Logger.getLogger(TcpClient.class.getName()).log(Level.SEVERE, null, x);
        }
        return response;
    }

    public void connect(String destIp, String port) {
        try {
            this.conn = new TcpConnection(destIp, Integer.parseInt(port));
//            this.conn = new TcpConnection(new Socket(destIp, Integer.parseInt(port)));
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public void close() {
        try {
            this.conn.getSocket().close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
