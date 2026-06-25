/**
 * Exercise 8: Implementing the Strategy Pattern
 * 
 * Scenario: Payment system where different payment methods 
 * (Credit Card, PayPal, Cryptocurrency) can be selected at runtime.
 */

// Strategy Interface
interface PaymentStrategy {
    boolean pay(double amount);
    boolean validatePaymentInfo();
    String getPaymentMethodName();
}

// Concrete Strategies
class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;
    
    public CreditCardPayment(String cardNumber, String cardHolderName, 
                            String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }
    
    @Override
    public boolean validatePaymentInfo() {
        return cardNumber != null && cardNumber.length() == 16 &&
               cardHolderName != null && !cardHolderName.isEmpty() &&
               expiryDate != null && cvv != null && cvv.length() == 3;
    }
    
    @Override
    public boolean pay(double amount) {
        if (!validatePaymentInfo()) {
            System.out.println("Invalid credit card information");
            return false;
        }
        System.out.println("Processing credit card payment of $" + amount);
        System.out.println("Card: " + cardNumber.substring(12) + " (****)" + 
                          ", Cardholder: " + cardHolderName);
        System.out.println("Payment processed successfully via Credit Card");
        return true;
    }
    
    @Override
    public String getPaymentMethodName() {
        return "Credit Card";
    }
}

class PayPalPayment implements PaymentStrategy {
    private String email;
    private String password;
    
    public PayPalPayment(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    @Override
    public boolean validatePaymentInfo() {
        return email != null && email.contains("@") &&
               password != null && password.length() >= 6;
    }
    
    @Override
    public boolean pay(double amount) {
        if (!validatePaymentInfo()) {
            System.out.println("Invalid PayPal credentials");
            return false;
        }
        System.out.println("Processing PayPal payment of $" + amount);
        System.out.println("Email: " + email);
        System.out.println("Authenticating with PayPal...");
        System.out.println("Payment processed successfully via PayPal");
        return true;
    }
    
    @Override
    public String getPaymentMethodName() {
        return "PayPal";
    }
}

class CryptoCurrencyPayment implements PaymentStrategy {
    private String walletAddress;
    private String cryptoType;
    
    public CryptoCurrencyPayment(String walletAddress, String cryptoType) {
        this.walletAddress = walletAddress;
        this.cryptoType = cryptoType;
    }
    
    @Override
    public boolean validatePaymentInfo() {
        return walletAddress != null && walletAddress.length() >= 26 &&
               cryptoType != null && (cryptoType.equals("BTC") || 
               cryptoType.equals("ETH") || cryptoType.equals("USDC"));
    }
    
    @Override
    public boolean pay(double amount) {
        if (!validatePaymentInfo()) {
            System.out.println("Invalid cryptocurrency wallet information");
            return false;
        }
        System.out.println("Processing " + cryptoType + " payment of $" + amount);
        System.out.println("Wallet Address: " + walletAddress.substring(0, 10) + "...");
        System.out.println("Confirming transaction on blockchain...");
        System.out.println("Payment processed successfully via " + cryptoType);
        return true;
    }
    
    @Override
    public String getPaymentMethodName() {
        return cryptoType + " Cryptocurrency";
    }
}

class BankTransferPayment implements PaymentStrategy {
    private String accountNumber;
    private String bankCode;
    private String accountHolderName;
    
    public BankTransferPayment(String accountNumber, String bankCode, 
                              String accountHolderName) {
        this.accountNumber = accountNumber;
        this.bankCode = bankCode;
        this.accountHolderName = accountHolderName;
    }
    
    @Override
    public boolean validatePaymentInfo() {
        return accountNumber != null && accountNumber.length() >= 10 &&
               bankCode != null && !bankCode.isEmpty() &&
               accountHolderName != null && !accountHolderName.isEmpty();
    }
    
    @Override
    public boolean pay(double amount) {
        if (!validatePaymentInfo()) {
            System.out.println("Invalid bank transfer information");
            return false;
        }
        System.out.println("Processing bank transfer of $" + amount);
        System.out.println("From account: " + accountNumber.substring(accountNumber.length() - 4));
        System.out.println("Account holder: " + accountHolderName);
        System.out.println("Processing through " + bankCode);
        System.out.println("Payment processed successfully via Bank Transfer");
        return true;
    }
    
    @Override
    public String getPaymentMethodName() {
        return "Bank Transfer";
    }
}

// Context Class
class ShoppingCart {
    private PaymentStrategy paymentStrategy;
    private double totalAmount;
    
    public ShoppingCart(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }
    
    public void checkout() {
        if (paymentStrategy == null) {
            System.out.println("No payment method selected");
            return;
        }
        System.out.println("\n--- Checkout with " + paymentStrategy.getPaymentMethodName() + " ---");
        System.out.println("Total Amount: $" + totalAmount);
        
        if (paymentStrategy.pay(totalAmount)) {
            System.out.println("Order confirmed!");
        } else {
            System.out.println("Payment failed. Order cancelled.");
        }
    }
}

// Test the Strategy Implementation
public class Exercise8_Strategy {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart(99.99);
        
        // Strategy 1: Pay with Credit Card
        System.out.println("=== Payment with Credit Card ===");
        PaymentStrategy creditCard = new CreditCardPayment(
                "1234567890123456",
                "John Doe",
                "12/25",
                "123"
        );
        cart.setPaymentStrategy(creditCard);
        cart.checkout();
        
        // Strategy 2: Pay with PayPal
        System.out.println("\n=== Payment with PayPal ===");
        PaymentStrategy paypal = new PayPalPayment(
                "john.doe@example.com",
                "password123"
        );
        cart.setPaymentStrategy(paypal);
        cart.checkout();
        
        // Strategy 3: Pay with Cryptocurrency
        System.out.println("\n=== Payment with Cryptocurrency ===");
        PaymentStrategy crypto = new CryptoCurrencyPayment(
                "1A1z7agoat2PYLC4pThHTmyvCjqnSap99",
                "BTC"
        );
        cart.setPaymentStrategy(crypto);
        cart.checkout();
        
        // Strategy 4: Pay with Bank Transfer
        System.out.println("\n=== Payment with Bank Transfer ===");
        PaymentStrategy bankTransfer = new BankTransferPayment(
                "9876543210",
                "CHASE",
                "John Doe"
        );
        cart.setPaymentStrategy(bankTransfer);
        cart.checkout();
        
        // Invalid payment attempt
        System.out.println("\n=== Invalid Credit Card Payment ===");
        PaymentStrategy invalidCard = new CreditCardPayment(
                "1234",
                "John Doe",
                "12/25",
                "12"
        );
        cart.setPaymentStrategy(invalidCard);
        cart.checkout();
    }
}
