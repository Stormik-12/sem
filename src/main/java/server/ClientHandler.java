package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    private final Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String clientAddress = clientSocket.getRemoteSocketAddress().toString();
            logger.info("Handling client: {}", clientAddress);
            out.println("Welcome to chat server! Your address: " + clientAddress);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                logger.info("Received from {}: {}", clientAddress, inputLine);
                out.println("Server echo: " + inputLine);

                if ("/exit".equalsIgnoreCase(inputLine)) {
                    break;
                }
            }
        } catch (IOException e) {
            logger.error("Error handling client", e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                logger.error("Error closing client socket", e);
            }
            logger.info("Client disconnected");
        }
    }
}