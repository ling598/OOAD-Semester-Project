package csci3002.project.game;

public class Food extends MenuItem {

}

class Cake extends Food {
    public Cake() {
        this.name = "Cake";
        this.ingredients.add("Flour");
        this.ingredients.add("Sugar");
        this.ingredients.add("Eggs");
        this.ingredients.add("Butter");
        this.ingredients.add("Baking powder");
        this.correct_satisfaction = 10;
        this.incorrect_satisfaction = -5;
    }
}

class Croissant extends Food {
    public Croissant() {
        this.name = "Croissant";
        this.ingredients.add("Flour");
        this.ingredients.add("Butter");
        this.ingredients.add("Sugar");
        this.ingredients.add("Yeast");
        this.correct_satisfaction = 8;
        this.incorrect_satisfaction = -3;
    }

}

class Pancake extends Food {
    public Pancake() {
        this.name = "Pancake";
        this.ingredients.add("Flour");
        this.ingredients.add("Milk");
        this.ingredients.add("Eggs");
        this.ingredients.add("Butter");
        this.correct_satisfaction = 10;
        this.incorrect_satisfaction = -5;
    }
}

class Muffin extends Food {
    public Muffin() {
        this.name = "Muffin";
        this.ingredients.add("Flour");
        this.ingredients.add("Sugar");
        this.ingredients.add("Baking powder");
        this.ingredients.add("Butter");
        this.ingredients.add("Eggs");
        this.ingredients.add("Milk");
        this.correct_satisfaction = 8;
        this.incorrect_satisfaction = -3;
    }
}

class FoodFactory {
    public Food createFood(String type) {
        if (type.equalsIgnoreCase("Cake")) {
            return new Cake();
        } else if (type.equalsIgnoreCase("Croissant")) {
            return new Croissant();
        } else if (type.equalsIgnoreCase("Pancake")) {
            return new Pancake();
        } else if (type.equalsIgnoreCase("Muffin")) {
            return new Muffin();
        } else {
            throw new IllegalArgumentException("Invalid food type.");
        }
    }
}
