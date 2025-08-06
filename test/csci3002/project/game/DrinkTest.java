package csci3002.project.game;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DrinkTest {
    DrinkFactory factory = new DrinkFactory();
    Drink drink = null;

    @Test
    public void createDrink() {
        Assert.assertNull(drink);
        int expected_incorrect = -5;

        String expected_name = "Frappe";
        drink = factory.createDrink(expected_name);
        Drink expected_drink = new Frappe();
        Assert.assertEquals(expected_drink.getName(), drink.getName());
        Assert.assertNotNull(drink.getIngredients());
        Assert.assertEquals(expected_incorrect, drink.getSatisfaction(false));

        expected_name = "Latte";
        drink = factory.createDrink(expected_name);
        expected_drink = new Latte();
        Assert.assertEquals(expected_drink.getName(), drink.getName());
        Assert.assertNotNull(drink.getIngredients());
        Assert.assertEquals(expected_incorrect, drink.getSatisfaction(false));

        int expected_correct = 8;
        expected_name = "Coffee";
        drink = factory.createDrink(expected_name);
        expected_drink = new Coffee();
        Assert.assertEquals(expected_drink.getName(), drink.getName());
        Assert.assertNotNull(drink.getIngredients());
        Assert.assertEquals(expected_correct, drink.getSatisfaction(true));

        expected_name = "Matcha";
        drink = factory.createDrink(expected_name);
        expected_drink = new Matcha();
        Assert.assertEquals(expected_drink.getName(), drink.getName());
        Assert.assertNotNull(drink.getIngredients());
        Assert.assertEquals(expected_correct, drink.getSatisfaction(true));

        expected_name = "Lemonade";
        drink = factory.createDrink(expected_name);
        expected_drink = new Lemonade();
        Assert.assertEquals(expected_drink.getName(), drink.getName());
        Assert.assertNotNull(drink.getIngredients());
        Assert.assertEquals(expected_correct, drink.getSatisfaction(true));
    }
}