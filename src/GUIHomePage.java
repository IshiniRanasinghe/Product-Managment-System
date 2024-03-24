import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// Class definition for the main GUIHomePage class, which extends JFrame and implements ActionListener
public class GUIHomePage extends JFrame implements ActionListener {
    static String list[] = {"All", "Electronics", "Clothing"};
    String[] columnNames = {"Product ID", "Product Name", "Category", "Price(£)", "Info"};
    static String[][] tableDetails = new String[50][5];
    JPanel panel1, panel2; //panel1= upper panel , panel2=bottom panel
    static JTable table1;
    JLabel categoryLabel, clickedItemL, productL, categoryL, nameL, sizeL, colorL, availableItemL, itemNumberL;
    static JComboBox<String> comboBox1;
    JButton shoppingCartBtn, addToCartBtn;
    JButton increaseQuantityBtn, decreaseQuantityBtn;
    JButton sortByNameBtn;
    JScrollPane scrollPane;
    JTextField quantity;
    private int addQuantity = 1;

    public void homePage() {
        panel1 = new JPanel();
        this.setLayout(new BorderLayout());

        panel1.setPreferredSize(new Dimension(700, 400));
        Border bottomBdr = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black);
        panel1.setBorder(bottomBdr);
        panel1.setLayout(null);
        this.add(panel1, BorderLayout.NORTH);

        categoryLabel = new JLabel(); // Creating the label
        categoryLabel.setText("Select Product Category"); // Adding the name to the label
        categoryLabel.setBounds(115, 45, 200, 25); // Updated position
        panel1.add(categoryLabel); // Adding category label

        comboBox1 = new JComboBox<>(list); // list = comboBox list// Creating the combo box with the categories
        comboBox1.setBounds(275, 40, 150, 40); // Setting up the bounds to the combo bov
        panel1.add(comboBox1);

        shoppingCartBtn = new JButton(); // Creating the shopping cart button
        shoppingCartBtn.setText("Shopping Cart"); // Setting the text
        shoppingCartBtn.setBounds(530, 10, 150, 40); //  Setting the bounds to the button
        panel1.add(shoppingCartBtn);

        DefaultTableModel tableModel = new DefaultTableModel(tableDetails, columnNames);
        table1 = new JTable(tableModel);

