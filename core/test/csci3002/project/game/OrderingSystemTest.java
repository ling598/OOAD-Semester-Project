package csci3002.project.game;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

import java.util.Map;


public class OrderingSystemTest {
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

        for (Map.Entry<String, Integer> ingredient : inventory.getIngredientsCost().entrySet()) {
            inventory.addIngredient(ingredient.getKey(), 10);
        }
    }

    @Test
    public void addOrder() {
        System.out.println("\t ADD ORDER TEST START");
        Assert.assertTrue(orderingSystem.orderMap.isEmpty());
        orderingSystem.addOrder(cat1);
        Assert.assertFalse(orderingSystem.orderMap.isEmpty());
        System.out.println("\t ADD ORDER TEST END \n");
    }

    @Test
    public void addReadyOrder() {
        System.out.println("\t ADD READY ORDER TEST START");

        Cat cat1 = new Cat(menuList);
        Cat cat2 = new Cat(menuList);

        orderingSystem.addOrder(cat1);
        orderingSystem.addOrder(cat2);

        MenuItem menuItem1 = orderingSystem.makeOrder(cat1.getOrder());
        MenuItem menuItem2 = orderingSystem.makeOrder(cat2.getOrder());

        orderingSystem.addReadyOrder(menuItem1);
        orderingSystem.addReadyOrder(menuItem2);

        Assert.assertEquals(2, orderingSystem.getReadyOrders().size());
        Assert.assertTrue(orderingSystem.getReadyOrders().contains(menuItem1));
        Assert.assertTrue(orderingSystem.getReadyOrders().contains(menuItem2));

        System.out.println("\t ADD READY ORDER TEST END \n");

        System.out.println("\tSHOW READY ORDERS:");
        orderingSystem.showReadyOrders();
    }

    @Test
    public void makeOrder() {
        System.out.println("\t MAKE ORDER TEST START");

        Drink drink = new Frappe();
        Food food = new Cake();

        MenuItem result1 = orderingSystem.makeOrder(drink);
        MenuItem result2 = orderingSystem.makeOrder(food);

        Assert.assertNotNull(result1);
        Assert.assertNotNull(result2);
        Assert.assertEquals(drink.getName(), result1.getName());
        Assert.assertEquals(food.getName(), result2.getName());

        System.out.println("\t MAKE ORDER TEST END \n");

        System.out.println("\t SHOW READY ORDERS:");
    }

    @Test
    public void makeDrink() {
        System.out.println("\t MAKE DRINK TEST START");

        String drinkName = "Coffee";

        Drink result = orderingSystem.makeDrink(drinkName);

        Assert.assertNotNull(result);
        Assert.assertEquals(drinkName, result.getName());

        System.out.println("\t MAKE DRINK TEST END \n");
    }

    @Test
    public void makeFood() {
        System.out.println("\t MAKE FOOD TEST START");

        String foodName = "Cake";

        Food result = orderingSystem.makeFood(foodName);

        Assert.assertNotNull(result);
        Assert.assertEquals(foodName, result.getName());

        System.out.println("\t MAKE FOOD TEST END \n");
    }
    @Test
    public void assignReadyOrder() {
        System.out.println("\t ASSIGN READY ORDER TEST START");

        orderingSystem.addOrder(cat1);
        orderingSystem.addOrder(cat2);
        orderingSystem.addOrder(cat3);

        orderingSystem.showOrdersInQueue();

        Assert.assertTrue(orderingSystem.getReadyOrders().isEmpty());
        MenuItem expectedOrder = cat1.getOrder();
        MenuItem actualOrder = orderingSystem.makeOrder(expectedOrder);
        orderingSystem.assignReadyOrder(actualOrder);

        Assert.assertNotNull(orderingSystem.getReadyOrders());

        System.out.println("\t ASSIGN READY ORDER TEST END \n");
    }

    @Test
    public void checkOrder(){
        System.out.println("\t CHECK ORDER TEST START");

        Cat cat = new Cat(menuList);
        orderingSystem.addOrder(cat);
        System.out.println("Cat Order: " + cat.getOrder().getName());
        MenuItem readyOrder = orderingSystem.makeOrder(cat.getOrder());
        orderingSystem.assignReadyOrder(readyOrder);
        boolean result = orderingSystem.checkOrder(cat);
        Assert.assertTrue(result);

        System.out.println("\t CHECK ORDER TEST END \n");
    }

    @Test
    public void submitReadyOrder() {
        System.out.println("\t SUBMIT READY ORDER TEST START");
        orderingSystem.addOrder(cat1);
        orderingSystem.addOrder(cat2);
        orderingSystem.addOrder(cat3);
        MenuItem expected_order = cat1.getOrder();
        MenuItem actual_order = orderingSystem.makeOrder(expected_order);
        orderingSystem.assignReadyOrder(actual_order);
        orderingSystem.submitReadyOrder(cat1);

        Assert.assertFalse(orderingSystem.getOrderMap().containsKey(cat1));
        System.out.println("\t SUBMIT READY ORDER TEST END");
    }

    @Test
    public void simpleRun() {
        // before game run starts, users need to purchase the ingredients they think are necessary
        // for the sake of a test, we will assume the user has more money than typically allotted
        // to purchase 10 of each ingredient

        // making sure inventory has 10 of each ingredient
        System.out.println("\t SIMPLE RUN TEST START");
        for (Map.Entry<String, Integer> ingredient : inventory.getIngredientsCost().entrySet()) {
            System.out.println(ingredient.getKey() + " costs " + ingredient.getValue() + " coin(s).");
            inventory.addIngredient(ingredient.getKey(), 10);
        }
        System.out.println();

        // assume game has begun and now customers are filing in with their orders
        orderingSystem.addOrder(cat1);
        orderingSystem.addOrder(cat2);
        orderingSystem.addOrder(cat3);

        orderingSystem.showOrdersInQueue();
        // user makes the orders
        // food/drink items can only be made one at a time (each)
        MenuItem expected_order = cat1.getOrder();
        // these functions make the menu items on a timer, returning the respective item according to the function
        // let us assume drinks are faster to 'make'
        MenuItem actual_order = orderingSystem.makeOrder(expected_order);
//        orderingSystem.makeDrink(drink);
//        orderingSystem.makeFood(food);
//        // since the food has finished first, so we'll assign our readyOrder variable to it
        orderingSystem.assignReadyOrder(actual_order);
//        // let's say we're giving the order to the first order in the queue
//        // the function will compare the order to the readyOrder items and if they are the same, the user is rewarded
//        // however much the item was worth, if not they lost like 5 coins or some constant instead and order/cat is gone
        orderingSystem.submitReadyOrder(cat1);
        // rinse and repeat until day is over or game checks if the user can fulfill any more orders
        System.out.println("\t SIMPLE RUN TEST END");
    }
}