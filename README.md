Multi-Client Chat Application (Java)

This is a simple chat application made with Java where multiple users can join and chat together. It uses Java Sockets for communication and Java Swing for the graphical interface.

Features:

Multiple users can connect and chat in real-time

Simple and clean Swing-based chat window

Messages are broadcast to all connected users

Users can exit the chat safely

Users can send basic image files (.jpg, .jpeg, .png)

How it works:

Run the MultiClientChatServer file first. This starts the server on port 5000.

Each user runs the MultiClientChatClient file and enters their name to join the chat.

Any message sent by one user is sent to everyone else.

Image files can also be sent, and they appear as file notifications.

Technologies used:

Java Sockets (Networking)

Java Swing (GUI)

Basic File Transfer using socket streams
