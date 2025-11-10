-- ============================================
-- Student Database Schema
-- ============================================
-- This script creates a sample database for 
-- Java JDBC learning examples
-- ============================================

-- Create database (if not exists)
CREATE DATABASE IF NOT EXISTS student_db;

-- Use the database
USE student_db;

-- ============================================
-- Table: students
-- ============================================
-- Stores student information
DROP TABLE IF EXISTS students;

CREATE TABLE students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    student_name VARCHAR(100) NOT NULL,
    age INT CHECK (age >= 0 AND age <= 150),
    email VARCHAR(100) UNIQUE,
    gpa DECIMAL(3, 2) CHECK (gpa >= 0.0 AND gpa <= 4.0),
    department VARCHAR(50),
    enrollment_date DATE DEFAULT (CURRENT_DATE),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================
-- Table: courses
-- ============================================
-- Stores course information
DROP TABLE IF EXISTS courses;

CREATE TABLE courses (
    course_id INT PRIMARY KEY AUTO_INCREMENT,
    course_code VARCHAR(10) UNIQUE NOT NULL,
    course_name VARCHAR(100) NOT NULL,
    credits INT DEFAULT 3,
    department VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- Table: enrollments
-- ============================================
-- Links students to courses
DROP TABLE IF EXISTS enrollments;

CREATE TABLE enrollments (
    enrollment_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    semester VARCHAR(20),
    grade VARCHAR(2),
    enrollment_date DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE,
    UNIQUE KEY unique_enrollment (student_id, course_id, semester)
);

-- ============================================
-- Indexes for better performance
-- ============================================
CREATE INDEX idx_student_name ON students(student_name);
CREATE INDEX idx_student_department ON students(department);
CREATE INDEX idx_course_code ON courses(course_code);
CREATE INDEX idx_enrollment_student ON enrollments(student_id);
CREATE INDEX idx_enrollment_course ON enrollments(course_id);

-- ============================================
-- Display table structures
-- ============================================
SHOW TABLES;
DESCRIBE students;
DESCRIBE courses;
DESCRIBE enrollments;
