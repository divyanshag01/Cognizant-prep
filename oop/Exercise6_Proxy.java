/**
 * Exercise 6: Implementing the Proxy Pattern
 * 
 * Scenario: Image viewer application that loads images from a remote server 
 * using Proxy Pattern for lazy initialization and caching.
 */

// Subject Interface
interface Image {
    void display();
}

// Real Subject
class RealImage implements Image {
    private String imageUrl;
    
    public RealImage(String imageUrl) {
        this.imageUrl = imageUrl;
        loadImageFromServer();
    }
    
    private void loadImageFromServer() {
        System.out.println("Loading image from server: " + imageUrl);
        // Simulate heavy loading operation
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Image loaded successfully: " + imageUrl);
    }
    
    @Override
    public void display() {
        System.out.println("Displaying image: " + imageUrl);
    }
}

// Proxy Class
class ProxyImage implements Image {
    private String imageUrl;
    private RealImage realImage;
    private boolean isLoaded = false;
    
    public ProxyImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    @Override
    public void display() {
        if (!isLoaded) {
            realImage = new RealImage(imageUrl);
            isLoaded = true;
        }
        realImage.display();
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public boolean isLoaded() {
        return isLoaded;
    }
}

// Image Cache for storing and retrieving cached images
class ImageCache {
    private static final java.util.Map<String, Image> cache = new java.util.HashMap<>();
    
    public static Image getImage(String imageUrl) {
        if (!cache.containsKey(imageUrl)) {
            System.out.println("Creating new proxy for: " + imageUrl);
            cache.put(imageUrl, new ProxyImage(imageUrl));
        } else {
            System.out.println("Retrieving from cache: " + imageUrl);
        }
        return cache.get(imageUrl);
    }
    
    public static void clearCache() {
        cache.clear();
        System.out.println("Cache cleared");
    }
}

// Test the Proxy Implementation
public class Exercise6_Proxy {
    public static void main(String[] args) {
        // Test 1: Basic proxy with lazy loading
        System.out.println("=== Test 1: Basic Proxy with Lazy Loading ===");
        Image proxyImage1 = new ProxyImage("image1.jpg");
        System.out.println("Proxy created (image not loaded yet)");
        
        System.out.println("\nFirst display call (image loads):");
        proxyImage1.display();
        
        System.out.println("\nSecond display call (image already loaded):");
        proxyImage1.display();
        
        // Test 2: Multiple proxy instances
        System.out.println("\n=== Test 2: Multiple Proxy Instances ===");
        Image proxyImage2 = new ProxyImage("image2.jpg");
        Image proxyImage3 = new ProxyImage("image3.jpg");
        
        System.out.println("\nDisplaying image 2:");
        proxyImage2.display();
        
        System.out.println("\nDisplaying image 3:");
        proxyImage3.display();
        
        // Test 3: Using ImageCache for caching
        System.out.println("\n=== Test 3: Using ImageCache for Caching ===");
        
        System.out.println("\nFirst access to image A:");
        Image cachedImage1 = ImageCache.getImage("imageA.jpg");
        cachedImage1.display();
        
        System.out.println("\nSecond access to image A (from cache):");
        Image cachedImage1Again = ImageCache.getImage("imageA.jpg");
        cachedImage1Again.display();
        
        System.out.println("\nFirst access to image B:");
        Image cachedImage2 = ImageCache.getImage("imageB.jpg");
        cachedImage2.display();
        
        System.out.println("\nSecond access to image B (from cache):");
        Image cachedImage2Again = ImageCache.getImage("imageB.jpg");
        cachedImage2Again.display();
    }
}
