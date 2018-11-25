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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.input.TeeInputStream;

/**
 *
 * @author Jacob
 */
public class TcpClient {

    private TcpConnection conn;

    public static void main(String argv[]) throws Exception {
        TcpClient testInstance = new TcpClient();
        testInstance.connect("192.168.1.10", "1109");
        testInstance.sendString("What up Ernie?");
        System.out.println(testInstance.waitUntilRecieve());
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

    public String sendString(String out) {
        String response = null;
        try (PrintWriter dataOut
                = new PrintWriter(conn.getOutputStream());
                BufferedReader dataIn
                = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            dataOut.println(out);
            dataOut.flush();
            response = dataIn.readLine();
            System.out.println("Local Port for client: " + conn.getSocket().getLocalPort());
            System.out.println("Remote \"SocketAddress\": " + conn.getSocket().getRemoteSocketAddress().toString());
            System.out.println("Remote Response: " + response);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }

    public String recieveString() {
        String response = null;
        try (BufferedReader dataIn
                = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            Thread.sleep(3000);
            response = dataIn.readLine();
            System.out.println("Local Port for client: " + conn.getSocket().getLocalPort());
            System.out.println("Remote \"SocketAddress\": " + conn.getSocket().getRemoteSocketAddress().toString());
            System.out.println("Remote Response: " + response);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(TcpClient.class.getName()).log(Level.SEVERE, null, ex);
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

    public void close() {
        try {
            this.conn.getSocket().close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
