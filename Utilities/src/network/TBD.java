/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.IOException;
import java.util.List;
import console.ScanIP;
import java.util.ArrayList;
import network.NetworkServer.ConnectionAction;
import network.connection.Connection;
import network.tcp.TcpClient;

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
    boolean validatedIP;

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
        boolean validatedIP = false;
        List clientIPs;
        try {
            String recievedData = conn.readAsString();

            if (recievedData.startsWith(keyword)) {
                
                System.out.println(recievedData);
                conn.sendString(" Recieved = " + recievedData);
//                String[] thisIP = recievedData.split("192", 5);
//                System.out.println(Arrays.toString(thisIP));
////              = clientIPs.add()
                validatedIP = true;
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
//        return validated;
    }

    public void connectClient(String clientAddress, String content) {
        TcpClient testInstance = new TcpClient();
        try {
            System.out.println("attempting " + clientAddress);
            testInstance.connect(clientAddress, port);
            testInstance.sendString(content);
        } catch (Throwable t) {

        }
    }

 public void findClient() throws IOException {
        List<String> IPList = ScanIP.getActiveIPs();
        List<String> validatedIPs = new ArrayList<String>();
        TBD searchServ = new TBD();
        String line = "192.168.1.16";
//        for (String line : IPList) {
            searchServ.connectClient(line, keyword + message);
//            validateIncomingConnection();
            if (validatedIP == true){
                validatedIPs.add(line);
            }
            System.out.println(validatedIPs);
//        }
    }
//    public void getClientIPs(Connection conn){
//        if (validated)
//    }
}
