public class ChatClient extends Application {
    private PrintWriter out;

    @Override
    public void start(Stage stage) {
        TextField messageField = new TextField();
        Button sendButton = new Button("Send");
        TextArea chatArea = new TextArea();

        sendButton.setOnAction(e -> {
            out.println(messageField.getText());
            messageField.clear();
        });

        // Подключение к серверу
        try {
            Socket socket = new Socket("localhost", 8081);
            out = new PrintWriter(socket.getOutputStream(), true);

            new Thread(() -> {
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()))) {
                    String response;
                    while ((response = in.readLine()) != null) {
                        Platform.runLater(() ->
                                chatArea.appendText(response + "\n"));
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(new VBox(10, chatArea, messageField, sendButton), 400, 300);
        stage.setScene(scene);
        stage.show();
    }
}