package csci3002.project.game;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

import java.util.List;

public class MenuListTest {
    Inventory inventory;
    DrinkFactory drinkFactory;
    FoodFactory foodFactory;
    MenuList menuList;
    OrderingSystem orderingSystem;
    Cat cat1;
    Cat cat2;
    Cat cat3;

    @Before
    public void setUp() {
        inventory = new Inventory();
        menuList = new MenuList();
        orderingSystem = new OrderingSystem(inventory);
        cat1 = new Cat(menuList);
        cat2 = new Cat(menuList);
        cat3 = new Cat(menuList);
    }
    @Test
    public void getDrinks() {
        System.out.println("\t GET DRINKS TEST START");

        List<Drink> drinksList = menuList.getDrinks();
        Assert.assertNotNull(drinksList);
        Assert.assertEquals(5, drinksList.size());

        for (Drink drink : drinksList) {
            System.out.println("Food: " + drink.getName());
        }

        System.out.println("\t GET DRINKS TEST END");
    }

    @Test
    public void getFoods() {
        System.out.println("\t GET FOODS TEST START");

        List<Food> foodList = menuList.getFoods();
        Assert.assertNotNull(foodList);
        Assert.assertEquals(4, foodList.size());

        for (Food food : foodList) {
            System.out.println("Food: " + food.getName());
        }

        System.out.println("\t GET FOODS TEST END");
    }

    @Test
    public void combineDrinksAndFoods() {
        System.out.println("\t COMBINE DRINKS AND FOODS TEST START");
        menuList.printCombinedMenu();
        System.out.println("\t COMBINE DRINKS AND FOODS TEST END");
    }

    @Test
    public void getRandomMenuItem() {
        System.out.println("\t GET RANDOM MENU ITEM TEST START");
        menuList.printRandomMenuItem();
        System.out.println("\t GET RANDOM MENU ITEM TEST END");
    }


}