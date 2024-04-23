import java.io.*;
import java.net.*;

public class Client {
    // Initialize socket and input output streams
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;
    private DataInputStream in = null;

    // Constructor to put ip address and port
    public Client(String address, int port) {
        // Establish a connection
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // Takes input from terminal
            input = new DataInputStream(System.in);

            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            // Sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException u) {
            System.out.println(u);
            return;
        } catch (IOException i) {
            System.out.println(i);
            return;
        }

        // String to read message from input
        String line = "";
        String response = "";

        // Get instructions from server
        try {
            line = in.readUTF();
            System.out.println("Server: " + line);
        } catch (IOException i) {
            System.out.println(i);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!line.equals("Over")) {
            try {
                line = reader.readLine();
                out.writeUTF(line);

                // Read and display the message from server
                response = in.readUTF();
                System.out.println("Server: " + response);

            } catch (IOException i) {
                System.out.println(i);
            }
        }

        // Close the connection
        try {
            input.close();
            out.close();
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        Client client = new Client("10.130.31.73", 4444);
    }
}
