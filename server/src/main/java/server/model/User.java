package server.model;

public class User {
    private int id;
    private String username;
    private String passwordHash;

    // Конструкторы, геттеры и сеттеры
    public User() {}

    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    // ... геттеры и сеттеры ...
}
