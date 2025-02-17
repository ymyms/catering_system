package persistence;

import model.Menu;
import model.Order;

// Represents an Menu and an Order together (for save and load purpose)
public class MenuAndOrder {
    private Menu menu; // menu
    private Order order; //order

    // Constructor
    // EFFECTS: set menu as given menu; set order as given order
    public MenuAndOrder(Menu menu, Order order) {
        this.menu = menu;
        this.order = order;
    }

    public Menu getMenu() {
        return menu;
    }

    public Order getOrder() {
        return order;
    }
}
