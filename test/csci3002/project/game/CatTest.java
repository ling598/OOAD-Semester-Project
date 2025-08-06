package csci3002.project.game;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

import static org.junit.jupiter.api.Assertions.*;

public class CatTest {

    MenuList menu;
    Cat cat;

    @Before
    public void setUp() {
        menu = new MenuList();
        cat = new Cat(menu);
    }
    @Test
    public void assignMenuItem() {
        String actual = cat.getOrder().toString();
        Assert.assertNotNull(actual);
        System.out.println(actual);
    }
}