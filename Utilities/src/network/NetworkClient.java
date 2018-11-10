/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Jacob
 */
public class NetworkClient {

    private Socket currentSocket;

    public static void main(String argv[]) throws Exception {
        testMethod();
//        String sentence;
//        String modifiedSentence;
//        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
//        Socket clientSocket = new Socket("localhost", 6789);
//        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
//        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//        sentence = inFromUser.readLine();
//        outToServer.writeBytes(sentence + 'n');
//        modifiedSentence = inFromServer.readLine();
//        System.out.println("FROM SERVER: " + modifiedSentence);
//        clientSocket.close();
    }

    public static void testMethod() {
        NetworkClient testInstance = new NetworkClient();
        testInstance.connect("localhost", "6789");
        testInstance.transmitStream("TESTESTEST");
    }

    public String transmitStream(String out) {
        String response = null;
        try (DataOutputStream dataOut
                = new DataOutputStream(currentSocket.getOutputStream());
                BufferedReader dataIn
                = new BufferedReader(new InputStreamReader(currentSocket.getInputStream()))) {
            dataOut.writeChars(out);
            response = dataIn.readLine();
            System.out.println("Local Port for client: " + currentSocket.getLocalPort());
            System.out.println("Remote \"SocketAddress\": " + currentSocket.getRemoteSocketAddress().toString());
            System.out.println("Remote Response: " + response);
        } catch (IOException ex) {
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
