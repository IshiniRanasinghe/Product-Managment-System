import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;
public class ShoppingCart extends Component {
static List<Product> listOfProducts = new ArrayList<>();
    public ShoppingCart(){}
        public void addElectronicProduct(String productId, String productName, int quantity, double price, String brand, int warrantyPeriod, String category){
            boolean productExists = false;
            // Check if the product already exists in the cart
        for (Product product : listOfProducts) {
                    if (product.getProductId().equals(productId)) {
                        // Product already exists in the cart
                        product.setAvailableItems(product.getAvailableItems() + quantity);
                        saveToFile("Cart Details");
                        productExists = true;
                        break;
                    }
                }
            if (!productExists) {
                Electronics electronics = new Electronics(productId, productName, quantity, price, brand, warrantyPeriod);
                listOfProducts.add(electronics);
                saveToFile("Cart Details");
            }
        }
    public void addClothingProduct(String productId, String productName, int quantity, double price, String size, String color, String category) {
        boolean productExists = false;        // Check if the product already exists in the cart
        for (Product product : listOfProducts) {
            if (product.getProductId().equals(productId)) {
                product.setAvailableItems(product.getAvailableItems() + quantity);      // Update the quantity of the existing product
                saveToFile("Cart Details");
                productExists = true;
                break;
            }
        }
        if (!productExists) {
            Clothing clothing = new Clothing(productId, productName, quantity, price, size, color);
            listOfProducts.add(clothing);
            saveToFile("Cart Details");
        }
    }
    public static List<Product> getCartDetails(){
        return listOfProducts;
    }
    public void saveToFile(String fileName) {
        try {
            File allDetails = new File(fileName);
            FileWriter writeDetails = new FileWriter(allDetails);
            for (Product product : listOfProducts) {
                writeDetails.write(product.toString());
                writeDetails.write("\n");
            }
            writeDetails.close();
            System.out.println("Data Stored Successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("Error while Saving to the file!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (EmptyStackException ee){
            System.out.println("e");
        }catch (SecurityException e) {
            System.out.println("A security exception occurred: " + e.getMessage());
        }
    }
    public boolean checkQuantity(){
        for (Product product : listOfProducts){
            if (product.getAvailableItems() > 2){
                return true;
            }
        }
        return false;
    }
    public  void calculate(){
        try{
            double total,firstPurchaseDiscount,discount,finalTotal;
            for (Product product: listOfProducts){
                total =+ product.getPrice()*product.getAvailableItems();
                GUIShoppingCart.totalPriceLbl.setText(String.valueOf(total));
                String buyer = LoginForm.loggedUserName;
                if (!nameCheck(buyer)){
                    firstPurchaseDiscount=total/10;
                    if (quantityCheck()){
                        discount=total/5;
                        finalTotal= total-(discount+firstPurchaseDiscount);
                        GUIShoppingCart.finalTotalPriceLbl.setText(String.valueOf(finalTotal));
                        GUIShoppingCart.DiscountLbl1.setText(String.valueOf(firstPurchaseDiscount));
                        GUIShoppingCart.sameItemLbl.setText(String.valueOf(discount));
                    }
                    else {
                        discount=0;
                        finalTotal= total-(discount+firstPurchaseDiscount);
                        GUIShoppingCart.finalTotalPriceLbl.setText(String.valueOf(finalTotal));
                        GUIShoppingCart.DiscountLbl1.setText(String.valueOf(firstPurchaseDiscount));
                        GUIShoppingCart.sameItemLbl.setText(String.valueOf(discount));
                    }
                    }
                else {
                    firstPurchaseDiscount=0;
                    if (quantityCheck()){
                        discount=total/5;
                        finalTotal= total-(discount+firstPurchaseDiscount);
                        GUIShoppingCart.finalTotalPriceLbl.setText(String.valueOf(finalTotal));
                        GUIShoppingCart.DiscountLbl1.setText(String.valueOf(firstPurchaseDiscount));
                        GUIShoppingCart.sameItemLbl.setText(String.valueOf(discount));
                    }
                    else {
                        discount=0;
                        finalTotal= total-(discount+firstPurchaseDiscount);
                        GUIShoppingCart.finalTotalPriceLbl.setText(String.valueOf(finalTotal));
                        GUIShoppingCart.DiscountLbl1.setText(String.valueOf(firstPurchaseDiscount));
                        GUIShoppingCart.sameItemLbl.setText(String.valueOf(discount));
                    }

                }
            }
        }
        catch (NullPointerException e){
        }
    }
    public boolean nameCheck ( String buyer){
        boolean check = false;
        for (String name : User.purchaseDetails){
            if (name == buyer){
                check = true;
                break;
            }
        }
        return check;
    }
    public boolean quantityCheck(){
        for (Product product:listOfProducts){
            if (product.getAvailableItems()>2){
                return (true);
            }
        }
        return false;
    }
}

