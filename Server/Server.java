import java.net.*;
import java.io.*;
import java.util.Random;

public class Server {
    
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    //test for timing
    private long startTime = 0;  
    //List of sentences to be used
    private final String[] sentences = {
        "The quick brown fox jumps over the lazy dog",
        "A quick movement of the enemy will jeopardize six gunboats",
        "The five boxing wizards jump quickly"
    };
    //for random sentence selection
    private Random random = new Random();  

    public Server(int port) {
        try {
            
            //to clear log file at every start of server run
            PrintWriter writer = new PrintWriter("log.txt");
            writer.print("");  
            writer.close();

            server = new ServerSocket(port);  //start server on the w/ port
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");

            socket = server.accept();  //connection from a client
            System.out.println("Client accepted");

            //stream to receive data from client
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            //stream to send data to client
            out = new DataOutputStream(socket.getOutputStream());

            //first message for game
            out.writeUTF("Type 'Ready' to start Typing test. Type 'Over' to end the connection. Type 'End Game' to quit anytime.");
            out.flush();

            String line = "";
            String currentTestSentence = "";

            while (!line.equalsIgnoreCase("Over")) {
                line = in.readUTF();
                System.out.println("Client: " + line);

                //to break the loop and close connection if end game inputed
                if (line.equalsIgnoreCase("End Game")) {
                    out.writeUTF("Game ended by user request.");
                    out.flush();
                    break;  
                } else if (line.equalsIgnoreCase("Ready")) {
                    //to select random sentence and prompt the client
                    currentTestSentence = sentences[random.nextInt(sentences.length)];
                    out.writeUTF("Type the following text: '" + currentTestSentence + "'");
                    startTime = System.currentTimeMillis();  //start timing
                } else if (line.equalsIgnoreCase(currentTestSentence)) {
                    long endTime = System.currentTimeMillis();
                    long timeElapsed = (endTime - startTime) / 1000;
                    //Send data taken to client
                    out.writeUTF("Good Job! You did it in " + timeElapsed + " seconds!\nType 'Ready' to start again, 'Log' to see all responses, or 'Over' to end the connection");

                    //log the response time to log.txt
                    try {
                        File logFile = new File("log.txt"); 
                        FileWriter fw = new FileWriter(logFile, true); 
                        BufferedWriter bw = new BufferedWriter(fw);
                        PrintWriter pw = new PrintWriter(bw);
                        pw.println("Log " + (new BufferedReader(new FileReader(logFile)).lines().count() + 1) + ": Response time: " + timeElapsed + " seconds");
                        pw.close();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                } else if (line.equalsIgnoreCase("Log")) {
                    try {
                        File logFile = new File("log.txt");
                        if (logFile.exists()) {
                            FileReader fr = new FileReader(logFile);
                            BufferedReader br = new BufferedReader(fr);
                            StringBuilder logContent = new StringBuilder();
                            String currentLine;
                            while ((currentLine = br.readLine()) != null) {
                                logContent.append(currentLine).append("\n");
                            }
                            br.close();
                            out.writeUTF("\n" + logContent.toString());
                        } else {
                            out.writeUTF("Log file does not exist");
                        }
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                    out.writeUTF("Type 'Ready' to start Typing test, 'Log' to see all responses, or 'Over' to end the connection");
                } else {
                    //error message from wrong input
                    out.writeUTF("Incorrect input. Please type the following text exactly as shown: '" + currentTestSentence + "'");
                }
                out.flush(); 
            }

            //close connections
            System.out.println("Closing connection");
            socket.close();
            in.close();
            out.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        //check port
        if (args.length < 1) {
            System.out.println("Please provide the port number as an argument.");
            return;
        }
        
        int port = Integer.parseInt(args[0]);  
        Server server = new Server(port);  
    }
}
