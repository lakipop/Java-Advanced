/**
 * BasicThreadDemo - Basic Thread Creation and Execution
 *
 * Demonstrates:
 * - Extending Thread class
 * - Overriding run() method
 * - Thread naming with setName()
 * - Starting threads with start()
 *
 * Original: Java-Design-Patterns/ThreadEX/
 * 
 * @author BICT Practicals Collection
 * @version 1.0
 */

/**
 * MythreadEX - Custom Thread Implementation
 * Extends Thread class to create custom thread behavior
 */
class MythreadEX extends Thread {

    @Override
    public void run() {
        String str = "Thread Started Running...";
        System.out.println("🔹 " + this.getName() + ": " + str);
        
        // Simulate some work
        for (int i = 1; i <= 5; i++) {
            System.out.println("   " + this.getName() + " - Step " + i);
            try {
                Thread.sleep(300); // Pause 300ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("✅ " + this.getName() + " completed execution!\n");
    }
}

/**
 * Main Demo Class
 */
public class BasicThreadDemo {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   BASIC THREADING DEMO                 ║");
        System.out.println("║   Thread Extension Example             ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        System.out.println("📌 Creating custom threads by extending Thread class...\n");

        // Create and configure first thread
        MythreadEX t1 = new MythreadEX();
        t1.setName("FirstThread");
        
        // Create and configure second thread
        MythreadEX t2 = new MythreadEX();
        t2.setName("SecondThread");
        
        // Create and configure third thread
        MythreadEX t3 = new MythreadEX();
        t3.setName("ThirdThread");
        
        System.out.println("🚀 Starting threads...\n");
        
        // Start all threads
        t1.start();
        t2.start();
        t3.start();
        
        // Wait for all threads to complete
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("=".repeat(50));
        System.out.println("✅ All Threads Completed Successfully!");
        System.out.println("=".repeat(50));
        
        // Key Concepts
        System.out.println("\n📚 KEY CONCEPTS:");
        System.out.println("• Extending Thread: Creates custom thread by inheritance");
        System.out.println("• run(): Contains thread execution logic");
        System.out.println("• start(): Begins thread execution (don't call run() directly!)");
        System.out.println("• setName(): Assigns readable name to thread");
        System.out.println("• getName(): Retrieves thread name");
        
        System.out.println("\n⚠️  IMPORTANT:");
        System.out.println("• Always call start() to begin thread (not run())");
        System.out.println("• start() creates new thread and calls run()");
        System.out.println("• Calling run() directly executes in current thread");
        
        System.out.println("\n💡 THREAD LIFECYCLE:");
        System.out.println("1. NEW: Thread created but not started");
        System.out.println("2. RUNNABLE: Thread.start() called, ready to run");
        System.out.println("3. RUNNING: Thread executing run() method");
        System.out.println("4. TERMINATED: Thread completed execution");
    }
}
