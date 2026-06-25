/**
 * Exercise 2: Implementing the Factory Method Pattern
 * 
 * Scenario: Document management system that creates different types 
 * of documents (Word, PDF, Excel) using Factory Method Pattern.
 */

import java.util.Date;

// Abstract Document Class (Product)
abstract class Document {
    protected String fileName;
    protected Date createdDate;
    
    public Document(String fileName) {
        this.fileName = fileName;
        this.createdDate = new Date();
    }
    
    public abstract void open();
    public abstract void save();
    public abstract void close();
    
    public String getFileName() {
        return fileName;
    }
}

// Concrete Document Classes
class WordDocument extends Document {
    public WordDocument(String fileName) {
        super(fileName);
    }
    
    @Override
    public void open() {
        System.out.println("Opening Word document: " + fileName);
    }
    
    @Override
    public void save() {
        System.out.println("Saving Word document: " + fileName);
    }
    
    @Override
    public void close() {
        System.out.println("Closing Word document: " + fileName);
    }
}

class PDFDocument extends Document {
    public PDFDocument(String fileName) {
        super(fileName);
    }
    
    @Override
    public void open() {
        System.out.println("Opening PDF document: " + fileName);
    }
    
    @Override
    public void save() {
        System.out.println("Saving PDF document: " + fileName);
    }
    
    @Override
    public void close() {
        System.out.println("Closing PDF document: " + fileName);
    }
}

class ExcelDocument extends Document {
    public ExcelDocument(String fileName) {
        super(fileName);
    }
    
    @Override
    public void open() {
        System.out.println("Opening Excel document: " + fileName);
    }
    
    @Override
    public void save() {
        System.out.println("Saving Excel document: " + fileName);
    }
    
    @Override
    public void close() {
        System.out.println("Closing Excel document: " + fileName);
    }
}

// Document Creator (Factory)
class DocumentCreator {
    public static Document createDocument(String type, String fileName) {
        switch(type.toLowerCase()) {
            case "word":
                return new WordDocument(fileName);
            case "pdf":
                return new PDFDocument(fileName);
            case "excel":
                return new ExcelDocument(fileName);
            default:
                throw new IllegalArgumentException("Unknown document type: " + type);
        }
    }
}

// Test the Factory Method Implementation
public class Exercise2_FactoryMethod {
    public static void main(String[] args) {
        // Create different types of documents using the factory
        Document wordDoc = DocumentCreator.createDocument("word", "report.docx");
        Document pdfDoc = DocumentCreator.createDocument("pdf", "presentation.pdf");
        Document excelDoc = DocumentCreator.createDocument("excel", "budget.xlsx");
        
        // Test operations on documents
        System.out.println("=== Word Document ===");
        wordDoc.open();
        wordDoc.save();
        wordDoc.close();
        
        System.out.println("\n=== PDF Document ===");
        pdfDoc.open();
        pdfDoc.save();
        pdfDoc.close();
        
        System.out.println("\n=== Excel Document ===");
        excelDoc.open();
        excelDoc.save();
        excelDoc.close();
    }
}
