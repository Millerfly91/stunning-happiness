/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Jacob
 */
public class TcpClient {

    private Socket currentSocket;
    private TcpConnection conn;

    public static void main(String argv[]) throws Exception {
        testMethod();
    }

    public static void testMethod() {
        for (int i = 0; i < 4; i++) {
            TcpClient testInstance = new TcpClient();
            testInstance.connect("192.168.1.10", "1109");
            testInstance.transmitStream("What up Ernie?");
        }
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
            this.conn = new TcpConnection(new Socket(destIp, Integer.parseInt(port)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
