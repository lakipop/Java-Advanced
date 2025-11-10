import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * ChatClient - Simple Chat Client using Sockets
 * 
 * This program demonstrates:
 * - Socket connection to server
 * - DataInputStream and DataOutputStream
 * - Bidirectional communication
 * - Client-server protocol
 * - Error handling for network operations
 * 
 * Original: MyChatClient practical
 * 
 * @author BICT Advanced Java Course
 * @version 1.0
 */
public class ChatClient {
    
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 55555;
    
    private Socket socket;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    private Scanner scanner;
    
    /**
     * Connect to server and start chat
     */
    public void connectToServer() {
        System.out.println("╔═══════════════════════════════════╗");
        System.out.println("║     Chat Client - BICT Course     ║");
        System.out.println("╚═══════════════════════════════════╝\n");
        
        try {
            // Connect to server
            System.out.println("⏳ Connecting to server at " + SERVER_HOST + ":" + SERVER_PORT);
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            System.out.println("✓ Connected to server!\n");
            
            // Setup streams
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
            scanner = new Scanner(System.in);
            
            // Send initial greeting
            String greeting = "Client: Hello Server! Ready to chat.";
            outputStream.writeUTF(greeting);
            outputStream.flush();
            System.out.println("📤 " + greeting);
            
            // Receive server response
            String serverGreeting = inputStream.readUTF();
            System.out.println("📩 " + serverGreeting + "\n");
            
            // Start chat loop
            chatLoop();
            
        } catch (UnknownHostException e) {
            System.err.println("Error: Unknown host - " + SERVER_HOST);
        } catch (ConnectException e) {
            System.err.println("Error: Cannot connect to server.");
            System.err.println("Make sure ChatServer is running first!");
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
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
                // Send message to server
                System.out.print("You: ");
                String clientMsg = scanner.nextLine();
                outputStream.writeUTF("Client: " + clientMsg);
                outputStream.flush();
                
                // Check if client wants to disconnect
                if (clientMsg.equalsIgnoreCase("bye")) {
                    System.out.println("\n✓ Disconnecting from server.");
                    break;
                }
                
                // Receive message from server
                String serverMsg = inputStream.readUTF();
                System.out.println("📩 " + serverMsg);
                
                // Check if server disconnected
                if (serverMsg.toLowerCase().contains("bye")) {
                    System.out.println("\n✓ Server disconnected.");
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
            if (socket != null) socket.close();
            if (scanner != null) scanner.close();
            System.out.println("\n✓ Connection closed.");
        } catch (IOException e) {
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }
    
    /**
     * Main method
     */
    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        
        System.out.println("Starting Chat Client...");
        System.out.println("Instructions:");
        System.out.println("  1. Make sure ChatServer is running");
        System.out.println("  2. Type your messages and press Enter");
        System.out.println("  3. Type 'bye' to disconnect\n");
        
        client.connectToServer();
        
        System.out.println("\nChat Client stopped.");
    }
}
