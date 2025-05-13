package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;

public class ChatServer {
    private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);
    private ServerSocket serverSocket;
    private Set<ClientHandler> clients = new HashSet<>();

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            logger.info("Server started on port {}", port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket, this);
                clients.add(handler);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            logger.error("Server error", e);
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
        logger.info("Client disconnected. Total clients: {}", clients.size());
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.start(8080);
    }
}