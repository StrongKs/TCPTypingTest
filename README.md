# Project Assignment 3

### Student Information
1. Kent Phipps    UFID: 402092184
2. Waleed
1. Samir

### How to compile and run
*Assuming you have Client and Server file in different machines*
1. Compile and run server.java

    • Open a terminal at Server folder and execute: 

        javac Server.java
        java Server <port>

2. Comple and run client.java

    • Open a terminal at Client folder and execute:

        javac Client.java
        java Client <Server-ip> <port>

3. From client, type 'Ready' to begin the typing test. Once completed correctly, response time is logged.

4. Client may then request to see all responses in log with 'Log' request.

5. Client may gracefully end the connection with 'Over' request.  