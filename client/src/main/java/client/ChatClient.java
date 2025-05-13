public class ChatClient {
    private static final Logger logger = LoggerFactory.getLogger(ChatClient.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        logger.info("Connected to server");
    }

    public void login(String username, String password) throws IOException {
        JsonNode request = mapper.createObjectNode()
                .put("type", "login")
                .put("username", username)
                .put("password", password);

        out.println(request.toString());
        String response = in.readLine();
        JsonNode jsonResponse = mapper.readTree(response);

        if (!jsonResponse.get("success").asBoolean()) {
            throw new IOException(jsonResponse.get("message").asText());
        }
    }

    // Аналогичные методы для регистрации и отправки сообщений
}