import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * HostLookupDemo - DNS Resolution and IP Address Lookup
 * 
 * This program demonstrates:
 * - InetAddress class usage
 * - DNS hostname resolution
 * - IP address retrieval
 * - Handling multiple IP addresses (load balancing)
 * - Exception handling for network operations
 * 
 * Original: MyHostLookup practical
 * 
 * @author BICT Advanced Java Course
 * @version 1.0
 */
public class HostLookupDemo {
    
    /**
     * Look up and display all IP addresses for a hostname
     * 
     * @param hostName The hostname or domain to look up
     * @return true if lookup successful, false otherwise
     */
    public static boolean lookupHost(String hostName) {
        try {
            System.out.println("\n🔍 Looking up: " + hostName);
            System.out.println("=".repeat(50));
            
            // Get all IP addresses for the hostname
            InetAddress[] addresses = InetAddress.getAllByName(hostName);
            
            if (addresses.length == 0) {
                System.out.println("⚠️  No IP addresses found");
                return false;
            }
            
            // Display canonical hostname
            System.out.println("📍 Hostname: " + addresses[0].getCanonicalHostName());
            System.out.println("📊 IP Addresses found: " + addresses.length);
            System.out.println();
            
            // Display all IP addresses
            for (int i = 0; i < addresses.length; i++) {
                InetAddress addr = addresses[i];
                System.out.printf("   [%d] IP: %-15s | Host: %s%n", 
                    i + 1, 
                    addr.getHostAddress(),
                    addr.getHostName());
            }
            
            System.out.println("=".repeat(50));
            return true;
            
        } catch (UnknownHostException e) {
            System.out.println("❌ Host not found: " + hostName);
            System.out.println("   Error: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get local machine information
     */
    public static void displayLocalHostInfo() {
        try {
            System.out.println("\n💻 Local Machine Information");
            System.out.println("=".repeat(50));
            
            InetAddress localHost = InetAddress.getLocalHost();
            
            System.out.println("🖥️  Hostname: " + localHost.getHostName());
            System.out.println("🌐 IP Address: " + localHost.getHostAddress());
            System.out.println("📍 Canonical Name: " + localHost.getCanonicalHostName());
            
            // Check if loopback
            if (localHost.isLoopbackAddress()) {
                System.out.println("🔄 Loopback: Yes (127.0.0.1)");
            }
            
            System.out.println("=".repeat(50));
            
        } catch (UnknownHostException e) {
            System.err.println("❌ Could not determine localhost: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrate common host lookups
     */
    public static void demonstrateCommonHosts() {
        System.out.println("\n📚 Common Host Lookup Examples");
        
        String[] commonHosts = {
            "www.google.com",
            "www.github.com",
            "localhost"
        };
        
        for (String host : commonHosts) {
            lookupHost(host);
            
            try {
                Thread.sleep(500); // Small delay between lookups
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Interactive mode - user enters hostnames
     */
    public static void interactiveMode() {
        Scanner scanner = new Scanner(System.in);
        String hostName;
        
        System.out.println("\n🔧 Interactive Host Lookup Mode");
        System.out.println("=".repeat(50));
        System.out.println("Enter hostnames to look up (type 'exit' to quit)");
        System.out.println("Examples: www.google.com, github.com, localhost");
        System.out.println("=".repeat(50));
        
        try {
            do {
                System.out.print("\n🌐 Enter hostname: ");
                hostName = scanner.nextLine().trim();
                
                if (!hostName.isEmpty() && !hostName.equalsIgnoreCase("exit")) {
                    lookupHost(hostName);
                }
                
            } while (!hostName.equalsIgnoreCase("exit"));
            
            System.out.println("\n👋 Exiting host lookup...");
        } finally {
            scanner.close();
        }
    }
    
    /**
     * Demonstrate IP address validation
     */
    public static void demonstrateIPValidation() {
        System.out.println("\n✅ IP Address Validation Demo");
        System.out.println("=".repeat(50));
        
        String[] testAddresses = {
            "8.8.8.8",           // Google DNS
            "192.168.1.1",       // Common router IP
            "127.0.0.1",         // Loopback
            "invalid.ip"         // Invalid
        };
        
        for (String ipStr : testAddresses) {
            try {
                InetAddress addr = InetAddress.getByName(ipStr);
                System.out.println("\n✓ Valid: " + ipStr);
                System.out.println("  Host: " + addr.getHostName());
                System.out.println("  Reachable: " + (addr.isReachable(2000) ? "Yes" : "No"));
                
            } catch (Exception e) {
                System.out.println("\n✗ Invalid: " + ipStr);
            }
        }
        
        System.out.println("\n" + "=".repeat(50));
    }
    
    /**
     * Main method
     */
    public static void main(String[] args) {
        System.out.println("\n🌐 Host Lookup & DNS Resolution Demo");
        System.out.println("Java Networking: InetAddress Class\n");
        
        // Display menu
        System.out.println("Select demonstration mode:");
        System.out.println("1. Show local machine info");
        System.out.println("2. Common hosts demonstration");
        System.out.println("3. IP address validation");
        System.out.println("4. Interactive mode");
        System.out.println("5. Run all demos");
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter choice (1-5): ");
        
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    displayLocalHostInfo();
                    break;
                case 2:
                    demonstrateCommonHosts();
                    break;
                case 3:
                    demonstrateIPValidation();
                    break;
                case 4:
                    interactiveMode();
                    break;
                case 5:
                    displayLocalHostInfo();
                    demonstrateCommonHosts();
                    demonstrateIPValidation();
                    System.out.println("\n" + "=".repeat(50));
                    System.out.println("All demos completed!");
                    System.out.println("=".repeat(50));
                    break;
                default:
                    System.out.println("Invalid choice. Running interactive mode...");
                    interactiveMode();
            }
            
        } catch (Exception e) {
            System.out.println("Invalid input. Running interactive mode...");
            scanner.nextLine(); // Clear buffer
            interactiveMode();
        } finally {
            scanner.close();
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Key Concepts Demonstrated:");
        System.out.println("  ✓ InetAddress class");
        System.out.println("  ✓ DNS hostname resolution");
        System.out.println("  ✓ IP address retrieval");
        System.out.println("  ✓ Multiple IP handling");
        System.out.println("  ✓ Network exception handling");
        System.out.println("=".repeat(50));
    }
}
