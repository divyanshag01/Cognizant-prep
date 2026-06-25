/**
 * Exercise 3: Implementing the Builder Pattern
 * 
 * Scenario: Creating complex objects such as a Computer with multiple 
 * optional parts using the Builder Pattern.
 */

// Product Class - Computer
class Computer {
    private String cpu;
    private String ram;
    private String storage;
    private String gpu;
    private String powerSupply;
    private String motherboard;
    
    public Computer(ComputerBuilder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.gpu = builder.gpu;
        this.powerSupply = builder.powerSupply;
        this.motherboard = builder.motherboard;
    }
    
    @Override
    public String toString() {
        return "Computer{" +
                "cpu='" + cpu + '\'' +
                ", ram='" + ram + '\'' +
                ", storage='" + storage + '\'' +
                ", gpu='" + gpu + '\'' +
                ", powerSupply='" + powerSupply + '\'' +
                ", motherboard='" + motherboard + '\'' +
                '}';
    }
    
    // Static Builder Class
    public static class ComputerBuilder {
        private String cpu;
        private String ram;
        private String storage;
        private String gpu;
        private String powerSupply;
        private String motherboard;
        
        // Optional component setters
        public ComputerBuilder setCPU(String cpu) {
            this.cpu = cpu;
            return this;
        }
        
        public ComputerBuilder setRAM(String ram) {
            this.ram = ram;
            return this;
        }
        
        public ComputerBuilder setStorage(String storage) {
            this.storage = storage;
            return this;
        }
        
        public ComputerBuilder setGPU(String gpu) {
            this.gpu = gpu;
            return this;
        }
        
        public ComputerBuilder setPowerSupply(String powerSupply) {
            this.powerSupply = powerSupply;
            return this;
        }
        
        public ComputerBuilder setMotherboard(String motherboard) {
            this.motherboard = motherboard;
            return this;
        }
        
        // Build method to create Computer instance
        public Computer build() {
            return new Computer(this);
        }
    }
}

// Test the Builder Implementation
public class Exercise3_Builder {
    public static void main(String[] args) {
        // Build a basic computer
        Computer basicComputer = new Computer.ComputerBuilder()
                .setCPU("Intel i5")
                .setRAM("8GB")
                .setStorage("256GB SSD")
                .build();
        
        System.out.println("Basic Computer:");
        System.out.println(basicComputer);
        
        // Build a gaming computer with all components
        Computer gamingComputer = new Computer.ComputerBuilder()
                .setCPU("Intel i9")
                .setRAM("32GB")
                .setStorage("1TB SSD")
                .setGPU("NVIDIA RTX 4090")
                .setPowerSupply("1000W")
                .setMotherboard("ASUS ROG Z790")
                .build();
        
        System.out.println("\nGaming Computer:");
        System.out.println(gamingComputer);
        
        // Build a workstation computer
        Computer workstationComputer = new Computer.ComputerBuilder()
                .setCPU("AMD Ryzen 9")
                .setRAM("64GB")
                .setStorage("2TB SSD")
                .setGPU("NVIDIA RTX 6000")
                .setPowerSupply("1500W")
                .setMotherboard("ASUS ProArt")
                .build();
        
        System.out.println("\nWorkstation Computer:");
        System.out.println(workstationComputer);
    }
}
