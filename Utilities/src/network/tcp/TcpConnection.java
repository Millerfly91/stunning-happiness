/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.connection.Connection;

/**
 *
 * @author Jacob
 */
public class TcpConnection implements Connection {

    private Socket socket;
    private PrintWriter activeOutput;
    private BufferedReader activeInput;
    private String remoteAddress;
    private int remotePort;
    private static int count = 0;
    private String connName = "TcpConnection" + ++count;

    public TcpConnection(final Socket socket) {
        initializeConnection(socket);
    }

    public TcpConnection(final String address, int port) {
        try {
            initializeConnection(new Socket(address, port));
        } catch (IOException t) {
            Logger.getLogger(TcpConnection.class.getName()).log(
                    Level.SEVERE,
                    "Faild to get socket connection.", t);
            this.socket = null;
        }
    }

    private void initializeConnection(Socket socket) {
        try {
            this.socket = socket;
            this.socket.setSoTimeout(0);
            this.remoteAddress = socket.getRemoteSocketAddress().toString();
            activeOutput = new PrintWriter(this.socket.getOutputStream(), true);
            activeInput = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (SocketException ex) {
            Logger.getLogger(TcpConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TcpConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Socket getSocket() {
        return this.socket;
    }

    @Override
    public void sendString(String message) {
        System.out.println(connName + " -> Sending to " + remoteAddress + "..."
                + " \"" + message + "\"");
        this.activeOutput.println(message);
    }

    @Override
    public OutputStream getOutputStream() {
        try {
            return socket.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(TcpConnection.class.getName()).
                    log(Level.SEVERE, "Failed to get OutputStream from Socket object.", ex);
            return null;
        }
    }

    @Override
    public InputStream getInputStream() {
        try {
            return socket.getInputStream();
        } catch (IOException ex) {
            Logger.getLogger(TcpConnection.class.getName()).
                    log(Level.SEVERE, "Failed to get InputStream from Socket object.", ex);
            return null;
        }
    }

    @Override
    public String readAsString() {
        try {
            Thread.sleep(1500);
            String message = activeInput.readLine();
            System.out.println(connName + " -> Recieved from " + remoteAddress + "... \"" + message + "\"");
            return message;
        } catch (Throwable ex) {
            Logger.getLogger(TcpConnection.class.getName()).
                    log(Level.SEVERE, "Failed to read string from connection.", ex);
            return null;
        }
    }

}
