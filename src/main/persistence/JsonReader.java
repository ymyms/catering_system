package persistence;

import model.Category;
import model.Dish;
import model.Menu;
import model.Order;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads MenuAndOrder from JSON data stored in file
public class JsonReader {
    private String source;
    private Menu menu;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads MenuAndOrder from file and returns it;
    //          throws IOException if an error occurs reading data from file
    public MenuAndOrder read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMenuAndOrder(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses MenuAndOrder from JSON object and returns it
    private MenuAndOrder parseMenuAndOrder(JSONObject jsonObject) {
        menu = parseMenu(jsonObject.getJSONObject("menu"));
        Order order = parseOrder(jsonObject.getJSONObject("order"));
        return new MenuAndOrder(menu,order);
    }

    // EFFECTS: parses order from JSON object and returns it
    private Order parseOrder(JSONObject jsonObject) {
        String name = jsonObject.getString("Table name");
//        int totalPrice = jsonObject.getInt("Order Total Price");
//        boolean isOrder = jsonObject.getBoolean("If Ordered");
        Order o = new Order(name);
        addDishesToOrder(o, jsonObject);
        return o;
    }

    // MODIFIES: o
    // EFFECTS: parses dishes from JSON object and adds them to order
    private void addDishesToOrder(Order o, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Order");
        for (Object json : jsonArray) {
            JSONObject nextDish = (JSONObject) json;
            addDishToOrder(o, nextDish);
        }
    }

    // MODIFIES: o
    // EFFECTS: parses dish from JSON object and adds it to order
    private void addDishToOrder(Order o, JSONObject jsonObject) {
        String dishName = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        Category category = Category.valueOf(jsonObject.getString("category"));
        int price = jsonObject.getInt("price");
        int num = jsonObject.getInt("have been ordered #");
        Dish d = new Dish(category,price,description,dishName,num);
        o.addOrder(d);
    }

    // EFFECTS: parses menu from JSON object and returns it
    private Menu parseMenu(JSONObject jsonObject) {
        String restName = jsonObject.getString("Rest name");
        Menu m = new Menu(restName);
        addDishesToMenu(m, jsonObject);
        return m;
    }

    // MODIFIES: m
    // EFFECTS: parses dishes from JSON object and adds them to menu
    private void addDishesToMenu(Menu m, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Menu");
        for (Object json : jsonArray) {
            JSONObject nextDish = (JSONObject) json;
            addDishToMenu(m, nextDish);
        }
    }

    // MODIFIES: m
    // EFFECTS: parses dish from JSON object and adds it to menu
    private void addDishToMenu(Menu m, JSONObject jsonObject) {
        String dishName = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        Category category = Category.valueOf(jsonObject.getString("category"));
        int price = jsonObject.getInt("price");
        int num = jsonObject.getInt("have been ordered #");
        Dish d = new Dish(category,price,description,dishName,num);
        m.addDish(d);
    }
}
