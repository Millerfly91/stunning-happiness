/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.tcp.replacement;

/**
 *
 * @author Jacob
 */
public interface Connection {

    boolean isOpen();

    public void open();

    public void close();

    public abstract boolean hasCredentials();

}
