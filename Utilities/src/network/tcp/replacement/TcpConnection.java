/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.tcp.replacement;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jacob
 */
public class TcpConnection implements Connection {

    private Socket socket;
    private Credential remoteCred;
    private DataOutputStream dataOut;
    private DataInputStream dataIn;

    public static void main(String[] args) {
        try {
            TcpConnection testInstance = new TcpConnection(new Socket("192.168.1.11", 1109));
            testInstance.sendString("I am client 1");
            System.out.println(testInstance.recieveString());
            testInstance.sendString("I am client 2");
            System.out.println(testInstance.recieveString());
            testInstance.sendString("I am client 3");
            System.out.println(testInstance.recieveString());
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(TcpConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public TcpConnection(Socket socket) {
        initializeDataStreams(socket);
    }

    private void initializeDataStreams(Socket socket) {
        this.socket = socket;
        try {
            dataOut = new DataOutputStream(socket.getOutputStream());
            dataIn = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(TcpConnection.class.getName()).log(
                    Level.SEVERE,
                    "Failed to initialize data streams with socket.", ex);
        }
    }

    public void sendString(String msg) {
        try {
            dataOut.writeUTF(msg);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String recieveString()
            throws Exception {
        String msg;
        try {
            msg = dataIn.readUTF();
        } catch (IOException ex) {
            throw new Exception("Failed to read string.", ex);
        }
        return msg;
    }

    @Override
    public boolean isOpen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void open() {
        initializeDataStreams(socket);
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(TcpConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean hasCredentials() {
        return remoteCred != null && remoteCred.isSet();
    }

}
