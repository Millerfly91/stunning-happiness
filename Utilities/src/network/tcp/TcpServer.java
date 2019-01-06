/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.connection.Connection;
import network.connection.HttpConnection;

/**
 *
 * @author Jacob
 */
public class TcpServer {

    private ServerSocket sockServ;
    private long totalConnectionsProcessed = 0;
    private final ThreadPoolExecutor threadExecutor;
    private ConnectionAction connectionAction;
    private int port;

    public static void main(String[] argv) {
        TcpServer testServ
                = new TcpServer().
                setAction(conn -> {
                    try {
                        String recievedData = conn.readAsString();
                        System.out.println(recievedData);
                        conn.sendString("TEST Out. Recieved = " + recievedData);
//                        System.out.println(conn.blockRecieveString());
                        System.out.println(conn.readAsString());
                        recievedData = conn.readAsString();
                        conn.sendString("TEST Out. Recieved = " + recievedData);
                        System.out.println(conn.readAsString());
                        recievedData = conn.readAsString();
                        conn.sendString("TEST Out. Recieved = " + recievedData);
                        conn.close();
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }).
                setPort(1109).
                start();
    }

    public TcpServer() {
        this.threadExecutor = new ThreadPoolExecutor(1, 3,
                10, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
    }

    public TcpServer start() {
        try {
            sockServ = new ServerSocket(port);
            setNextWorker();
        } catch (IOException ex) {
            Logger.getLogger(TcpServer.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return this;
    }

    public TcpServer setAction(ConnectionAction<TcpConnection> connectionAction) {
        this.connectionAction = connectionAction;
        return this;
    }

    public TcpServer setPort(int port) {
        this.port = port;
        return this;
    }

    public void stop() {
        if (sockServ != null) {
            try {
                sockServ.close();
            } catch (IOException ex) {
                Logger.getLogger(TcpServer.class.getName()).log(
                        Level.WARNING,
                        "Error trying to close socket.", ex);
            }
        }
    }

    protected void setNextWorker() {
        ServerTask cw = new ServerTask(connectionAction);
        System.out.println("Starting a new task to wait for next connection. "
                + "Total: " + ++totalConnectionsProcessed);
        threadExecutor.execute(cw);
    }

    public void shutdown() {
        threadExecutor.shutdownNow();
    }

    protected TcpConnection waitForNextConnection() throws IOException {
        Socket s = sockServ.accept();
        return new TcpConnection(s);
    }

    public class ServerTask implements Runnable {

        BufferedReader dataIn;
        PrintWriter dataOut;
        ConnectionAction action;

        public ServerTask(ConnectionAction action) {
            this.action = action;
        }

        @Override
        public void run() {
            try {
                System.out.println("Starting up client handler.");
                TcpConnection newConnection = waitForNextConnection();
                setNextWorker();

                long timeToProcess = System.currentTimeMillis();
                action.action(newConnection);
                timeToProcess = System.currentTimeMillis() - timeToProcess;
                System.out.println("Process time " + timeToProcess + " ms");
            } catch (Throwable ex) {
                Logger.getLogger(TcpServer.class.getName()).log(
                        Level.SEVERE,
                        "Error made it to top of worker.", ex);
            }
        }

    }

    @FunctionalInterface
    public interface ConnectionAction<T extends Connection> {

        public abstract void action(T conn);
    }

}