        table1.setAutoCreateRowSorter(true);
        table1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());

        for (int i = 0; i < table1.getColumnCount(); i++) {
            table1.getTableHeader().getColumnModel().getColumn(i).setResizable(false);
        }
        int[] columnWidths = {100, 150, 100, 80, 200};

        for (int i = 0; i < table1.getColumnCount(); i++) {
            TableColumn column = table1.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
            column.setMinWidth(columnWidths[i]);
            column.setMaxWidth(columnWidths[i]);
        }
        int headerHeight = 30;  // Set your desired height
        table1.getTableHeader().setPreferredSize(new Dimension(0, headerHeight));

        int rowHeight = 30;  // Set your desired height
        table1.setRowHeight(rowHeight);

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table1.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        scrollPane = new JScrollPane(table1);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(20, 90, 650, 270);
        panel1.add(scrollPane);

        panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(700, 400)); // Setting the Dimension
        // bottomPanel.setBorder(new LineBorder(Color.BLACK,2)); // Setting the boarder color and a boarder
        panel2.setLayout(null); // Setting the layout to null in the panel
        this.add(panel2, BorderLayout.SOUTH);

        clickedItemL = new JLabel();
        clickedItemL.setText("Selected Product - Details");
        clickedItemL.setFont(clickedItemL.getFont().deriveFont(Font.BOLD)); //  Bolding the text
        clickedItemL.setBounds(50, 40, 200, 50);
        panel2.add(clickedItemL);

        productL = new JLabel(); // A label for the product ID
        productL.setText("Product id: ");
        productL.setBounds(50, 70, 200, 50);
        panel2.add(productL);

        categoryL = new JLabel(); // A label for the category
        categoryL.setText("Category: ");
        categoryL.setBounds(50, 100, 200, 50);
        panel2.add(categoryL);

        nameL = new JLabel(); // A label for the Name
        nameL.setText("Name: ");
        nameL.setBounds(50, 130, 200, 50);
        panel2.add(nameL);

        sizeL = new JLabel(); // A label for the size
        sizeL.setText("Size: ");
        sizeL.setBounds(50, 160, 200, 50);
        panel2.add(sizeL);

        colorL = new JLabel(); // A label for color
        colorL.setText("Colour: ");
        colorL.setBounds(50, 190, 200, 50);
        panel2.add(colorL);

        availableItemL = new JLabel(); // A label for the available items
        availableItemL.setText("Items Available: ");
        availableItemL.setBounds(50, 220, 200, 50);
        panel2.add(availableItemL);

        itemNumberL = new JLabel(); // A label for items to add
        itemNumberL.setText("Select no of Items to Add:");
        itemNumberL.setBounds(50, 250, 170, 50);
        panel2.add(itemNumberL);

        quantity = new JTextField(); //  text field to show the quantity
        quantity.setText(String.valueOf(addQuantity));
        quantity.setBounds(280, 265, 40, 20);
        quantity.setHorizontalAlignment(JTextField.CENTER); // Center the text
        panel2.add(quantity);

        increaseQuantityBtn = new JButton();
        increaseQuantityBtn.setText("+");
        increaseQuantityBtn.setBounds(325, 265, 50, 20);
        panel2.add(increaseQuantityBtn);

        decreaseQuantityBtn = new JButton();
        decreaseQuantityBtn.setText("-");
        decreaseQuantityBtn.setBounds(225, 265, 50, 20);
        panel2.add(decreaseQuantityBtn);

        addToCartBtn = new JButton(); // Add to the cart button
        addToCartBtn.setText("Add to Shopping Cart");
        addToCartBtn.setBounds(200, 310, 200, 40);
        panel2.add(addToCartBtn);
        // Adding action listeners to buttons and combo box
        addToCartBtn.addActionListener(this);

        shoppingCartBtn.addActionListener(this);

        comboBox1.addActionListener(this);
        // Setting up the JFrame properties
        this.setTitle("Westminster Shopping Centre"); //  Setting the title
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Setting the frame that exit on close
        this.setSize(700, 800); // Setting the window size
        this.setVisible(true);
        // Adding action listeners for increasing and decreasing quantity buttons
        increaseQuantityBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addQuantity++;
                quantity.setText(String.valueOf(addQuantity));
            }
        });

        decreaseQuantityBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addQuantity > 1) {
                    addQuantity--;
                    quantity.setText(String.valueOf(addQuantity));
                }

            }
        });
        // Adding a list selection listener to the table to update the details panel when a row is selected
        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table1.getSelectedRow();
                    updateDetailsPanel(selectedRow);
                }
            }
        });
        // Setting the default selection in the combo box and loading data into the table
        comboBox1.setSelectedIndex(0);
        loadToTable();

         }

    private void updateDetailsPanel(int selectedRow) {
        if (selectedRow >= 0 && selectedRow < table1.getRowCount()) {
            String productId = tableDetails[selectedRow][0];

            productL.setText("Product id: " + tableDetails[selectedRow][0]);
            categoryL.setText("Category: " + tableDetails[selectedRow][2]);
            nameL.setText("Name: " + tableDetails[selectedRow][1]);
            sizeL.setText("Size: "); // Update with actual size if available
            colorL.setText("Colour: "); // Update with actual color if available
            availableItemL.setText("Items Available: " + getAvailableItems(productId)); // Update with actual available items if available

            if (tableDetails[selectedRow][2].equals("Electronic")) {
                sizeL.setText("Brand: " + tableDetails[selectedRow][4].split(",")[0]); // Extract brand from the info column
                colorL.setText("Warranty Period: " + tableDetails[selectedRow][4].split(",")[1]); // Extract warranty period from the info column
            } else {
                sizeL.setText("Size: " + tableDetails[selectedRow][4].split(",")[0]); // Update with actual size if available
                colorL.setText("Colour: " + tableDetails[selectedRow][4].split(",")[1]); // Update with actual color if available
            }
        } else {
            // Reset all labels when no row is selected
            productL.setText("Product id: ");
            categoryL.setText("Category: ");
            nameL.setText("Name: ");
            sizeL.setText("Size: ");
            colorL.setText("Colour: ");
            availableItemL.setText("Items Available: ");
        }
    }
    // Main method to create an instance of GUIHomePage and initialize the home page
    public static void main(String[] args) {
        GUIHomePage shoppingCentre = new GUIHomePage();
        shoppingCentre.homePage();
    }

    // ActionListener implementation for handling button and combo box actions
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == shoppingCartBtn) {
            new GUIShoppingCart(); // Create a new instance of GUIShoppingCart when the shopping cart button is clicked
        } else if (e.getSource()== addToCartBtn) {
            addingItemsToCart();    // Call the addingItemsToCart method when the add to cart button is clicked
        } else {
            loadToTable();      // Reload data into the table when the combo box selection changes
        }
    }
    // Method to load data into the table based on the selected product type
    public static void loadToTable() {
        int number = 0;
        String selectedProductType = Objects.requireNonNull(comboBox1.getSelectedItem()).toString();
        switch (selectedProductType) {
            case ("Electronics"): {
                try {
                    File loadFile = new File("C:\\Users\\ishin\\OneDrive\\Documents\\IIT\\YEAR 2\\OOP\\CW\\CW2 Submission\\CW2_OOP\\src\\file.txt");
                    Scanner scannerFile = new Scanner(loadFile);
                    while (scannerFile.hasNextLine()) {
                        String line = scannerFile.nextLine();
                        if (line.contains("Electronics")) {
                            Pattern electronicsPattern = Pattern.compile("Electronics\\[ productId= (\\d+), productName= (\\w+), availableItems= (\\d+), price= (\\d+\\.\\d+), brand= (\\w+), warrantyPeriod= (\\d+),type= (\\w+)]");
                            Matcher matchingItem = electronicsPattern.matcher(line);
                            if (matchingItem.find()) {
                                String productId = matchingItem.group(1);
                                String productName = matchingItem.group(2);
                                int availableItems = Integer.parseInt(matchingItem.group(3));
                                double price = Double.parseDouble(matchingItem.group(4));
                                String brand = matchingItem.group(5);
                                int warrantyPeriod = Integer.parseInt(matchingItem.group(6));
                                String type = matchingItem.group(7);

                                tableDetails[number][0] = productId;
                                tableDetails[number][1] = productName;
                                tableDetails[number][3] = String.valueOf(price);
                                tableDetails[number][4] = brand + "," + warrantyPeriod + "Weeks";
                                tableDetails[number][2] = type;
                                number++;

                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error While loading");
                }
                DefaultTableModel model = (DefaultTableModel) table1.getModel();
                model.setRowCount(0);
                for (int x = 0; x < number; x++) {
                    model.addRow(tableDetails[x]);
                }
                break;
            }
            case ("Clothing"): {
                try {
                    File loadFile = new File("C:\\Users\\ishin\\OneDrive\\Documents\\IIT\\YEAR 2\\OOP\\CW\\CW2 Submission\\CW2_OOP\\src\\file.txt");
                    Scanner scannerFile = new Scanner(loadFile);
                    while (scannerFile.hasNextLine()) {
                        String line = scannerFile.nextLine();
                        if (line.contains("Clothing")) {
                            Pattern clothingPattern = Pattern.compile("Clothing\\[ productId= (\\w+), productName= (\\w+), availableItems= (\\d+), price= (\\d+\\.\\d+), size= (\\w+), color= (\\w+),type= (\\w+)]");
                            Matcher matchingItem = clothingPattern.matcher(line);
                            if (matchingItem.find()) {
                                String productId = matchingItem.group(1);
                                String productName = matchingItem.group(2);
                                int availableItems = Integer.parseInt(matchingItem.group(3));
                                double price = Double.parseDouble(matchingItem.group(4));
                                String size = matchingItem.group(5);
                                String color = matchingItem.group(6);
                                String type = matchingItem.group(7);

                                tableDetails[number][0] = productId;
                                tableDetails[number][1] = productName;
                                tableDetails[number][2] = type;
                                tableDetails[number][3] = String.valueOf(price);
                                tableDetails[number][4] = size + "," + color;
                                number++;
                            }
                        }
                    }

                } catch (Exception e) {
                    System.out.println("Error While loading");
                }
                DefaultTableModel model = (DefaultTableModel) table1.getModel();
                model.setRowCount(0);
                for (int x = 0; x < number; x++) {
                    model.addRow(tableDetails[x]);
                }
                break;
            }
            case ("All"): {

                try {

                    File loadFile = new File("C:\\Users\\ishin\\OneDrive\\Documents\\IIT\\YEAR 2\\OOP\\CW\\CW2 Submission\\CW2_OOP\\src\\file.txt");
                    Scanner scannerFile = new Scanner(loadFile);
                    while (scannerFile.hasNextLine()) {
                        String getType = scannerFile.nextLine();
                        if (getType.contains("Electronics")) {
                            Pattern electronicsPattern = Pattern.compile("Electronics\\[ productId= (\\d+), productName= (\\w+), availableItems= (\\d+), price= (\\d+\\.\\d+), brand= (\\w+), warrantyPeriod= (\\d+),type= (\\w+)]");
                            Matcher matchingItem = electronicsPattern.matcher(getType);
                            if (matchingItem.find()) {
                                String productId = matchingItem.group(1);
                                String productName = matchingItem.group(2);
                                int availableItems = Integer.parseInt(matchingItem.group(3));
                                double price = Double.parseDouble(matchingItem.group(4));
                                String brand = matchingItem.group(5);
                                int warrantyPeriod = Integer.parseInt(matchingItem.group(6));
                                String type = matchingItem.group(7);
                                Electronics electronicsItems = new Electronics(productId, productName, availableItems, price, brand, warrantyPeriod);

                                tableDetails[number][0] = productId;
                                tableDetails[number][1] = productName;
                                tableDetails[number][3] = String.valueOf(price);
                                tableDetails[number][4] = brand + "," + warrantyPeriod + "Weeks";
                                tableDetails[number][2] = type;
                                number++;

                            }
                        } else {
                            Pattern clothingPattern = Pattern.compile("Clothing\\[ productId= (\\w+), productName= (\\w+), availableItems= (\\d+), price= (\\d+\\.\\d+), size= (\\w+), color= (\\w+),type= (\\w+)]");
                            Matcher matchingItem = clothingPattern.matcher(getType);
                            if (matchingItem.find()) {
                                String productId = matchingItem.group(1);
                                String productName = matchingItem.group(2);
                                int availableItems = Integer.parseInt(matchingItem.group(3));
                                double price = Double.parseDouble(matchingItem.group(4));
                                String size = matchingItem.group(5);
                                String color = matchingItem.group(6);
                                String type = matchingItem.group(7);

                                tableDetails[number][0] = productId;
                                tableDetails[number][1] = productName;
                                tableDetails[number][2] = type;
                                tableDetails[number][3] = String.valueOf(price);
                                tableDetails[number][4] = size + "," + color;
                                number++;
                            }
                        }
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                DefaultTableModel model = (DefaultTableModel) table1.getModel();
                model.setRowCount(0);
                for (int x = 0; x < number; x++) {
                    model.addRow(tableDetails[x]);
                }
            }
        }
    }
    // Method to retrieve the available items for a product from the data file
    public static String getAvailableItems(String productId) {
        String availableItems = null;

        try {
            File fileRead = new File("C:\\Users\\ishin\\OneDrive\\Documents\\IIT\\YEAR 2\\OOP\\CW\\CW2 Submission\\CW2_OOP\\src\\file.txt"); // Assuming the file is named "ProductData.txt"
            Scanner fileScanner = new Scanner(fileRead);

            while (fileScanner.hasNextLine()) {
                String productType = fileScanner.nextLine();

                if (productType.contains("productId= " + productId)) {
                    // Use a regex pattern to extract the available items
                    Pattern pattern = Pattern.compile("availableItems= (\\d+)");
                    Matcher matcher = pattern.matcher(productType);
                    if (matcher.find()) {
                        availableItems = matcher.group(1);
                        break; // No need to continue searching
                    }
                }
            }

            fileScanner.close(); // Close the scanner after use
        } catch (FileNotFoundException e) {
            // Handle file not found error
            System.err.println("Product data file not found: " + e.getMessage());
            // Optionally, throw an exception or return a default value
        }

        return availableItems;
    }
    // Method to add items to the shopping cart
    private void addingItemsToCart() {
        int selectedRow2 = table1.getSelectedRow();
        if (selectedRow2 != -1) {
            String productId = (String) table1.getValueAt(selectedRow2, 0);
            String productName = (String) table1.getValueAt(selectedRow2, 1);
            String category = (String) table1.getValueAt(selectedRow2, 2);
            String price = (String) table1.getValueAt(selectedRow2, 3);
            String info = (String) table1.getValueAt(selectedRow2, 4);
            String availableItems = getAvailableItems(productId);

            if (Objects.equals(table1.getValueAt(selectedRow2, 2), "clothing")) {

                String[] infoParts = info.split(",");
                if (infoParts.length == 2) {
                    String size = infoParts[0];
                    String color = infoParts[1];
                    int availableItems2 = Integer.parseInt(availableItems);
                    int quantityForAdd = Integer.parseInt(quantity.getText());
                    if (quantityForAdd <= availableItems2) {

                        System.out.println(productId+price+productName+category+availableItems+size+color);
                        ShoppingCart addingAClothingProduct = new ShoppingCart();
                        addingAClothingProduct.addClothingProduct(productId, productName, quantityForAdd, Double.parseDouble(price), color, String.valueOf(size), category);
                    } else {
                        // Handle the case where the quantity exceeds the available items
                        JOptionPane.showMessageDialog(this, "Quantity exceeds the available items!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else if (Objects.equals(table1.getValueAt(selectedRow2, 2), "Electronic")) {
                String[] infoParts = info.split(",");
                if (infoParts.length == 2) {
                    String brand = infoParts[0];
                    String warranty = infoParts[1];
                    String[] warrantyInfo = warranty.split("W");
                    String warrantyTime = warrantyInfo[0];

                    int availableItems2 = Integer.parseInt(availableItems);

                    // Parse the quantity from the JTextField
                    int quantityToAdd = Integer.parseInt(quantity.getText());

                    // Check if the quantity is less than or equal to the available items
                    if (quantityToAdd <= availableItems2) {
                        // Add the product to the shopping cart
                        ShoppingCart addingAnElectronicProduct = new ShoppingCart();
                        addingAnElectronicProduct.addElectronicProduct(productId, productName, quantityToAdd, Double.parseDouble(price), brand, Integer.parseInt(warrantyTime), category);
                    } else {
                        // Handle the case where the quantity exceeds the available items
                        JOptionPane.showMessageDialog(this, "Quantity exceeds the available items!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            for (Product product : ShoppingCart.getCartDetails()) {
                System.out.println(product);
            }
        } else {
            // Handle the case where  any row not selected
            JOptionPane.showMessageDialog(this, "Please select an item before adding to the cart!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to get the total items for a product in the shopping cart
    public int getTotalItems(String productId) {
        int totalItems = 0;
        for (Product product : ShoppingCart.listOfProducts) {
            if (product.getProductId().equals(productId)) {
                totalItems += product.getAvailableItems();
            }
        }
        return totalItems;
    }

}




