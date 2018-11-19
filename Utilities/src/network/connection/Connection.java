/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.connection;

import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Jacob
 */
public interface Connection {

    public OutputStream getOutputStream();

    public InputStream getInputStream();

    public String readAsString();

    public void sendString(String message);
}
