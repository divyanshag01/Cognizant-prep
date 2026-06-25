package Week1.DSA;

class Employee {
    private String employeeId;
    private String name;
    private String position;
    private double salary;

    public Employee(String employeeId, String name, String position, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    public String getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public String getPosition() { return position; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", salary=$" + salary +
                '}';
    }
}

public class EmployeeManagementSystem {
    private Employee[] employees;
    private int size;
    private int capacity;

    public EmployeeManagementSystem(int capacity) {
        this.capacity = capacity;
        this.employees = new Employee[capacity];
        this.size = 0;
    }

    // 1. Add Employee
    public boolean addEmployee(Employee employee) {
        if (size >= capacity) {
            System.out.println("Error: Cannot add employee. System is at full capacity (" + capacity + ").");
            return false;
        }
        employees[size] = employee;
        size++;
        System.out.println("Added Employee: " + employee.getName());
        return true;
    }

    // 2. Search Employee by ID
    public Employee searchEmployee(String employeeId) {
        for (int i = 0; i < size; i++) {
            if (employees[i].getEmployeeId().equals(employeeId)) {
                return employees[i];
            }
        }
        return null;
    }

    // 3. Traverse Employees
    public void traverseEmployees() {
        if (size == 0) {
            System.out.println("No employees in the system.");
            return;
        }
        System.out.println("--- Employee Directory (" + size + "/" + capacity + ") ---");
        for (int i = 0; i < size; i++) {
            System.out.println("  " + employees[i]);
        }
        System.out.println("----------------------------------------");
    }

    // 4. Delete Employee by ID
    public boolean deleteEmployee(String employeeId) {
        int targetIndex = -1;
        for (int i = 0; i < size; i++) {
            if (employees[i].getEmployeeId().equals(employeeId)) {
                targetIndex = i;
                break;
            }
        }

        if (targetIndex == -1) {
            System.out.println("Error: Employee with ID " + employeeId + " not found.");
            return false;
        }

        String removedName = employees[targetIndex].getName();
        // Shift elements to the left to fill the gap
        for (int i = targetIndex; i < size - 1; i++) {
            employees[i] = employees[i + 1];
        }
        employees[size - 1] = null; // Clear last reference
        size--;

        System.out.println("Deleted Employee: " + removedName + " (ID: " + employeeId + ")");
        return true;
    }

    public static void main(String[] args) {
        EmployeeManagementSystem ems = new EmployeeManagementSystem(5);

        System.out.println("=== Employee Management System Demo ===");
        
        // 1. Add Employees
        ems.addEmployee(new Employee("E001", "Alice Smith", "Software Engineer", 85000));
        ems.addEmployee(new Employee("E002", "Bob Jones", "Product Manager", 95000));
        ems.addEmployee(new Employee("E003", "Charlie Brown", "QA Engineer", 70000));
        ems.addEmployee(new Employee("E004", "Diana Prince", "UX Designer", 80000));
        ems.traverseEmployees();

        // 2. Search Employee
        System.out.println("\nSearching for Employee E003...");
        Employee found = ems.searchEmployee("E003");
        System.out.println("Found: " + found);

        // 3. Delete Employee
        System.out.println("\nDeleting Employee E002...");
        ems.deleteEmployee("E002");
        ems.traverseEmployees();

        // 4. Test capacity limit
        System.out.println("\nAdding more employees to test capacity limit...");
        ems.addEmployee(new Employee("E005", "Evan Wright", "HR Specialist", 65000));
        ems.addEmployee(new Employee("E006", "Fiona Gallagher", "Data Scientist", 105000));
        ems.addEmployee(new Employee("E007", "George Costanza", "Architect", 120000)); // Should fail
        ems.traverseEmployees();

        // Analysis
        System.out.println("\n=== Complexity Analysis of Operations ===");
        System.out.println("1. Add Employee:");
        System.out.println("   - Time Complexity: O(1). Inserting at the end of the array using a size tracker takes constant time.");
        System.out.println("   - Space Complexity: O(1) auxiliary space.");
        System.out.println("2. Search Employee:");
        System.out.println("   - Time Complexity: O(N) worst/average case. Must scan array linearly to find matching ID.");
        System.out.println("   - Space Complexity: O(1) auxiliary space.");
        System.out.println("3. Traverse Employees:");
        System.out.println("   - Time Complexity: O(N). Prints every element in the active range.");
        System.out.println("   - Space Complexity: O(1) auxiliary space.");
        System.out.println("4. Delete Employee:");
        System.out.println("   - Time Complexity: O(N). Searching takes O(N), and shifting up to N elements takes O(N).");
        System.out.println("   - Space Complexity: O(1) auxiliary space.");

        System.out.println("\n=== Limitations of Arrays for Employee Management ===");
        System.out.println("1. Fixed Size: Once created, the size of an array cannot be dynamically changed. If the company grows beyond the initial capacity, we must create a new larger array and copy all records, which is costly.");
        System.out.println("2. Costly Deletions/Insertions: Deleting an element requires shifting subsequent elements to maintain contiguous memory, which takes linear O(N) time.");
        System.out.println("3. Continuous Memory Allocation: Arrays require contiguous memory blocks, which might be hard to allocate for extremely large data sizes.");
    }
}
