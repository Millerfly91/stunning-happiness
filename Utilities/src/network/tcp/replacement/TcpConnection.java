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
    private DataOutputStream dataOut;
    private DataInputStream dataIn;

    public static void main(String[] args) {
        try {
            Logger.getLogger(TcpConnection.class.getName()).log(Level.SEVERE, "LOGGER WRITE TEST");
            TcpConnection testInstance = new TcpConnection(new Socket("192.168.1.11", 1109));
            testInstance.sendString("I am client 1");
            System.out.println(testInstance.recieveString());
            testInstance.sendString("I am client 2");
            System.out.println(testInstance.recieveString());
            testInstance.sendString("I am client 3");
            System.out.println(testInstance.recieveString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public TcpConnection(Socket socket) throws ConnectionException {
        initializeDataStreams(socket);
    }

    private void initializeDataStreams(Socket socket)
            throws ConnectionException {
        this.socket = socket;
        try {
            dataOut = new DataOutputStream(socket.getOutputStream());
            dataIn = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            throw new ConnectionException("Failed to initialize the data stream.", ex);
        }
    }

    public void sendString(
            String msg) throws ConnectionException {
        try {
            dataOut.writeUTF(msg);
        } catch (IOException ex) {
            throw new ConnectionException(
                    "Failed to send string. " + msg, ex);
        }
    }

    public String recieveString()
            throws ConnectionException {
        String msg;
        try {
            msg = dataIn.readUTF();
        } catch (IOException ex) {
            throw new ConnectionException("Failed to read string.", ex);
        }
        return msg;
    }

    @Override
    public boolean isOpen() {
        return socket.isConnected();
    }

    @Override
    public void open() throws ConnectionException {
        initializeDataStreams(socket);
    }

    @Override
    public void close() throws ConnectionException {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(TcpConnection.class.getName()).log(
                    Level.SEVERE,
                    "Failed to close TcpConnection.",
                    ex);
        }
    }

    @Override
    public boolean hasCredentials() {

        /* No credentials necessary for TcpConnection. */
        return true;
    }

}
