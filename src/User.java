import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class User {
    private String username;
    private String password;
    public static ArrayList<User> usersList = new ArrayList<>();
    public static ArrayList <String> purchaseDetails = new ArrayList<>();
    //Constructor
    public User(String username,String password){
        this.username   =  username;
        this.password   =  password;
    }
    // Getters and setters
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
    public static boolean checkCustomer(String userName){
        boolean bool = false;
        for (String customer : purchaseDetails){
            if (customer.equals(userName)){
                System.out.println(customer);
                bool = true;
            }
        }
        return bool;
    }
    public void saveData(String fileName){
        try {
            File allDetails = new File(fileName);
            FileWriter writeDetails = new FileWriter(allDetails,true);
            for (String customer : purchaseDetails) {
                writeDetails.write(customer);
                writeDetails.write("\n");
            }
            writeDetails.close();
            System.out.println("Data Stored Successfully!");
        } catch (IOException e) {
            System.out.println("Error while Saving to the file!");
        }
    }
    public static void userDataLoad(String fileName) {
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                purchaseDetails.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
    }
}
