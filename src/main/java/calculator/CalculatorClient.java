package calculator;

import java.rmi.Naming;
import java.util.Scanner;

/**
 * CalculatorClient - RMI Client for Calculator
 * 
 * This program demonstrates:
 * - Looking up remote objects in RMI registry
 * - Calling remote methods
 * - Handling remote exceptions
 * - Interactive RMI client
 * 
 * Original: MyRMICalculator practical (Client class)
 * 
 * @author BICT Advanced Java Course
 * @version 1.0
 */
public class CalculatorClient {
    
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════╗");
        System.out.println("║   RMI Calculator Client - BICT    ║");
        System.out.println("╚═══════════════════════════════════╝\n");
        
        Scanner scanner = null;
        try {
            // Lookup remote calculator service
            String lookupName = "rmi://localhost/CalculatorService";
            System.out.println("⏳ Looking up calculator service...");
            System.out.println("   Service URL: " + lookupName);
            
            CalculatorInterface calculator = (CalculatorInterface) Naming.lookup(lookupName);
            System.out.println("✓ Connected to remote calculator!\n");
            
            // Interactive calculator
            scanner = new Scanner(System.in);
            
            System.out.println("=".repeat(50));
            System.out.println("Remote Calculator Menu:");
            System.out.println("1. Addition");
            System.out.println("2. Subtraction");
            System.out.println("3. Multiplication");
            System.out.println("4. Division");
            System.out.println("5. Power");
            System.out.println("6. Run Demo");
            System.out.println("0. Exit");
            System.out.println("=".repeat(50));
            
            while (true) {
                System.out.print("\nEnter choice (0-6): ");
                int choice = scanner.nextInt();
                
                if (choice == 0) {
                    System.out.println("\n✓ Disconnecting from server. Goodbye!");
                    break;
                }
                
                if (choice == 6) {
                    // Run demo
                    runDemo(calculator);
                    continue;
                }
                
                if (choice < 1 || choice > 5) {
                    System.out.println("Invalid choice! Please enter 0-6.");
                    continue;
                }
                
                // Get numbers for operation
                System.out.print("Enter first number: ");
                double num1 = scanner.nextDouble();
                System.out.print("Enter second number: ");
                double num2 = scanner.nextDouble();
                
                // Perform operation
                performOperation(calculator, choice, num1, num2);
            }
            
        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
            System.err.println("\nMake sure:");
            System.err.println("  1. CalculatorServer is running");
            System.err.println("  2. RMI registry is active on port 1099");
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
    
    /**
     * Perform calculation based on choice
     */
    private static void performOperation(CalculatorInterface calc, int choice, 
                                        double num1, double num2) {
        try {
            int a = (int) num1;
            int b = (int) num2;
            int result;
            
            System.out.println("\n⏳ Sending request to server...");
            
            switch (choice) {
                case 1:
                    result = calc.add(a, b);
                    System.out.println("✓ Result: " + a + " + " + b + " = " + result);
                    break;
                case 2:
                    result = calc.subtract(a, b);
                    System.out.println("✓ Result: " + a + " - " + b + " = " + result);
                    break;
                case 3:
                    result = calc.multiply(a, b);
                    System.out.println("✓ Result: " + a + " × " + b + " = " + result);
                    break;
                case 4:
                    result = calc.divide(a, b);
                    System.out.println("✓ Result: " + a + " ÷ " + b + " = " + result);
                    break;
                case 5:
                    double powResult = calc.power(num1, b);
                    System.out.println("✓ Result: " + num1 + " ^ " + b + " = " + powResult);
                    break;
            }
        } catch (ArithmeticException e) {
            System.err.println("✗ Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("✗ Remote error: " + e.getMessage());
        }
    }
    
    /**
     * Run demonstration of all operations
     */
    private static void runDemo(CalculatorInterface calc) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running Calculator Demo...");
        System.out.println("=".repeat(50));
        
        try {
            // Addition
            System.out.println("\n1. Addition:");
            int sum = calc.add(25, 15);
            System.out.println("   25 + 15 = " + sum);
            
            // Subtraction
            System.out.println("\n2. Subtraction:");
            int diff = calc.subtract(50, 20);
            System.out.println("   50 - 20 = " + diff);
            
            // Multiplication
            System.out.println("\n3. Multiplication:");
            int product = calc.multiply(12, 8);
            System.out.println("   12 × 8 = " + product);
            
            // Division
            System.out.println("\n4. Division:");
            int quotient = calc.divide(100, 5);
            System.out.println("   100 ÷ 5 = " + quotient);
            
            // Power
            System.out.println("\n5. Power:");
            double power = calc.power(2, 10);
            System.out.println("   2 ^ 10 = " + power);
            
            // Division by zero (error handling)
            System.out.println("\n6. Division by Zero (Error Handling):");
            try {
                calc.divide(10, 0);
            } catch (ArithmeticException e) {
                System.out.println("   ✓ Error caught: " + e.getMessage());
            }
            
            System.out.println("\n" + "=".repeat(50));
            System.out.println("✓ Demo completed successfully!");
            System.out.println("=".repeat(50));
            
        } catch (Exception e) {
            System.err.println("Demo error: " + e.getMessage());
        }
    }
}
