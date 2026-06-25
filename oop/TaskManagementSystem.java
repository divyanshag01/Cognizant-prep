package Week1.DSA;

class Task {
    private String taskId;
    private String taskName;
    private String status;

    public Task(String taskId, String taskName, String status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.status = status;
    }

    public String getTaskId() { return taskId; }
    public String getTaskName() { return taskName; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return "Task{" +
                "taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

class TaskNode {
    Task task;
    TaskNode next;

    public TaskNode(Task task) {
        this.task = task;
        this.next = null;
    }
}

public class TaskManagementSystem {
    private TaskNode head;
    private TaskNode tail; // Maintain tail for O(1) additions

    public TaskManagementSystem() {
        this.head = null;
        this.tail = null;
    }

    // 1. Add Task (at the end of the list)
    public void addTask(Task task) {
        TaskNode newNode = new TaskNode(task);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        System.out.println("Added Task: " + task.getTaskName());
    }

    // 2. Search Task by ID
    public Task searchTask(String taskId) {
        TaskNode current = head;
        while (current != null) {
            if (current.task.getTaskId().equals(taskId)) {
                return current.task;
            }
            current = current.next;
        }
        return null;
    }

    // 3. Traverse Tasks
    public void traverseTasks() {
        if (head == null) {
            System.out.println("Task list is empty.");
            return;
        }
        System.out.println("--- Current Task List ---");
        TaskNode current = head;
        while (current != null) {
            System.out.println("  " + current.task);
            current = current.next;
        }
        System.out.println("-------------------------");
    }

    // 4. Delete Task by ID
    public boolean deleteTask(String taskId) {
        if (head == null) {
            System.out.println("Error: Task list is empty.");
            return false;
        }

        // Case 1: Head node needs to be deleted
        if (head.task.getTaskId().equals(taskId)) {
            String name = head.task.getTaskName();
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                head = head.next;
            }
            System.out.println("Deleted Task: " + name + " (ID: " + taskId + ")");
            return true;
        }

        // Case 2: Node is somewhere in middle or end
        TaskNode prev = head;
        TaskNode current = head.next;
        while (current != null) {
            if (current.task.getTaskId().equals(taskId)) {
                String name = current.task.getTaskName();
                prev.next = current.next;
                // If we deleted the tail, update tail pointer
                if (current == tail) {
                    tail = prev;
                }
                System.out.println("Deleted Task: " + name + " (ID: " + taskId + ")");
                return true;
            }
            prev = current;
            current = current.next;
        }

        System.out.println("Error: Task with ID " + taskId + " not found.");
        return false;
    }

    public static void main(String[] args) {
        TaskManagementSystem tms = new TaskManagementSystem();

        System.out.println("=== Task Management System Demo ===");

        // 1. Add Tasks
        tms.addTask(new Task("T001", "Design Database Schema", "Done"));
        tms.addTask(new Task("T002", "Setup Spring Boot App", "In Progress"));
        tms.addTask(new Task("T003", "Create REST APIs", "Pending"));
        tms.addTask(new Task("T004", "Integrate Security Layer", "Pending"));
        tms.traverseTasks();

        // 2. Search Task
        System.out.println("\nSearching for Task T003...");
        Task found = tms.searchTask("T003");
        System.out.println("Found: " + found);

        // 3. Delete Tasks
        System.out.println("\nDeleting Task T002 (Middle Node)...");
        tms.deleteTask("T002");
        tms.traverseTasks();

        System.out.println("\nDeleting Task T001 (Head Node)...");
        tms.deleteTask("T001");
        tms.traverseTasks();

        System.out.println("\nDeleting Task T004 (Tail Node)...");
        tms.deleteTask("T004");
        tms.traverseTasks();

        // Analysis
        System.out.println("\n=== Complexity Analysis of Singly Linked List ===");
        System.out.println("1. Add Task (at tail):");
        System.out.println("   - Time Complexity: O(1) since we maintain a tail pointer.");
        System.out.println("   - Space Complexity: O(1) auxiliary space.");
        System.out.println("2. Search Task:");
        System.out.println("   - Time Complexity: O(N) worst/average case. Must traverse list sequentially.");
        System.out.println("   - Space Complexity: O(1) auxiliary space.");
        System.out.println("3. Traverse Tasks:");
        System.out.println("   - Time Complexity: O(N). Visits each node sequentially.");
        System.out.println("   - Space Complexity: O(1) auxiliary space.");
        System.out.println("4. Delete Task:");
        System.out.println("   - Time Complexity: O(N) worst/average case. Finding the element takes O(N) traversal.");
        System.out.println("   - Space Complexity: O(1) auxiliary space.");

        System.out.println("\n=== Singly Linked List vs Array Comparison ===");
        System.out.println("1. Memory Allocation:");
        System.out.println("   - Array: Allocates a contiguous block of memory. Dynamic expansion requires copying the whole array.");
        System.out.println("   - Linked List: Allocates memory dynamically for each node. Node pointers add a small storage overhead per element.");
        System.out.println("2. Insertion/Deletion Performance:");
        System.out.println("   - Array: Shifting elements to insert or delete elements in the middle/front takes O(N) time.");
        System.out.println("   - Linked List: Once the node's position is found (or if inserting at head/tail), updating next pointers takes O(1) time without shifting elements.");
        System.out.println("3. Access and Search:");
        System.out.println("   - Array: Supports direct random access O(1) by index.");
        System.out.println("   - Linked List: Accessing by index requires traversing elements from the head node, which takes O(N) time.");
    }
}
