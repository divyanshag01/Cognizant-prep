/**
 * Exercise 10: Implementing the MVC Pattern
 * 
 * Scenario: Simple application for managing student records using MVC pattern.
 */

import java.util.*;

// Model - Student Class
class Student {
    private int studentId;
    private String name;
    private String email;
    private double gpa;
    private String major;
    
    public Student(int studentId, String name, String email, double gpa, String major) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.gpa = gpa;
        this.major = major;
    }
    
    // Getters and Setters
    public int getStudentId() {
        return studentId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public double getGpa() {
        return gpa;
    }
    
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
    
    public String getMajor() {
        return major;
    }
    
    public void setMajor(String major) {
        this.major = major;
    }
    
    @Override
    public String toString() {
        return "Student{" +
                "id=" + studentId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", gpa=" + gpa +
                ", major='" + major + '\'' +
                '}';
    }
}

// Model - StudentRepository (Data Storage)
class StudentRepository {
    private Map<Integer, Student> students;
    private int nextId;
    
    public StudentRepository() {
        this.students = new HashMap<>();
        this.nextId = 1;
    }
    
    public void addStudent(Student student) {
        students.put(student.getStudentId(), student);
        System.out.println("Student added to database");
    }
    
    public Student getStudentById(int id) {
        return students.get(id);
    }
    
    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }
    
    public void updateStudent(Student student) {
        students.put(student.getStudentId(), student);
        System.out.println("Student updated in database");
    }
    
    public void deleteStudent(int id) {
        students.remove(id);
        System.out.println("Student deleted from database");
    }
    
    public Student findByEmail(String email) {
        for (Student student : students.values()) {
            if (student.getEmail().equals(email)) {
                return student;
            }
        }
        return null;
    }
    
    public int getNextId() {
        return nextId++;
    }
}

// View - Student View
class StudentView {
    public void displayStudent(Student student) {
        System.out.println("\n--- Student Information ---");
        System.out.println("ID: " + student.getStudentId());
        System.out.println("Name: " + student.getName());
        System.out.println("Email: " + student.getEmail());
        System.out.println("GPA: " + String.format("%.2f", student.getGpa()));
        System.out.println("Major: " + student.getMajor());
    }
    
    public void displayAllStudents(List<Student> students) {
        System.out.println("\n--- All Students ---");
        System.out.println(String.format("%-5s %-20s %-25s %-6s %-15s", 
                "ID", "Name", "Email", "GPA", "Major"));
        System.out.println("-------------------------------------------------------------------");
        
        for (Student student : students) {
            System.out.println(String.format("%-5d %-20s %-25s %-6.2f %-15s",
                    student.getStudentId(),
                    student.getName(),
                    student.getEmail(),
                    student.getGpa(),
                    student.getMajor()));
        }
    }
    
    public void displayMessage(String message) {
        System.out.println(message);
    }
    
    public void displayError(String error) {
        System.out.println("ERROR: " + error);
    }
}

// Controller - StudentController
class StudentController {
    private StudentRepository repository;
    private StudentView view;
    
    public StudentController(StudentRepository repository, StudentView view) {
        this.repository = repository;
        this.view = view;
    }
    
    public void createStudent(String name, String email, double gpa, String major) {
        int id = repository.getNextId();
        Student student = new Student(id, name, email, gpa, major);
        repository.addStudent(student);
        view.displayMessage("Student created successfully with ID: " + id);
    }
    
    public void displayStudent(int id) {
        Student student = repository.getStudentById(id);
        if (student != null) {
            view.displayStudent(student);
        } else {
            view.displayError("Student not found");
        }
    }
    
    public void displayAllStudents() {
        List<Student> students = repository.getAllStudents();
        view.displayAllStudents(students);
    }
    
    public void updateStudentGPA(int id, double newGpa) {
        Student student = repository.getStudentById(id);
        if (student != null) {
            student.setGpa(newGpa);
            repository.updateStudent(student);
            view.displayMessage("Student GPA updated to: " + newGpa);
        } else {
            view.displayError("Student not found");
        }
    }
    
    public void updateStudentEmail(int id, String newEmail) {
        Student student = repository.getStudentById(id);
        if (student != null) {
            student.setEmail(newEmail);
            repository.updateStudent(student);
            view.displayMessage("Student email updated to: " + newEmail);
        } else {
            view.displayError("Student not found");
        }
    }
    
    public void removeStudent(int id) {
        Student student = repository.getStudentById(id);
        if (student != null) {
            repository.deleteStudent(id);
            view.displayMessage("Student removed successfully");
        } else {
            view.displayError("Student not found");
        }
    }
    
    public void findStudentByEmail(String email) {
        Student student = repository.findByEmail(email);
        if (student != null) {
            view.displayStudent(student);
        } else {
            view.displayError("Student with email " + email + " not found");
        }
    }
}

// Test the MVC Implementation
public class Exercise10_MVC {
    public static void main(String[] args) {
        // Create MVC components
        StudentRepository repository = new StudentRepository();
        StudentView view = new StudentView();
        StudentController controller = new StudentController(repository, view);
        
        // Test creating students
        System.out.println("=== Creating Students ===");
        controller.createStudent("Alice Johnson", "alice@university.edu", 3.85, "Computer Science");
        controller.createStudent("Bob Smith", "bob@university.edu", 3.65, "Engineering");
        controller.createStudent("Carol White", "carol@university.edu", 3.92, "Mathematics");
        controller.createStudent("David Brown", "david@university.edu", 3.45, "Physics");
        
        // Display all students
        System.out.println("\n=== All Students ===");
        controller.displayAllStudents();
        
        // Display specific student
        System.out.println("\n=== Displaying Student with ID 1 ===");
        controller.displayStudent(1);
        
        // Update student GPA
        System.out.println("\n=== Updating Student GPA ===");
        controller.updateStudentGPA(1, 3.90);
        
        // Update student email
        System.out.println("\n=== Updating Student Email ===");
        controller.updateStudentEmail(2, "bob.smith@university.edu");
        
        // Find student by email
        System.out.println("\n=== Finding Student by Email ===");
        controller.findStudentByEmail("carol@university.edu");
        
        // Display all students after updates
        System.out.println("\n=== All Students After Updates ===");
        controller.displayAllStudents();
        
        // Remove a student
        System.out.println("\n=== Removing Student ===");
        controller.removeStudent(4);
        
        // Display all students after deletion
        System.out.println("\n=== Final Student List ===");
        controller.displayAllStudents();
    }
}
