package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents an order from a table with order status, total price of the order when placed
public class Order implements Writable {
    private List<Dish> order; // order of the dishes
    private String table; // table name for tracking
    private boolean isOrder; // if the order has been placed
    private int totalPrice; // order total price

    // Constructor
    // REQUIRES: tableName has a non-zero length
    // EFFECTS: name of the restaurant is set to restName; create an empty order;
    //          set the isOrder to false; set the status to prepare; and initialized
    //          the total price
    public Order(String tableName) {
        this.order = new ArrayList<>();
        this.table = tableName;
        isOrder = false;
        totalPrice = 0;
    }

    public List<Dish> getOrderList() {
        return order;
    }

    public String getOrderTable() {
        return table;
    }

    public boolean getIsOrder() {
        return isOrder;
    }

    // MODIFIES: this
    // EFFECTS: calculate the total price of the order
    public int getTotalPrice() {
        if (!order.isEmpty()) {
            totalPrice = 0;
            for (Dish o : order) {
                totalPrice = totalPrice + o.getPrice() * o.getNum();
            }
        }

        return totalPrice;
    }

    // MODIFIES: this
    // EFFECTS: set the isOrder to true
    public void placeOrder() {
        isOrder = true;
        EventLog.getInstance().logEvent(new Event("The order is placed."));
    }

    // REQUIRES: the name need to exactly match the menu
    // MODIFIES: this
    // EFFECTS: add a dish to the order list when the given dish name match the menu
    //          when the order is not placed and the dish has not previously added;
    //          if the dish name already in the order list add the number of time it get
    //          ordered, otherwise it does not change anything
    public void addOrder(String name, Menu menu) {
        List<Dish> d = menu.getDishList();
        boolean isAdd = false;
        if (!isOrder) {
            if (order.isEmpty()) {
                checkIf(name,d);
            } else {
                for (Dish o : order) {
                    if (name.equals(o.getDishName())) {
                        o.increase();
                        isAdd = true;
                    }
                }
                if (!isAdd) {
                    checkIf(name,d);
                }
            }
        }
        EventLog.getInstance().logEvent(new Event(name + "(dish) added to order"));
    }

    // MODIFIES: this
    // EFFECTS: add a dish to the order list
    public void addOrder(Dish d) {
        order.add(d);
    }

    // REQUIRES: the name need to exactly match the menu
    // MODIFIES: this
    // EFFECTS: add a dish to the order list and increase number of time
    //          the dish get ordered
    private void checkIf(String name, List<Dish> d) {
        for (Dish dish : d) {
            if (name.equals(dish.getDishName())) {
                dish.increase();
                order.add(dish);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: remove a specific dish from the order (reduce number of order time
    //          if the dish ordered more than 1 time, otherwise remove from the list)
    //          when the order is not placed and the order list is not empty,
    //          otherwise it does not change anything
    public void removeOrder(String name) {
        if (!isOrder && !order.isEmpty()) {
            for (Dish dish : order) {
                if (name.equals(dish.getDishName())) {
                    dish.decrease();
                    if (dish.getNum() == 0) {
                        order.remove(dish);
                        break;
                    }
                }
            }
        }
        EventLog.getInstance().logEvent(new Event(name + "(dish) removed from the order"));
    }

    // EFFECTS: save order as JSONObject and return it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Table name", table);
        json.put("Order", dishToOrder());
        json.put("Order Total Price", totalPrice);
        json.put("If Ordered", isOrder);
        return json;
    }

    // EFFECTS: returns dishes in this order as a JSON array
    private JSONArray dishToOrder() {
        JSONArray jsonArray = new JSONArray();

        for (Dish d : order) {
            jsonArray.put(d.toJson());
        }

        return jsonArray;
    }

}
