
import java.io.*;
import java.util.Scanner;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    String name;
    int rollNo;
    transient String temporaryNote;

    Student(String name, int rollNo, String temporaryNote) {
        this.name = name;
        this.rollNo = rollNo;
        this.temporaryNote = temporaryNote;
    }

    void display() {
        System.out.println("Name: " + name);
        System.out.println("Roll No: " + rollNo);
        System.out.println("Temporary Note: " + temporaryNote);
        System.out.println();
    }
}

public class SerializationDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Student[] students = new Student[2];

            for (int i = 0; i < students.length; i++) {
                System.out.println("Enter details for Student " + (i + 1) + ":");
                System.out.print("Name: ");
                String name = sc.nextLine();

                System.out.print("Roll number: ");
                int rollNo = Integer.parseInt(sc.nextLine());

                System.out.print("Temporary note: ");
                String note = sc.nextLine();

                students[i] = new Student(name, rollNo, note);
            }

            // Save the array to a file
            try (ObjectOutputStream out =
                         new ObjectOutputStream(new FileOutputStream("students.dat"))) {
                out.writeObject(students);
            }

            System.out.println("\nObjects saved successfully.");

            // Load the objects back from the file
            Student[] loadedStudents;
            try (ObjectInputStream in =
                         new ObjectInputStream(new FileInputStream("students.dat"))) {
                loadedStudents = (Student[]) in.readObject();
            }

            System.out.println("\nLoaded student details:");
            for (Student student : loadedStudents) {
                student.display();
            }

            System.out.println("Notice: Temporary Note is null because it is transient.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid roll number. Enter an integer.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}
