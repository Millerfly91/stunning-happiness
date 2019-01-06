/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.tcp.replacement;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Jacob
 */
public class TestTest {
      public static void main(String[] args) {
        int serverPort = 1109;

        try {
            InetAddress inetAdd = InetAddress.getByName("192.168.1.16");
            Socket socket = new Socket(inetAdd, serverPort);

            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            DataInputStream dIn = new DataInputStream(in);
            DataOutputStream dOut = new DataOutputStream(out);

            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Type in something and press enter. Will send it to the server and tell ya what it thinks.");
            System.out.println();

            String line = null;
            while (true) {
                line = keyboard.readLine();
                System.out.println("Wrinting Something on the server");
                dOut.writeUTF(line);
                dOut.flush();

                line = dIn.readUTF();
                System.out.println("Line Sent back by the server---" + line);
            }
        }
        catch (Exception e) { 
        e.printStackTrace();}
    }
}
