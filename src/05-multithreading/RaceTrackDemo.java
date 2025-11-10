/**
 * RaceTrackDemo - Synchronized Methods and Thread Coordination
 * 
 * This program demonstrates:
 * - Synchronized methods preventing race conditions
 * - Multiple threads accessing shared resource
 * - Thread-safe execution
 * - Thread naming and identification
 * - Critical section protection
 * 
 * Original: RacerCarDemo practical
 * 
 * Scenario: Multiple race cars competing on a common track section
 * 
 * @author BICT Advanced Java Course
 * @version 1.0
 */

/**
 * RaceTrack - Shared resource representing common track section
 * Only one car can pass through at a time (synchronized)
 */
class RaceTrack implements Runnable {
    private int carsFinished = 0;
    private static final int TRACK_STAGES = 5;
    
    /**
     * Synchronized run method - ensures only one car on track at a time
     */
    @Override
    public synchronized void run() {
        navigateCommonSection();
    }
    
    /**
     * Cars must navigate this common section one at a time
     */
    private void navigateCommonSection() {
        String carName = Thread.currentThread().getName();
        
        System.out.println("\n🏁 [" + carName + "] Entering common track section...");
        
        for (int stage = 1; stage <= TRACK_STAGES; stage++) {
            System.out.printf("   🚗 [%s] Stage %d/%d%n", carName, stage, TRACK_STAGES);
            
            try {
                // Simulate time to navigate each stage
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.err.println("Race interrupted for " + carName);
                return;
            }
        }
        
        carsFinished++;
        System.out.println("✅ [" + carName + "] Completed track! Position: #" + carsFinished);
    }
    
    /**
     * Get total cars that finished
     */
    public synchronized int getCarsFinished() {
        return carsFinished;
    }
}

/**
 * RaceCar - Represents a racing car that creates competitor threads
 */
class RaceCar implements Runnable {
    private final String[] carNames;
    
    public RaceCar(String... carNames) {
        this.carNames = carNames;
    }
    
    @Override
    public void run() {
        System.out.println("🏎️  Starting race with " + carNames.length + " cars!");
        
        RaceTrack track = new RaceTrack();
        Thread[] carThreads = new Thread[carNames.length];
        
        // Create and start threads for each car
        for (int i = 0; i < carNames.length; i++) {
            carThreads[i] = new Thread(track, carNames[i]);
            carThreads[i].start();
            
            // Small delay between car starts
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // Wait for all cars to finish
        for (Thread carThread : carThreads) {
            try {
                carThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("\n🏆 Race completed! All " + track.getCarsFinished() + " cars finished.");
    }
}

public class RaceTrackDemo {
    
    /**
     * Demonstrate basic race with 3 cars
     */
    public static void basicRace() {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║      Basic Race - Synchronized Track      ║");
        System.out.println("╚════════════════════════════════════════════╝");
        
        RaceCar race = new RaceCar("Ferrari", "Lamborghini", "Porsche");
        Thread raceThread = new Thread(race, "Race-Controller");
        raceThread.start();
        
        try {
            raceThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Demonstrate what happens without synchronization
     */
    public static void demonstrateUnsynchronized() {
        System.out.println("\n\n╔════════════════════════════════════════════╗");
        System.out.println("║   Unsynchronized Access Demonstration     ║");
        System.out.println("╚════════════════════════════════════════════╝");
        
        System.out.println("⚠️  Without synchronization, output would be:");
        System.out.println("   - Interleaved and confusing");
        System.out.println("   - Race conditions possible");
        System.out.println("   - Unpredictable results");
        System.out.println("\n✅ With synchronized methods:");
        System.out.println("   - Orderly execution");
        System.out.println("   - Thread-safe access");
        System.out.println("   - Predictable results\n");
    }
    
    /**
     * Demonstrate larger race with 5 cars
     */
    public static void largeRace() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║     Large Race - 5 Cars Competition       ║");
        System.out.println("╚════════════════════════════════════════════╝");
        
        RaceCar race = new RaceCar(
            "Red Bull Racing",
            "Mercedes AMG",
            "McLaren",
            "Aston Martin",
            "Alpine F1"
        );
        
        Thread raceThread = new Thread(race, "F1-Race-Controller");
        raceThread.start();
        
        try {
            raceThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Main method demonstrating synchronized thread coordination
     */
    public static void main(String[] args) {
        System.out.println("\n🏁 Race Track Synchronization Demonstration");
        System.out.println("Advanced Threading: synchronized, Thread Coordination\n");
        
        // Demo 1: Basic 3-car race
        basicRace();
        
        // Demo 2: Explain synchronization benefits
        demonstrateUnsynchronized();
        
        // Demo 3: Larger 5-car race
        largeRace();
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Race Track Demo Completed!");
        System.out.println("Key Concepts Demonstrated:");
        System.out.println("  ✓ Synchronized methods");
        System.out.println("  ✓ Critical section protection");
        System.out.println("  ✓ Thread-safe shared resources");
        System.out.println("  ✓ Thread.join() for coordination");
        System.out.println("  ✓ Multiple threads competing for resource");
        System.out.println("  ✓ Preventing race conditions");
        System.out.println("=".repeat(50));
    }
}
