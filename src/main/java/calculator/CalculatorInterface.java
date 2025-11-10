package calculator;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * CalculatorInterface - RMI Remote Interface for Calculator
 * 
 * This interface demonstrates:
 * - Remote interface extending Remote
 * - Remote methods throwing RemoteException
 * - Contract for distributed calculator operations
 * 
 * Original: MyRMICalculator practical (MyCalcInter)
 * 
 * @author BICT Advanced Java Course
 * @version 1.0
 */
public interface CalculatorInterface extends Remote {
    
    /**
     * Add two integers remotely
     * 
     * @param a first number
     * @param b second number
     * @return sum of a and b
     * @throws RemoteException if remote communication fails
     */
    int add(int a, int b) throws RemoteException;
    
    /**
     * Subtract two integers remotely
     * 
     * @param a first number
     * @param b second number
     * @return difference (a - b)
     * @throws RemoteException if remote communication fails
     */
    int subtract(int a, int b) throws RemoteException;
    
    /**
     * Multiply two integers remotely
     * 
     * @param a first number
     * @param b second number
     * @return product of a and b
     * @throws RemoteException if remote communication fails
     */
    int multiply(int a, int b) throws RemoteException;
    
    /**
     * Divide two integers remotely
     * 
     * @param a dividend
     * @param b divisor
     * @return quotient (a / b)
     * @throws RemoteException if remote communication fails
     * @throws ArithmeticException if b is zero
     */
    int divide(int a, int b) throws RemoteException, ArithmeticException;
    
    /**
     * Calculate power (a raised to b)
     * 
     * @param base base number
     * @param exponent exponent
     * @return base raised to exponent
     * @throws RemoteException if remote communication fails
     */
    double power(double base, int exponent) throws RemoteException;
}
