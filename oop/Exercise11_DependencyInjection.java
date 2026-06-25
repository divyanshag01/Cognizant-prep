/**
 * Exercise 11: Implementing Dependency Injection
 * 
 * Scenario: Customer management application where the service class 
 * depends on a repository class using Dependency Injection.
 */

import java.util.*;

// Model - Customer Class
class Customer {
    private int customerId;
    private String name;
    private String email;
    private String phone;
    private String address;
    
    public Customer(int customerId, String name, String email, String phone, String address) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
    
    public int getCustomerId() {
        return customerId;
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
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

// Repository Interface - Define contract
interface CustomerRepository {
    void save(Customer customer);
    Customer findById(int id);
    List<Customer> findAll();
    void update(Customer customer);
    void delete(int id);
}

// Concrete Repository Implementation - In-Memory
class InMemoryCustomerRepository implements CustomerRepository {
    private Map<Integer, Customer> customers;
    
    public InMemoryCustomerRepository() {
        this.customers = new HashMap<>();
    }
    
    @Override
    public void save(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
        System.out.println("[InMemory] Customer saved: " + customer.getName());
    }
    
    @Override
    public Customer findById(int id) {
        System.out.println("[InMemory] Searching for customer with ID: " + id);
        return customers.get(id);
    }
    
    @Override
    public List<Customer> findAll() {
        System.out.println("[InMemory] Retrieving all customers");
        return new ArrayList<>(customers.values());
    }
    
    @Override
    public void update(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
        System.out.println("[InMemory] Customer updated: " + customer.getName());
    }
    
    @Override
    public void delete(int id) {
        customers.remove(id);
        System.out.println("[InMemory] Customer deleted with ID: " + id);
    }
}

// Alternative Repository Implementation - File-based
class FileBasedCustomerRepository implements CustomerRepository {
    private Map<Integer, Customer> customers;
    
    public FileBasedCustomerRepository() {
        this.customers = new HashMap<>();
    }
    
    @Override
    public void save(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
        System.out.println("[FileBase] Saving customer to file: " + customer.getName());
    }
    
    @Override
    public Customer findById(int id) {
        System.out.println("[FileBase] Reading customer from file with ID: " + id);
        return customers.get(id);
    }
    
    @Override
    public List<Customer> findAll() {
        System.out.println("[FileBase] Reading all customers from file");
        return new ArrayList<>(customers.values());
    }
    
    @Override
    public void update(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
        System.out.println("[FileBase] Updating customer in file: " + customer.getName());
    }
    
    @Override
    public void delete(int id) {
        customers.remove(id);
        System.out.println("[FileBase] Deleting customer from file with ID: " + id);
    }
}

// Database Repository Implementation
class DatabaseCustomerRepository implements CustomerRepository {
    private Map<Integer, Customer> customers;
    
    public DatabaseCustomerRepository() {
        this.customers = new HashMap<>();
    }
    
    @Override
    public void save(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
        System.out.println("[Database] Executing INSERT query for: " + customer.getName());
    }
    
    @Override
    public Customer findById(int id) {
        System.out.println("[Database] Executing SELECT query for ID: " + id);
        return customers.get(id);
    }
    
    @Override
    public List<Customer> findAll() {
        System.out.println("[Database] Executing SELECT * query");
        return new ArrayList<>(customers.values());
    }
    
    @Override
    public void update(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
        System.out.println("[Database] Executing UPDATE query for: " + customer.getName());
    }
    
    @Override
    public void delete(int id) {
        customers.remove(id);
        System.out.println("[Database] Executing DELETE query for ID: " + id);
    }
}

// Service Class - Depends on Repository Interface (Dependency Injection)
class CustomerService {
    private CustomerRepository repository;
    private int nextCustomerId;
    
    // Constructor Injection - Dependencies are passed through constructor
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
        this.nextCustomerId = 1;
    }
    
    public void createCustomer(String name, String email, String phone, String address) {
        Customer customer = new Customer(nextCustomerId++, name, email, phone, address);
        repository.save(customer);
        System.out.println("Service: Customer created successfully");
    }
    
    public Customer getCustomer(int id) {
        Customer customer = repository.findById(id);
        if (customer != null) {
            System.out.println("Service: Found customer - " + customer.getName());
            return customer;
        } else {
            System.out.println("Service: Customer not found");
            return null;
        }
    }
    
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }
    
