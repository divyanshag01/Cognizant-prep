/**
 * Exercise 4: Implementing the Adapter Pattern
 * 
 * Scenario: Payment processing system that integrates with multiple 
 * third-party payment gateways with different interfaces using Adapter Pattern.
 */

// Target Interface - Standard payment gateway interface
interface PaymentGateway {
    boolean processPayment(double amount);
    boolean refundPayment(double amount);
    String getTransactionStatus(String transactionId);
}

// Adaptee Classes - Third-party payment systems with different interfaces
class StripePaymentSystem {
    public boolean chargeCard(double amount) {
        System.out.println("Charging $" + amount + " using Stripe");
        return true;
    }
    
    public boolean reverseCharge(double amount) {
        System.out.println("Reversing $" + amount + " charge on Stripe");
        return true;
    }
    
    public String checkStatus(String txnId) {
        return "Completed";
    }
}

class PayPalPaymentSystem {
    public void sendPayment(double amount) {
        System.out.println("Sending $" + amount + " via PayPal");
    }
    
    public void cancelPayment(double amount) {
        System.out.println("Canceling $" + amount + " payment on PayPal");
    }
    
    public String getPaymentStatus(String paymentId) {
        return "Processed";
    }
}

class SquarePaymentSystem {
    public void capturePayment(double amount) {
        System.out.println("Capturing $" + amount + " using Square");
    }
    
    public void voidPayment(double amount) {
        System.out.println("Voiding $" + amount + " payment on Square");
    }
    
    public String retrieveStatus(String transactionId) {
        return "Success";
    }
}

// Adapter Classes
class StripeAdapter implements PaymentGateway {
    private StripePaymentSystem stripe;
    
    public StripeAdapter() {
        this.stripe = new StripePaymentSystem();
    }
    
    @Override
    public boolean processPayment(double amount) {
        return stripe.chargeCard(amount);
    }
    
    @Override
    public boolean refundPayment(double amount) {
        return stripe.reverseCharge(amount);
    }
    
    @Override
    public String getTransactionStatus(String transactionId) {
        return stripe.checkStatus(transactionId);
    }
}

class PayPalAdapter implements PaymentGateway {
    private PayPalPaymentSystem paypal;
    
    public PayPalAdapter() {
        this.paypal = new PayPalPaymentSystem();
    }
    
    @Override
    public boolean processPayment(double amount) {
        paypal.sendPayment(amount);
        return true;
    }
    
    @Override
    public boolean refundPayment(double amount) {
        paypal.cancelPayment(amount);
        return true;
    }
    
    @Override
    public String getTransactionStatus(String transactionId) {
        return paypal.getPaymentStatus(transactionId);
    }
}

class SquareAdapter implements PaymentGateway {
    private SquarePaymentSystem square;
    
    public SquareAdapter() {
        this.square = new SquarePaymentSystem();
    }
    
    @Override
    public boolean processPayment(double amount) {
        square.capturePayment(amount);
        return true;
    }
    
    @Override
    public boolean refundPayment(double amount) {
        square.voidPayment(amount);
        return true;
    }
    
    @Override
    public String getTransactionStatus(String transactionId) {
        return square.retrieveStatus(transactionId);
    }
}

// Test the Adapter Implementation
public class Exercise4_Adapter {
    public static void main(String[] args) {
        // Create adapters for different payment gateways
        PaymentGateway stripeGateway = new StripeAdapter();
        PaymentGateway paypalGateway = new PayPalAdapter();
        PaymentGateway squareGateway = new SquareAdapter();
        
        double paymentAmount = 100.00;
        
        // Process payment using Stripe
        System.out.println("=== Stripe Payment ===");
        stripeGateway.processPayment(paymentAmount);
        System.out.println("Status: " + stripeGateway.getTransactionStatus("TXN123"));
        stripeGateway.refundPayment(paymentAmount);
        
        // Process payment using PayPal
        System.out.println("\n=== PayPal Payment ===");
        paypalGateway.processPayment(paymentAmount);
        System.out.println("Status: " + paypalGateway.getTransactionStatus("PP456"));
        paypalGateway.refundPayment(paymentAmount);
        
        // Process payment using Square
        System.out.println("\n=== Square Payment ===");
        squareGateway.processPayment(paymentAmount);
        System.out.println("Status: " + squareGateway.getTransactionStatus("SQ789"));
        squareGateway.refundPayment(paymentAmount);
    }
}
