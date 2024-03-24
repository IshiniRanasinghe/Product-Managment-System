public class Electronics extends Product {
    // Private fields specific to Electronics
    private String brand; // Brand of the electronic product
    private int warrantyPeriod; // Warranty period of the electronic product

    // Constructor for Electronics, initializing fields and calling super constructor
    public Electronics(String productId, String productName, int availableItems, double price, String brand, int warrantyPeriod) {
        super(productId, productName, availableItems, price); // Call to superclass constructor
        this.brand = brand; // Initialize brand
        this.warrantyPeriod = warrantyPeriod; // Initialize warranty period
    }
    // Getter method for brand
    public String getBrand() {
        return brand;
    }
    // Getter method for warranty period
    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }
    // Setter method for brand
    public void setBrand(String brand) {
        this.brand = brand;
    }
    // Setter method for warranty period
    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }
    // Override toString method to provide custom string representation
    @Override
    public String toString() {
        return "Electronics[" +
                " productId= " + productId +
                ", productName= " + productName +
                ", availableItems= " + availableItems +
                ", price= " + price +
                ", brand= " + brand +
                ", warrantyPeriod= " + warrantyPeriod + ",type= Electronic"+// Include brand and warranty period in the string representation
                ']';
    }
}
