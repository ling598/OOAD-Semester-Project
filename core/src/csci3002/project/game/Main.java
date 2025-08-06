package csci3002.project.game;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n\t\t WELCOME TO CUDDLING PAWS CAFE!");
        Inventory inventory = new Inventory();
        // simulate the user buying 10 of each ingredient for the sake of testing
        Iterator<Map.Entry<String, Integer>> iterator = inventory.getIngredientsCost().entrySet().iterator();
        while(iterator.hasNext()) {
            inventory.addIngredient(iterator.next().getKey(), 10);
        }

        CafeGame game = CafeGame.getInstance(inventory);
        System.out.println("\n Let's start the day! Customers have begun to arrive. \n");
        game.createOrders();
        do {
            game.createOrders();
            game.printCustomerOrders();
            System.out.println();
            MenuItem order = game.getOrderingSystem().makeOrder(game.makeOrderFromInput());
            System.out.println();
            game.getOrderingSystem().assignReadyOrder(order);
            game.giveOrder();
        } while (game.getOrderingSystem().getCatCount() != 5);
        System.out.println("\n !! CONGRATS YOU MADE THROUGH THE DAY !! \n");
        System.out.println("\t\t Game Over \n");
    }
}