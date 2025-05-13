package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);
    private ServerSocket serverSocket;
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private boolean isRunning = true;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            logger.info("Server started on port {}", port);

            Runtime.getRuntime().addShutdownHook(new Thread(this::stop));

            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                logger.info("New connection from {}", clientSocket.getRemoteSocketAddress());
                threadPool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            if (isRunning) {
                logger.error("Server error", e);
            }
        }
    }

    public void stop() {
        isRunning = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            logger.error("Error closing server socket", e);
        }
        threadPool.shutdown();
        logger.info("Server stopped");
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.start(8081); // Можно вынести порт в конфиг
    }
}