package ui;

import model.*;
import model.Event;
import model.Menu;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.MenuAndOrder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

//GUI System for Customer & Staff
public class CateringG extends JFrame implements ActionListener {

    public static final int WIDTH = 1000; // frame width
    public static final int HEIGHT = 700; // frame height
    protected static Menu menu; // menu
    private Dish d1; // sample dish 1
    private Dish d2; // sample dish 2
    private Dish d3; // sample dish 3
    private Dish d4; // sample dish 4
    protected static Order order; // an order
    protected static List<Dish> dishList; // menu list
    private JPanel panel; // panel
    private JButton viewMenuButton; // view menu button
    private JButton adminButton; // admin access button
    private JButton saveButton; // save button
    private JButton loadButton; // load button
    private static final String JSON_STORE = "./data/orderandmenu.json";
    private JsonWriter jsonWriter; // writer
    private JsonReader jsonReader; // reader
    private MenuAndOrder mao; // menu and order

    // Constructor
    // EFFECTS: set up
    public CateringG() {
        super("Catering System");
        initialField();
        setUpPanel();
        setUpFrame();
    }

    // EFFECTS: when the frame close, it clear all the menu num
    private void clearNum() {
        for (Dish num : menu.getDishList()) {
            num.setNum(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: initialise all the fields
    private void initialField() {
        this.menu = new Menu("KIKI RESTAURANT");
        d1 = new Dish(Category.APPETIZER, 15,"Korean spicy stir-fried rice cake.",
                "Tteok-Bokki",0);
        d2 = new Dish(Category.MAIN, 18,"Beef,vegetables, and yolk, soft tofu soup.",
                "Beef Tofu Soup",0);
        d3 = new Dish(Category.DESSERT, 18,"sweet dessert",
                "Cheese Cake",0);
        d4 = new Dish(Category.DRINK, 3,"pop",
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

    // MODIFIES: this
    // EFFECTS: set up frame
    private void setUpFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new GridBagLayout());
        add(panel);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleWindowClosing();
            }
        });
        setVisible(true);
    }

    // EFFECTS: Handle the window closing event
    private void handleWindowClosing() {
        EventLog eventLog = EventLog.getInstance();
        System.out.println("Events logged since application started:");
        for (Event event : eventLog) {
            System.out.println(event);
        }
        // Close the application
        System.exit(0);
    }


    // MODIFIES: this
    // EFFECTS: set up panel
    private void setUpPanel() {
        panel = new JPanel(new GridLayout(4, 1));
        viewMenuButton = createButton("View Menu", e -> showNewWindow(new ViewMenuWindow()));
        adminButton = createButton("Admin Access", e -> showNewWindow(new AdminWindow()));
        saveButton = createButton("Save Order and Menu", e -> handleSaveButton());
        loadButton = createButton("Load Order and Menu", e -> handleLoadButton());
        panel.add(viewMenuButton);
        panel.add(adminButton);
        panel.add(saveButton);
        panel.add(loadButton);
    }

    // EFFECTS: load menu and order
    private void handleLoadButton() {
        try {
            mao = jsonReader.read();
            menu = mao.getMenu();
            order = mao.getOrder();
            dishList = menu.getDishList();
            JOptionPane.showMessageDialog(this,"Loaded "
                    + mao.getOrder().getOrderTable() + " from " + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,"Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: save menu and order
    private void handleSaveButton() {
        try {
            mao = new MenuAndOrder(menu, order);
            jsonWriter.open();
            jsonWriter.write(mao);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this,
                    "Saved " + mao.getOrder().getOrderTable() + " to " + JSON_STORE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: create button
    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        return button;
    }

    // EFFECTS: setup new window
    private void showNewWindow(JFrame newWindow) {
        this.setVisible(false); // Hide the current window
        newWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        newWindow.setSize(WIDTH, HEIGHT);
        newWindow.setLocationRelativeTo(this);
        newWindow.setVisible(true);

        newWindow.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                CateringG.this.setVisible(true); // Show the previous window on close
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args){
    }
}