package csci3002.project.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.*;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;

public class GameScreen implements Screen {
    final CafeGame game;
    // include assets here
    float screenWidth = 2304;
    float screenHeight = 1536;
    float bigPadding = 25;
    float smallPadding = 10;
    private final Stage stage;
    Texture background;
    int balance;
    int amount = 1;
    int total_cost;

    /// DONE BUTTON ///
    boolean done = false;
    boolean inGameScreen = false;

    Label remaining_balance;
    Table cafe_table;
    OrthographicCamera camera;

    public GameScreen(final CafeGame game) {
        this.game = game;
        balance = game.getInventory().getMoneyBalance();

        // set assets here
        background = new Texture(Gdx.files.internal("Start-Day.png"));


        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        Gdx.input.setInputProcessor(stage); //Start taking input from the ui

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
    }

    @Override
    public void show() {
        showIngredientButtons();
    }

    @Override
    public void render(float delta) {
        // ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(background, 0,0);
        game.batch.end();

        stage.act(delta);
        stage.draw();

        if (done) {
            changeScreens();
            done = false;
        }

        if (inGameScreen) {
            update();
            inGameScreen = false;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
        stage.clear();
    }

    public void changeScreens() {
        game.getInventory().displayInventory();
        dispose();

        inGameScreen = true;

        game.createOrders();

        background = new Texture(Gdx.files.internal("Cafe-Play.jpeg"));
        cafe_table = new Table();
        cafe_table.top();
        cafe_table.setFillParent(true);
        stage.addActor(cafe_table);

        cafe_table.padTop(bigPadding*6);
        showFoodItems();
        showDrinkItems();
        cafe_table.row();
        showReadyOrder();
        showCustomers();

    }

    public void update() {
        // cafe_table.removeActor(cafe_table.getChildren().get(cafe_table.getChildren().size-1));
        //showReadyOrder();
    }
    public BitmapFont createFont(int size, Color color) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/Gluten-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.borderColor = color;
        parameter.borderWidth = 3;
        return generator.generateFont(parameter);
    }

    public TextButton createTextButton(String text) {
        BitmapFont font = createFont(12, Color.BLACK);

        TextureRegionDrawable up_drawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/btn-blank.png"))));
        TextureRegionDrawable down_drawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/btn-blank-click.png"))));
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(up_drawable, down_drawable, up_drawable, font);

        return new TextButton(text, style);
    }

