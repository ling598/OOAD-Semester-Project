package csci3002.project.game;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class OrderingSystem {
    public Map<Cat, String> orderMap;
    public List<MenuItem> readyOrders;
    public MenuItem readyOrder;

    int catCount;
    public int profit;

    public DrinkFactory drinkFactory;
    public FoodFactory foodFactory;
    public Inventory inventory;

    public OrderingSystem(Inventory inventory) {
        this.orderMap = new HashMap<>();
        this.readyOrders = new ArrayList<>();

        this.drinkFactory = new DrinkFactory();
        this.foodFactory = new FoodFactory();
        this.inventory = inventory;

        this.profit = 0;
        this.catCount = 0;
    }

    public int getCatCount() { return catCount; }
    public Map<Cat, String> getOrderMap() { return orderMap; }
    public List<MenuItem> getReadyOrders() { return readyOrders; }

    public void addOrder(Cat cat) {
        MenuItem order = cat.getOrder();
        String chosenItem = order.getName();
        if (chosenItem != null) {
            orderMap.put(cat, chosenItem);
        }
    }
    public void addReadyOrder(MenuItem item) {
        readyOrders.add(item);
    }

    // no
    public void showOrdersInQueue() {
        System.out.println("Orders in the queue:");
        for (Map.Entry<Cat, String> entry : orderMap.entrySet()) {
            String chosenItem = entry.getValue();
            System.out.println("- " + chosenItem);
        }
    }

    public MenuItem makeOrder(MenuItem order) {
        String chosenItem = null;
        if (order instanceof Drink) {
            chosenItem = order.getName();
            drinkProcessTimer();
            Drink drink = makeDrink(chosenItem);
            return drink;
        } else if (order instanceof Food) {
            chosenItem = order.getName();
            foodProcessTimer();
            Food food = makeFood(chosenItem);
            return food;
        }
        return null;
    }

    public Drink makeDrink(String drink_name) {
        return inventory.createDrink(drink_name, drinkFactory);
    }

    public Food makeFood(String food_name) {
        return inventory.createFood(food_name, foodFactory);
    }

    // no
    public void drinkProcessTimer() {
        System.out.println("Waiting for Drink...");
        try {
            Thread.sleep(5000); // Sleep for 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Drink Order complete.");
    }

    // no
    public void foodProcessTimer() {
        System.out.println("Waiting for Food...");
        try {
            Thread.sleep(7000); // Sleep for 10 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Food Order complete.");
    }

    public void assignReadyOrder(MenuItem order) {
        readyOrder = order;
    }

    public boolean checkOrder(Cat cat) {
        return cat.getOrder().getName().equals(readyOrder.getName());
    }

    public void submitReadyOrder(Cat cat) {
        if(orderMap.containsKey(cat)) {
            if (checkOrder(cat)) {
                System.out.println("Correct order! You served a " + readyOrder.getName());
            } else {
                System.out.println("Incorrect order! You served a " + readyOrder.getName() + " when they wanted a " + cat.getOrder().getName());
            }
            profit += cat.getOrder().getSatisfaction(checkOrder(cat));
            catCount++;
            orderMap.remove(cat);
        } else {
            System.out.println("Could not find cat!");
        }
    }

    // no
    //for the List
    public void showReadyOrders() {
        System.out.println("Ready orders:");
        for (MenuItem order : readyOrders) {
            System.out.println("- " + order.getName());
        }
    }

}
