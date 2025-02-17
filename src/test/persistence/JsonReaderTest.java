package persistence;

import model.Category;
import model.Dish;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            MenuAndOrder mao = reader.read();
            fail("IOException...");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmpty() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyMenuAndOrder.json");
        try {
            MenuAndOrder mao = reader.read();
            assertEquals("Menu", mao.getMenu().getRestName());
            assertEquals(0, mao.getMenu().getDishList().size());
            assertEquals("A1", mao.getOrder().getOrderTable());
            assertEquals(0, mao.getOrder().getOrderList().size());
        } catch (IOException e) {
            fail("not able to read");
        }
    }

    @Test
    void testReaderGeneral() {
        JsonReader reader = new JsonReader("./data/testReaderGeneral.json");
        try {
            MenuAndOrder mao = reader.read();
            assertEquals("Menu", mao.getMenu().getRestName());
            List<Dish> menuList = mao.getMenu().getDishList();
            assertEquals(4, menuList.size());
            checkDish(Category.APPETIZER,14,"Korean spicy stir-fried rice cake.",
                    "Tteok-Bokki",2, menuList.get(0));
            checkDish(Category.MAIN, 18,"Beef,vegetables, and yolk, soft tofu soup.",
                    "Beef Tofu Soup",1, menuList.get(1));
            checkDish(Category.DESSERT, 18,"","Cheese Cake",0, menuList.get(2));
            checkDish(Category.DRINK, 3,"","Coca Cola",0, menuList.get(3));

            List<Dish> orderList = mao.getOrder().getOrderList();
            assertEquals(2, orderList.size());
            checkDish(Category.APPETIZER,14,"Korean spicy stir-fried rice cake.",
                    "Tteok-Bokki",2, orderList.get(0));
            checkDish(Category.MAIN, 18,"Beef,vegetables, and yolk, soft tofu soup.",
                    "Beef Tofu Soup",1, orderList.get(1));
        } catch (IOException e) {
            fail("not able to read");
        }
    }
}