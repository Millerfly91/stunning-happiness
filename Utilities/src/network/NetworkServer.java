/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jacob
 */
public class NetworkServer {

    ServerSocket sockServ;
    BlockingQueue<Runnable> runQueue;
    ThreadPoolExecutor threadExecutor;
    ConnectionAcction connectionAction;

    public static void main(String[] argv) {
        NetworkServer testServer = new NetworkServer();
        testServer.setAction(conn -> {
            try {
                String recievedData = conn.readString();
                System.out.println(recievedData);
                conn.sendString("TEST Out. Recieved = " + recievedData);

//                String recievedData = processConnection(sock);
//                System.out.println("Recieved from client: " + recievedData);
//                writeDataOut(sock, recievedData.toUpperCase());
            } catch (Throwable t) {
                t.printStackTrace();
            }
        });
        testServer.listen(1109);
    }

    public NetworkServer() {
        this.runQueue = new LinkedBlockingQueue<>();
        this.threadExecutor = new ThreadPoolExecutor(0, 3, 10, TimeUnit.MINUTES, runQueue);
    }

    public NetworkServer setAction(ConnectionAcction connectionAction) {
        this.connectionAction = connectionAction;
        return this;
    }

    public void listen(int port) {
        try {
            sockServ = new ServerSocket(port);
            setNextWorker();
            System.out.println("Listening on port " + port);
        } catch (IOException ex) {
            Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close() {
        if (sockServ != null) {
            try {
                sockServ.close();
            } catch (IOException ex) {
                Logger.getLogger(NetworkServer.class.getName()).log(Level.WARNING, "Error trying to close socket.", ex);
            }
        }
    }

    private int connCount = 0;

    protected void setNextWorker() {
        ServerTask cw = new ServerTask(connectionAction);
        System.out.println("Starting a new task to wait for next connection. Total: " + connCount++);
        threadExecutor.execute(cw);

    }

    public void shutdown(){
        threadExecutor.shutdownNow();
    }
    
    protected Socket blockForNextConnection() throws IOException {
        Socket sock = sockServ.accept();
        return sock;
    }

    public class ServerTask implements Runnable {

        BufferedReader dataIn;
        PrintWriter dataOut;
        ConnectionAcction action;
//                = (sock) -> {
//            try {
//                String recievedData = processConnection(sock);
//                System.out.println("Recieved from client: " + recievedData);
//                writeDataOut(sock, recievedData.toUpperCase());
//            } catch (Throwable t) {
//                t.printStackTrace();
//            }
//        };

        public ServerTask(ConnectionAcction action) {
            this.action = action;
        }

        @Override
        public void run() {
            try {
                System.out.println("Starting up client handler.");
                Socket sock = blockForNextConnection();
                setNextWorker();

                long timeToProcess = System.currentTimeMillis();
                HttpConnection newHttpConn = new HttpConnection(sock);
                action.action(newHttpConn);
                timeToProcess = System.currentTimeMillis() - timeToProcess;
                System.out.println("Time to process connection: " + timeToProcess + " ms");
            } catch (Throwable ex) {
                Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE,
                        "Error made it to top of worker.", ex);
            }
        }

        protected String readStringIn(Socket sock)
                throws IOException {
            dataIn = new BufferedReader(
                    new InputStreamReader(sock.getInputStream()));
            return dataIn.readLine();
        }

        protected void writeDataOut(Socket sock, String otuput)
                throws IOException {
            dataOut = new PrintWriter(sock.getOutputStream(), true);
            dataOut.println(otuput);
        }

    }

    @FunctionalInterface
    public interface ConnectionAcction {

        public abstract void action(Connection conn);
    }

}
