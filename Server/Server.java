// A Java program for a Server
import java.net.*;
import java.io.*;

public class Server {
	//initialize socket and input stream
	private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	private long startTime = 0;

	// constructor with port
	public Server(int port) {
		// starts server and waits for a connection
		try {
			server = new ServerSocket(port);
			System.out.println("Server started");

			System.out.println("Waiting for a client ...");

			socket = server.accept();
			System.out.println("Client accepted");

			// takes input from the client socket
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

			// sends output to the socket
			out = new DataOutputStream(socket.getOutputStream());

			String line = "";

			// send instructions to client
			try {
				out.writeUTF("Type 'Ready' to start Typing test. Type 'Over' to end the connection");
				out.flush();
			} catch (IOException e) {
				System.out.println(e);
			}

			// reads message from client until "Over" is sent
			while (!line.equals("Over")) {
				try {
					line = in.readUTF();
					System.out.println("Client: " + line);

					if (line.equals("Ready")) {
						line = "Type the following text: 'The quick brown fox jumps over the lazy dog'";
			
						startTime = System.currentTimeMillis(); // Set the start time
					} else if (line.equals("The quick brown fox jumps over the lazy dog")) {
						long endTime = System.currentTimeMillis(); // Set the end time
						long timeElapsed = (endTime - startTime) / 1000; // Calculate the time elapsed in seconds
					
						line = "Good Job! You did it in " + timeElapsed + " seconds!\nType 'Ready' to start again or 'Over' to end the connection";
						
						// Append the response time to log.txt
						try {
							File logFile = new File("log.txt");
							if (!logFile.exists()) {
								logFile.createNewFile();
							}
							FileReader fr = new FileReader(logFile);
							BufferedReader br = new BufferedReader(fr);
							String lastLine = "";
							String currentLine;
							while ((currentLine = br.readLine()) != null) {
								lastLine = currentLine;
							}
							br.close();

							int logNumber;
							if (lastLine.isEmpty()) {
								logNumber = 1;
							} else {
								logNumber = Integer.parseInt(lastLine.substring(4, lastLine.indexOf(":")));
								logNumber++;
							}

							FileWriter fw = new FileWriter("log.txt", true);
							BufferedWriter bw = new BufferedWriter(fw);
							PrintWriter pw = new PrintWriter(bw);
							pw.println("Log " + logNumber + ": Response time: " + timeElapsed + " seconds");
							pw.close();
						} catch (IOException e) {
							System.out.println(e);
						}

						
					}

					// write to the client
					out.writeUTF("thank you for the message: " + line);
					out.flush();

				} catch (IOException i) {
					System.out.println(i);
				}
			}
			System.out.println("Closing connection");

			// close connection
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
