/**
 * RunnableThreadDemo - Runnable Interface Implementation
 *
 * Demonstrates:
 * - Creating threads using Runnable interface
 * - Thread object creation and starting
 * - Getting thread name and ID
 * - Multiple threads executing concurrently
 *
 * Original: Java-Design-Patterns/RunnableThread/
 * 
 * @author BICT Practicals Collection
 * @version 1.0
 */

/**
 * MyThreadInter - Implements Runnable Interface
 * This approach is preferred when class already extends another class
 */
class MyThreadInter implements Runnable {
    
    @Override
    public void run() {
        // Thread execution logic
        System.out.println("🔹 Thread executing: " + Thread.currentThread().getName());
        System.out.println("   Thread ID: " + Thread.currentThread().threadId());
        
        // Simulate some work
        for (int i = 1; i <= 5; i++) {
            System.out.println("   " + Thread.currentThread().getName() + " - Count: " + i);
            try {
                Thread.sleep(500); // Pause for 500ms
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
public class RunnableThreadDemo {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   RUNNABLE INTERFACE THREADING DEMO    ║");
        System.out.println("║   Multiple Threads with Runnable       ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        System.out.println("📌 Creating threads using Runnable interface...\n");

        // Create Runnable instances
        MyThreadInter g1 = new MyThreadInter();
        MyThreadInter g2 = new MyThreadInter();
        
        // Create Thread objects with Runnable
        Thread t1 = new Thread(g1, "Worker-1");
        Thread t2 = new Thread(g2, "Worker-2");
        
        System.out.println("🚀 Starting threads...\n");
        
        // Start threads
        t1.start();
        t2.start();
        
        // Wait for threads to complete
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("=".repeat(50));
        System.out.println("✅ All Threads Completed!");
        System.out.println("=".repeat(50));
        
        // Key Concepts
        System.out.println("\n📚 KEY CONCEPTS:");
        System.out.println("• Runnable Interface: Defines run() method for thread logic");
        System.out.println("• Thread Object: Wraps Runnable and manages thread execution");
        System.out.println("• start(): Begins thread execution (calls run() internally)");
        System.out.println("• join(): Waits for thread to complete");
        
        System.out.println("\n💡 RUNNABLE vs THREAD:");
        System.out.println("✓ Runnable: More flexible (class can extend another)");
        System.out.println("✓ Runnable: Better for task-based design");
        System.out.println("✓ Thread: Simpler but limits inheritance");
    }
}