    public TextButton setUpMenuButton (String text) {
        TextButton button = createTextButton(text);

        button.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                BuyIngredientPopup(text);
            }
        });

        return button;
    }

    private ImageButton createImageButton(String up_file, String down_file) {
        Texture up_texture = new Texture(Gdx.files.internal(up_file));
        Texture down_texture = new Texture(Gdx.files.internal(down_file));
        TextureRegionDrawable up_draw = new TextureRegionDrawable(new TextureRegion(up_texture));
        TextureRegionDrawable down_draw = new TextureRegionDrawable(new TextureRegion(down_texture));

        return new ImageButton(up_draw, down_draw);
    }

    private ImageButton createDoubleImageButton(Texture icon_texture) {
        Texture up_texture = new Texture(Gdx.files.internal("buttons/sqr-idle.png"));
        Texture down_texture = new Texture(Gdx.files.internal("buttons/sqr-click.png"));
        TextureRegionDrawable up_draw = new TextureRegionDrawable(new TextureRegion(up_texture));
        TextureRegionDrawable down_draw = new TextureRegionDrawable(new TextureRegion(down_texture));
        TextureRegionDrawable icon_draw = new TextureRegionDrawable(new TextureRegion(icon_texture));
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up           = up_draw;
        style.down         = down_draw;
        style.checked      = up_draw;
        style.imageUp      = icon_draw;
        style.imageDown    = icon_draw;
        style.imageChecked = icon_draw;

        return new ImageButton(style);
    }

    private ImageButton createMenuItemButton(String file_name, String item_name) {
        Texture texture = new Texture(Gdx.files.internal(file_name));
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(texture));

        ImageButton menu_item_btn = new ImageButton(drawable);
        menu_item_btn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                MenuItem item = game.getMenuList().getItemFromString(item_name);
                game.getOrderingSystem().makeOrder(item);

                Actor pop_up;
                for (Actor actor: stage.getActors())
                {
                    if (Objects.equals(actor.getName(), "menu_popup")) {
                        pop_up = actor;
                        pop_up.addAction(Actions.removeActor());
                        break;
                    }
                }
            }
        });

        return menu_item_btn;
    }

    public Label createLabel(String text, int size) {
        BitmapFont font = createFont(size, Color.BLACK);
        Label.LabelStyle style = new Label.LabelStyle(font, null);
        return new Label(text, style);
    }

    public Window.WindowStyle setWindowStyle() {
        BitmapFont font = createFont(32, Color.WHITE);

        Texture texture = new Texture(Gdx.files.internal("rect.png"));
        TextureRegionDrawable background = new TextureRegionDrawable(new TextureRegion(texture));

        return new Window.WindowStyle(font, Color.BLACK, background);
    }

    public void showIngredientButtons() {
        Table container = new Table();
        container.setFillParent(true);
        container.top();

        Table table = new Table();

        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setOverscroll(false, false);

        Label title = createLabel("Buy Ingredients", 32);
        remaining_balance = createLabel("Remaining Balance: " + balance, 32);

        TextButton coffeeButton = setUpMenuButton("Coffee");
        TextButton milkButton = setUpMenuButton("Milk");
        TextButton iceButton = setUpMenuButton("Ice");
        TextButton matchaButton = setUpMenuButton("Matcha Powder");
        TextButton waterButton = setUpMenuButton("Water");
        TextButton lemonButton = setUpMenuButton("Lemon");
        TextButton flourButton = setUpMenuButton("Flour");
        TextButton sugarButton = setUpMenuButton("Sugar");
        TextButton eggsButton = setUpMenuButton("Eggs");
        TextButton bakingPowderButton = setUpMenuButton("Baking Powder");
        TextButton yeastButton = setUpMenuButton("Yeast");

        table.add(coffeeButton).pad(smallPadding);
        table.add(milkButton).pad(smallPadding);
        table.add(iceButton).pad(smallPadding);
        table.add(matchaButton).pad(smallPadding);
        table.row();
        table.add(waterButton).pad(smallPadding);
        table.add(lemonButton).pad(smallPadding);
        table.add(flourButton).pad(smallPadding);
        table.add(sugarButton).pad(smallPadding);
        table.row();
        table.add(eggsButton).pad(smallPadding);
        table.add(bakingPowderButton).pad(smallPadding);
        table.add(yeastButton).pad(smallPadding);
        table.padBottom(smallPadding);

        ImageButton doneButton = createImageButton("buttons/Tick-Idle.png", "buttons/Tick-Click.png");
        doneButton.setPosition((stage.getWidth()-doneButton.getWidth())/2, 30);
        doneButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                done = true;
            }
        });

        container.padTop(bigPadding);
        container.add(title).pad(smallPadding);
        container.row();
        container.add(scrollPane);
        container.row();
        container.add(remaining_balance).pad(smallPadding);
        container.row();
        container.add(doneButton);
        container.padBottom(bigPadding);

        stage.addActor(container);
    }

    public void BuyIngredientPopup(String ingredient) {
        float width = stage.getWidth() - 100;
        float height = stage.getHeight() - 100;
        Window.WindowStyle style = setWindowStyle();
        Window popup = new Window("Purchase " + ingredient, style);
        popup.setMovable(false);
        popup.pack();
        popup.setBounds((stage.getWidth()-width)/2, (stage.getWidth()-height)/4, width, height);

        Table container = new Table();
        Table table = new Table();

        Label amt_label = createLabel("x" + amount, 24);
        total_cost = game.getInventory().findCostPerUnit(ingredient)*amount;
        Label cost = createLabel("Total cost: " + total_cost, 24);
        Label remaining = createLabel("Remaining Balance: " + (balance-total_cost), 24);
        ImageButton done_btn = createImageButton("buttons/Tick-Idle.png", "buttons/Tick-Click.png");
        done_btn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                game.getInventory().replenishInventory(ingredient, amount);
                balance = game.getInventory().getMoneyBalance();
                remaining_balance.setText("Remaining Balance: " + balance);

                amount = 1;
                popup.addAction(Actions.removeActor());
            }
        });
        TextButton add_btn = createTextButton("+");
        add_btn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                if ((game.getInventory().findCostPerUnit(ingredient)*(amount + 1)) < balance) {
                    amount += 1;
                    amt_label.setText("x" + amount);

                    total_cost = game.getInventory().findCostPerUnit(ingredient)*amount;
                    cost.setText("Total cost: " + total_cost);
                    remaining.setText("Remaining Balance: " + (balance - total_cost));
                }
            }
        });

        TextButton sub_btn = createTextButton("-");
        sub_btn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                if ((amount - 1) > 0) {
                    amount -= 1;
                    amt_label.setText("x" + amount);

                    total_cost = game.getInventory().findCostPerUnit(ingredient)*amount;
                    cost.setText("Total cost: " + total_cost);
                    remaining.setText("Remaining Balance: " + (balance - total_cost));
                }
            }
        });

        table.add(sub_btn).pad(smallPadding);
        table.add(amt_label).pad(smallPadding);
        table.add(add_btn).pad(smallPadding);

        container.padTop(bigPadding);
        container.add(table).pad(smallPadding);
        container.row();
        container.add(cost).pad(smallPadding);
        container.row();
        container.add(remaining).pad(smallPadding);
        container.row();
        container.add(done_btn).pad(bigPadding);
        container.padBottom(smallPadding);

        popup.add(container);

        stage.addActor(popup);
    }

    public void showFoodItems() {
        // show a clickable button for making food on the left
        // have it pop up with a list of the food and their image

        TextButton food_btn = createTextButton("Make Food");
        cafe_table.add(food_btn).pad(bigPadding);
        food_btn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                foodPopUp();
            }
        });


        // when a food button is clicked, return the food's name
        // and then the pop-up disappears and after a certain amount
        // of time the food pops up in the ready order box

        // uses MenuList getFoods(), OrderingSystem makeFood() & AssignReadyOrder()
    }

    public void foodPopUp() {
        float width = stage.getWidth() - 100;
        float height = stage.getHeight() - 100;
        Window.WindowStyle style = setWindowStyle();
        Window menu_popup = new Window("Select a food to make", style);
        menu_popup.setMovable(false);
        menu_popup.pack();
        menu_popup.setBounds((stage.getWidth()-width)/2, (stage.getWidth()-height)/4, width, height);

        Table container = new Table();
        Table table = new Table();

        for (Food food : game.getMenuList().getFoods())
        {
            ImageButton btn;
            if (Objects.equals(food.getName(), "Cake")) {
                btn = createMenuItemButton("menu/cake.png", "Cake");
            } else if (Objects.equals(food.getName(), "Croissant")) {
                btn = createMenuItemButton("menu/croissant.png", "Croissant");
            } else if (Objects.equals(food.getName(), "Pancake")) {
                table.row();
                btn = createMenuItemButton("menu/pancake.png", "Pancake");
            } else {
                btn = createMenuItemButton("menu/muffin.png", "Muffin");
            }
            table.add(btn).pad(smallPadding).size(140,140);
        }

        container.padTop(bigPadding);
        container.add(table).pad(smallPadding);
        container.padBottom(smallPadding);

        menu_popup.add(container);
        menu_popup.setName("menu_popup");

        stage.addActor(menu_popup);
    }

    public void showDrinkItems() {
        // show a clickable button for making drinks on the right
        // have it pop up with a list of the drinks and their image
        TextButton drink_btn = createTextButton("Make Drink");
        cafe_table.add(drink_btn).pad(bigPadding);
        drink_btn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                drinkPopUp();
            }
        });
        // when a drink button is clicked, the pop-up disappears and
        // after a certain amount of time the drink pops up in the
        // ready order box

        // uses MenuList getDrinks(), OrderingSystem makeDrink() & AssignReadyOrder()
    }

    public void drinkPopUp() {
        float width = stage.getWidth() - 100;
        float height = stage.getHeight() - 100;
        Window.WindowStyle style = setWindowStyle();
        Window menu_popup = new Window("Select a drink to make", style);
        menu_popup.setMovable(false);
        menu_popup.pack();
        menu_popup.setBounds((stage.getWidth()-width)/2, (stage.getWidth()-height)/4, width, height);

        Table container = new Table();
        Table table = new Table();

        for (Drink drink : game.getMenuList().getDrinks())
        {
            ImageButton btn;
            if (Objects.equals(drink.getName(), "Coffee")) {
                btn = createMenuItemButton("menu/coffee.png", "Coffee");
            } else if (Objects.equals(drink.getName(), "Latte")) {
                btn = createMenuItemButton("menu/latte.png", "Latte");
            } else if (Objects.equals(drink.getName(), "Frappe")) {
                btn = createMenuItemButton("menu/frapp.png", "Frappe");
            } else if (Objects.equals(drink.getName(), "Matcha")) {
                table.row();
                btn = createMenuItemButton("menu/matcha.png", "Matcha");
            } else {
                btn = createMenuItemButton("menu/lemonade.png", "Lemonade");
            }
            table.add(btn).pad(smallPadding).size(130,130);
        }

        container.padTop(bigPadding);
        container.add(table).pad(smallPadding);
        container.padBottom(smallPadding);

        menu_popup.add(container);
        menu_popup.setName("menu_popup");

        stage.addActor(menu_popup);
    }

    public Texture getOrderTexture(String order_name) {
        if (Objects.equals(order_name, "Cake")) {
            return new Texture(Gdx.files.internal("menu/cake.png"));
        } else if (Objects.equals(order_name, "Croissant")) {
            return new Texture(Gdx.files.internal("menu/croissant.png"));
        } else if (Objects.equals(order_name, "Pancake")) {
            return new Texture(Gdx.files.internal("menu/pancake.png"));
        } else if (Objects.equals(order_name, "Muffin")) {
            return new Texture(Gdx.files.internal("menu/muffin.png"));
        } else if (Objects.equals(order_name, "Coffee")) {
            return new Texture(Gdx.files.internal("menu/coffee.png"));
        } else if (Objects.equals(order_name, "Latte")) {
            return new Texture(Gdx.files.internal("menu/latte.png"));
        } else if (Objects.equals(order_name, "Frappe")) {
            return new Texture(Gdx.files.internal("menu/frapp.png"));
        } else if (Objects.equals(order_name, "Matcha")) {
            return new Texture(Gdx.files.internal("menu/matcha.png"));
        } else {
            return new Texture(Gdx.files.internal("menu/lemonade.png"));
        }
    }

    // TODO: CREATE FUNCTION THAT SHOWS A BOX WITH CUSTOMERS THAT POP UP
    public void showCustomers() {
        // show the customer table on the bottom right
        Table container = new Table();
        Texture texture = new Texture(Gdx.files.internal("rect.png"));
        TextureRegionDrawable background = new TextureRegionDrawable(new TextureRegion(texture));
        Table table = new Table();
        container.padLeft(smallPadding);
        container.left();
        container.setBackground(background);
        container.setSize(stage.getWidth()/2, 100);

        Map<Cat, String> map = game.getOrderingSystem().getOrderMap();

        for(Map.Entry<Cat, String> entry : map.entrySet()) {
            Cat cat = entry.getKey();
            String chosenItem = entry.getValue();

            table.add(assignCat(cat,chosenItem)).pad(smallPadding);
        }
        container.add(table);
        cafe_table.add(container).pad(smallPadding).padTop(bigPadding);

        // uses OrderingSystem showOrdersInQueue()
    }

    // TODO: CREATE A FUNCTION THAT ASSIGNS A RANDOM IMAGE TO A CAT THAT HAS ORDERED
    public Table assignCat(Cat cat, String order_name) {
        // Get directory
        FileHandle dir = Gdx.files.internal("cats");

        // Get a random texture of dir
        Texture randomTexture = new Texture(dir.list()[random.nextInt(dir.list().length)]);

        Table table = new Table();
        table.setSize(20, 130);

        Texture order_texture = getOrderTexture(order_name);
        Image order_image = new Image(order_texture);
        ImageButton cat_btn = createDoubleImageButton(randomTexture);

        // TODO: ADD BUTTON LISTENER

        table.padTop(smallPadding);
        table.add(order_image).size(60, 60);
        table.row();
        table.add(cat_btn);
        table.padBottom(smallPadding);

        return table;
        // get the cat's corresponding order's texture and make it an image button
        // have the image button respond by removing the button's correlated cat from the list
        // and removing it from the customers table when clicked
        // put the two into a table where the order button is on the bottom
        // place table into the customer table

    }

    // TODO: CREATE FUNCTION THAT SHOWS A BOX THAT HAS READY ORDER ITEMS
    public void showReadyOrder() {
        // show ready orders table on the bottom left
        Table container = new Table();
        Texture texture = new Texture(Gdx.files.internal("rect.png"));
        TextureRegionDrawable background = new TextureRegionDrawable(new TextureRegion(texture));
        container.padLeft(smallPadding);
        container.right();
        container.setBackground(background);
        container.setSize(stage.getWidth()/2, 100);

        for(MenuItem item : game.getOrderingSystem().readyOrders) {
            Texture order_texture = getOrderTexture(item.getName());
            ImageButton order_btn = createDoubleImageButton(order_texture);

            order_btn.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y){
                    game.getOrderingSystem().readyOrder = item;
                    order_btn.addAction(Actions.removeActor());
                }
            });
            container.add(order_btn);
        }

        cafe_table.add(container).pad(smallPadding).padTop(bigPadding);

        // Uses OrderingSystem SubmitReadyOrder()
    }

}
