package org.example;

import java.io.*;
import java.net.*;



// Xử lý từng client trong một luồng riêng biệt
class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream input = socket.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(input));
             OutputStream output = socket.getOutputStream();
             PrintWriter writer = new PrintWriter(output, true)) {

            String text;
            while ((text = reader.readLine()) != null) {
                System.out.println("Received: " + text);

                // Phản hồi lại client
                writer.println("Server: " + text);

                // Thoát nếu nhận được "bye" từ client
                if ("bye".equalsIgnoreCase(text)) {
                    System.out.println("Client disconnected");
                    break;
                }
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
