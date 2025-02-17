package persistence;

import model.Category;
import model.Dish;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkDish(Category category, int price, String description, String dishName, int num, Dish d) {
        assertEquals(dishName, d.getDishName());
        assertEquals(price, d.getPrice());
        assertEquals(description, d.getDescription());
        assertEquals(num, d.getNum());
        assertEquals(category, d.getCategory());
    }
}