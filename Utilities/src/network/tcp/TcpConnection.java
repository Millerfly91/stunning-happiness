/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.tcp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    private String remoteAddress;
    private int remotePort;
    private static int count = 0;
    private String connName = "TcpConnection#" + ++count;
    private long lastUsed_ms;

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
            outputStream = new DataOutputStream(this.socket.getOutputStream());
            inputStream = new DataInputStream(this.socket.getInputStream());
//            activeOutput = new PrintWriter(this.socket.getOutputStream(), true);
//            activeInput = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
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
        if (!socket.isConnected()) {
            System.out.println(connName + " is not connected so cannot send message.");
        }
        System.out.println(connName + " -> Sending to " + remoteAddress + "..."
                + " \"" + message + "\"");
        try {
            //        this.activeOutput.println(message);
//        this.activeOutput.flush();
            this.outputStream.writeUTF(message);
            this.outputStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(TcpConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        lastUsed_ms = System.currentTimeMillis();
    }

    public void close() {
        try {
            this.socket.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
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
            Thread.sleep(1000);
//            String message = activeInput.readLine();
            String message = inputStream.readUTF();
            lastUsed_ms = System.currentTimeMillis();
            System.out.println(connName + " -> Recieved from " + remoteAddress + "... \"" + message + "\"");
            return message;
        } catch (Throwable ex) {
            Logger.getLogger(TcpConnection.class.getName()).
                    log(Level.SEVERE, "Failed to read string from connection.", ex);
            return null;
        }
    }

    public String blockRecieveString() {
        while (true) {
            try {
                Thread.sleep(100);
                String response = this.readAsString();
                if (response != null && response.length() > 0) {
                    return response;
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

    }

}
