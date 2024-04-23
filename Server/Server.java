import java.net.*;
import java.io.*;
import java.util.Random;

public class Server {
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private long startTime = 0;
    private final String[] sentences = {
        "The quick brown fox jumps over the lazy dog",
        "A quick movement of the enemy will jeopardize six gunboats",
        "The five boxing wizards jump quickly"
    };
    private Random random = new Random();

    public Server(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");

            socket = server.accept();
            System.out.println("Client accepted");

            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF("Type 'Ready' to start Typing test. Type 'Over' to end the connection. Type 'End Game' to quit anytime.");
            out.flush();

            String line = "";
            String currentTestSentence = "";

            while (!line.equalsIgnoreCase("Over")) {
                line = in.readUTF();
                System.out.println("Client: " + line);

                if (line.equalsIgnoreCase("End Game")) {
                    out.writeUTF("Game ended by user request.");
                    out.flush();
                    break;
                } else if (line.equalsIgnoreCase("Ready")) {
                    currentTestSentence = sentences[random.nextInt(sentences.length)];
                    out.writeUTF("Type the following text: '" + currentTestSentence + "'");
                    startTime = System.currentTimeMillis();
                } else if (line.equalsIgnoreCase(currentTestSentence)) {
                    long endTime = System.currentTimeMillis();
                    long timeElapsed = (endTime - startTime) / 1000;
                    out.writeUTF("Good Job! You did it in " + timeElapsed + " seconds!\nType 'Ready' to start again, 'Log' to see all responses, or 'Over' to end the connection");
                } else {
                    out.writeUTF("Incorrect input. Please type the following text exactly as shown: '" + currentTestSentence + "'\nType 'Ready' to start again or 'Over' to end the connection");
                }
                out.flush();
            }

            System.out.println("Closing connection");
            socket.close();
            in.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        Server server = new Server(4444);
    }
}
