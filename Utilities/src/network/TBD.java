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
import network.tcp.TcpServer.ConnectionAction;
import network.connection.Connection;
import network.tcp.TcpClient;
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
                = new TcpServer().
                        setAction(newConnectionAction).
                        setPort(1109).
                        start();
    }

    public boolean validateIncomingConnection(Connection conn) {
//        validatedIP = false;
        List clientIPs;
        try {
            String recievedData = conn.readAsString();
            System.out.println(recievedData);
            if (checkKeyword(recievedData, conn)) return true;

        } catch (Throwable t) {
            t.printStackTrace();
        }
        return false;
    }

    public boolean checkKeyword(String recievedData, Connection conn) {
        if (recievedData.startsWith(keyword)) {
            System.out.println(recievedData);
            conn.sendString(" Recieved = " + recievedData);
//                String[] thisIP = recievedData.split("192", 5);
//                System.out.println(Arrays.toString(thisIP));
////              = clientIPs.add()
//                validatedIP = true;
            return true;
        }
        return false;
    }

    public Connection connectClient(String clientAddress, String content) {
        TcpClient client = new TcpClient();
        try {
            System.out.println("attempting " + clientAddress);
            client.connect(clientAddress, port);
            client.sendString(content);
            return client.getTcpConnection();
        } catch (Throwable t) {

        }
        return client.getTcpConnection();
    }

 public void findClient() throws IOException {
        List<String> IPList = ScanIP.getActiveIPs();
        List<String> validatedIPs = new ArrayList<String>();
        String line = "192.168.1.16";
//        for (String line : IPList) {
//            connectClient(line, keyword + message);
//            validateIncomingConnection();
            if (validateIncomingConnection(connectClient(line,keyword)) == true){
                validatedIPs.add(line);
            }
            System.out.println(validatedIPs);
//        }
    }
//    public void getClientIPs(Connection conn){
//        if (validated)
//    }

//    private boolean validateIncomingConnection() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
