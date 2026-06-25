/**
 * Exercise 9: Implementing the Command Pattern
 * 
 * Scenario: Home automation system where commands can be issued 
 * to turn devices on or off.
 */

import java.util.*;

// Command Interface
interface Command {
    void execute();
    void undo();
    String getDescription();
}

// Receiver Classes
class Light {
    private String location;
    private boolean isOn;
    
    public Light(String location) {
        this.location = location;
        this.isOn = false;
    }
    
    public void on() {
        isOn = true;
        System.out.println(location + " light is now ON");
    }
    
    public void off() {
        isOn = false;
        System.out.println(location + " light is now OFF");
    }
    
    public boolean isOn() {
        return isOn;
    }
}

class Fan {
    private String location;
    private int speed; // 0 = off, 1 = low, 2 = medium, 3 = high
    
    public Fan(String location) {
        this.location = location;
        this.speed = 0;
    }
    
    public void on(int speed) {
        this.speed = Math.min(speed, 3);
        System.out.println(location + " fan is ON at speed " + this.speed);
    }
    
    public void off() {
        speed = 0;
        System.out.println(location + " fan is OFF");
    }
    
    public int getSpeed() {
        return speed;
    }
}

class Thermostat {
    private String location;
    private double temperature;
    
    public Thermostat(String location) {
        this.location = location;
        this.temperature = 70.0;
    }
    
    public void setTemperature(double temperature) {
        this.temperature = temperature;
        System.out.println(location + " thermostat set to " + temperature + "°F");
    }
    
    public double getTemperature() {
        return temperature;
    }
}

// Concrete Command Classes for Light
class LightOnCommand implements Command {
    private Light light;
    
    public LightOnCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.on();
    }
    
    @Override
    public void undo() {
        light.off();
    }
    
    @Override
    public String getDescription() {
        return "Turn light on";
    }
}

class LightOffCommand implements Command {
    private Light light;
    
    public LightOffCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.off();
    }
    
    @Override
    public void undo() {
        light.on();
    }
    
    @Override
    public String getDescription() {
        return "Turn light off";
    }
}

// Concrete Command Classes for Fan
class FanOnCommand implements Command {
    private Fan fan;
    private int speed;
    
    public FanOnCommand(Fan fan, int speed) {
        this.fan = fan;
        this.speed = speed;
    }
    
    @Override
    public void execute() {
        fan.on(speed);
    }
    
    @Override
    public void undo() {
        fan.off();
    }
    
    @Override
    public String getDescription() {
        return "Turn fan on at speed " + speed;
    }
}

class FanOffCommand implements Command {
    private Fan fan;
    private int previousSpeed;
    
    public FanOffCommand(Fan fan) {
        this.fan = fan;
        this.previousSpeed = fan.getSpeed();
    }
    
    @Override
    public void execute() {
        fan.off();
    }
    
    @Override
    public void undo() {
        fan.on(previousSpeed);
    }
    
    @Override
    public String getDescription() {
        return "Turn fan off";
    }
}

// Concrete Command for Thermostat
class SetTemperatureCommand implements Command {
    private Thermostat thermostat;
    private double newTemperature;
    private double previousTemperature;
    
    public SetTemperatureCommand(Thermostat thermostat, double temperature) {
        this.thermostat = thermostat;
        this.newTemperature = temperature;
        this.previousTemperature = thermostat.getTemperature();
    }
    
    @Override
    public void execute() {
        thermostat.setTemperature(newTemperature);
    }
    
    @Override
    public void undo() {
        thermostat.setTemperature(previousTemperature);
    }
    
    @Override
    public String getDescription() {
        return "Set temperature to " + newTemperature + "°F";
    }
}

// Macro Command - Execute multiple commands
class MacroCommand implements Command {
    private List<Command> commands;
    
    public MacroCommand() {
        this.commands = new ArrayList<>();
    }
    
    public void addCommand(Command command) {
        commands.add(command);
    }
    
    public void removeCommand(Command command) {
        commands.remove(command);
    }
    
    @Override
    public void execute() {
        System.out.println("Executing macro command...");
        for (Command command : commands) {
            command.execute();
        }
    }
    
    @Override
    public void undo() {
        System.out.println("Undoing macro command...");
        for (int i = commands.size() - 1; i >= 0; i--) {
            commands.get(i).undo();
        }
    }
    
    @Override
    public String getDescription() {
        return "Macro command with " + commands.size() + " commands";
    }
}

// Invoker Class - Remote Control
class RemoteControl {
    private Command lastCommand;
    private Stack<Command> history;
    
    public RemoteControl() {
        this.history = new Stack<>();
    }
    
    public void executeCommand(Command command) {
        System.out.println("Executing: " + command.getDescription());
        command.execute();
        lastCommand = command;
        history.push(command);
    }
    
    public void undo() {
        if (!history.isEmpty()) {
            Command command = history.pop();
            System.out.println("Undoing: " + command.getDescription());
            command.undo();
        } else {
            System.out.println("No command to undo");
        }
    }
    
    public void showHistory() {
        System.out.println("\nCommand History:");
        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + ". " + history.get(i).getDescription());
        }
    }
}

// Test the Command Implementation
public class Exercise9_Command {
    public static void main(String[] args) {
        // Create devices
        Light livingRoomLight = new Light("Living Room");
        Light bedroomLight = new Light("Bedroom");
        Fan livingRoomFan = new Fan("Living Room");
        Thermostat thermostat = new Thermostat("Main Floor");
        
        // Create remote control (invoker)
        RemoteControl remote = new RemoteControl();
        
        // Test individual commands
        System.out.println("=== Individual Commands ===");
        remote.executeCommand(new LightOnCommand(livingRoomLight));
        remote.executeCommand(new FanOnCommand(livingRoomFan, 2));
        remote.executeCommand(new SetTemperatureCommand(thermostat, 72.0));
        
        // Test undo
        System.out.println("\n=== Undo Commands ===");
        remote.undo();
        remote.undo();
        remote.undo();
        
        // Test macro command
        System.out.println("\n=== Macro Command (Good Morning) ===");
        MacroCommand morningRoutine = new MacroCommand();
        morningRoutine.addCommand(new LightOnCommand(livingRoomLight));
        morningRoutine.addCommand(new LightOnCommand(bedroomLight));
        morningRoutine.addCommand(new FanOnCommand(livingRoomFan, 1));
        morningRoutine.addCommand(new SetTemperatureCommand(thermostat, 72.0));
        
        remote.executeCommand(morningRoutine);
        
        // Test undo macro
        System.out.println("\n=== Undo Macro Command ===");
        remote.undo();
        
        // Show command history
        remote.showHistory();
    }
}
