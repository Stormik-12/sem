package server;

import server.repository.UserRepository;
import server.service.AuthService;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private final AuthService authService;

    public ChatServer() {
        this.authService = new AuthService(
                new UserRepository(
                        "jdbc:postgresql://localhost:5432/chatdb",
                        "postgres",
                        "postgres"
                )
        );
    }

    public void start(int port) {
        ExecutorService threadPool = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                threadPool.execute(
                        new ClientHandler(clientSocket, authService)
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ChatServer().start(8081);
    }
}