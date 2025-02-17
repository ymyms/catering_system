package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class OrderTest {

    private Order order;
    private Menu menu;
    private Dish d1;
    private Dish d2;
    private Dish d3;
    private Dish d4;

    @BeforeEach
    void runBefore() {
        order = new Order("A1");
        menu = new Menu("Sumi Korean Restaurant");
        d1 = new Dish(Category.APPETIZER, 15,"Korean spicy stir-fried rice cake.",
                "Tteok-Bokki",0);
        d2 = new Dish(Category.MAIN, 18,"Beef,vegetables, and yolk, soft tofu soup.",
                "Beef Tofu Soup",0);
        d3 = new Dish(Category.DESSERT, 18,"",
                "Cheese Cake",0);
        d4 = new Dish(Category.DRINK, 3,"",
                "Coca Cola",0);
        menu.addDish(d1);
        menu.addDish(d2);
        menu.addDish(d3);
        menu.addDish(d4);
    }

    @Test
    void testConstructor() {
        assertEquals("A1", order.getOrderTable());
        assertFalse(order.getIsOrder());
        List<Dish> d = order.getOrderList();
        assertEquals(0, d.size());
        assertEquals(0, order.getTotalPrice());
    }

    @Test
    void testPrice() {
        order.addOrder("Tteok-Bokki",menu);
        order.addOrder("Beef Tofu Soup",menu);
        order.addOrder("Cheese Cake",menu);
        order.addOrder("Coca Cola",menu);
        assertEquals(54, order.getTotalPrice());
        order.removeOrder("Coca Cola");
        assertEquals(51, order.getTotalPrice());

    }

    @Test
    void testRepeatPrice() {
        order.addOrder("Tteok-Bokki",menu);
        order.addOrder("Tteok-Bokki",menu);

        assertEquals(30, order.getTotalPrice());
        order.removeOrder("Tteok-Bokki");
        assertEquals(15, order.getTotalPrice());

    }

    @Test
    void testPlaceOrder() {
        order.addOrder("Tteok-Bokki",menu);
        order.addOrder("Tteok-Bokki",menu);
        order.addOrder("Tteok-Bokki",menu);
        order.addOrder("Coca Cola",menu);
        order.placeOrder();
        order.addOrder("Coca Cola",menu);
        order.removeOrder("Coca Cola");

        List<Dish> d = order.getOrderList();
        assertEquals(2, d.size());
        assertEquals(48, order.getTotalPrice());
    }

    @Test
    void testRemoveOrder() {
        order.addOrder("Coca Cola",menu);
        order.removeOrder("Coca Cole");
        List<Dish> d = order.getOrderList();
        assertEquals(1, d.size());
        assertEquals(3, order.getTotalPrice());
    }

    @Test
    void testRemoveNoOrder() {
        order.removeOrder("Coca Cole");
        List<Dish> d = order.getOrderList();
        assertEquals(0, d.size());
        assertEquals(0, order.getTotalPrice());
    }



}
