/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jacob
 */
public class HttpConnection implements Connection {

    private final Socket socket;

    public HttpConnection(final Socket socket) {
        this.socket = socket;
    }

    @Override
    public void sendString(String message) {
        try {
            PrintWriter dataOut = new PrintWriter(socket.getOutputStream(), true);
            dataOut.println(message);
        } catch (IOException ex) {
            Logger.getLogger(HttpConnection.class.getName()).
                    log(Level.SEVERE, "Failed to send string " + message, ex);
        }
    }

    @Override
    public OutputStream getOutputStream() {
        try {
            return socket.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(HttpConnection.class.getName()).
                    log(Level.SEVERE, "Failed to get OutputStream from Socket object.", ex);
            return null;
        }
    }

    @Override
    public InputStream getInputStream() {
        try {
            return socket.getInputStream();
        } catch (IOException ex) {
            Logger.getLogger(HttpConnection.class.getName()).
                    log(Level.SEVERE, "Failed to get InputStream from Socket object.", ex);
            return null;
        }
    }

    @Override
    public String readString() {
        try {
            BufferedReader dataIn = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            return dataIn.readLine();
        } catch (Throwable ex) {
            Logger.getLogger(HttpConnection.class.getName()).
                    log(Level.SEVERE, "Failed to read string from connection.", ex);
            return null;
        }
    }

}
