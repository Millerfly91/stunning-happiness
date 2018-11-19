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
import java.util.concurrent.BlockingQueue;
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
    private final BlockingQueue<Runnable> runQueue;
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
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }).
                setPort(1109).
                start();
    }

    public TcpServer() {
        this.runQueue = new LinkedBlockingQueue<>();
        this.threadExecutor = new ThreadPoolExecutor(0, 3, 10, TimeUnit.MINUTES, runQueue);
    }

    public TcpServer start() {
        try {
            sockServ = new ServerSocket(port);
            setNextWorker();
        } catch (IOException ex) {
            Logger.getLogger(TcpServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }

    public TcpServer setAction(ConnectionAction connectionAction) {
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
                Logger.getLogger(TcpServer.class.getName()).log(Level.WARNING, "Error trying to close socket.", ex);
            }
        }
    }

    private int connCount = 0;

    protected void setNextWorker() {
        ServerTask cw = new ServerTask(connectionAction);
        System.out.println("Starting a new task to wait for next connection. Total: " + connCount++);
        threadExecutor.execute(cw);

    }

    public void shutdown() {
        threadExecutor.shutdownNow();
    }

    protected HttpConnection blockForNextConnection() throws IOException {
        Socket s = sockServ.accept();
        return new HttpConnection(s);
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
                HttpConnection newHttpConn = blockForNextConnection();
                setNextWorker();

                long timeToProcess = System.currentTimeMillis();
                action.action(newHttpConn);
                timeToProcess = System.currentTimeMillis() - timeToProcess;
                System.out.println("Time to process connection: " + timeToProcess + " ms");
            } catch (Throwable ex) {
                Logger.getLogger(TcpServer.class.getName()).log(Level.SEVERE,
                        "Error made it to top of worker.", ex);
            }
        }

    }

    @FunctionalInterface
    public interface ConnectionAction {

        public abstract void action(Connection conn);
    }


    
}
