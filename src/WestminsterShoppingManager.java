import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WestminsterShoppingManager implements shoppingManager {
    public static ArrayList<Product> productList = new ArrayList<>();
    @Override
    public void addNewProducts() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Electronics");
        System.out.println("2. Clothing");
        System.out.print("Select the type of product you want to add:");
        int productChoice = 0; // Initialize productChoice
        while (true) {
            if (scanner.hasNextInt()) {
                productChoice = scanner.nextInt();
                scanner.nextLine();
                if (productChoice == 1 || productChoice == 2) {
                    break;
                } else {
                    System.out.print("\nInvalid input.\nPlease enter 1 or 2: ");
                }
            } else {
                System.out.print("\nInvalid input.Enter correct input to continue.\n Please enter an integer: ");
                scanner.next();
            }
        }
        System.out.println("Enter product id: ");
        String productId = scanner.next();

        System.out.println("Enter Product name: ");
        String productName = scanner.next();

        int numberOfItems = 0; // Initialize numberOfItems
        while (true) {
            System.out.println("Input Number Of items to add: ");
            if (scanner.hasNextInt()) {
                numberOfItems = scanner.nextInt();
                if (numberOfItems > 0) {
                    break;
                } else {
                    System.out.println("Invalid input.Enter correct input to continue. \nNumber of items should be greater than 0: ");
                }
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); // Consume the invalid input
            }
        }
        while (true) {
            if (productList.size() >= 50) {
                System.out.println("No more products can be added!!\n Maximum capacity has been reached.");
                return;
            } else {
                double price = 0.0; // Initialize price
                System.out.println("Enter Price of an item: ");
                while (true) {
                    if (scanner.hasNextDouble()) {
                        price = scanner.nextDouble();
                        if (price > 0) {
                            break;
                        } else {
                            System.out.println("Invalid input. Price should be greater than 0.");
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.next();
                    }
                }
                switch (productChoice) {
                    case 1: // electronic items
                        System.out.println("Enter brand: ");
                        String brand = scanner.next();
                        System.out.println("Enter Warranty period (in months): ");
                        int warrantyPeriod = 0;
                        while (true) {
                            try {
                                warrantyPeriod = scanner.nextInt();
                                if (warrantyPeriod > 0) {
                                    break;
                                } else {
                                    System.out.println("Warranty period must be a positive number. Please enter a valid value: ");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Warranty period must be a number. Please enter a valid value: ");
                                scanner.next();
                            }
                        }
                        // create and add the electronics items
                        Electronics electronicProduct = new Electronics(productId, productName, numberOfItems, price, brand, warrantyPeriod);
                        productList.add(electronicProduct);
                        System.out.println("Item added successfully!!");
                        break;

                    case 2: // clothing
                        System.out.println("Enter size (XXL,XL,L,M,S): ");
                        String size = "";
                        while (true) {
                            size = scanner.next().toUpperCase(); // Convert input to uppercase for case-insensitive comparison
                            if (size.equals("XXL") || size.equals("XL") || size.equals("L") || size.equals("M") || size.equals("S")) {
                                break;
                            } else {
                                System.out.println("Invalid size.\nPlease enter one of the following options: XXL, XL, L, M, S");
                            }
                        }
                        System.out.println("Enter color: ");
                        String color = scanner.next();
                        //add product to the list
                        Clothing clothingProduct = new Clothing(productId, productName, numberOfItems, price, size, color);
                        productList.add(clothingProduct);
                        System.out.println("Item added successfully!!");
                        break;
                    default:
                        System.out.println("Enter valid choice.\nThe product was not added.");
                }
                break;
            }
        }
    }

    @Override
    public void removeProducts() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Remove products part\n");
        System.out.println("Enter the Product id: ");
        String removeProductId = scanner.next();

        Iterator<Product> iterator = productList.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getProductId().equals(removeProductId)) {
                iterator.remove(); // Safely remove the product using the iterator
                System.out.println("Successfully removed product");
                System.out.println("total Number of Products: "+productList.size());
                return; // Exit the method after removing the product
            }
        }
        // If the loop completes without finding the product
        System.out.println("Product with ID " + removeProductId + " not found.");
    }
    @Override
    public void listOfProducts() {
        Collections.sort(productList, (p1, p2) -> p1.getProductName().compareTo(p2.getProductName()));
        for (Product p : productList) {

            System.out.println(p);
        }
    }
    @Override
    public void saveFile(String file) {
        try {
            File addDetails = new File(file);
            FileWriter saveToFile = new FileWriter(addDetails);
            BufferedWriter bufferedWriter = new BufferedWriter(saveToFile);

            for (Product product : productList) {
                bufferedWriter.write(product.toString());
                bufferedWriter.write("\n");
            }
            bufferedWriter.close();
            saveToFile.close();
            System.out.println("Data stored successfully!");

        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }
    @Override
    public void loadFromFile(String file) {
        try {
            File loadFile = new File(file);
            Scanner scannerFile = new Scanner(loadFile);
            while (scannerFile.hasNextLine()) {
                String line = scannerFile.nextLine().trim();
                if (line.startsWith("Electronic")) {
                    Pattern electronicsPattern = Pattern.compile("Electronics\\[ productId= (\\d+), productName= (\\w+), availableItems= (\\d+), price= (\\d+\\.\\d+), brand= (\\w+), warrantyPeriod= (\\d+),type= (\\w+)]");
                    Matcher matchingItem = electronicsPattern.matcher(line);
                    if (matchingItem.find()) {
                        String productId = matchingItem.group(1);
                        String productName = matchingItem.group(2);
                        int availableItems = Integer.parseInt(matchingItem.group(3));
                        double price = Double.parseDouble(matchingItem.group(4));
                        String brand = matchingItem.group(5);
                        int warrantyPeriod = Integer.parseInt(matchingItem.group(6));
                        Electronics electronicsItems = new Electronics(productId, productName, availableItems, price, brand, warrantyPeriod);
                        productList.add(electronicsItems);
                    }
                } else if (line.startsWith("Clothing")) {
                    Pattern clothingPattern = Pattern.compile("Clothing\\[ productId= (\\w+), productName= (\\w+), availableItems= (\\d+), price= (\\d+\\.\\d+), size= (\\w+), color= (\\w+),type= (\\w+)]");
                    Matcher matchingItem = clothingPattern.matcher(line);
                    if (matchingItem.find()) {
                        String productId = matchingItem.group(1);
                        String productName = matchingItem.group(2);
                        int availableItems = Integer.parseInt(matchingItem.group(3));
                        double price = Double.parseDouble(matchingItem.group(4));
                        String size = matchingItem.group(5);
                        String color = matchingItem.group(6);
                        Clothing clothingItem = new Clothing(productId, productName, availableItems, price, size, color);
                        productList.add(clothingItem);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            MenuList();
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice >= 1 && choice <= 6) {
                    handleChoice(choice);
                    if (choice == 6) {
                        break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 6.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the non-integer input
            }
        }
        scanner.close();
    }
    public void handleChoice(int choice) {
        switch (choice) {
            case 1:
                System.out.println("You chose to add a new product.");
                addNewProducts();
                break;
            case 2:
                System.out.println("You chose to remove a product.");
                removeProducts();
                break;
            case 3:
                System.out.println("You chose to view all products.");
                listOfProducts();
                break;
            case 4:
                System.out.println("You chose to save to file.");
                saveFile("C:\\Users\\ishin\\OneDrive\\Documents\\IIT\\YEAR 2\\OOP\\CW\\CW2 Submission\\CW2_OOP\\src\\file.txt");
                break;
            case 5:
                System.out.println("You chose to load from file.");
                loadFromFile("C:\\Users\\ishin\\OneDrive\\Documents\\IIT\\YEAR 2\\OOP\\CW\\CW2 Submission\\CW2_OOP\\src\\file.txt");
                break;
            case 6:
                System.out.println("Exiting...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    public static void MenuList() {
        System.out.println("\nWelcome to the Westminster Shopping Manager:");
        System.out.println("1. Add a new product to the system");
        System.out.println("2. Remove a product from the system");
        System.out.println("3. View all products in the system");
        System.out.println("4. save to File");
        System.out.println("5. load from File");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }
}



