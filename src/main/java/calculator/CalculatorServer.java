package calculator;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * CalculatorServer - RMI Server for Calculator
 * 
 * This program demonstrates:
 * - Creating RMI registry
 * - Binding remote object to registry
 * - RMI server setup and lifecycle
 * - Naming service usage
 * 
 * Original: MyRMICalculator practical (Server class)
 * 
 * @author BICT Advanced Java Course
 * @version 1.0
 */
public class CalculatorServer {
    
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════╗");
        System.out.println("║   RMI Calculator Server - BICT    ║");
        System.out.println("╚═══════════════════════════════════╝\n");
        
        try {
            // Create RMI registry on port 1099
            System.out.println("⏳ Creating RMI registry on port 1099...");
            LocateRegistry.createRegistry(1099);
            System.out.println("✓ RMI registry created\n");
            
            // Create calculator implementation
            System.out.println("⏳ Creating calculator implementation...");
            CalculatorImpl calculator = new CalculatorImpl();
            
            // Bind calculator to registry
            String bindName = "rmi://localhost/CalculatorService";
            System.out.println("⏳ Binding calculator to: " + bindName);
            Naming.rebind(bindName, calculator);
            System.out.println("✓ Calculator bound successfully!\n");
            
            System.out.println("=".repeat(50));
            System.out.println("🚀 Server is ready and waiting for clients...");
            System.out.println("=".repeat(50));
            System.out.println();
            System.out.println("Server Information:");
            System.out.println("  • Service Name: CalculatorService");
            System.out.println("  • Port: 1099");
            System.out.println("  • Status: Running");
            System.out.println();
            System.out.println("To stop the server, press Ctrl+C");
            System.out.println();
            
        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
