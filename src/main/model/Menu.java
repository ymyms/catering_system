package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents an menu with a list of dishes
public class Menu implements Writable {
    protected List<Dish> menu; // list of dishes
    private String restName; //name of the restaurant

    // Constructor
    // REQUIRES: restName has a non-zero length
    // EFFECTS: name of the restaurant is set to restName; create an empty menu
    public Menu(String restName) {
        this.menu = new ArrayList<>();
        this.restName = restName;
    }

    public List<Dish> getDishList() {
        return menu;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String n) {
        restName = n;
    }

    // MODIFIES: this
    // EFFECTS: add a dish to the menu
    public void addDish(Dish dish) {
        menu.add(dish);
        EventLog.getInstance().logEvent(new Event(dish.getDishName() + "(dish) added to menu"));
    }

    // MODIFIES: this
    // EFFECTS: remove a specific dish from the menu
    public void removeDish(Dish dish) {
        if (!menu.isEmpty()) {
            menu.remove(dish);
            EventLog.getInstance().logEvent(new Event(dish.getDishName() + "(dish) removed from menu"));
        }
    }

    // EFFECTS: save menu as JSONObject and return it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Rest name", restName);
        json.put("Menu", dishToMenu());
        return json;
    }

    // MODIFIES: this, jsonArray
    // EFFECTS: returns dishes in this menu as a JSON array
    private JSONArray dishToMenu() {
        JSONArray jsonArray = new JSONArray();

        for (Dish d : menu) {
            jsonArray.put(d.toJson());
        }

        return jsonArray;
    }
}
