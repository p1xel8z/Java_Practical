
public class Student {

    @Column(name = "StudentName")
    String name;

    @Column(name = "StudentAge")
    int age;

    @Column(name = "StudentEmail")
    String email;

    public void display() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Email: " + email);
    }
}
