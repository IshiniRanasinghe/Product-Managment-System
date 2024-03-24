public class  Product {
    protected String productId;
    protected String productName;
    protected int availableItems;
    protected double price;
    public Product(String productId,String productName,int availableItems,double price){
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.availableItems = availableItems;
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public int getAvailableItems() {
        return availableItems;
    }
    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return "Product[" +
                "productId= " + productId +
                ", productName= " + productName +
                ", availableItems= " + availableItems +
                ", price= " + price +
                ']';
    }
}
