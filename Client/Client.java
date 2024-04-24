import java.io.*;
import java.net.*;

public class Client {

    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;
    private DataInputStream in = null;

    //for ip address and port
    public Client(String address, int port) {
        //for making the connection
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            //get user inputs
            input = new DataInputStream(System.in);

            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            //to sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException u) {
            System.out.println(u);
            return;
        } catch (IOException i) {
            System.out.println(i);
            return;
        }

        //get info of input of user
        String line = "";
        String response = "";

        //to get instructions from server
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

                
                response = in.readUTF();
                System.out.println("Server: " + response);

            } catch (IOException i) {
                System.out.println(i);
            }
        }

        //to close the connection
        try {
            input.close();
            out.close();
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        if (args.length < 2) {
            System.out.println("Please provide the IP address and port number");
            return;
        }

        String ipAddress = args[0];
        int port = Integer.parseInt(args[1]);
        
        Client client = new Client(ipAddress, port);
    }
}
