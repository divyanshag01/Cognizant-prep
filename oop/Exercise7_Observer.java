/**
 * Exercise 7: Implementing the Observer Pattern
 * 
 * Scenario: Stock market monitoring application where multiple clients 
 * need to be notified whenever stock prices change.
 */

import java.util.*;

// Observer Interface
interface StockObserver {
    void update(String stockSymbol, double oldPrice, double newPrice);
}

// Subject Interface
interface Subject {
    void attach(StockObserver observer);
    void detach(StockObserver observer);
    void notifyObservers();
}

// Concrete Subject - Stock
class Stock implements Subject {
    private String symbol;
    private double price;
    private List<StockObserver> observers;
    
    public Stock(String symbol, double initialPrice) {
        this.symbol = symbol;
        this.price = initialPrice;
        this.observers = new ArrayList<>();
    }
    
    @Override
    public void attach(StockObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println(observer + " attached to stock " + symbol);
        }
    }
    
    @Override
    public void detach(StockObserver observer) {
        if (observers.remove(observer)) {
            System.out.println(observer + " detached from stock " + symbol);
        }
    }
    
    @Override
    public void notifyObservers() {
        for (StockObserver observer : observers) {
            observer.update(symbol, price, price);
        }
    }
    
    public void setPrice(double newPrice) {
        double oldPrice = this.price;
        this.price = newPrice;
        System.out.println("Stock " + symbol + " price changed from $" + oldPrice + " to $" + newPrice);
        notifyObservers();
    }
    
    public double getPrice() {
        return price;
    }
    
    public String getSymbol() {
        return symbol;
    }
}

// Concrete Observer - Investor
class Investor implements StockObserver {
    private String name;
    
    public Investor(String name) {
        this.name = name;
    }
    
    @Override
    public void update(String stockSymbol, double oldPrice, double newPrice) {
        System.out.println("Notification to " + name + ": Stock " + stockSymbol + 
                " price changed from $" + oldPrice + " to $" + newPrice);
    }
    
    @Override
    public String toString() {
        return "Investor{" + name + "}";
    }
}

// Concrete Observer - Portfolio Manager
class PortfolioManager implements StockObserver {
    private String name;
    private Map<String, Integer> portfolio;
    
    public PortfolioManager(String name) {
        this.name = name;
        this.portfolio = new HashMap<>();
    }
    
    public void addStock(String stockSymbol, int quantity) {
        portfolio.put(stockSymbol, quantity);
    }
    
    @Override
    public void update(String stockSymbol, double oldPrice, double newPrice) {
        if (portfolio.containsKey(stockSymbol)) {
            int quantity = portfolio.get(stockSymbol);
            double gainLoss = (newPrice - oldPrice) * quantity;
            System.out.println("Alert to " + name + ": " + stockSymbol + 
                    " price changed. Gain/Loss: $" + gainLoss);
        }
    }
    
    @Override
    public String toString() {
        return "PortfolioManager{" + name + "}";
    }
}

// Concrete Observer - Trading Bot
class TradingBot implements StockObserver {
    private String botId;
    private double threshold;
    
    public TradingBot(String botId, double threshold) {
        this.botId = botId;
        this.threshold = threshold;
    }
    
    @Override
    public void update(String stockSymbol, double oldPrice, double newPrice) {
        double percentChange = ((newPrice - oldPrice) / oldPrice) * 100;
        if (Math.abs(percentChange) > threshold) {
            System.out.println("Bot " + botId + " triggered for " + stockSymbol + 
                    " - Price change: " + String.format("%.2f", percentChange) + "%");
        }
    }
    
    @Override
    public String toString() {
        return "TradingBot{" + botId + "}";
    }
}

// Test the Observer Implementation
public class Exercise7_Observer {
    public static void main(String[] args) {
        // Create stocks
        Stock appleStock = new Stock("AAPL", 150.00);
        Stock googleStock = new Stock("GOOGL", 2800.00);
        
        // Create observers
        Investor investor1 = new Investor("John Doe");
        Investor investor2 = new Investor("Jane Smith");
        PortfolioManager portfolioManager = new PortfolioManager("Fund Manager ABC");
        TradingBot tradingBot = new TradingBot("Bot-001", 2.0);
        
        // Add portfolio holdings
        portfolioManager.addStock("AAPL", 100);
        portfolioManager.addStock("GOOGL", 50);
        
        // Attach observers to stocks
        System.out.println("=== Attaching Observers ===");
        appleStock.attach(investor1);
        appleStock.attach(investor2);
        appleStock.attach(portfolioManager);
        appleStock.attach(tradingBot);
        
        googleStock.attach(portfolioManager);
        googleStock.attach(tradingBot);
        
        // Change stock prices
        System.out.println("\n=== Updating Apple Stock Price ===");
        appleStock.setPrice(155.00);
        
        System.out.println("\n=== Updating Apple Stock Price Again ===");
        appleStock.setPrice(148.00);
        
        System.out.println("\n=== Updating Google Stock Price ===");
        googleStock.setPrice(2850.00);
        
        // Detach observer
        System.out.println("\n=== Detaching Investor2 from Apple Stock ===");
        appleStock.detach(investor2);
        
        System.out.println("\n=== Updating Apple Stock Price After Detachment ===");
        appleStock.setPrice(152.00);
    }
}
