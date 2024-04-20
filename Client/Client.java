// A Java program for a Client
import java.io.*;
import java.net.*;

public class Client {
	// initialize socket and input output streams
	private Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream out = null;
	private DataInputStream in = null;

	// constructor to put ip address and port
	public Client(String address, int port)
	{
		// establish a connection
		try {
			socket = new Socket(address, port);
			System.out.println("Connected");

			// takes input from terminal
			input = new DataInputStream(System.in);

			in = new DataInputStream(
				new BufferedInputStream(socket.getInputStream()));

			// sends output to the socket
			out = new DataOutputStream(
				socket.getOutputStream());
		}
		catch (UnknownHostException u) {
			System.out.println(u);
			return;
		}
		catch (IOException i) {
			System.out.println(i);
			return;
		}

		// string to read message from input
		String line = "";
		String response = "";

		// get instructions from server
		try {
			line = in.readUTF();
			System.out.println("Server: " + line);
		}
		catch (IOException i) {
			System.out.println(i);
		}

		// keep reading until "Over" is input
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (!line.equals("Over")) {
			try {
				line = reader.readLine();
				out.writeUTF(line);

				// read and display the message from server
				response = in.readUTF();
				System.out.println("Server: " + response);

			} catch (IOException i) {
				System.out.println(i);
			}
		}

		// close the connection
		try {
			input.close();
			out.close();
			socket.close();
		}
		catch (IOException i) {
			System.out.println(i);
		}
	}

	public static void main(String args[])
	{
		Client client = new Client("10.20.0.49", 4444);
	}
}
