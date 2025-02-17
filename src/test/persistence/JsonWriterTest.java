package persistence;

import model.Category;
import model.Dish;
import model.Menu;
import model.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Menu m = new Menu("Menu");
            Order o = new Order("A1");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmpty() {
        try {
            Menu m = new Menu("Menu");
            Order o = new Order("A1");
            MenuAndOrder mo = new MenuAndOrder(m,o);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyMenuAndOrder.json");
            writer.open();
            writer.write(mo);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyMenuAndOrder.json");
            MenuAndOrder mao = reader.read();
            assertEquals("Menu", mao.getMenu().getRestName());
            assertEquals(0, mao.getMenu().getDishList().size());
            assertEquals("A1", mao.getOrder().getOrderTable());
            assertEquals(0, mao.getOrder().getOrderList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneral() {
        try {
            Menu m = new Menu("Menu");
            Order o = new Order("A1");
            MenuAndOrder mo = new MenuAndOrder(m,o);
            Dish d1 = new Dish(Category.APPETIZER, 15,"Korean spicy stir-fried rice cake.",
                    "Tteok-Bokki",0);
            Dish d2 = new Dish(Category.MAIN, 18,"Beef,vegetables, and yolk, soft tofu soup.",
                    "Beef Tofu Soup",0);
            Dish d3 = new Dish(Category.DESSERT, 18,"","Cheese Cake",0);
            Dish d4 = new Dish(Category.DRINK, 3,"","Coca Cola",0);
            m.addDish(d1);
            m.addDish(d2);
            m.addDish(d3);
            m.addDish(d4);
            o.addOrder("Tteok-Bokki",m);
            o.addOrder("Beef Tofu Soup",m);
            o.addOrder("Tteok-Bokki",m);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneral.json");
            writer.open();
            writer.write(mo);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneral.json");
            MenuAndOrder mao = reader.read();
            assertEquals("Menu", mao.getMenu().getRestName());
            List<Dish> menuList = mao.getMenu().getDishList();
            assertEquals(4, menuList.size());
            checkDish(Category.APPETIZER,15,"Korean spicy stir-fried rice cake.",
                    "Tteok-Bokki",2, menuList.get(0));
            checkDish(Category.MAIN, 18,"Beef,vegetables, and yolk, soft tofu soup.",
                    "Beef Tofu Soup",1, menuList.get(1));
            checkDish(Category.DESSERT, 18,"","Cheese Cake",0, menuList.get(2));
            checkDish(Category.DRINK, 3,"","Coca Cola",0, menuList.get(3));

            List<Dish> orderList = mao.getOrder().getOrderList();
            assertEquals(2, orderList.size());
            checkDish(Category.APPETIZER,15,"Korean spicy stir-fried rice cake.",
                    "Tteok-Bokki",2, orderList.get(0));
            checkDish(Category.MAIN, 18,"Beef,vegetables, and yolk, soft tofu soup.",
                    "Beef Tofu Soup",1, orderList.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}