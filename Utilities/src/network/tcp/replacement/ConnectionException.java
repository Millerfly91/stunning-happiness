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
public class ConnectionException extends Exception {

    public ConnectionException() {
        super();
    }

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(Exception cause) {
        super(cause);
    }

    public ConnectionException(String message, Exception cause) {
        super(message, cause);
    }

}
