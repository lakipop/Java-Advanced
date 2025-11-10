-- ============================================
-- Sample Data for Student Database
-- ============================================
-- This script inserts sample data for testing
-- JDBC examples and learning
-- ============================================

USE student_db;

-- ============================================
-- Insert Sample Students
-- ============================================
INSERT INTO students (student_name, age, email, gpa, department, enrollment_date) VALUES
('John Doe', 20, 'john.doe@example.com', 3.75, 'Computer Science', '2023-09-01'),
('Jane Smith', 22, 'jane.smith@example.com', 3.90, 'Computer Science', '2022-09-01'),
('Mike Johnson', 21, 'mike.j@example.com', 3.45, 'Information Technology', '2023-09-01'),
('Sarah Williams', 19, 'sarah.w@example.com', 3.88, 'Computer Science', '2024-09-01'),
('David Brown', 23, 'david.b@example.com', 3.25, 'Software Engineering', '2021-09-01'),
('Emily Davis', 20, 'emily.d@example.com', 3.92, 'Computer Science', '2023-09-01'),
('James Wilson', 22, 'james.w@example.com', 3.15, 'Information Technology', '2022-09-01'),
('Lisa Anderson', 21, 'lisa.a@example.com', 3.78, 'Computer Science', '2023-09-01'),
('Robert Taylor', 24, 'robert.t@example.com', 3.50, 'Software Engineering', '2020-09-01'),
('Maria Garcia', 19, 'maria.g@example.com', 3.95, 'Computer Science', '2024-09-01');

-- ============================================
-- Insert Sample Courses
-- ============================================
INSERT INTO courses (course_code, course_name, credits, department) VALUES
('CS101', 'Introduction to Programming', 3, 'Computer Science'),
('CS102', 'Data Structures', 4, 'Computer Science'),
('CS201', 'Object-Oriented Programming', 3, 'Computer Science'),
('CS202', 'Database Systems', 4, 'Computer Science'),
('CS301', 'Software Engineering', 3, 'Computer Science'),
('IT101', 'Web Development', 3, 'Information Technology'),
('IT201', 'Network Fundamentals', 3, 'Information Technology'),
('SE101', 'Software Design Patterns', 3, 'Software Engineering'),
('SE201', 'Agile Development', 3, 'Software Engineering'),
('CS401', 'Machine Learning', 4, 'Computer Science');

-- ============================================
-- Insert Sample Enrollments
-- ============================================
INSERT INTO enrollments (student_id, course_id, semester, grade) VALUES
-- Student 1 (John Doe) enrollments
(1, 1, '2023 Fall', 'A'),
(1, 2, '2024 Spring', 'B+'),
(1, 3, '2024 Fall', 'A-'),

-- Student 2 (Jane Smith) enrollments
(2, 1, '2022 Fall', 'A'),
(2, 2, '2023 Spring', 'A'),
(2, 4, '2023 Fall', 'A-'),
(2, 5, '2024 Spring', 'A'),

-- Student 3 (Mike Johnson) enrollments
(3, 1, '2023 Fall', 'B'),
(3, 6, '2024 Spring', 'B+'),
(3, 7, '2024 Fall', 'B'),

-- Student 4 (Sarah Williams) enrollments
(4, 1, '2024 Fall', 'A'),

-- Student 5 (David Brown) enrollments
(5, 1, '2021 Fall', 'B+'),
(5, 2, '2022 Spring', 'B'),
(5, 8, '2023 Fall', 'A-'),
(5, 9, '2024 Spring', 'B+'),

-- Student 6 (Emily Davis) enrollments
(6, 1, '2023 Fall', 'A'),
(6, 2, '2024 Spring', 'A'),
(6, 3, '2024 Fall', 'A'),

-- Student 7 (James Wilson) enrollments
(7, 1, '2022 Fall', 'C+'),
(7, 6, '2023 Spring', 'B'),
(7, 7, '2024 Fall', 'B-'),

-- Student 8 (Lisa Anderson) enrollments
(8, 1, '2023 Fall', 'A-'),
(8, 2, '2024 Spring', 'B+'),
(8, 3, '2024 Fall', 'A'),

-- Student 9 (Robert Taylor) enrollments
(9, 1, '2020 Fall', 'B'),
(9, 2, '2021 Spring', 'B+'),
(9, 8, '2022 Fall', 'A'),
(9, 9, '2023 Spring', 'A-'),

-- Student 10 (Maria Garcia) enrollments
(10, 1, '2024 Fall', 'A');

-- ============================================
-- Verify Data
-- ============================================
-- Check row counts
SELECT 'Students' as Table_Name, COUNT(*) as Row_Count FROM students
UNION ALL
SELECT 'Courses', COUNT(*) FROM courses
UNION ALL
SELECT 'Enrollments', COUNT(*) FROM enrollments;

-- Display sample data
SELECT * FROM students LIMIT 5;
SELECT * FROM courses LIMIT 5;
SELECT * FROM enrollments LIMIT 10;

-- Sample query: Students with their enrollments
SELECT 
    s.student_name,
    c.course_code,
    c.course_name,
    e.semester,
    e.grade
FROM students s
JOIN enrollments e ON s.student_id = e.student_id
JOIN courses c ON e.course_id = c.course_id
ORDER BY s.student_name, e.semester
LIMIT 10;
