package studentmanagement;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class StudentManagementSystem {
    private static final String FILE_NAME = "students.dat";
    private Map<String, Student> students = new HashMap<>();

    public StudentManagementSystem() {
        loadStudents();
    }

    public void addStudent(String id, String name, int age) {
        if (!students.containsKey(id)) {
            students.put(id, new Student(id, name, age));
            saveStudents();
            System.out.println("Student added successfully.");
        } else {
            System.out.println("Student with this ID already exists.");
        }
    }

    public void updateStudent(String id, String name, int age) {
        Student student = students.get(id);
        if (student != null) {
            student.setName(name);
            student.setAge(age);
            saveStudents();
            System.out.println("Student updated successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    public void deleteStudent(String id) {
        if (students.remove(id) != null) {
            saveStudents();
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    public void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No students in the system.");
        } else {
            for (Student student : students.values()) {
                System.out.println(student);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void loadStudents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (Map<String, Student>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File not found, no problem, it will be created on save
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveStudents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        sms.displayMenu();
    }

    public void displayMenu() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("\nStudent Management System");
                System.out.println("1. Add Student");
                System.out.println("2. Update Student");
                System.out.println("3. Delete Student");
                System.out.println("4. Display Students");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(reader.readLine());

                switch (choice) {
                    case 1:
                        System.out.print("Enter Student ID: ");
                        String id = reader.readLine();
                        System.out.print("Enter Student Name: ");
                        String name = reader.readLine();
                        System.out.print("Enter Student Age: ");
                        int age = Integer.parseInt(reader.readLine());
                        addStudent(id, name, age);
                        break;
                    case 2:
                        System.out.print("Enter Student ID to Update: ");
                        id = reader.readLine();
                        System.out.print("Enter New Student Name: ");
                        name = reader.readLine();
                        System.out.print("Enter New Student Age: ");
                        age = Integer.parseInt(reader.readLine());
                        updateStudent(id, name, age);
                        break;
                    case 3:
                        System.out.print("Enter Student ID to Delete: ");
                        id = reader.readLine();
                        deleteStudent(id);
                        break;
                    case 4:
                        displayStudents();
                        break;
                    case 5:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
