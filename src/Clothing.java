public class Clothing extends Product{
    private String size;
    private String color;
    // Constructor
    public Clothing(String productId, String productName, int availableItems, double price, String size, String color) {
        super(productId, productName, availableItems, price);
        this.size = size;
        this.color = color;
    }
    // Getter for size
    public String getSize() {
        return size;
    }
    // Setter for size
    public void setSize(String size) {
        this.size = size;
    }
    // Getter for color
    public String getColor() {
        return color;
    }
    // Setter for color
    public void setColor(String color) {
        this.color = color;
    }
    // Overridden toString method to provide a custom string representation of a Clothing object
    @Override
    public String toString() {
        return "Clothing[" +
                " productId= " + productId +
                ", productName= " + productName +
                ", availableItems= " + availableItems +
                ", price= " + price +
                ", size= " + size +
                ", color= " + color +",type= clothing"+
                ']';
    }
}
