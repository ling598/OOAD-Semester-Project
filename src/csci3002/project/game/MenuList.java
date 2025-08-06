package csci3002.project.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MenuList {
    public List<Drink> drinks;
    public List<Food> foods;
    public Random random;

    public MenuList() {
        this.drinks = new ArrayList<>();
        this.foods = new ArrayList<>();
        this.random = new Random();

        DrinkFactory drinkFactory = new DrinkFactory();
        drinks.add(drinkFactory.createDrink("Coffee"));
        drinks.add(drinkFactory.createDrink("Latte"));
        drinks.add(drinkFactory.createDrink("Frappe"));
        drinks.add(drinkFactory.createDrink("Matcha"));
        drinks.add(drinkFactory.createDrink("Lemonade"));

        FoodFactory foodFactory = new FoodFactory();
        foods.add(foodFactory.createFood("Cake"));
        foods.add(foodFactory.createFood("Croissant"));
        foods.add(foodFactory.createFood("Pancake"));
        foods.add(foodFactory.createFood("Muffin"));
    }
    public List<Drink> getDrinks() {
        return drinks;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public List<MenuItem> combineDrinksAndFoods() {
        List<MenuItem> menuItems = new ArrayList<>();

        menuItems.addAll(drinks);
        menuItems.addAll(foods);

        return menuItems;
    }

    public MenuItem getItemFromString(String item_name) {
        Iterator<MenuItem> iterator = combineDrinksAndFoods().iterator();
        while (iterator.hasNext()) {
            MenuItem i = iterator.next();
            if (i.getName().equals(item_name)) {
                return i;
            }
        }
        return null;
    }

    public void printCombinedMenu() {
        List<MenuItem> combinedMenu = combineDrinksAndFoods();
        for (MenuItem item : combinedMenu) {
            if (item instanceof Drink) {
                System.out.println("Drink: " + ((Drink) item).getName());
            } else if (item instanceof Food) {
                System.out.println("Food: " + ((Food) item).getName());
            }
        }
    }

    public MenuItem getRandomMenuItem() {
        List<MenuItem> combinedMenu = combineDrinksAndFoods();
        if (combinedMenu.isEmpty()) {
            return null; // Return null if the combined menu is empty
        }
        int randomIndex = random.nextInt(combinedMenu.size());
        return combinedMenu.get(randomIndex);
    }

    public void printRandomMenuItem() {
        Object randomItem = getRandomMenuItem();
        if (randomItem != null) {
            if (randomItem instanceof Drink) {
                Drink drink = (Drink) randomItem;
                System.out.println("Randomly picked Drink: " + drink.getName());
            } else if (randomItem instanceof Food) {
                Food food = (Food) randomItem;
                System.out.println("Randomly picked Food: " + food.getName());
            }
        } else {
            System.out.println("No menu items available.");
        }
    }


}

