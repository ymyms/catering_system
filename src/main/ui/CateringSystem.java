package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.MenuAndOrder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

//Console
public class CateringSystem {

    private Menu menu; // menu
    private Dish d1; // sample dish 1
    private Dish d2; // sample dish 2
    private Dish d3; // sample dish 3
    private Dish d4; // sample dish 4
    private Scanner s1; // receive user input
    private boolean going; // boolean keep placeOrder() loop
    private String name; // table name
    private Order order; // an order
    private List<Dish> dishList; // menu list

    private static final String JSON_STORE = "./data/orderandmenu.json";
    private JsonWriter jsonWriter; // writer
    private JsonReader jsonReader; // reader
    private MenuAndOrder mao; // menu and order

    // Constructor
    // EFFECTS: declare the scanner, menu, dishes, order, writer, reader and add dishes to the menu
    public CateringSystem() {
        s1 = new Scanner(System.in);
        this.menu = new Menu("KIKI RESTAURANT");
        d1 = new Dish(Category.APPETIZER, 15,"Korean spicy stir-fried rice cake.",
                "Tteok-Bokki",0);
        d2 = new Dish(Category.MAIN, 18,"Beef,vegetables, and yolk, soft tofu soup.",
                "Beef Tofu Soup",0);
        d3 = new Dish(Category.DESSERT, 18,"",
                "Cheese Cake",0);
        d4 = new Dish(Category.DRINK, 3,"",
                "Coca Cola",0);
        menu.addDish(d1);
        menu.addDish(d2);
        menu.addDish(d3);
        menu.addDish(d4);
        order = new Order("Costumer");
        dishList = menu.getDishList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: prompt user and give all the choices they can select
    private void userPrompt() {
        System.out.println("\nSelect from:");
        System.out.println("\tv -> view menu");
        System.out.println("\ta -> admin access");
        System.out.println("\ts -> save order and menu to file");
        System.out.println("\tl -> load order and menu from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: keep prompt user for giving them options to choose;
    //          end the system if user input "q"
    public void setUp() {
        boolean keepGoing = true;
        String choice = null;
        while (keepGoing) {
            userPrompt();
            choice = s1.next();
            if (choice.equals("q")) {
                clearNum();
                System.out.println("EXISTING THE SYSTEM......");
                keepGoing = false;
            } else {
                processChoice(choice);
            }
        }
    }

    // EFFECTS: launch different function regarding the choice of user
    private void processChoice(String choice) {
        if (choice.equals("v")) {
            //askTable();
            displayMenu();
            menuPrompt();
        } else if (choice.equals("a")) {
            adminChoice();
        } else if (choice.equals("s")) {
            saveFile();
        } else if (choice.equals("l")) {
            loadFile();
        } else {
            System.out.println("INVALID CHOICE. PLEASE TRY AGAIN.");
        }
    }

    // EFFECTS: print out the menu
    public void displayMenu() {
        System.out.println("\n ================ MENU ================ ");
        for (Dish d : menu.getDishList()) {
            System.out.println(d.getDishName() + " $" + d.getPrice());
            System.out.println(d.getDescription());
        }
        System.out.println(" ========================================== ");
    }

    // MODIFIES: this
    // EFFECTS: ask the user about the table name and return the name
    private void askTable() {
        System.out.println("Please enter your table name: ");
        name = s1.nextLine();
        order = new Order(name);
    }

    // MODIFIES: this
    // EFFECTS: keep prompt user by giving them options - order
    private void menuPrompt() {
        going = true;
        String choice = null;
        while (going) {
            orderSelection();
            choice = s1.next();
            if (choice.equals("q")) {
                going = false;
            } else {
                processSelection(choice);
            }
        }
    }

    // EFFECTS: launch different order function regarding the choice of user
    private void processSelection(String choice) {
        if (choice.equals("m")) {
            makeOrder();
        } else if (choice.equals("r")) {
            reOrder();
        } else if (choice.equals("p")) {
            place();
        } else if (choice.equals("v")) {
            view();
        } else {
            System.out.println("INVALID SELECTION. PLEASE TRY AGAIN.");
        }
    }

    // MODIFIES: menu.num
    // EFFECTS: clear all the order times once placeOrder() end avoid
    //          previous record of number of time when create a new order
    private void clearNum() {
        for (Dish num : menu.getDishList()) {
            num.setNum(0);
        }
    }

    // EFFECTS: provide options for user about order
    private void orderSelection() {
        System.out.println("\nSelect from:");
        System.out.println("\tm -> make order");
        System.out.println("\tr -> remove order");
        System.out.println("\tp -> place order");
        System.out.println("\tv -> view order");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: add dish to the order based on user's given dish name
    private void makeOrder() {
        s1.nextLine();
        System.out.println("\nPlease enter the exact dish name you want to order: ");
        String add = s1.nextLine();
        order.addOrder(add,menu);
        System.out.println("-add " + add + " to the order list-");
    }


    // MODIFIES: this
    // EFFECTS: remove dish from the order based on user's given dish name
    private void reOrder() {
        if (!order.getOrderList().isEmpty()) {
            s1.nextLine();
            System.out.println("\nPlease enter the exact dish name you want to remove: ");
            String remove = s1.nextLine();
            order.removeOrder(remove);
            System.out.println("-remove " + remove + " from the order list-");
        } else {
            System.out.println("\nSorry, you have not added anything to the list.");
        }
    }

    // MODIFIES: this
    // EFFECTS: place the order and print out the recipe & total price
    private void place() {
        order.placeOrder();
        System.out.println("\n");
        for (Dish dish : order.getOrderList()) {
            System.out.println(dish.getDishName() + " x" + dish.getNum() + " $" + dish.getPrice());
        }
        System.out.println("Total price (placed): $" + order.getTotalPrice());
        System.out.println("\n");
        going = false;
    }

    // EFFECTS: print out the order that has not placed
    private void view() {
        List<Dish> d = order.getOrderList();
        if (d.isEmpty()) {
            System.out.println("\nYou have not ordered any food.");
        } else {
            System.out.println("\n--------------Order List-------------");
            for (Dish dish : d) {
                System.out.println("$" + dish.getPrice() + " " + dish.getDishName() + " x" + dish.getNum());
            }
            System.out.println("Total: $" + order.getTotalPrice());
            System.out.println("--------------------------------------");
        }
    }

    // EFFECTS: display the current menu and prompt staff
    private void staffPrompt() {
        displayMenu();
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add dish");
        System.out.println("\tr -> remove dish");
        System.out.println("\te -> edit dish");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: admin, choice
    // EFFECTS: keep prompt staff to make choices
    private void adminChoice() {
        Boolean admin = true;
        String choice = null;

        while (admin) {
            staffPrompt();
            choice = s1.next();
            if (choice.equals("q")) {
                admin = false;
            } else {
                processStaffSelection(choice);
            }
        }
    }

    // EFFECTS: provide options for staff about menu
    private void processStaffSelection(String choice) {
        if (choice.equals("a")) {
            addDishToMenu();
        } else if (choice.equals("r")) {
            removeDishFromMenu();
        } else if (choice.equals("e")) {
            editDish();
        } else {
            System.out.println("INVALID SELECTION. PLEASE TRY AGAIN.");
        }
    }

    // MODIFIES: this
    // EFFECTS: add dish to the menu
    private void addDishToMenu() {
        s1.nextLine();
        System.out.println("Please enter the dish name you want to add: ");
        String name = s1.nextLine();

        System.out.println("Please enter the category the dish belongs (number)");
        System.out.println("1: APPETIZER/ 2: MAIN/ 3:DESSERT/ 4.DRINK: ");
        int category = s1.nextInt();
        s1.nextLine();

        System.out.println("Please enter the price of the dish you want to set: ");
        int price = s1.nextInt();
        s1.nextLine();

        System.out.println("Please enter the description of the dish: ");
        String description = s1.nextLine();

        Category c = checkCategory(category);
        Dish d = new Dish(c,price,description,name,0);
        menu.addDish(d);
    }

    // MODIFIES: this
    // EFFECTS: remove the exist dish from the menu
    private void removeDishFromMenu() {
        displayMenuInOrder();
        System.out.println("Please enter the dish number that you want to remove: ");
        int n = s1.nextInt();
        menu.removeDish(dishList.get(n - 1));
    }

    // MODIFIES: this
    // EFFECTS: edit the exist dish on the menu
    private void editDish() {
        displayMenuInOrder();

        System.out.println("Please enter the dish number that you want to edit: ");
        int n = s1.nextInt();
        s1.nextLine();
        Dish d = dishList.get(n - 1);

        System.out.println("Please enter the new dish name: ");
        String name = s1.nextLine();
        d.setDishName(name);

        System.out.println("Please enter the new dish price: ");
        int price = s1.nextInt();
        s1.nextLine(); // Consume the newline character
        d.setPrice(price);

        System.out.println("Please enter the new dish description: ");
        String description = s1.nextLine();
        d.setDescription(description);

        System.out.println("Please enter the new dish category");
        System.out.println("1: APPETIZER/ 2: MAIN/ 3: DESSERT/ 4: DRINK:");
        int category = s1.nextInt();
        s1.nextLine(); // Consume the newline character
        Category c = checkCategory(category);
        d.setCategory(c);
    }

    // EFFECT: display the dishes on menu
    private void displayMenuInOrder() {
        int i = 1;
        for (Dish d : dishList) {
            System.out.println(i + ". " + d.getDishName());
            i++;
        }
    }

    // MODIFIES: c
    // EFFECT: based on the given int to return a corresponding Category
    private Category checkCategory(int category) {
        Category c = null;
        if (category == 1) {
            c = Category.APPETIZER;
        } else if (category == 2) {
            c = Category.MAIN;
        } else if (category == 3) {
            c = Category.DESSERT;
        } else {
            c = Category.DRINK;
        }
        return c;
    }

    // EFFECTS: saves mao (menu and order) to file
    private void saveFile() {
        try {
            mao = new MenuAndOrder(menu, order);
            jsonWriter.open();
            jsonWriter.write(mao);
            jsonWriter.close();
            System.out.println("Saved " + mao.getOrder().getOrderTable() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: loads mao (menu and order) to file
    private void loadFile() {
        try {
            mao = jsonReader.read();
            menu = mao.getMenu();
            order = mao.getOrder();
            System.out.println("Loaded " + mao.getOrder().getOrderTable() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


}
