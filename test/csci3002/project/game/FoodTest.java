package csci3002.project.game;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

public class FoodTest {
    FoodFactory factory = new FoodFactory();
    Food food = null;

    @Test
    public void createFood() {
        Assert.assertNull(food);
        int expected_incorrect = -3;

        String expected_name = "Croissant";
        food = factory.createFood(expected_name);
        Food expected_food = new Croissant();
        Assert.assertEquals(expected_food.getName(), food.getName());
        Assert.assertNotNull(food.getIngredients());
        Assert.assertEquals(expected_incorrect, food.getSatisfaction(false));

        expected_name = "Muffin";
        food = factory.createFood(expected_name);
        expected_food = new Muffin();
        Assert.assertEquals(expected_food.getName(), food.getName());
        Assert.assertNotNull(food.getIngredients());
        Assert.assertEquals(expected_incorrect, food.getSatisfaction(false));

        int expected_correct = 10;
        expected_name = "Pancake";
        food = factory.createFood(expected_name);
        expected_food = new Pancake();
        Assert.assertEquals(expected_food.getName(), food.getName());
        Assert.assertNotNull(food.getIngredients());
        Assert.assertEquals(expected_correct, food.getSatisfaction(true));

        expected_name = "Cake";
        food = factory.createFood(expected_name);
        expected_food = new Cake();
        Assert.assertEquals(expected_food.getName(), food.getName());
        Assert.assertNotNull(food.getIngredients());
        Assert.assertEquals(expected_correct, food.getSatisfaction(true));
    }
}