# Project Assignment 3

### Student Information
1. Kent Phipps    UFID: 402092184
2. Waleed Aref    UFID: 96185607
1. Simar          UFID: 62217796

# TCPTypingTest

The TCPTypingTest is a client-server based application that allows users to test their typing speed and accuracy over a TCP connection. The server sends typing prompts to the client, times the responses, and logs the results. The client can end the session or request previous session logs.

## Features

- **Typing Test**: Receive randomly chosen sentences to type which helps in testing typing speed and accuracy.
- **Logging**: Each typing response time is logged for future retrieval.
- **Session Control**: Clients can request to end the session gracefully or forcefully.

## How to Run the Program

### Server Setup

1. **Compile the Server**:
    - Navigate to the directory containing `Server.java`.
    - Compile the server using the Java compiler:
      ```bash
      javac Server.java
      ```

2. **Run the Server**:
    - Start the server with a specific port:
      ```bash
      java Server <port>
      ```
    - Example:
      ```bash
      java Server 4444
      ```

### Client Setup

1. **Compile the Client**:
    - Navigate to the directory containing `Client.java`.
    - Compile the client using the Java compiler:
      ```bash
      javac Client.java
      ```

2. **Run the Client**:
    - Connect to the server by specifying the server's IP address and port:
      ```bash
      java Client <Server-ip> <port>
      ```
    - Example:
      ```bash
      java Client 192.168.1.5 4444
      ```

### Usage

- **Start Typing Test**:
  - From the client terminal, type `Ready` to begin the typing test. The server will send a sentence to type.
- **View Logs**:
  - Type `Log` to request viewing all past responses' logs.
- **End Session Gracefully**:
  - Type `Over` to end the connection gracefully.
- **Force End Game**:
  - Type `End Game` at any point to immediately end the typing test and close the connection.

