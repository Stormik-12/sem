package server.service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jakarta.persistence.*;

public class DatabaseService {
    private final EntityManagerFactory emf;

    public DatabaseService() {
        emf = Persistence.createEntityManagerFactory("chat-persistence-unit");
    }

    // Методы для работы с пользователями и сообщениями
}

