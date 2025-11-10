import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DatabaseConnector - Database Connection Utility
 * 
 * This class demonstrates:
 * - Reading database configuration from properties file
 * - Establishing JDBC connection to MySQL
 * - Connection pooling basics
 * - Error handling for database connections
 * - Secure credential management
 * 
 * @author BICT Advanced Java Course
 * @version 1.0
 */
public class DatabaseConnector {
    private static Connection connection = null;
    private static Properties dbProperties = null;
    
    /**
     * Load database properties from configuration file
     * 
     * @param configFile path to the properties file
     * @return Properties object with database configuration
     */
    private static Properties loadDatabaseProperties(String configFile) {
        Properties props = new Properties();
        
        try (FileInputStream fis = new FileInputStream(configFile)) {
            props.load(fis);
            System.out.println("✓ Database configuration loaded successfully");
        } catch (IOException e) {
            System.err.println("Error loading configuration file: " + e.getMessage());
            System.err.println("Please ensure '" + configFile + "' exists in resources/config/");
            return null;
        }
        
        return props;
    }
    
    /**
     * Establish connection to MySQL database
     * 
     * @return Connection object or null if connection fails
     */
    public static Connection getConnection() {
        // Return existing connection if still valid
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
        } catch (SQLException e) {
            System.err.println("Error checking connection status: " + e.getMessage());
        }
        
        // Load database properties if not already loaded
        if (dbProperties == null) {
            String configPath = "resources/config/db.properties";
            dbProperties = loadDatabaseProperties(configPath);
            
            if (dbProperties == null) {
                System.err.println("\n=== CONFIGURATION REQUIRED ===");
                System.err.println("1. Copy 'resources/config-templates/db-template.properties'");
                System.err.println("   to 'resources/config/db.properties'");
                System.err.println("2. Update with your actual database credentials");
                System.err.println("3. Ensure MySQL server is running");
                return null;
            }
        }
        
        // Get connection parameters
        String url = dbProperties.getProperty("db.url");
        String username = dbProperties.getProperty("db.username");
        String password = dbProperties.getProperty("db.password");
        
        // Validate configuration
        if (url == null || url.contains("YOUR_HOST") || 
            username == null || username.equals("YOUR_USERNAME")) {
            System.err.println("\n=== INVALID CONFIGURATION ===");
            System.err.println("Please update resources/config/db.properties with:");
            System.err.println("  - Your MySQL host and database name");
            System.err.println("  - Your MySQL username and password");
            return null;
        }
        
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✓ MySQL JDBC Driver loaded");
            
            // Establish connection
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("✓ Database connection established successfully");
            System.out.println("  Connected to: " + url);
            
            return connection;
            
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            System.err.println("Please download and add mysql-connector-java to your classpath");
            System.err.println("Download from: https://dev.mysql.com/downloads/connector/j/");
            return null;
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            System.err.println("Error: " + e.getMessage());
            System.err.println("\nPossible causes:");
            System.err.println("  1. MySQL server is not running");
            System.err.println("  2. Incorrect database name, username, or password");
            System.err.println("  3. Database 'college_db' does not exist");
            System.err.println("     Run: resources/database-scripts/schema.sql");
            System.err.println("  4. User does not have required permissions");
            return null;
        }
    }
    
    /**
     * Close database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
    
    /**
     * Test database connection
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("=== Database Connection Test ===\n");
        
        // Attempt to connect
        Connection conn = getConnection();
        
        if (conn != null) {
            try {
                // Get database metadata
                System.out.println("\n=== Connection Details ===");
                System.out.println("Database Product: " + 
                                 conn.getMetaData().getDatabaseProductName());
                System.out.println("Database Version: " + 
                                 conn.getMetaData().getDatabaseProductVersion());
                System.out.println("Driver Name: " + 
                                 conn.getMetaData().getDriverName());
                System.out.println("Driver Version: " + 
                                 conn.getMetaData().getDriverVersion());
                System.out.println("Catalog: " + conn.getCatalog());
                
                System.out.println("\n✓ Connection test successful!");
                
            } catch (SQLException e) {
                System.err.println("Error retrieving metadata: " + e.getMessage());
            } finally {
                closeConnection();
            }
        } else {
            System.err.println("\n✗ Connection test failed!");
            System.err.println("Please check your configuration and try again.");
        }
    }
}
