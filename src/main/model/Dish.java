package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an dish having a category, price, dish name, description and # of ordered
public class Dish implements Writable {
    private String dishName; // the name of the dish
    private String description; // small description of the dish (maybe empty string)
    private Category category; // specific dish category
    private int price; // the price of the dish
    private int num; // number of time the dish has been ordered in one order

    // Constructor
    // REQUIRES: dishName has a non-zero length and price >= 0
    // EFFECTS: category of the dish is set to given category; description is set to
    //          given description; price is set to given price; name of the dish is
    //          set to given dishName and initialize the number of time the dish
    //          has been ordered to 0
    public Dish(Category category, int price, String description, String dishName, int num) {
        this.category = category;
        this.price = price;
        this.description = description;
        this.dishName = dishName;
        this.num = num;
    }

    public Category getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getDishName() {
        return dishName;
    }

    public int getNum() {
        return num;
    }

    public void setPrice(int price) {
        this.price = price;
        EventLog.getInstance().logEvent(new Event("Dish price edited from menu"));
    }

    public void setDescription(String description) {
        this.description = description;
        EventLog.getInstance().logEvent(new Event("Dish description edited from menu"));
    }

    public void setCategory(Category category) {
        this.category = category;
        EventLog.getInstance().logEvent(new Event("Dish category edited from menu"));
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
        EventLog.getInstance().logEvent(new Event("Dish name edited from menu"));
    }

    public void setNum(int num) {
        this.num = num;
    }

    // REQUIRES: num > 0
    // MODIFIES: this
    // EFFECTS: decrease the number of order time by 1
    public void decrease() {
        num--;
    }

    // MODIFIES: this
    // EFFECTS: increase the number of order time by 1
    public void increase() {
        num++;
    }

    // EFFECTS: save dish as JSONObject and return it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", dishName);
        json.put("description", description);
        json.put("category", category);
        json.put("price", price);
        json.put("have been ordered #", num);
        return json;
    }

}
