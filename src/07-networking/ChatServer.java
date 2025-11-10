import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * ChatServer - Simple Chat Server using Sockets
 * 
 * This program demonstrates:
 * - ServerSocket for accepting connections
 * - Socket communication
 * - DataInputStream and DataOutputStream
 * - Bidirectional communication
 * - Simple chat protocol
 * 
 * Original: MyChatServer practical
 * 
 * @author BICT Advanced Java Course
 * @version 1.0
 */
public class ChatServer {
    
    private static final int PORT = 55555;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    private Scanner scanner;
    
    /**
     * Initialize server resources
     */
    public void startServer() {
        System.out.println("╔═══════════════════════════════════╗");
        System.out.println("║     Chat Server - BICT Course     ║");
        System.out.println("╚═══════════════════════════════════╝\n");
        
        try {
            // Create server socket
            serverSocket = new ServerSocket(PORT);
            System.out.println("✓ Server started on port " + PORT);
            System.out.println("⏳ Waiting for client connection...\n");
            
            // Accept client connection
            clientSocket = serverSocket.accept();
            System.out.println("✓ Client connected from: " + 
                             clientSocket.getInetAddress().getHostAddress());
            System.out.println();
            
            // Setup streams
            outputStream = new DataOutputStream(clientSocket.getOutputStream());
            inputStream = new DataInputStream(clientSocket.getInputStream());
            scanner = new Scanner(System.in);
            
            // Initial greeting
            String welcomeMsg = inputStream.readUTF();
            System.out.println("📩 " + welcomeMsg);
            
            String greeting = "Server: Hello Client! Welcome to the chat server.";
            outputStream.writeUTF(greeting);
            outputStream.flush();
            System.out.println("📤 " + greeting + "\n");
            
            // Start chat loop
            chatLoop();
            
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            cleanup();
        }
    }
    
    /**
     * Main chat loop for exchanging messages
     */
    private void chatLoop() {
        System.out.println("=".repeat(50));
        System.out.println("Chat started! Type 'bye' to exit.");
        System.out.println("=".repeat(50) + "\n");
        
        while (true) {
            try {
                // Receive message from client
                String clientMsg = inputStream.readUTF();
                System.out.println("📩 " + clientMsg);
                
                // Check if client wants to disconnect
                if (clientMsg.toLowerCase().contains("bye")) {
                    System.out.println("\n✓ Client disconnected.");
                    break;
                }
                
                // Send message to client
                System.out.print("You: ");
                String serverMsg = scanner.nextLine();
                outputStream.writeUTF("Server: " + serverMsg);
                outputStream.flush();
                
                // Check if server wants to disconnect
                if (serverMsg.equalsIgnoreCase("bye")) {
                    System.out.println("\n✓ Server shutting down.");
                    break;
                }
                
            } catch (IOException e) {
                System.err.println("Communication error: " + e.getMessage());
                break;
            }
        }
    }
    
    /**
     * Clean up resources
     */
    private void cleanup() {
        try {
            if (inputStream != null) inputStream.close();
            if (outputStream != null) outputStream.close();
            if (clientSocket != null) clientSocket.close();
            if (serverSocket != null) serverSocket.close();
            if (scanner != null) scanner.close();
            System.out.println("\n✓ All resources cleaned up.");
        } catch (IOException e) {
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }
    
    /**
     * Main method
     */
    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        
        System.out.println("Starting Chat Server...");
        System.out.println("Instructions:");
        System.out.println("  1. Run this server first");
        System.out.println("  2. Then run ChatClient in another terminal");
        System.out.println("  3. Type messages and press Enter");
        System.out.println("  4. Type 'bye' to disconnect\n");
        
        server.startServer();
        
        System.out.println("\nChat Server stopped.");
    }
}
