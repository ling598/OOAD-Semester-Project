package csci3002.project.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.w3c.dom.Text;

public class MainMenuScreen implements Screen {
    final CafeGame game;
    OrthographicCamera camera;
    SpriteBatch batch;
    private Stage stage;
    float screenWidth = 2304;
    float screenHeight = 1536;
    private Texture menuBackground;
    private Texture title;
    /// PLAY BUTTON ///
    private ImageButton playButton;
    boolean play = false;
    /// EXIT BUTTON ///
    private ImageButton exitButton;
    boolean exit = false;

    public MainMenuScreen(final CafeGame game) {
        this.game = game;

        menuBackground = new Texture(Gdx.files.internal("MainMenu.png"));
        title = new Texture(Gdx.files.internal("title1.png"));

        playButton = createButton("buttons/Play-Idle.png", "buttons/Play-Click.png"); //Set the button up
        exitButton = createButton("buttons/Exit-Idle.png", "buttons/Exit-Click.png"); //Set the button up

        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        stage.addActor(playButton); //Add the button to the stage to perform rendering and take input.
        stage.addActor(exitButton);
        Gdx.input.setInputProcessor(stage); //Start taking input from the ui

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
    }

    @Override
    public void show() {
        playButton.setPosition((stage.getWidth()-playButton.getWidth())/2, ((stage.getHeight()-playButton.getHeight())/2));
        exitButton.setPosition((stage.getWidth()-exitButton.getWidth())/2, 30);

    }

    @Override
    public void render(float delta) {
        // ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(menuBackground, 0,0);
        game.batch.draw(title, (camera.viewportWidth-title.getWidth())/2,4*(camera.viewportHeight- title.getHeight())/5);
        game.batch.end();

        stage.act();
        stage.draw();

        playButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                play = true;
            }
        });

        exitButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                exit = true;
            }
        });

        if (play) {
            dispose();
            game.setScreen(new GameScreen(game));

        }
        if (exit) {
            dispose();
            Gdx.app.exit();
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
        menuBackground.dispose();
        // playIdleButton.dispose();
        // exitIdleButton.dispose();
        title.dispose();
    }

    private ImageButton createButton(String up_file, String down_file) {
        Texture up_texture = new Texture(Gdx.files.internal(up_file));
        Texture down_texture = new Texture(Gdx.files.internal(down_file));
        TextureRegionDrawable up_draw = new TextureRegionDrawable(new TextureRegion(up_texture));
        TextureRegionDrawable down_draw = new TextureRegionDrawable(new TextureRegion(down_texture));

       return new ImageButton(up_draw, down_draw);
    }
}
