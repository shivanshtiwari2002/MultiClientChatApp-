import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MultiClientChatClient implements ActionListener {
    JFrame frame;
    JTextArea chatArea;
    JTextField messageField;
    JButton sendButton, exitButton, sendFileButton;
    DataInputStream dis;
    DataOutputStream dos;
    String clientName;

    public MultiClientChatClient(String clientName) {
        this.clientName = clientName;

        frame = new JFrame("Chat Client - " + clientName);
        frame.getContentPane().setBackground(new Color(220, 240, 255)); 

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);

        messageField = new JTextField();
        sendButton = new JButton("📤 Send");
        exitButton = new JButton("❌ Exit");
        sendFileButton = new JButton("📂 Send File");

        sendButton.setBackground(new Color(0, 150, 136));
        sendButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(255, 87, 34));
        exitButton.setForeground(Color.WHITE);
        sendFileButton.setBackground(new Color(33, 150, 243));
        sendFileButton.setForeground(Color.WHITE);

        sendButton.addActionListener(this);
        exitButton.addActionListener(this);
        sendFileButton.addActionListener(this);
        messageField.addActionListener(this);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(messageField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(sendFileButton);
        bottomPanel.add(exitButton);

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);
        frame.add(bottomPanel, BorderLayout.NORTH);

        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        try {
            Socket socket = new Socket("localhost", 5000);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF(clientName);

            new Thread(() -> {
                try {
                    String message;
                    while (true) {
                        message = dis.readUTF();
                        chatArea.append(message + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == sendButton || e.getSource() == messageField) {
                String message = messageField.getText();
                dos.writeUTF(message);
                dos.flush();
                chatArea.append("You: " + message + "\n");
                messageField.setText("");
            } else if (e.getSource() == exitButton) {
                dos.writeUTF("exit");
                dos.flush();
                System.exit(0);
            } else if (e.getSource() == sendFileButton) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png"));
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    sendFile(selectedFile);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void sendFile(File file) throws IOException {
        dos.writeUTF("File: " + file.getName()); 
        dos.flush();

      
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            dos.write(buffer, 0, bytesRead);
        }
        dos.flush();
        fileInputStream.close();
        chatArea.append("You sent a file: " + file.getName() + "\n");
    }

    public static void main(String[] args) {
        String clientName = JOptionPane.showInputDialog("Enter your name:");
        new MultiClientChatClient(clientName);
    }
}
