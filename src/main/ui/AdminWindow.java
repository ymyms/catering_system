package ui;

import model.Category;
import model.Dish;

import javax.swing.*;
import java.awt.*;

// Represents the admin access page
public class AdminWindow extends JFrame {
    JPanel menuPanel; //menu panel
    GridBagConstraints gbc;

    // Constructor
    // EFFECTS: create a frame with name and call initial() to initialize all the fields
    public AdminWindow() {
        super("Admin Access");
        // Create a panel with GridBagLayout to display dishes
        initial();
    }

    // MODIFIES: this
    // EFFECTS: initialize all the fields, display menu
    private void initial() {
        menuPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        displayMenu();
    }

    // EFFECTS: show the menu
    private void displayMenu() {
        setPanel();
        // Create a button to simulate ordering the selected dishes
        // Set the layout manager
        setLayout(new BorderLayout());
        // Add the menu panel to the center of the frame
        add(new JScrollPane(menuPanel), BorderLayout.CENTER);

        JButton addDishB = new JButton("Add Dish");
        addDishB.addActionListener(e -> addDishToMenu());
        JButton removeDish = new JButton("Remove Dish");
        removeDish.addActionListener(e -> removeDishFromMenu());
        JButton editDish = new JButton("Edit Dish");
        editDish.addActionListener(e -> editDish());
        JButton filterDish = new JButton("Filter (category)");
        filterDish.addActionListener(e -> filterDish());

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(addDishB);
        buttonsPanel.add(removeDish);
        buttonsPanel.add(editDish);
        buttonsPanel.add(filterDish);

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    //EFFECTS: reset the panel
    private void setPanel() {
        // Clear existing components from the menuPanel
        menuPanel.removeAll();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        // Add each dish to the panel
        for (Dish dish : CateringG.dishList) {
            JLabel nameLabel = new JLabel(dish.getDishName() + "  $" + dish.getPrice() + "  ("
                    + dish.getDescription() + ")");
            menuPanel.add(nameLabel, gbc);
            gbc.gridy++;
            // Add some spacing
            gbc.insets = new Insets(5, 0, 5, 0);
        }

        // Repaint the menuPanel to reflect the changes
        menuPanel.revalidate();
        menuPanel.repaint();
    }

    // MODIFIES: CateringG.menu
    // EFFECTS: add dish to the menu
    private void addDishToMenu() {
        setPanel();
        // Get input for the dish attributes
        String dishName = JOptionPane.showInputDialog(this, "Enter Dish Name:");
        String priceInput = JOptionPane.showInputDialog(this, "Enter Dish Price:");
        String description = JOptionPane.showInputDialog(this, "Enter Dish Description:");
        String categoryInput = JOptionPane.showInputDialog(this, "Enter Dish Category "
                + "(APPETIZER, MAIN, DESSERT, DRINK):");

        // Validate and convert the price input to int
        int price;
        try {
            price = Integer.parseInt(priceInput);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid price format. Please enter a valid integer.");
            return; // Exit the method if the price is not a valid integer
        }

        // Validate and convert the category input to the Category enum
        Category category;
        try {
            category = Category.valueOf(categoryInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Invalid category. Please enter a valid category.");
            return; // Exit the method if the category is not valid
        }
        Dish d = new Dish(category,price,description,dishName,0);
        CateringG.menu.addDish(d);
        setPanel();
    }

    // MODIFIES: CateringG.menu
    // EFFECTS: remove the exist dish from the menu
    private void removeDishFromMenu() {
        setPanel();
        // Get the dish name to remove
        String dishToRemove = JOptionPane.showInputDialog(this, "Enter Dish Name to Remove:");

        // Check if the dish name is provided
        if (dishToRemove != null && !dishToRemove.trim().isEmpty()) {
            // Find the corresponding Dish object in your menu and remove it
            for (Dish dish : CateringG.dishList) {
                if (dish.getDishName().equals(dishToRemove)) {
                    CateringG.menu.removeDish(dish);
                    break; // Stop searching after removing the dish
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a valid dish name.");
        }
        setPanel();
    }

    // MODIFIES: CateringG.menu
    // EFFECTS: edit the exist dish on the menu
    private void editDish() {
        setPanel();
        String dishToEdit = JOptionPane.showInputDialog(this, "Enter Dish Name to Edit:");

        if (dishToEdit != null && !dishToEdit.trim().isEmpty()) {
            for (Dish dish : CateringG.dishList) {
                if (dish.getDishName().equals(dishToEdit)) {
                    String[] prompts = {"New Dish Name:", "New Dish Price:", "New Dish Description:",
                            "New Dish Category (APPETIZER, MAIN, DESSERT, DRINK):"};
                    String[] inputs = gatherInputs(prompts);

                    int newPrice = Integer.parseInt(inputs[1]);
                    Category newCategory = Category.valueOf(inputs[3]);

                    if (newPrice != Integer.MIN_VALUE && newCategory != null) {
                        updateDish(dish, inputs[0], newPrice, inputs[2], newCategory);
                        break;
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a valid dish name.");
        }
        setPanel();
    }

    // EFFECTS: put all the input into one string list
    private String[] gatherInputs(String[] prompts) {
        String[] inputs = new String[prompts.length];
        for (int i = 0; i < prompts.length; i++) {
            inputs[i] = JOptionPane.showInputDialog(this, prompts[i]);
        }
        return inputs;
    }

    // EFFECTS: change all the info about one dish based on the new given info
    private void updateDish(Dish dish, String newName, int newPrice, String newDescription, Category newCategory) {
        dish.setDishName(newName);
        dish.setPrice(newPrice);
        dish.setDescription(newDescription);
        dish.setCategory(newCategory);
    }

    // EFFECTS: filter the dish by category
    private void filterDish() {
        Category category = askCategory();
        
        menuPanel.removeAll();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel categoryLabel = new JLabel(category.toString());
        menuPanel.add(categoryLabel, gbc);
        gbc.gridy++;
        for (Dish dish : CateringG.dishList) {
            if (dish.getCategory() == category) {
                JLabel dishLabel = new JLabel(dish.getDishName() + "  $" + dish.getPrice() + "  ("
                        + dish.getDescription() + ")");
                menuPanel.add(dishLabel, gbc);
                gbc.gridy++;

                // Add some spacing
                gbc.insets = new Insets(5, 0, 5, 0);
            }
        }

        // Repaint the menuPanel to reflect the changes
        menuPanel.revalidate();
        menuPanel.repaint();
    }

    private Category askCategory() {
        String categoryInput = JOptionPane.showInputDialog(this, 
                "Enter Dish Category for filter " + "(APPETIZER, MAIN, DESSERT, DRINK):");
        // Validate and convert the category input to the Category enum
        Category category = null;
        try {
            category = Category.valueOf(categoryInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, 
                    "Invalid category. Please enter a valid category.");
        }
        return category;
    }
}
