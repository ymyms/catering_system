package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MenuTest {

    private Menu menu;
    private Dish d1;
    private Dish d2;
    private Dish d3;
    private Dish d4;

    @BeforeEach
    void runBefore() {
        menu = new Menu("Sumi Korean Restaurant");
        d1 = new Dish(Category.APPETIZER, 15,"Korean spicy stir-fried rice cake.",
                "Tteok-Bokki",0);
        d2 = new Dish(Category.MAIN, 18,"Beef,vegetables, and yolk, soft tofu soup.",
                "Beef Tofu Soup",0);
        d3 = new Dish(Category.DESSERT, 18,"",
                "Cheese Cake",0);
        d4 = new Dish(Category.DRINK, 3,"",
                "Coca Cola",0);
    }

    @Test
    void testConstructor() {
        assertEquals("Sumi Korean Restaurant", menu.getRestName());
        assertEquals(0, menu.menu.size());
        menu.setRestName("KIKI Korean Restaurant");
        assertEquals("KIKI Korean Restaurant", menu.getRestName());
    }

    @Test
    void testAddDish() {
        menu.addDish(d1);
        menu.addDish(d2);
        menu.addDish(d3);
        menu.addDish(d4);

        List<Dish> d = menu.getDishList();

        assertEquals(4, d.size());
        assertEquals(d1, d.get(0));
        assertEquals(d2, d.get(1));
        assertEquals(d3, d.get(2));
        assertEquals(d4, d.get(3));
    }

    @Test
    void testAddAndRemoveDish() {
        menu.addDish(d1);
        menu.addDish(d2);
        menu.removeDish(d1);
        menu.addDish(d3);

        List<Dish> d = menu.getDishList();

        assertEquals(2, d.size());
        assertEquals(d2, d.get(0));
        assertEquals(d3, d.get(1));
    }


    @Test
    void testRemoveDish() {
        menu.removeDish(d1);
        assertEquals(0, menu.menu.size());
    }



}
