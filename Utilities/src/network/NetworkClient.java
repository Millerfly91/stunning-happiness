/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jacob
 */
public class NetworkClient {

    private Socket currentSocket;

    public static void main(String argv[]) throws Exception {
        testMethod();
    }

    public static void testMethod() {
        NetworkClient testInstance = new NetworkClient();
        testInstance.connect("192.168.1.10", "1109");
        testInstance.transmitStream("What up Ernie?");
    }

    public String recieveString() {
        String response = null;
        try (BufferedReader dataIn
                = new BufferedReader(new InputStreamReader(currentSocket.getInputStream()))) {
            Thread.sleep(3000);
            response = dataIn.readLine();
            System.out.println("Local Port for client: " + currentSocket.getLocalPort());
            System.out.println("Remote \"SocketAddress\": " + currentSocket.getRemoteSocketAddress().toString());
            System.out.println("Remote Response: " + response);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    public String transmitStream(String out) {
        String response = null;
        try (PrintWriter dataOut
                = new PrintWriter(currentSocket.getOutputStream());
                BufferedReader dataIn
                = new BufferedReader(new InputStreamReader(currentSocket.getInputStream()))) {
            dataOut.println(out);
            dataOut.flush();
            response = dataIn.readLine();
            System.out.println("Local Port for client: " + currentSocket.getLocalPort());
            System.out.println("Remote \"SocketAddress\": " + currentSocket.getRemoteSocketAddress().toString());
            System.out.println("Remote Response: " + response);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }

    public void connect(String destIp, String port) {
        try {
            Socket clientSocket = new Socket(destIp, Integer.parseInt(port));
            this.currentSocket = clientSocket;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
