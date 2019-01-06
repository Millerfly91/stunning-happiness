/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.tcp.replacement;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import misc.time.Stopwatch;

/**
 *
 * @author Jacob
 */
public class TcpServer {

    private ServerSocket socketListener;
    private int port;
    private ConnectionAction connectionAction;
    private ExecutorService executorService;
    private ConnectionDispatcher connDispatcher;

    public static void main(String[] args) {
        TcpServer testInstance = new TcpServer(1109);
        try {
            testInstance.setAction(connection -> {
                try {
                    Stopwatch stopwatch = new Stopwatch();
                    stopwatch.start();
                    System.out.println(connection.recieveString());
                    connection.sendString("HELLO I AM SERVER..");
                    System.out.println(connection.recieveString());
                    connection.sendString("HELLO I AM SERVER.. 2");
                    System.out.println(connection.recieveString());
                    connection.sendString("HELLO I AM SERVER.. 3");
                    stopwatch.stop();
                    System.out.println("TIME TO RUN " + stopwatch.elapsed(TimeUnit.MICROSECONDS));
                    stopwatch.reset();
                } catch (Exception ex) {
                    Logger.getLogger(TcpServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            testInstance.start();
            Thread.sleep(5 * 60 * 1000);
        } catch (Throwable ex) {
            Logger.getLogger(TcpServer.class.getName()).log(
                    Level.SEVERE, "jlr logger test", ex);
        }
        System.out.println("Stopping test run...");
        testInstance.stop();
    }

    public TcpServer(int port) {
        this.port = port;
    }

    public TcpServer start() {
        try {
            System.out.println("Stating TcpServer on port: " + port + " at " + new Date(System.currentTimeMillis()));
            socketListener = new ServerSocket(port);
            initializeExecutorService();
            connDispatcher = new ConnectionDispatcher(this);
            connDispatcher.start();
        } catch (IOException ex) {
            Logger.getLogger(TcpServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }

    public void stop() {
        System.out.println("Stopping TcpServer...");
        connDispatcher.stop();
        try {
            socketListener.close();
        } catch (IOException ex) {
            Logger.getLogger(TcpServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        executorService.shutdown();
    }

    protected TcpConnection waitForIncomingConnection() throws Exception {
        Socket s = null;
        synchronized (socketListener) {
            try {
                s = socketListener.accept();

            } catch (SocketException socketEx) {
                if (!socketEx.getMessage().toLowerCase().contains("socket closed")) {
                    throw new Exception("Failure accepting incoming connection.", socketEx);
                }
            }
        }
        if (s == null) {
            throw new Exception("Socket recieved from listener is null.");
        }
        return new TcpConnection(s);
    }

    public TcpServer setAction(ConnectionAction<TcpConnection> connectionAction) {
        this.connectionAction = connectionAction;
        return this;
    }

    public TcpServer setPort(final int port) {
        this.port = port;
        return this;
    }

    private void initializeExecutorService() {
        ThreadPoolExecutor exec = new ThreadPoolExecutor(
                2, 2,
                2, TimeUnit.MINUTES,
                new ArrayBlockingQueue<Runnable>(100));
        this.executorService = exec;
    }

    private ConnectionTask buildNewConnectionTask(TcpConnection connection) {
        return new ConnectionTask(this.connectionAction, connection);
    }

    private void submitTaskToRunQueue(ConnectionTask connTask) {
        this.executorService.submit(connTask);
    }

    private class ConnectionTask implements Runnable {

        private ConnectionAction<TcpConnection> action;
        private TcpConnection connection;

        public ConnectionTask(
                final ConnectionAction action,
                final TcpConnection connection) {
            this.action = action;
            this.connection = connection;
        }

        private ConnectionTask() {
        }

        @Override
        public void run() {

            System.out.println("New Task starting to wait for connection...");
            try {
                action.action(connection);
            } catch (Exception ex) {
                Logger.getLogger(TcpServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }// peni spenis peni s

    }

    @FunctionalInterface
    public interface ConnectionAction<T extends Connection> {

        public abstract void action(T conn);

    }

    /**
     * Purpose is to continuously wait for incoming requests and then passes
     * them off to be processed.
     *
     */
    private class ConnectionDispatcher implements Runnable {

        private final TcpServer tcpServer;
        private Thread MY_THREAD;
        private AtomicBoolean continueProcessing;

        public ConnectionDispatcher(TcpServer server) {
            this.tcpServer = server;
            this.continueProcessing = new AtomicBoolean(false);
        }

        public void start() {
            MY_THREAD = new Thread(this);
            MY_THREAD.start();
        }

        public void stop() {
            if (this.MY_THREAD != null) {
                try {
                    this.continueProcessing.set(false);
                    this.MY_THREAD.join(1);
                } catch (InterruptedException ex) {
                }
            }
        }

        @Override
        public void run() {
            continueProcessing.set(true);
            while (continueProcessing.get()) {
                try {
                    dispatchIncomingConnection();
                } catch (Exception ex) {
                    Logger.getLogger(TcpServer.class.getName()).log(
                            Level.SEVERE, "Failed to dispatch a request.", ex);
                }
            }

        }

        private void dispatchIncomingConnection() throws Exception {

            TcpConnection newConnection = tcpServer.waitForIncomingConnection();

            ConnectionTask newConnTask = tcpServer.buildNewConnectionTask(newConnection);

            tcpServer.submitTaskToRunQueue(newConnTask);
        }

    }
}
