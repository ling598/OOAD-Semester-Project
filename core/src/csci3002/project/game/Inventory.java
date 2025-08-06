package csci3002.project.game;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class Inventory {
    private final Map<String, Integer> ingredients_cost;
    private final Map<String, Integer> ingredients_owned;
    private int moneyBalance;

    public Inventory() {
        this.ingredients_owned = new HashMap<>();
        this.moneyBalance = 200;

        this.ingredients_cost = new HashMap<>();
        ingredients_cost.put("Coffee", 5);
        ingredients_cost.put("Milk", 2);
        ingredients_cost.put("Ice", 1);
        ingredients_cost.put("Matcha powder", 5);
        ingredients_cost.put("Water", 1);
        ingredients_cost.put("Lemon", 1);
        ingredients_cost.put("Flour", 5);
        ingredients_cost.put("Sugar", 1);
        ingredients_cost.put("Eggs", 3);
        ingredients_cost.put("Butter", 3);
        ingredients_cost.put("Baking powder", 2);
        ingredients_cost.put("Yeast", 2);
    }

    public Map<String, Integer> getIngredientsCost() { return ingredients_cost; }
    public Map<String, Integer> getIngredientsOwned() { return ingredients_owned; }

    public void addIngredient(String ingredientName, int quantity) {
        if (ingredients_owned.containsKey(ingredientName)) {
            int currentQuantity = ingredients_owned.get(ingredientName);
            ingredients_owned.put(ingredientName, (currentQuantity + quantity));
        } else {
            ingredients_owned.put(ingredientName, quantity);
        }
    }

    public boolean deductIngredient(String ingredientName, int quantity) {
        if (ingredients_owned.containsKey(ingredientName)) {
            int currentQuantity = ingredients_owned.get(ingredientName);
            if (currentQuantity >= quantity) {
                ingredients_owned.put(ingredientName, currentQuantity - quantity);
                return true;
            } else {
                System.out.println("Not enough " + ingredientName + " in inventory.");
                return false;
            }
        } else {
            System.out.println(ingredientName + " not found in inventory.");
            return false;
        }
    }

    public boolean enoughMoney(int totalCost)
    {
        return totalCost <= moneyBalance;
    }

    public int findCostPerUnit(String ingredientName) {
        int costPerUnit = 0;
        if (ingredients_cost.containsKey(ingredientName)) {
            costPerUnit = ingredients_cost.get(ingredientName);
        }
        return costPerUnit;
    }
    public void replenishInventory(String ingredientName, int quantity) {
        int costPerUnit = findCostPerUnit(ingredientName);

        int totalCost = quantity * costPerUnit;
        if (enoughMoney(totalCost)) {
            addIngredient(ingredientName, quantity);
            moneyBalance -= totalCost;
            System.out.println("Inventory replenished: " + ingredientName + " x " + quantity);
        } else {
            System.out.println("Insufficient funds to replenish inventory.");
        }
    }

    public int getMoneyBalance() { return moneyBalance; }

    public void depositMoney(int amount) {
        moneyBalance += amount;
        System.out.println("Money deposited: $" + amount);
    }

    public Drink createDrink(String drinkType, DrinkFactory factory) {
        Drink drink = factory.createDrink(drinkType);
        List<String> drinkIngredients = drink.getIngredients();
        for (String ingredient : drinkIngredients) {
            if (!deductIngredient(ingredient, 1)) {
                System.out.println("Could not create drink");
                return null;
            } // Deduct one unit of each ingredient used in the drink
        }
        System.out.println("Drink created: " + drink.getName());

        return drink;
    }

    public Food createFood(String drinkType, FoodFactory factory) {
        Food food = factory.createFood(drinkType);
        List<String> foodIngredients = food.getIngredients();
        for (String ingredient : foodIngredients) {
            if(!deductIngredient(ingredient, 1)) {
                System.out.println("Could not create food");
                return null;
            } // Deduct one unit of each ingredient used in the food
        }
        System.out.println("Food created: " + food.getName());

        return food;
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        Iterator<Map.Entry<String, Integer>> iterator = ingredients_owned.entrySet().iterator();
        if (iterator.hasNext()) {
            do {
                Map.Entry<String, Integer> entry = iterator.next();
                String ingredient = entry.getKey();
                int quantity = entry.getValue();
                System.out.println("\t" + ingredient + ": " + quantity);
            } while (iterator.hasNext());
        }
        System.out.println("Money Balance: $" + moneyBalance);
    }
}