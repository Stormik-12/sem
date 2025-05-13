public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    private ChatClient client;

    public void setClient(ChatClient client) {
        this.client = client;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            client.login(username, password);
            // Переход к чату
        } catch (Exception e) {
            statusLabel.setText("Login failed: " + e.getMessage());
        }
    }

    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            client.register(username, password);
            statusLabel.setText("Registration successful!");
        } catch (Exception e) {
            statusLabel.setText("Registration failed: " + e.getMessage());
        }
    }
}