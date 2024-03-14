import java.util.ArrayList;
import java.util.Scanner;
import java.util.Iterator;
//creating book class
class Book{
    String title;
    String author;
    String ISBN;
    int quantity;
    int sold = 0; //this is for showing amount sold in the sales report
    //Constructor
    public Book(String title, String author, String ISBN, int quantity){
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.quantity = quantity;
    }
}

class InventoryManager {
    //declaring arraylist inventory to hold Book objects
    public ArrayList<Book> inventory;
    //declaring an int to keep track of total sales
    private int totalSales;
    public InventoryManager(){
        //innitializing inventory arraylist
        this.inventory = new ArrayList<>();
        //default books
        inventory.add(new Book("The Lorax", "Dr. Seuss", "9780007455935", 20));
        inventory.add(new Book("The Cat in the Hat", "Dr. Seuss", "9780394800011", 45));
        inventory.add(new Book("Green Eggs and Ham", "Dr. Seuss", "9780007355914", 30));
    }
    //creating an addBook method to add books
    public void addBook(String title, String author, String ISBN, int quantity){
        Book book = new Book(title, author, ISBN, quantity);
        inventory.add(book);
    }
    //creating a method to display the inventory
    public void displayInventory(){
        System.out.println("-----Current inventory:-----");
        //iterate through each Book object in the arraylist and print corresponding information
        for(Book book : inventory){
            System.out.println("Title: " + book.title + " Author: " + book.author + " ISBN: " + book.ISBN + " Quantity: " + book.quantity);
        }

    }
    //creating a sellBook method
    public void sellBook(String title, int sellQuantity){
        boolean titleFound = false; //boolean to handle error message
        //iterate through each Book object in the arraylist
        for(Book book : inventory){
            if (book.title.equals(title)){ //if matching title is found
                titleFound = true;//handling for error message
                if (sellQuantity <= book.quantity){ //if amount to sell is less than or equal to amount in stock
                    book.quantity = book.quantity - sellQuantity; //updates quantity in stock by subtracting amount to sell
                    totalSales += sellQuantity; //adding sell quantity to total sales to keep track each time books are sold
                    book.sold = book.sold + sellQuantity; //updating sold amount for whichever book was sold
                    System.out.println("Successfully sold " + sellQuantity + " copies of " + book.title);
                } else {
                    //if amount in stock is less than amount to sell then display error message
                    System.out.println("Sale amount exceeds stock"); 
                }
                break;
            }
        }
        //handling error message
        if(!titleFound){
            System.out.println(title + " not found in system.");
        }
    }
    //method to generate sales report
    public void generateSalesReport(){
        System.out.println("-----Sales Report-----");
        //iterating through each Book object in array list
        for(Book book : inventory){
            //showing amount sold per book
            System.out.println("Title: " + book.title + " Quantity sold: " + book.sold + " Remaining stock: " + book.quantity);
        }
        //showing total sales
        System.out.println("Total quantity sold: " + totalSales);
    }

    //method to update stock
    public void updateStock(String title, int quantity){
        boolean titleFound = false; //boolean for handling error message
        for(Book book : inventory){
            if (book.title.equals(title)){
                titleFound = true;//handling error message
                if(quantity + book.quantity >= 0){ //preventing stock to go below 0
                    book.quantity += quantity; //updating stock
                    System.out.println(title + " successfully updated with " + quantity + " books. current stock is: " + book.quantity ); 
                    break;
                } else{
                    System.out.println("Stock can not go below 0.");
                }
            }
        }
        //error handling message
        if (!titleFound) {
            System.out.println(title + " not found in system.");
        }
    }

    //method to remove book listing
    public void removeBook(String title){
        //had to use iterator to delete Book objects from array
        Iterator<Book> iterator = inventory.iterator();
        boolean removed = false; //error handling

        while (iterator.hasNext()){
            Book book = iterator.next();
            if (book.title.equals(title)){
                totalSales = totalSales - book.sold; //deleting deleted book sales from total sales
                iterator.remove();
                System.out.println(title + " has been successfully removed.");
                removed = true; //error handling
                break;
            }
        }
        //error handling message
        if (!removed){
            System.out.println(title + " not found in the system.");
        }
    }
}

public class BookstoreInventoryManager {
    public static void main(String[] args) {
        InventoryManager inventoryManager = new InventoryManager();
        Scanner sc = new Scanner(System.in);
        //while loop for menu with switch
        int choice;
        while (true) {
            System.out.println("\n-----Main Menu-----");
            System.out.println("1. Display Inventory");
            System.out.println("2. Generate Sales Report");
            System.out.println("3. Add Book");
            System.out.println("4. Sell Book");
            System.out.println("5. Update Stock");
            System.out.println("6. Remove Book");
            System.out.println("7. Exit");

            try{
                choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        //display inventory
                        inventoryManager.displayInventory();
                        break;
                    case 2:
                        //generate sales report
                        inventoryManager.generateSalesReport();
                        break;
                    case 3:
                        //add book
                        System.out.println("Enter book title to add:");
                        sc.nextLine();
                        String title = sc.nextLine();
                        System.out.println("Enter authors name:");
                        String author = sc.nextLine();
                        System.out.println("Enter ISBN:");
                        String ISBN = sc.nextLine();
                        System.out.println("Enter quantity:");
                        int quantity = sc.nextInt();
                        inventoryManager .addBook(title, author, ISBN, quantity);
                        break;
                    case 4:
                        //sell book
                        System.out.println("Enter book title to sell:");
                        sc.nextLine();
                        title = sc.nextLine();
                        System.out.println("Enter quantity:");
                        int sellQuantity = sc.nextInt();
                        inventoryManager.sellBook(title, sellQuantity);
                        break;
                    case 5:
                        System.out.println("Enter book title to update stock:");
                        sc.nextLine();
                        title = sc.nextLine();
                        System.out.println("Enter quantity to update (+/-):");
                        quantity = sc.nextInt();
                        inventoryManager.updateStock(title, quantity);
                        break;
                    case 6:
                        System.out.println("Enter book title to remove");
                        sc.nextLine();
                        title = sc.nextLine();
                        inventoryManager.removeBook(title);
                        break;
                    case 7:
                        //exit
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                    }
            } catch (java.util.InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    sc.next();
                }
            
        }
    }
}
