import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GUIShoppingCart extends JFrame implements ActionListener {
    static JLabel totalPriceLbl, DiscountLbl1, sameItemLbl, finalTotalPriceLbl;
    JLabel totalLabel, firstPurchaseDiscountLbl, sameCategoryDiscountLbl, finalTotalLbl;
    JPanel cartPanel;
    static JTable cartDetailsTable;
    JButton removeBtn, purchaseBtn;
    String [] columNames = {"Product", "Quantity", "Price(£)"};
    static String [][] tableContent = new String[50][3];
    JScrollPane scrollPane;
    public GUIShoppingCart(){
        this.setLayout(new BorderLayout()); // Setting the boarder layout
        cartPanel = new JPanel();
        cartPanel.setLayout(null);
        this.add(cartPanel);
        DefaultTableModel tableModel = new DefaultTableModel(tableContent,columNames);

        cartDetailsTable = new JTable(tableModel);
        cartDetailsTable.setAutoCreateRowSorter(true); // Enable sorting

        // Setting all the columns to a fixes size
        for (int i = 0; i < cartDetailsTable.getColumnCount(); i++) {
            cartDetailsTable.getTableHeader().getColumnModel().getColumn(i).setResizable(false);
        }

        // Increase the height of the header row
        int headerHeight = 50;  // Set your desired height
        cartDetailsTable.getTableHeader().setPreferredSize(new Dimension(0, headerHeight));

        // Increase the height of all rows
        int rowHeight = 50;  // Set your desired height
        cartDetailsTable.setRowHeight(rowHeight);

        // Center align the column names
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) cartDetailsTable.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // A scroll pane to scroll the table
        scrollPane = new JScrollPane(cartDetailsTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(50,30,700,300);

        cartPanel.add(scrollPane);

        totalLabel = new JLabel();
        totalLabel.setText("Total :");
        totalLabel.setBounds(470,350,300,30);
        cartPanel.add(totalLabel);

        totalPriceLbl = new JLabel();
        totalPriceLbl.setText("0.00 £");
        totalPriceLbl.setBounds(580,350,300,30);
        cartPanel.add(totalPriceLbl);

        firstPurchaseDiscountLbl = new JLabel();
        firstPurchaseDiscountLbl.setText("First Purchase Discount (10%) :");
        firstPurchaseDiscountLbl.setBounds(315,390,250,30);
        cartPanel.add(firstPurchaseDiscountLbl);

        DiscountLbl1 = new JLabel();
        DiscountLbl1.setText("-0.00 £");
        DiscountLbl1.setBounds(570,390,300,30);
        cartPanel.add(DiscountLbl1);

        sameCategoryDiscountLbl = new JLabel();
        sameCategoryDiscountLbl.setText("Three Items in same Category Discount (20%) :");
        sameCategoryDiscountLbl.setBounds(217,430,300,30);
        cartPanel.add(sameCategoryDiscountLbl);

        sameItemLbl = new JLabel();
        sameItemLbl.setText("-0.00 £");
        sameItemLbl.setBounds(570,430,300,30);
        cartPanel.add(sameItemLbl);

        finalTotalLbl = new JLabel();
        finalTotalLbl.setText("Final Total :");
        // Set the font to bold
        Font boldFont = new Font(finalTotalLbl.getFont().getFontName(), Font.BOLD, finalTotalLbl.getFont().getSize());
        finalTotalLbl.setFont(boldFont);
        finalTotalLbl.setBounds(430,470,300,30);
        cartPanel.add(finalTotalLbl);

        finalTotalPriceLbl = new JLabel();
        finalTotalPriceLbl.setText("0.00 £");
        finalTotalPriceLbl.setFont(boldFont); // Set the font to bold
        finalTotalPriceLbl.setBounds(575,470,300,30);
        cartPanel.add(finalTotalPriceLbl);

        removeBtn = new JButton();
        removeBtn.setText("Clear All");
        removeBtn.setBounds(50,510,110,40);
        cartPanel.add(removeBtn);

        purchaseBtn = new JButton();
        purchaseBtn.setText("Purchase");
        purchaseBtn.setBounds(510,510,110,40);
        cartPanel.add(purchaseBtn);

        removeBtn.addActionListener(this);
        purchaseBtn.addActionListener(this);
        ShoppingCart a = new ShoppingCart();
        a.calculate();

        initializeTableContentFromFile();
        this.setTitle("Shopping Cart"); //  Setting the title
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Setting the frame that exit on close
        this.setSize(800,600); // Setting the window size
        this.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == removeBtn){
            if (e.getSource() == removeBtn){
                guiRemoveProduct();
                updateTableContent();
                System.out.println(Arrays.deepToString(tableContent));
            } else if (e.getSource() == purchaseBtn) {
                purchaseItems();
                saveUserDataToFile();
            }
        }
    }
    private void saveUserDataToFile() {
        try {
            // Create a FileWriter with append mode set to true
            File userfile = new File("UserData.txt");
            FileWriter writer = new FileWriter("UserData.txt", true);
            // Iterate through the tableContent array and write each row to the file
            for (String[] row : tableContent) {
                if (row != null) {
                    for (String data : row) {
                        writer.write(data + "\t"); // Write each element of the row separated by a tab
                    }
                    writer.write(System.lineSeparator()); // Add a new line after each row
                }
            }

            // Close the FileWriter
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace(); // Handle exceptions appropriately
        }
    }
    public void guiRemoveProduct() {
        int selectedRowIndex = cartDetailsTable.getSelectedRow();
        if (selectedRowIndex != -1) { // Check if a row is selected
            DefaultTableModel model = (DefaultTableModel) cartDetailsTable.getModel();
            model.removeRow(selectedRowIndex); // Remove the selected row from the table model

            // Remove the corresponding row from the tableContent array
            for (int i = selectedRowIndex; i < tableContent.length - 1; i++) {
                tableContent[i] = tableContent[i + 1];
            }
            tableContent[tableContent.length - 1] = new String[3]; // Clear the last row
            // Update the total price label
            updateTotalPriceLabel();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to remove.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void initializeTableContentFromFile() {
        try {
            File fileRead = new File("Cart Details");
            Scanner fileScanner = new Scanner(fileRead);

            int rowCount = 0; // Keep track of the current row count in the tableContent array

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                Pattern electronicsPattern = Pattern.compile("Electronics\\[ productId= (\\d+), productName= (\\w+), availableItems= (\\d+), price= (\\d+\\.\\d+), brand= (\\w+), warrantyPeriod= (\\d+),type= (\\w+)]");
                Pattern clothingPattern = Pattern.compile("Clothing\\[ productId= (\\w+), productName= (\\w+), availableItems= (\\d+), price= (\\d+\\.\\d+), size= (\\w+), color= (\\w+),type= (\\w+)]");

                Matcher electronicMatchingItem = electronicsPattern.matcher(line);
                Matcher clothingMatchingItem = clothingPattern.matcher(line);

                if (electronicMatchingItem.find()) {
                    String productName = electronicMatchingItem.group(2);
                    int availableItems = Integer.parseInt(electronicMatchingItem.group(3));
                    double price = Double.parseDouble(electronicMatchingItem.group(4));

                    // Add the item to the tableContent array
                    tableContent[rowCount][0] = productName;
                    tableContent[rowCount][1] = String.valueOf(availableItems);
                    tableContent[rowCount][2] = String.valueOf(price);
                    rowCount++;
                } else if (clothingMatchingItem.find()) {
                    String productName = clothingMatchingItem.group(2);
                    int availableItems = Integer.parseInt(clothingMatchingItem.group(3));
                    double price = Double.parseDouble(clothingMatchingItem.group(4));

                    // Add the item to the tableContent array
                    tableContent[rowCount][0] = productName;
                    tableContent[rowCount][1] = String.valueOf(availableItems);
                    tableContent[rowCount][2] = String.valueOf(price);
                    rowCount++;
                }
            }
            // Create a new DefaultTableModel with the table content
            DefaultTableModel model = new DefaultTableModel(tableContent, columNames);
            // Set the new model to the cartDetailsTable
            cartDetailsTable.setModel(model);
            // Update the total price label
            updateTotalPriceLabel();
        } catch (Exception ex) {
            ex.printStackTrace(); // Handle exceptions appropriately
        }
    }
    private void updateTotalPriceLabel() {
        double totalPrice = 0.0;

        // Iterate through the tableContent array and calculate the total price
        for (String[] row : tableContent) {
            if (row != null && row.length >= 3) {
                String priceString = row[2]; // Assuming the price is in the third column
                String quantityString = row[1]; // Assuming the quantity is in the second column

                if (priceString != null && !priceString.trim().isEmpty() &&
                        quantityString != null && !quantityString.trim().isEmpty()) {
                    try {
                        double price = Double.parseDouble(priceString.trim());
                        int quantity = Integer.parseInt(quantityString.trim());

                        // Multiply price by quantity for each item and add to the total price
                        totalPrice += (price * quantity);
                    } catch (NumberFormatException e) {
                        // Handle the case where the price or quantity string is not a valid number
                        e.printStackTrace(); // Print the exception for debugging
                    }
                }
            }
        }

        // Update the total price label
        totalPriceLbl.setText(String.format("%.2f £", totalPrice));
    }


    public void updateTableContent() {
        // Clear the current table content
        for (int i = 0; i < tableContent.length; i++) {
            tableContent[i] = new String[3]; // Clear the row
        }
        // Update the table content with the cart items
        String[][] cartItems = new String[0][];
        for (int i = 0; i < cartItems.length; i++) {
            if (i < tableContent.length) {
                tableContent[i] = cartItems[i]; // Update each row with the corresponding cart item
            } else {
                // If the cart has more items than the table can display, break the loop
                break;
            }
        }
        // Update the table model with the new table content
        DefaultTableModel model = (DefaultTableModel) cartDetailsTable.getModel();
        model.setDataVector(tableContent, columNames);
        // Update the total price label
        updateTotalPriceLabel();
    }

    public void purchaseItems() {
        // Calculate the final total price based on the items in the cart
        double totalPrice = 0.0;
        for (String[] row : tableContent) {
            if (row != null) {
                totalPrice += Double.parseDouble(row[2]); // Assuming the price is in the third column
            }
        }
        // Apply discounts if applicable
        double finalTotalPrice = applyDiscounts(totalPrice);
        // Display the final total price
        finalTotalPriceLbl.setText(String.format("%.2f £", finalTotalPrice));
        // Perform any other actions related to the purchase (e.g., update inventory, generate receipt, etc.)
    }

    private double applyDiscounts(double totalPrice) {
        // Apply any discounts based on business rules
        // For example, you can apply a 10% discount for the first purchase
        double discount = 0.0;
        if (isFirstPurchase()) {
            discount += 0.10 * totalPrice; // 10% discount for the first purchase
            DiscountLbl1.setText(String.format("-%.2f £", discount)); // Update the discount label
        }

        // Apply a 20% discount if there are three items in the same category
        int clothingCount = countItemsInCategory("Clothing");
        if (clothingCount >= 3) {
            discount += 0.20 * totalPrice; // 20% discount for three items in the same category
            sameItemLbl.setText(String.format("-%.2f £", discount)); // Update the discount label
        }

        // Calculate the final total price after applying discounts
        double finalTotalPrice = totalPrice - discount;

        return finalTotalPrice;
    }

    private boolean isFirstPurchase() {
        // Implement logic to check if it's the user's first purchase
        // For example, you can check a database or a flag in the user's profile
        return true; // Placeholder for demonstration
    }

    private int countItemsInCategory(String category) {
        // Implement logic to count the number of items in a specific category in the cart
        // Iterate through the tableContent array and count the items in the specified category
        int count = 0;
        for (String[] row : tableContent) {
            if (row != null && row[0].equalsIgnoreCase(category)) {
                count++;
            }
        }
        return count;
    }
    /*public static class TablePop{
        public void updateTableContent(int count) {
            GUIShoppingCart.tableContent = new String[50][3];
            try {
                File fileRead = new File("Cart Details");
                Scanner fileScanner = new Scanner(fileRead);

                while (fileScanner.hasNextLine()) {
                    String productType = fileScanner.nextLine();

                    if (productType.contains("Electronic")) {
                        Pattern readPattern = Pattern.compile("Product ID - (\\w+), Product Name - (\\w+), Available in stock - (\\d+), Price - (\\d+\\.\\d+), Brand is - (\\w+), Warranty is - (\\d+), Item type - (\\w+)");
                        Matcher matchingItem = readPattern.matcher(productType);

                        if (matchingItem.find()) {
                            String productId = matchingItem.group(1);
                            String productName = matchingItem.group(2);
                            double price = Double.parseDouble(matchingItem.group(4));
                            int quantity = Integer.parseInt(matchingItem.group(3));
                            String brand = matchingItem.group(5);
                            int warranty = Integer.parseInt(matchingItem.group(6));

                            tableContent[count][0] = "<html>" + productId + "<br>" + productName + "<br>" + brand + ", " + warranty + " Weeks warranty" + "</html>";
                            tableContent[count][1] = String.valueOf(quantity);
                            tableContent[count][2] = String.valueOf(price);

                            count++;

                            DefaultTableModel model = (DefaultTableModel) cartDetailsTable.getModel();
                            model.setRowCount(0);
                            for (int i = 0; i < count; i++) {
                                model.addRow(tableContent[i]);
                            }
                        }
                    } else {
                        Pattern readPattern = Pattern.compile("Product ID - (\\w+), Product Name - (\\w+), Available in stock - (\\d+), Price - (\\d+\\.\\d+), Size is - (\\w+), Colour is - (\\w+), Item type - (\\w+)");
                        Matcher matchingItem = readPattern.matcher(productType);

                        if (matchingItem.find()) {
                            String productId = matchingItem.group(1);
                            String productName = matchingItem.group(2);
                            int quantity = Integer.parseInt(matchingItem.group(3));
                            double price = Double.parseDouble(matchingItem.group(4));
                            String size = matchingItem.group(5);
                            String color = matchingItem.group(6);

                            tableContent[count][0] = "<html>" + productId + "<br>" + productName + "<br>" + size + ", " + color + " Weeks warranty" + "</html>";
                            tableContent[count][1] = String.valueOf(quantity);
                            tableContent[count][2] = String.valueOf(price);
                            count++;
                            DefaultTableModel model = (DefaultTableModel) cartDetailsTable.getModel();
                            model.setRowCount(0);
                            for (int i = 0; i < count; i++) {
                                model.addRow(tableContent[i]);
                            }
                        }
                    }
                }
            } catch (NullPointerException e){
                // throw new RuntimeException();
            }catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }*/
}
