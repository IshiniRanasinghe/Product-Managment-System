//interface that defines the contract for managing products in a store
public interface shoppingManager {
    //implements the methods
    void addNewProducts();
    void removeProducts();
    void listOfProducts();
    void saveFile(String file);
    void loadFromFile(String file);
}

