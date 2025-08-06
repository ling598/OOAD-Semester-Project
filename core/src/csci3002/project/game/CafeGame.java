package csci3002.project.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;


public class CafeGame extends Game {
	private static CafeGame instance;

	SpriteBatch batch;
	Texture img;
	public BitmapFont font;

	private final Inventory inventory;
	private OrderingSystem orderingSystem;
	private MenuList menuList;
	private OrderBuilder builder;

	private CafeGame(Inventory inventory) {
		this.inventory = inventory;
		orderingSystem = new OrderingSystem(inventory);
		menuList = new MenuList();
		this.builder = new CatOrderBuilder();
	}

	public static CafeGame getInstance(Inventory inventory) {
		if (instance == null) {
			instance = new CafeGame(inventory);
		}
		return instance;
	}
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont(); // use libGDX's default Arial font
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	public Inventory getInventory() { return inventory; }

	public OrderingSystem getOrderingSystem() { return orderingSystem; }
	public MenuList getMenuList() { return menuList; }

	public void createOrders() {
		boolean create_order = builder.setCustomer(menuList)
				.setOrder(orderingSystem)
				.build();
		if(!create_order) {
			throw new IllegalStateException("Order was not created properly.");
		}
	}

	public void printCustomerOrders() {
		orderingSystem.showOrdersInQueue();
	}

	public MenuItem makeOrderFromInput() {
		Scanner myObj = new Scanner(System.in);
		int choice;
		do {
			System.out.println("What kind of Menu item would you like to make? (1 for food, 2 for drink)");
			choice = myObj.nextInt();
		} while (choice != 1 && choice != 2);

		if (choice == 1) {
			do {
				System.out.println("What kind of food would you like to make?");
				int i = 1;
				for(Food food : menuList.getFoods()) {
					System.out.println(i + ". " + food.getName());
					i++;
				}
				choice = myObj.nextInt();
				System.out.println();
			} while (choice < 0 || choice > 5);

			switch (choice) {
				case 1:
					return menuList.getItemFromString("Cake");
				case 2:
					return menuList.getItemFromString("Croissant");
				case 3:
					return menuList.getItemFromString("Pancake");
				case 4:
					return menuList.getItemFromString("Muffin");
				default:
					return null;
			}
		} else {
			do {
				System.out.println("What kind of drink would you like to make?");
				int i = 1;
				for(Drink drink : menuList.getDrinks()) {
					System.out.println(i + ". " + drink.getName());
					i++;
				}
				choice = myObj.nextInt();
				System.out.println();
			} while (choice < 0 || choice > 6);

			switch (choice) {
				case 1:
					return menuList.getItemFromString("Coffee");
				case 2:
					return menuList.getItemFromString("Latte");
				case 3:
					return menuList.getItemFromString("Frappe");
				case 4:
					return menuList.getItemFromString("Matcha");
				case 5:
					return menuList.getItemFromString("Lemonade");
				default:
					return null;
			}
		}
	}

	public void giveOrder() {
		Scanner myObj = new Scanner(System.in);
		int num_cats = orderingSystem.getOrderMap().size();
		int choice;
		do {
			System.out.println("Which customer would you like to give this to?");
			int i = 1;
			for (Map.Entry<Cat, String> entry : orderingSystem.getOrderMap().entrySet()) {
				String chosenItem = entry.getValue();
				System.out.println("Cat " + i + " - " + chosenItem);
				i++;
			}
			choice = myObj.nextInt();
			System.out.println();
		} while (choice < 0 && choice > num_cats);

		int i = 1;
		for (Cat cat : orderingSystem.getOrderMap().keySet()) {
			if (i == choice) {
				orderingSystem.submitReadyOrder(cat);
				break;
			}
			i++;
		}
		// throw new IllegalStateException("You did not correctly serve anything. :(");
	}
}

interface OrderBuilder
{
	OrderBuilder setCustomer(MenuList menuList);
	OrderBuilder setOrder(OrderingSystem orderingSystem);
	boolean build();
}

class CatOrderBuilder implements OrderBuilder {
	private Cat customer;
	private MenuList menuList;
	OrderingSystem orderingSystem;

	@Override
	public OrderBuilder setCustomer(MenuList menuList) {
		this.menuList = menuList;
		this.customer = new Cat(menuList);

		return this;
	}

	@Override
	public OrderBuilder setOrder(OrderingSystem orderingSystem) {
		this.orderingSystem = orderingSystem;
		return this;
	}

	@Override
	public boolean build() {
		if (customer == null || menuList == null || orderingSystem == null) {
			System.out.println("Customer, menu item, and ordering system must be set.");
			return false;
		}
		orderingSystem.addOrder(customer);
		return true;
	}
}