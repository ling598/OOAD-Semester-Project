package csci3002.project.game;

public class Drink extends MenuItem {

}
class Coffee extends Drink {
    public Coffee() {
        this.name = "Coffee";
        this.ingredients.add("Coffee");
        this.ingredients.add("Sugar");
        this.correct_satisfaction = 8;
        this.incorrect_satisfaction = -3;
    }

}

class Latte extends Drink {
    public Latte() {
        this.name = "Latte";
        this.ingredients.add("Coffee");
        this.ingredients.add("Milk");
        this.ingredients.add("Sugar");
        this.correct_satisfaction = 10;
        this.incorrect_satisfaction = -5;
    }

}

class Frappe extends Drink {
    public Frappe() {
        this.name = "Frappe";
        this.ingredients.add("Coffee");
        this.ingredients.add("Milk");
        this.ingredients.add("Ice");
        this.ingredients.add("Sugar");
        this.correct_satisfaction = 10;
        this.incorrect_satisfaction = -5;
    }

}

class Matcha extends Drink {
    public Matcha() {
        this.name = "Matcha";
        this.ingredients.add("Matcha powder");
        this.ingredients.add("Water");
        this.ingredients.add("Milk");
        this.ingredients.add("Sugar");
        this.correct_satisfaction = 8;
        this.incorrect_satisfaction = -3;
    }


}

class Lemonade extends Drink {
    public Lemonade() {
        this.name = "Lemonade";
        this.ingredients.add("Lemon");
        this.ingredients.add("Water");
        this.ingredients.add("Sugar");
        this.correct_satisfaction = 8;
        this.incorrect_satisfaction = -3;
    }


}

class DrinkFactory {
    public Drink createDrink(String type) {
        if (type.equalsIgnoreCase("Coffee")) {
            return new Coffee();
        } else if (type.equalsIgnoreCase("Latte")) {
            return new Latte();
        } else if (type.equalsIgnoreCase("Frappe")) {
            return new Frappe();
        } else if (type.equalsIgnoreCase("Matcha")) {
            return new Matcha();
        } else if (type.equalsIgnoreCase("Lemonade")) {
            return new Lemonade();
        } else {
            throw new IllegalArgumentException("Invalid drink type.");
        }
    }
}
