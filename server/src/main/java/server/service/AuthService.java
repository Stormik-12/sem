package server.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import server.model.User;
import server.repository.UserRepository;
import java.util.Optional;

public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public boolean register(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            return false;
        }

        String hashedPassword = passwordEncoder.encode(password);
        User newUser = new User(username, hashedPassword);
        return userRepository.save(newUser);
    }

    public boolean authenticate(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent() &&
                passwordEncoder.matches(password, user.get().getPasswordHash());
    }
}
