package bookstoreManagementSystem;

import java.util.*;

class Books 
{
    public String title;
    public String author;
    public int isbn;
    public int quantity;
    public double price;
    public double discount;
    public double total;

    public Books(String title, String author, int isbn, int quantity, double price, double discount, double total) 
    {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.total = total;
    }

    public Books(String title, String author, int isbn, double price, double discount, double total)
    {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.discount = discount;
        this.total = total;
    }

    public String getTitle() 
    {
        return title;
    }

    public String getAuthor() 
    {
        return author;
    }

    public int getQuantity() 
    {
        return quantity;
    }

    public double getPrice() 
    {
        return price;
    }
}

class Admin 
{
    private final String username;
    private String password;

    public Admin(String username, String password) 
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername() 
    {
        return username;
    }

    public String getPassword() 
    {
        return password;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }
}

public class BookstoreManagemetSystem
{
    private final List<Books> books;
    private Admin admin;
    private Scanner scanner;
    private static  String ADMIN_PASSWORD = "1234";
    private static final List<Books> list = new ArrayList<>();

    public BookstoreManagemetSystem() 
    {
        books = new ArrayList<>();
    }

    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);
        int choice;

        System.out.println("******************************Welcome to the Book Store********************************\n");

        // Main Menu
        do 
        {
            System.out.println("Main Menu:");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Exit");
            System.out.print("Please enter your choice: ");
            choice = input.nextInt();
            System.out.println();

            switch (choice) 
            {
                case 1:
                    // Admin part
                    if (authenticateAdmin(input)) 
                    {
                        adminMenu(input, list);
                    } 
                    else 
                    {
                        System.out.println("Access denied. Incorrect password.");
                    }
                    break;
                case 2:
                    // User part
                    userMenu(input, list);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (true);
    }

    private static boolean authenticateAdmin(Scanner input) 
    {
        System.out.print("Enter admin password: ");
        String password = input.next();
        return password.equals(ADMIN_PASSWORD);
    }

    private static void adminMenu(Scanner input, List<Books> list) 
    {
        do 
        {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add book");
            System.out.println("2. Remove book");
            System.out.println("3. Update book price");
            System.out.println("4. Change admin password");
            System.out.println("5. Back to Main Menu");
            System.out.print("Please enter your choice: ");
            int choice = input.nextInt();
            System.out.println();

            switch (choice) 
            {
                case 1:
                    displayBook(input, list);
                    break;
                case 2:
                    rmbook(input, list);
                    break;
                case 3:
                    updateBookPrice(input, list);
                    break;
                case 4:
                    changeAdminPassword(input);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (true);
    }

    private static void userMenu(Scanner scan, List<Books> list) 
    {
        boolean quit = false;
        List<Books> boughtBooks = new ArrayList<>();
        List<Double> boughtPrices = new ArrayList<>();
         
        do 
        {
            System.out.println("\nUser Menu:");
            System.out.println("1. List books");
            System.out.println("2. Buy book");
            System.out.println("3. Receipt");
            System.out.println("4. Back to Main Menu");
            System.out.print("Please enter your choice: ");
            int choice = scan.nextInt();
            System.out.println();
            switch (choice) 
            {
                case 1:
                    listBooks(list);
                    break;
                case 2:
                    buyBook(scan, list, boughtBooks, boughtPrices);
                    break;
                case 3:
                    generateReceipt(boughtBooks, boughtPrices);
                    break;
                case 4:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (!quit);
    }

    public void viewBooks() {
        System.out.println("Available books:");
        for (Books book : books) 
        {
            System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthor() + ", Quantity: " + book.getQuantity() + ", Price: " + book.getPrice());
        }
    }

    private static void listBooks(List<Books> list) 
    {
        System.out.println("List of Books:");
        System.out.println("Title---------------------------------Price");
        for (Books book : list) 
        {
            try
            {
                System.out.println(book.title + "---------------------------------" + book.price);
            }
            catch(NullPointerException e)
            {
                System.out.println("There are no available books on the list");
            }
        }
    }

    private static void buyBook(Scanner input, List<Books> list, List<Books> boughtBooks, List<Double> boughtPrices) 
    {
        System.out.print("Enter the title of the book you want to buy: ");
        String title = input.next();
        boolean found = false;
        for (Books book : list) 
        {
            if (book.title.equalsIgnoreCase(title)) 
            {
                found = true;
                System.out.println("Book Details:");
                System.out.println("Title: " + book.title);
                System.out.println("Author: " + book.author);
                System.out.println("ISBN: " + book.isbn);
                System.out.println("Quantity: " +book.quantity);
                System.out.println("Price: " + book.price);
                System.out.println("Discount: " + book.discount+"%");
                System.out.print("Do you want to buy this book? (yes/no): ");
                String buyChoice = input.next();
                if (buyChoice.equalsIgnoreCase("yes")) 
                {
                    boughtBooks.add(book);
                    boughtPrices.add(book.price);
                    System.out.println("------------------Book bought successfully!-------------------------\n");
                } 
                else if (buyChoice.equalsIgnoreCase("no")) 
                {
                    System.out.println("Purchase canceled.\n");
                } 
                else 
                {
                    System.out.println("Invalid choice. Purchase canceled.\n");
                }
                break;
            }
        }
        if (!found) 
        {
            System.out.println("Book not found.\n");
        }
    }

    private static void generateReceipt(List<Books> boughtBooks, List<Double> boughtPrices) 
    {
        if (boughtBooks.isEmpty()) 
        {
            System.out.println("No books bought yet.\n");
            return;
        }
        System.out.println("Receipt:");
        for (int i = 0; i < boughtBooks.size(); i++) 
        {
            System.out.println((i + 1) + ". " + boughtBooks.get(i).title + " - $" + ((boughtBooks.get(i).price)-(boughtBooks.get(i).discount*boughtPrices.get(i))));
        }
        System.out.println();
    }

    private static void displayBook(Scanner input, List<Books> list) 
    {
        System.out.print("Enter Book Title: ");
        String title = input.next();
        input.nextLine(); // Consume newline
        System.out.print("Enter Author: ");
        String author = input.nextLine();

           // Check if book already exists in the list
            for (Books book : list) 
            {
                if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
                    System.out.println("****************************The book is already in the store.*****************************");
                    return;
                }
            }
        System.out.print("Enter ISBN: ");
        int isbn = input.nextInt();
        System.out.print("Enter Price: ");
        double price = input.nextDouble();
        System.out.print("Enter Discount: ");
        double discount = input.nextDouble();
        double total = price - (discount * price);     
        
        // If book does not exist in the list, add it

        list.add(new Books(title, author, isbn, 1, price, discount, total));
        System.out.println("------------------------------Book added successfully!--------------------------------------\n");
    }
    private static void rmbook(Scanner input, List<Books> list) 
    {
            if(list.isEmpty())
            {
                System.out.println("*****************************The store is Empty*****************************");
            }
            else
            {
                System.out.print("Enter the title of the book to remove: ");
                String title = input.next();
                input.nextLine(); 
                for (Books book : list) 
                {
                    if(book.getTitle().equals(title))
                    {
                        System.out.print("Are you sure you want to delete the book "+ title + "? -- (yes / no)");
                        String response = input.next();
                        input.nextLine();
                        if(response.equalsIgnoreCase("No"))
                        {
                            System.out.print("----------------------------Book "+ title + "is not removed-------------------------------");
                            return;
                        }
                        else if(response.equalsIgnoreCase("Yes"))
                        {
                            list.remove(book);
                            System.out.println("-------------------- Book removed successfully!---------------------------\n");
                            return;
                        }
                        System.out.println(book.getTitle()+"------------------------"+book.getAuthor());
                    }
                    else
                    {
                        System.out.println("********************************The book is not found in the store!******************");
                    }
                }
            }
    }

    private static void updateBookPrice(Scanner input, List<Books> list) 
    {
        System.out.print("Enter the title of the book to update price: ");
        String title = input.next();
        boolean found = false;
        for (Books book : list) 
        {
            if (book.title.equalsIgnoreCase(title)) 
            {
                found = true;
                System.out.print("Enter new price: ");
                double newPrice = input.nextDouble();
                book.price = newPrice;
                System.out.println("----------------------Price updated successfully!--------------------------\n");
                break;
            }
        }
        if (!found) 
        {
            System.out.println("Book not found.\n");
        }
    }

    private static void changeAdminPassword(Scanner input) 
    {
        System.out.print("Enter current password: ");
        String currentPassword = input.next();
        if (currentPassword.equals(ADMIN_PASSWORD)) 
        {
            System.out.print("Enter new password: ");
            String newPassword = input.next();
                   ADMIN_PASSWORD = newPassword;
            System.out.println("----------------------Password changed successfully!-----------------");
        } 
        else 
        {
            System.out.println("Invalid current password.");
        }
    }
}

