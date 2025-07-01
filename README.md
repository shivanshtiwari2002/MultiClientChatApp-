Multi-Client Chat Application (Java)
This is a simple multi-client chat application built using Java Sockets and Swing GUI.

💬 Features:
✅ Multiple users can connect to the server and chat in real-time
✅ Clean, modern GUI using Java Swing
✅ Broadcasts messages to all connected clients
✅ Exit option to leave the chat gracefully
✅ Option to send image files (.jpg, .jpeg, .png) to other users (basic file transfer)

⚙️ How it Works:
First, run the MultiClientChatServer file to start the server on port 5000

Multiple users can run the MultiClientChatClient file and join the chat by entering their name

Messages sent by any user are broadcasted to all other users

Users can also send image files which will be displayed as file notifications

🛠️ Technologies Used:
Java Sockets (Networking)

Java Swing (GUI)

Basic File Transfer over Socket Streams

🚀 How to Run:
Compile the server file:

bash
Copy
Edit
javac MultiClientChatServer.java
java MultiClientChatServer
Compile and run the client for each user:

bash
Copy
Edit
javac MultiClientChatClient.java
java MultiClientChatClient
📂 Notes:
The server runs on localhost and port 5000 by default

You can modify the IP/Port in the client code to run over different machines on the same network

Currently, file sending is basic and works for image files only

🎯 Purpose:
This project is for educational/demo purposes to understand:
✔ Java Socket Programming
✔ Multi-threading with multiple clients
✔ Building GUI-based chat apps in Java
