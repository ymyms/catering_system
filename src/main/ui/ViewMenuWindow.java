package ui;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Represents the viewing menu page
public class ViewMenuWindow extends JFrame {

    private JPanel menuPanel; //menu panel
    private List<JCheckBox> checkBoxes; //list of checkboxes in front of dish
    private List<JSpinner> quantitySpinners; //list of quantity spinners in front of dish
    private JPanel summaryPanel; //summary panel
    private JButton placeOrderButton; //order button

    // Constructor
    // EFFECTS: create a frame with name and call initialize() to initialize all the fields
    public ViewMenuWindow() {
        super("View Menu");
        initialize();
    }

    // MODIFIES: this
    // EFFECTS: initialize all the fields, display menu and setup
    private void initialize() {
        menuPanel = new JPanel(new GridBagLayout());
        checkBoxes = new ArrayList<>();
        quantitySpinners = new ArrayList<>();
        displayMenu();
        menuInitial();
        add(new JScrollPane(menuPanel), BorderLayout.CENTER);
        add(createSummaryPanel(), BorderLayout.EAST);
        add(createOrderButton(), BorderLayout.SOUTH);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setVisible(true);
    }

    // MODIFIES: this, gbc, checkBox, spinner
    // EFFECTS: show the menu
    private void displayMenu() {
        GridBagConstraints gbc = gbcInitial();
        for (Dish dish : CateringG.dishList) {
            JCheckBox checkBox = new JCheckBox();
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 0, 100, 1));
            spinner.setEnabled(false); // Initially disabled

            JLabel dishLabel = new JLabel(dish.getDishName() + "  $" + dish.getPrice() + "  ("
                    + dish.getDescription() + ")");
            gbc.gridx = 0;
            menuPanel.add(checkBox, gbc);
            gbc.gridx = 1;
            menuPanel.add(spinner, gbc);
            gbc.gridx = 2;
            menuPanel.add(dishLabel, gbc);

            checkBox.addActionListener(e -> {
                spinner.setEnabled(checkBox.isSelected());
                updateSummaryPanel(dish, checkBox.isSelected());
            });
            spinner.addChangeListener(e -> updateSummaryPanel(dish, (int) spinner.getValue()));
            checkBoxes.add(checkBox);
            quantitySpinners.add(spinner);
            gbc.gridy++;
            gbc.insets = new Insets(5, 0, 5, 0);
        }
    }

    // MODIFIES: this
    // EFFECTS: reset the menu
    private void menuInitial() {
        menuPanel.revalidate();
        menuPanel.repaint();
    }

    // MODIFIES: gbc
    // EFFECTS: setup GridBagConstraints
    private GridBagConstraints gbcInitial() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        return gbc;
    }

    // MODIFIES: this
    // EFFECTS: setup summaryPanel
    private JPanel createSummaryPanel() {
        summaryPanel = new JPanel(new GridBagLayout());
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Order Summary"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel totalPriceLabel = new JLabel("Total Price: $0.00");
        JLabel totalQuantityLabel = new JLabel("Total Quantity: 0");

        gbc.insets = new Insets(5, 10, 5, 10);
        summaryPanel.add(totalPriceLabel, gbc);
        gbc.gridy++;
        summaryPanel.add(totalQuantityLabel, gbc);

        return summaryPanel;
    }

    // EFFECTS: update summaryPanel based on the selections
    private void updateSummaryPanel(Dish dish, boolean selected) {
        if (selected) {
            CateringG.order.addOrder(dish.getDishName(),CateringG.menu);
        } else {
            CateringG.order.removeOrder(dish.getDishName());
        }
        updateSummaryLabels();
    }

    // EFFECTS: update summaryPanel based on the quantity
    private void updateSummaryPanel(Dish dish, int quantity) {
        dish.setNum(quantity);
        updateSummaryLabels();
    }

    // EFFECTS: update summaryPanel info
    private void updateSummaryLabels() {
        int totalPrice = 0;
        int totalQuantity = 0;

        for (Dish d : CateringG.order.getOrderList()) {
            int quantity = d.getNum();

            totalPrice += d.getPrice() * quantity;
            totalQuantity += quantity;
        }

        JLabel totalPriceLabel = (JLabel) summaryPanel.getComponent(0);
        JLabel totalQuantityLabel = (JLabel) summaryPanel.getComponent(1);

        totalPriceLabel.setText("Total Price: $" + totalPrice);
        totalQuantityLabel.setText("Total Quantity: " + totalQuantity);
    }

    // EFFECTS: create a place order button and return it
    private JButton createOrderButton() {
        placeOrderButton = new JButton("Place Order");
        placeOrderButton.addActionListener(e -> placeOrder());
        return placeOrderButton;
    }

    // MODIFIES: placeOrderButton, orderDetails, CateringG.order
    // EFFECTS: place order and show the recipe
    private void placeOrder() {
        popImage();

        StringBuilder orderDetails = new StringBuilder("Order Details:\n");

        for (Dish d : CateringG.order.getOrderList()) {
            int quantity = d.getNum();
            orderDetails.append(d.getDishName()).append(" - Quantity: ").append(quantity).append("\n");
        }
        JOptionPane.showMessageDialog(this, orderDetails.toString(), "Order Placed", JOptionPane.INFORMATION_MESSAGE);
        updateSummaryLabels();
        placeOrderButton.setEnabled(false);
        enableCheckBoxes(true);
        CateringG.order.placeOrder();
    }

    // EFFECTS: visual component
    private void popImage() {
        JFrame f = new JFrame();
        f.setSize(200,152);
        f.setResizable(false);
        f.setDefaultCloseOperation(f.DISPOSE_ON_CLOSE);

        //create imageicon
        ImageIcon icon = new ImageIcon("/Users/yingmingsha/Desktop/CPSC210/project_r0c5s/src/main/ui/purchase.gif");
        JLabel label = new JLabel();
        label.setSize(200,152);
        label.setIcon(icon);

        //add imageicon to the frame
        f.add(label);

        //set visible
        f.setVisible(true);
    }

    // EFFECTS: enable all the checkboxes
    private void enableCheckBoxes(boolean enable) {
        // Enable or disable all checkboxes based on the provided boolean value
        for (JCheckBox checkBox : checkBoxes) {
            checkBox.setEnabled(enable);
        }
    }
}