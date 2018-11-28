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

    public TcpConnection(final Socket socket) {
        this.socket = socket;
    }

    public TcpConnection(final String address, int port) {
        try {
            this.socket = new Socket(address, port);
            this.socket.setSoTimeout(0);
            activeOutput = new PrintWriter(this.socket.getOutputStream(), true);
            activeInput = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(TcpConnection.class.getName()).log(Level.SEVERE, null,
                    "Faild to get socket connection.");
            this.socket = null;
        }
    }

    public Socket getSocket() {
        return this.socket;
    }

    @Override
    public void sendString(String message) {
//            PrintWriter dataOut = new PrintWriter(socket.getOutputStream(), true);
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
            return activeInput.readLine();
        } catch (Throwable ex) {
            Logger.getLogger(TcpConnection.class.getName()).
                    log(Level.SEVERE, "Failed to read string from connection.", ex);
            return null;
        }
    }

}
