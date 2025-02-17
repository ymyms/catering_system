package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DishTest {

    private Dish d1;
    private Dish d2;
    private Dish d3;
    private Dish d4;

    @BeforeEach
    void runBefore() {
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
        assertEquals("Korean spicy stir-fried rice cake.", d1.getDescription());
        assertEquals(15, d1.getPrice());
        assertEquals(Category.APPETIZER, d1.getCategory());
        assertEquals("Tteok-Bokki", d1.getDishName());
        assertEquals(0, d1.getNum());
    }

    @Test
    void testSetMethods() {
        d2.setCategory(Category.APPETIZER);
        d3.setPrice(19);
        d2.setDishName("Corn Cheese");
        d4.setDescription("Soft Drink");

        d1.increase();
        d1.increase();
        assertEquals(2, d1.getNum());
        d1.decrease();
        assertEquals(1, d1.getNum());
        d1.setNum(0);
        assertEquals(0, d1.getNum());

        assertEquals("Soft Drink", d4.getDescription());
        assertEquals(19, d3.getPrice());
        assertEquals(Category.APPETIZER, d2.getCategory());
        assertEquals("Corn Cheese", d2.getDishName());
    }

}