    public void updateCustomer(int id, String name, String email, String phone, String address) {
        Customer customer = repository.findById(id);
        if (customer != null) {
            customer.setName(name);
            customer.setEmail(email);
            customer.setPhone(phone);
            customer.setAddress(address);
            repository.update(customer);
            System.out.println("Service: Customer updated successfully");
        } else {
            System.out.println("Service: Customer not found");
        }
    }
    
    public void deleteCustomer(int id) {
        repository.delete(id);
        System.out.println("Service: Customer deleted successfully");
    }
    
    public void displayAllCustomers() {
        List<Customer> customers = getAllCustomers();
        System.out.println("\nAll Customers:");
        System.out.println(String.format("%-5s %-20s %-25s %-15s %-30s",
                "ID", "Name", "Email", "Phone", "Address"));
        System.out.println("---------------------------------------------------------------------------");
        for (Customer customer : customers) {
            System.out.println(String.format("%-5d %-20s %-25s %-15s %-30s",
                    customer.getCustomerId(),
                    customer.getName(),
                    customer.getEmail(),
                    customer.getPhone(),
                    customer.getAddress()));
        }
    }
}

// Dependency Injection Container
class DIContainer {
    public static CustomerService createCustomerServiceWithInMemoryRepository() {
        CustomerRepository repository = new InMemoryCustomerRepository();
        return new CustomerService(repository);
    }
    
    public static CustomerService createCustomerServiceWithFileRepository() {
        CustomerRepository repository = new FileBasedCustomerRepository();
        return new CustomerService(repository);
    }
    
    public static CustomerService createCustomerServiceWithDatabaseRepository() {
        CustomerRepository repository = new DatabaseCustomerRepository();
        return new CustomerService(repository);
    }
}

// Test the Dependency Injection Implementation
public class Exercise11_DependencyInjection {
    public static void main(String[] args) {
        // Test 1: Using In-Memory Repository
        System.out.println("=== Using In-Memory Repository ===");
        CustomerService serviceInMemory = DIContainer.createCustomerServiceWithInMemoryRepository();
        
        serviceInMemory.createCustomer("Alice Johnson", "alice@example.com", "555-0101", "123 Main St");
        serviceInMemory.createCustomer("Bob Smith", "bob@example.com", "555-0102", "456 Oak Ave");
        serviceInMemory.createCustomer("Carol White", "carol@example.com", "555-0103", "789 Pine Rd");
        
        serviceInMemory.displayAllCustomers();
        
        serviceInMemory.updateCustomer(1, "Alice Johnson", "alice.updated@example.com", "555-0101", "123 Main St");
        
        // Test 2: Using File-Based Repository
        System.out.println("\n=== Using File-Based Repository ===");
        CustomerService serviceFile = DIContainer.createCustomerServiceWithFileRepository();
        
        serviceFile.createCustomer("David Brown", "david@example.com", "555-0201", "321 Elm St");
        serviceFile.createCustomer("Eve Davis", "eve@example.com", "555-0202", "654 Maple Dr");
        
        serviceFile.displayAllCustomers();
        
        // Test 3: Using Database Repository
        System.out.println("\n=== Using Database Repository ===");
        CustomerService serviceDatabase = DIContainer.createCustomerServiceWithDatabaseRepository();
        
        serviceDatabase.createCustomer("Frank Miller", "frank@example.com", "555-0301", "987 Cedar Ln");
        serviceDatabase.createCustomer("Grace Lee", "grace@example.com", "555-0302", "147 Birch Way");
        
        serviceDatabase.displayAllCustomers();
        
        // Test 4: Switching repositories at runtime
        System.out.println("\n=== Switching Repositories at Runtime ===");
        System.out.println("Currently using In-Memory Repository:");
        serviceInMemory.displayAllCustomers();
        
        System.out.println("\nSwitching to Database Repository:");
        serviceDatabase.displayAllCustomers();
        
        // Test 5: Demonstrating loose coupling
        System.out.println("\n=== Demonstrating Loose Coupling ===");
        System.out.println("The service doesn't care which repository implementation is used");
        System.out.println("We can easily swap repositories without changing service code");
    }
}
