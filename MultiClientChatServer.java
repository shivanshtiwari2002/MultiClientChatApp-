import java.io.*;
import java.net.*;
import java.util.*;

public class MultiClientChatServer {
    private static final int PORT = 5000;
    private static Set<ClientHandler> clientHandlers = new HashSet<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clientHandlers) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public static void removeClient(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
    }
}

class ClientHandler implements Runnable {
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String clientName = dis.readUTF();
            System.out.println(clientName + " has joined the chat.");
            MultiClientChatServer.broadcast(clientName + " has joined the chat.", this);

            String message;
            while (true) {
                message = dis.readUTF();
                if (message.equalsIgnoreCase("exit")) {
                    System.out.println(clientName + " has left the chat.");
                    MultiClientChatServer.broadcast(clientName + " has left the chat.", this);
                    break;
                }
                System.out.println(clientName + ": " + message);
                MultiClientChatServer.broadcast(clientName + ": " + message, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                MultiClientChatServer.removeClient(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        try {
            dos.writeUTF(message);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
