import java.sql.*;

/**
 * StudentCRUD - Complete CRUD Operations for Students
 * 
 * This class demonstrates:
 * - CREATE: Inserting new student records
 * - READ: Retrieving student data
 * - UPDATE: Modifying existing records
 * - DELETE: Removing student records
 * - Prepared statements for security
 * - Transaction management
 * 
 * @author BICT Advanced Java Course
 * @version 1.0
 */
public class StudentCRUD {
    
    /**
     * Create a new student record
     * 
     * @param firstName student's first name
     * @param lastName student's last name
     * @param email student's email
     * @param dateOfBirth student's birth date (YYYY-MM-DD)
     * @param major student's major
     * @return true if successful, false otherwise
     */
    public static boolean createStudent(String firstName, String lastName, 
                                       String email, String dateOfBirth, String major) {
        String sql = "INSERT INTO students (first_name, last_name, email, date_of_birth, major) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setDate(4, Date.valueOf(dateOfBirth));
            pstmt.setString(5, major);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✓ Student created successfully!");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error creating student: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Read and display all students
     */
    public static void readAllStudents() {
        String sql = "SELECT student_id, first_name, last_name, email, date_of_birth, " +
                    "major, enrollment_date FROM students ORDER BY student_id";
        
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n=== All Students ===");
            System.out.println("-".repeat(100));
            System.out.printf("%-5s %-15s %-15s %-30s %-12s %-20s %-12s%n",
                            "ID", "First Name", "Last Name", "Email", "DOB", "Major", "Enrolled");
            System.out.println("-".repeat(100));
            
            int count = 0;
            while (rs.next()) {
                System.out.printf("%-5d %-15s %-15s %-30s %-12s %-20s %-12s%n",
                    rs.getInt("student_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getDate("date_of_birth"),
                    rs.getString("major"),
                    rs.getDate("enrollment_date"));
                count++;
            }
            
            System.out.println("-".repeat(100));
            System.out.println("Total students: " + count);
            
        } catch (SQLException e) {
            System.err.println("Error reading students: " + e.getMessage());
        }
    }
    
    /**
     * Read student by ID
     * 
     * @param studentId the student's ID
     */
    public static void readStudentById(int studentId) {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                System.out.println("\n=== Student Details ===");
                System.out.println("ID: " + rs.getInt("student_id"));
                System.out.println("Name: " + rs.getString("first_name") + " " + 
                                 rs.getString("last_name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Date of Birth: " + rs.getDate("date_of_birth"));
                System.out.println("Major: " + rs.getString("major"));
                System.out.println("Enrollment Date: " + rs.getDate("enrollment_date"));
            } else {
                System.out.println("Student with ID " + studentId + " not found.");
            }
            
            rs.close();
            
        } catch (SQLException e) {
            System.err.println("Error reading student: " + e.getMessage());
        }
    }
    
    /**
     * Update student information
     * 
     * @param studentId student's ID
     * @param email new email (null to keep unchanged)
     * @param major new major (null to keep unchanged)
     * @return true if successful, false otherwise
     */
    public static boolean updateStudent(int studentId, String email, String major) {
        StringBuilder sql = new StringBuilder("UPDATE students SET ");
        boolean needsComma = false;
        
        if (email != null) {
            sql.append("email = ?");
            needsComma = true;
        }
        
        if (major != null) {
            if (needsComma) sql.append(", ");
            sql.append("major = ?");
        }
        
        sql.append(" WHERE student_id = ?");
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            int paramIndex = 1;
            
            if (email != null) {
                pstmt.setString(paramIndex++, email);
            }
            
            if (major != null) {
                pstmt.setString(paramIndex++, major);
            }
            
            pstmt.setInt(paramIndex, studentId);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✓ Student updated successfully!");
                return true;
            } else {
                System.out.println("Student with ID " + studentId + " not found.");
            }
            
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Delete a student record
     * 
     * @param studentId student's ID to delete
     * @return true if successful, false otherwise
     */
    public static boolean deleteStudent(int studentId) {
        // First check if student exists
        String checkSql = "SELECT first_name, last_name FROM students WHERE student_id = ?";
        String deleteSql = "DELETE FROM students WHERE student_id = ?";
        
        try (Connection conn = DatabaseConnector.getConnection()) {
            
            // Check if student exists
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, studentId);
                ResultSet rs = checkStmt.executeQuery();
                
                if (!rs.next()) {
                    System.out.println("Student with ID " + studentId + " not found.");
                    rs.close();
                    return false;
                }
                
                String name = rs.getString("first_name") + " " + rs.getString("last_name");
                rs.close();
                
                // Delete student
                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                    deleteStmt.setInt(1, studentId);
                    int rowsAffected = deleteStmt.executeUpdate();
                    
                    if (rowsAffected > 0) {
                        System.out.println("✓ Student deleted: " + name);
                        return true;
                    }
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Search students by major
     * 
     * @param major the major to search for
     */
    public static void searchByMajor(String major) {
        String sql = "SELECT student_id, first_name, last_name, email, major " +
                    "FROM students WHERE major LIKE ? ORDER BY last_name";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + major + "%");
            ResultSet rs = pstmt.executeQuery();
            
            System.out.println("\n=== Students in " + major + " ===");
            System.out.println("-".repeat(80));
            
            int count = 0;
            while (rs.next()) {
                System.out.printf("ID: %-5d | %s %s | %s%n",
                    rs.getInt("student_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"));
                count++;
            }
            
            System.out.println("-".repeat(80));
            System.out.println("Found " + count + " student(s)");
            
            rs.close();
            
        } catch (SQLException e) {
            System.err.println("Error searching students: " + e.getMessage());
        }
    }
    
    /**
     * Main method to demonstrate CRUD operations
     */
    public static void main(String[] args) {
        System.out.println("=== Student CRUD Operations Demo ===\n");
        
        // Read all students
        readAllStudents();
        
        // Read specific student
        System.out.println("\n--- Reading Student ID: 1 ---");
        readStudentById(1);
        
        // Search by major
        searchByMajor("Computer Science");
        
        // Create new student
        System.out.println("\n--- Creating New Student ---");
        createStudent("Alice", "Johnson", "alice.j@email.com", 
                     "2003-05-15", "Computer Science");
        
        // Update student
        System.out.println("\n--- Updating Student ---");
        updateStudent(1, "new.email@college.edu", null);
        
        // Verify update
        readStudentById(1);
        
        System.out.println("\n=== CRUD Operations Completed ===");
        System.out.println("Note: Delete operation not demonstrated to preserve sample data.");
        System.out.println("To test delete: deleteStudent(studentId);");
        
        DatabaseConnector.closeConnection();
    }
}
