package server.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver; // null для общих сообщений

    private String content;
    private LocalDateTime timestamp;

    // Геттеры и сеттеры
}
