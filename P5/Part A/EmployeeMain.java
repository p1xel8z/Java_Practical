abstract class Employee{
    String name;
    String id;
    Employee(String name,String id)
    {
        this.name = name;
        this.id = id;
    }
    abstract double monthlySalary();
}

class FullTime extends Employee {

    double fixed;

    FullTime(String name,String id) {
        super(name, id);
        this.fixed = 10000;
    }

    FullTime(String name,String id,double fixed) {
        super(name, id);
        this.fixed = fixed;
    }

    double monthlySalary()
    {
        return fixed;
    }
}

class PartTime extends Employee {

    double hours;
    double rate;

    
    PartTime(String name,String id) {
        super(name, id);
        this.hours = 8;
        this.rate = 15;
    }

    PartTime(String name,String id,double hours,double rate) {
        super(name, id);
        this.hours = hours;
        this.rate = rate;
    }

    double monthlySalary()
    {
        return hours*rate;
    }
}

class Intern extends Employee {

    double stipend;

    Intern(String name , String id) {
        super(name, id);
        this.stipend = 10000;
    }

    Intern(String name,String id,double stipend) {
        super(name, id);
        this.stipend = stipend;
    }

    double monthlySalary()
    {
        return stipend;
    }
}

class EmployeeMain {

    public static void main(String[] args) {

        Employee[] employees = new Employee[5];

        employees[0] = new FullTime("John", "F001", 50000);
        employees[1] = new PartTime("Alice", "P001", 80, 200);
        employees[2] = new Intern("Bob", "I001", 15000);
        employees[3] = new FullTime("David", "F002", 60000);
        employees[4] = new Intern("Emma", "I002", 12000);

        double total = 0;

        for (int i = 0; i < employees.length; i++) {

            double salary = employees[i].monthlySalary();

            System.out.println(
                employees[i].name + " salary: " + salary
            );

            if (employees[i] instanceof Intern) {
                System.out.println("Note: This employee is an intern.");
            }

            total += salary;
        }

        System.out.println("Total Salary: " + total);
    }
}
