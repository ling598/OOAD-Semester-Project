package csci3002.project.game;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

import java.util.Map;

public class InventoryTest {
    Inventory inventory;
    @Before
    public void setUp() {
        inventory = new Inventory();
    }
    @Test
    public void addIngredient() {
        String ingredient_name = "Coffee";
        int expected_quantity = 5;

        Assert.assertTrue(inventory.getIngredientsOwned().isEmpty());
        inventory.addIngredient(ingredient_name, expected_quantity);
        int current_quantity = inventory.getIngredientsOwned().get(ingredient_name);
        Assert.assertEquals(expected_quantity, current_quantity);
    }

    @Test
    public void deductIngredient() {
        addIngredient();
        String ingredient_name = "Coffee";
        int original_quantity = 5;
        int deducted_quantity = 2;
        int expected_quantity = 3;

        int current_quantity = inventory.getIngredientsOwned().get(ingredient_name);
        Assert.assertEquals(original_quantity, current_quantity);
        inventory.deductIngredient(ingredient_name, deducted_quantity);
        current_quantity = inventory.getIngredientsOwned().get(ingredient_name);
        Assert.assertEquals(expected_quantity, current_quantity);
    }

    @Test
    public void enoughMoney() {
        String ingredient_name = "Coffee";
        int quantity = 3;
        int expected_balance = 200;
        int total_cost = quantity * inventory.findCostPerUnit(ingredient_name);

        Assert.assertEquals(expected_balance, inventory.getMoneyBalance());
        Assert.assertTrue(inventory.enoughMoney(total_cost));
    }

    @Test
    public void findCostPerUnit() {
        String ingredient1 = "Coffee";
        String ingredient2 = "Matcha powder";
        int expected_cost = 5;

        Assert.assertEquals(expected_cost, inventory.findCostPerUnit(ingredient1));
        Assert.assertEquals(expected_cost, inventory.findCostPerUnit(ingredient2));
    }

    @Test
    public void replenishInventory() {
        String ingredient_name = "Coffee";
        int expected_quantity = 5;
        int original_balance = 200;
        int expected_balance = original_balance - (expected_quantity * inventory.findCostPerUnit(ingredient_name));

        Assert.assertEquals(original_balance, inventory.getMoneyBalance());
        Assert.assertTrue(inventory.getIngredientsOwned().isEmpty());

        inventory.replenishInventory(ingredient_name, expected_quantity);

        Assert.assertEquals(expected_balance, inventory.getMoneyBalance());
        int current_quantity = inventory.getIngredientsOwned().get(ingredient_name);
        Assert.assertEquals(expected_quantity, current_quantity);
    }

    @Test
    public void depositMoney() {
        int current_balance = 200;
        int add_to_balance = 200;
        int expected_balance = current_balance + add_to_balance;

        Assert.assertEquals(current_balance, inventory.getMoneyBalance());

        inventory.depositMoney(add_to_balance);

        current_balance = inventory.getMoneyBalance();
        Assert.assertEquals(expected_balance, current_balance);
    }

    @Test
    public void createDrink() {
        for (Map.Entry<String, Integer> ingredient : inventory.getIngredientsCost().entrySet()) {
            System.out.println(ingredient.getKey() + ":" + ingredient.getValue());
            inventory.addIngredient(ingredient.getKey(), 10);
        }

        String drink_name = "Latte";
        Drink expected_drink = new Latte();

        Drink actual_drink = inventory.createDrink(drink_name, new DrinkFactory());
        Assert.assertEquals(expected_drink.getName(), actual_drink.getName());
    }

    @Test
    public void createFood() {
        for (Map.Entry<String, Integer> ingredient : inventory.getIngredientsCost().entrySet()) {
            System.out.println(ingredient.getKey() + ":" + ingredient.getValue());
            inventory.addIngredient(ingredient.getKey(), 10);
        }

        String food_name = "Cake";
        Food expected_food = new Cake();

        Food actual_food = inventory.createFood(food_name, new FoodFactory());
        Assert.assertEquals(expected_food.getName(), actual_food.getName());
    }


}