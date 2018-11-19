/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.IOException;
import java.util.List;
import console.ScanIP;
import network.NetworkServer.ConnectionAction;
import network.connection.Connection;

/**
 *
 * @author James
 */
public class TBD {

    String clientAddress = "192.168.1.10";
    String port = "1109";
    String keyword = "BERT";
    NetworkServer server;
    List validatedClients;
    String message = "Hello Ernie!";

    public static void main(String[] argv) throws IOException {
        TBD startserv = new TBD();
        startserv.startServer();
        startserv.findClient();

//        startserv.connectClient("192.168.1.10", "1109", "What up Ernie?");
    }

    public void startServer() {
        ConnectionAction newConnectionAction = conn -> {
            validateIncomingConnection(conn);
        };

        server
                = new NetworkServer().
                        setAction(newConnectionAction).
                        setPort(1109).
                        start();
    }

    public void validateIncomingConnection(Connection conn) {
        try {
            String recievedData = conn.readAsString();

            if (recievedData.startsWith(keyword)) {
                System.out.println(recievedData.substring(keyword.length() + 1));
                conn.sendString(keyword + " Recieved = " + recievedData);

            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void connectClient(String clientAddress, String content) {
        NetworkClient testInstance = new NetworkClient();
        try {
            System.out.println("attempting " + clientAddress);
            testInstance.connect(clientAddress, port);
            testInstance.transmitStream(content);
        } catch (Throwable t) {

        }
    }

    public void findClient() throws IOException {
        List<String> IPList = ScanIP.getActiveIPs();
        TBD searchServ = new TBD();

        for (String line : IPList) {
            searchServ.connectClient(line, keyword + message);
        }
    }

}
