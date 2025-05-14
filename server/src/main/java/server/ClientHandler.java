package server;

import server.service.AuthService;
import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final AuthService authService;
    private PrintWriter out;
    private String username;

    public ClientHandler(Socket socket, AuthService authService) {
        this.clientSocket = socket;
        this.authService = authService;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()))) {

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("Welcome! Commands: /register, /login, /exit");

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.startsWith("/register ")) {
                    handleRegister(inputLine);
                } else if (inputLine.startsWith("/login ")) {
                    handleLogin(inputLine);
                } else if ("/exit".equalsIgnoreCase(inputLine)) {
                    break;
                } else if (username != null) {
                    handleMessage(inputLine);
                } else {
                    out.println("ERROR: Please authenticate first");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleRegister(String input) {
        String[] parts = input.split(" ", 3);
        if (parts.length != 3) {
            out.println("ERROR: Usage: /register username password");
            return;
        }

        if (authService.register(parts[1], parts[2])) {
            out.println("REGISTER_SUCCESS");
        } else {
            out.println("REGISTER_FAILED: Username exists");
        }
    }

    private void handleLogin(String input) {
        String[] parts = input.split(" ", 3);
        if (parts.length != 3) {
            out.println("ERROR: Usage: /login username password");
            return;
        }

        if (authService.authenticate(parts[1], parts[2])) {
            username = parts[1];
            out.println("LOGIN_SUCCESS Welcome, " + username);
        } else {
            out.println("LOGIN_FAILED: Invalid credentials");
        }
    }

    private void handleMessage(String message) {
        // Здесь будет логика обработки сообщений
        out.println(username + ": " + message);
    }
}