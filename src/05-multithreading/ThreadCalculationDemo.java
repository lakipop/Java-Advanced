/**
 * ThreadCalculationDemo - Multiple Threading Examples
 *
 * Demonstrates:
 * - Thread extending Thread class
 * - Synchronized methods with Runnable
 * - Thread priorities and sleep
 * - Sum, multiplication, and timer examples
 *
 * Original: Java-Design-Patterns/SumThread/
 * 
 * @author BICT Practicals Collection
 * @version 1.0
 */

/**
 * ThreadDev - Thread for Sum Calculation
 * Extends Thread class directly
 */
class ThreadDev extends Thread {

    @Override
    public void run() {
        int sum = 0;
        System.out.println("➕ Starting sum calculation in: " + getName());
        
        for (int i = 0; i < 11; i++) {
            sum += i;
        }
        
        System.out.println("✅ Sum Result: " + sum + " [Thread: " + getName() + ", ID: " + threadId() + "]\n");
    }
}

/**
 * ThreadMultiplication - Thread for Multiplication
 * Calculates factorial
 */
class ThreadMultiplication extends Thread {
    
    @Override
    public void run() {
        int multiplier = 1;
        System.out.println("✖️  Starting multiplication in: " + getName());
        
        for (int i = 1; i < 11; i++) {
            multiplier *= i;
        }
        
        System.out.println("✅ Multiplication Result: " + multiplier + " [Thread: " + getName() + ", ID: " + threadId() + "]\n");
    }
}

/**
 * Timer - Simple Timer Thread
 * Counts with sleep intervals
 */
class Timer extends Thread {
    
    @Override
    public void run() {
        System.out.println("⏱️  Timer starting in: " + getName() + "\n");
        
        int count = 0;
        for (int i = 0; i < 11; i++) {
            System.out.println("   ⏰ Timer Count: " + count + " [" + getName() + "]");
            count += 1;
            
            try {
                Thread.sleep(1000); // Sleep 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("✅ Timer completed in: " + getName() + "\n");
    }
}

/**
 * MyTimer - Synchronized Timer with Runnable
 * Demonstrates thread synchronization
 */
class MyTimer implements Runnable {
    
    @Override
    public void run() {
        printNumbers();
    }
    
    /**
     * Synchronized method - only one thread can execute at a time
     * Prevents race conditions
     */
    public synchronized void printNumbers() {
        int count = 0;
        
        System.out.println("🔒 Synchronized timer starting: " + Thread.currentThread().getName() + "\n");
        
        for (int i = 0; i < 11; i++) {
            System.out.println("   " + Thread.currentThread().getName() + " - Time: " + count);
            count += 1;
            
            try {
                Thread.sleep(500); // Sleep 500ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("✅ " + Thread.currentThread().getName() + " completed!\n");
    }
}

/**
 * Main Demo Class
 */
public class ThreadCalculationDemo {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   THREADING CALCULATION DEMO           ║");
        System.out.println("║   Sum, Multiplication, Timer Examples  ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        System.out.println("🎯 DEMO 1: Thread Calculations\n");
        
        // Sum and Multiplication threads
        ThreadDev sum = new ThreadDev();
        sum.setName("SumThread");
        sum.start();
        
        ThreadMultiplication mul = new ThreadMultiplication();
        mul.setName("MultiplyThread");
        mul.start();
        
        try {
            sum.join();
            mul.join();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("─".repeat(50) + "\n");
        System.out.println("🎯 DEMO 2: Synchronized Timer\n");
        
        // Synchronized timer with Runnable
        MyTimer timerTask = new MyTimer();
        
        Thread th1 = new Thread(timerTask, "Timer-A");
        Thread th2 = new Thread(timerTask, "Timer-B");
        
        // Set priorities (optional)
        th1.setPriority(Thread.MIN_PRIORITY);
        th2.setPriority(Thread.MAX_PRIORITY);
        
        th1.start();
        th2.start();
        
        try {
            th1.join();
            th2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("=".repeat(50));
        System.out.println("✅ All Threading Demos Completed!");
        System.out.println("=".repeat(50));
        
        // Key Concepts
        System.out.println("\n📚 KEY CONCEPTS:");
        System.out.println("• Thread.sleep(): Pauses thread execution");
        System.out.println("• synchronized: Ensures one thread access at a time");
        System.out.println("• join(): Waits for thread completion");
        System.out.println("• setPriority(): Suggests thread execution priority");
        
        System.out.println("\n💡 THREAD SAFETY:");
        System.out.println("✓ Synchronized methods prevent race conditions");
        System.out.println("✓ Only ONE thread can execute synchronized method at once");
        System.out.println("✓ Other threads wait until current thread completes");
    }
}
