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
    ThreadPoolExecutor tpe;

    public static void main(String[] argv) {
        NetworkServer testServer = new NetworkServer();
        testServer.listen(1109);
    }

    public NetworkServer() {
        this.runQueue = new LinkedBlockingQueue<>();
        this.tpe = new ThreadPoolExecutor(0, 3, 10, TimeUnit.MINUTES, runQueue);
    }

    public void listen(int port) {
        try {
            sockServ = new ServerSocket(port);
            startNextWorker();
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

    protected void startNextWorker() {
        ClientWorker cw = new ClientWorker();
//        runQueue.add(cw);
        tpe.execute(cw);
        
    }

    protected Socket getSocketAndProcess() throws IOException {
        Socket sock = sockServ.accept();
        return sock;
    }

    public class ClientWorker implements Runnable {

        BufferedReader dataIn;
        PrintWriter dataOut;

        @Override
        public void run() {
            try {
                System.out.println("Starting up client handler.");
                Socket sock = getSocketAndProcess();
                startNextWorker();
                String recievedData = processConnection(sock);
                System.out.println("Recieved from client: " + recievedData);
                writeDataOut(sock, recievedData.toUpperCase());

            } catch (Throwable ex) {
                Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, "Error made it to top of worker.", ex);
            }
        }

        protected String processConnection(Socket sock) throws IOException {
            dataIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            return dataIn.readLine();
        }

        protected void writeDataOut(Socket sock, String otuput) throws IOException {
            dataOut = new PrintWriter(sock.getOutputStream(), true);
            dataOut.println(otuput);
        }

    }

}
