package csci3002.project.game;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
    protected String name;
    protected List<String> ingredients = new ArrayList<>();
    protected int correct_satisfaction;
    protected int incorrect_satisfaction;
    String getName() {
        return name;
    }

    List<String> getIngredients() {
        return ingredients;
    }

    int getSatisfaction(boolean isCorrect) {
        return isCorrect ? correct_satisfaction : incorrect_satisfaction;
    }
}
