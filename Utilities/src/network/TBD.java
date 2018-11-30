/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import network.tcp.TcpClient;
import network.tcp.TcpConnection;
import network.tcp.TcpServer;

/**
 *
 * @author James
 */
public class TBD {

    String clientAddress = "192.168.1.10";
    String port = "1109";
    String keyword = "BERT";
    TcpServer server;
    List validatedClients;
    String message = "Hello Ernie!";
    boolean validatedIP;
    List<String> validatedIPs = new ArrayList<String>();

    public static void main(String[] argv) throws IOException {
       
            TBD startserv = new TBD();
            startserv.startServer();
             while (true){
            startserv.findClient();   
        }
//        startserv.connectClient("192.168.1.10", "1109", "What up Ernie?");
    }

    public void startServer() {

        server = new TcpServer().
                setAction(this::validateIncomingConnection).
                setPort(1109).
                start();
    }

    public boolean validateIncomingConnection(TcpConnection conn) {
        try {
            String recievedData = conn.readAsString();
            if (checkKeyword(recievedData)) {
                conn.sendString("BERT: You are being sent this message.");
//                addToValideIPs(conn.getSocket().getRemoteSocketAddress().toString());
                return true;
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return false;
    }

//    private void addToValideIPs(String adress) {
//        System.out.println("Adding new valid IP to list: " + adress);
//        validatedIPs.add(adress);
//    }

    public boolean checkKeyword(String recievedData) {
        boolean valid = false;
        if (recievedData.contains(keyword)) {
            valid = true;
        }
        return valid;
    }

    public TcpConnection connectClient(String clientAddress, String content) {
        TcpClient client = new TcpClient();
        try {
            client.connect(clientAddress, port);
            client.sendString(content);
            return client.getTcpConnection();
        } catch (Throwable t) {

        }
        return client.getTcpConnection();
    }

    public List<String> findClient() throws IOException {
        String line = "192.168.1.18";
        if (validateIncomingConnection(connectClient(line, keyword)) == true) {
            System.out.println("Adding to validatedIps: " + line);
        }
        System.out.println(validatedIPs);
        return validatedIPs;
    }
}
