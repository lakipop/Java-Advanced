package calculator;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * CalculatorImpl - Implementation of Remote Calculator
 * 
 * This class demonstrates:
 * - Extending UnicastRemoteObject
 * - Implementing remote interface
 * - Remote method implementations
 * - Exception handling in RMI
 * 
 * Original: MyRMICalculator practical (MyCalculator)
 * 
 * @author BICT Advanced Java Course
 * @version 1.0
 */
public class CalculatorImpl extends UnicastRemoteObject implements CalculatorInterface {
    
    /**
     * Constructor - must throw RemoteException
     */
    public CalculatorImpl() throws RemoteException {
        super();
        System.out.println("✓ Calculator implementation created");
    }
    
    @Override
    public int add(int a, int b) throws RemoteException {
        int result = a + b;
        System.out.println("Calculation: " + a + " + " + b + " = " + result);
        return result;
    }
    
    @Override
    public int subtract(int a, int b) throws RemoteException {
        int result = a - b;
        System.out.println("Calculation: " + a + " - " + b + " = " + result);
        return result;
    }
    
    @Override
    public int multiply(int a, int b) throws RemoteException {
        int result = a * b;
        System.out.println("Calculation: " + a + " × " + b + " = " + result);
        return result;
    }
    
    @Override
    public int divide(int a, int b) throws RemoteException, ArithmeticException {
        if (b == 0) {
            System.out.println("Error: Division by zero attempted!");
            throw new ArithmeticException("Cannot divide by zero!");
        }
        int result = a / b;
        System.out.println("Calculation: " + a + " ÷ " + b + " = " + result);
        return result;
    }
    
    @Override
    public double power(double base, int exponent) throws RemoteException {
        double result = Math.pow(base, exponent);
        System.out.println("Calculation: " + base + " ^ " + exponent + " = " + result);
        return result;
    }
}
