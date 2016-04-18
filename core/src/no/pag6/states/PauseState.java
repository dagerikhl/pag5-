package no.pag6.states;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import no.pag6.game.PAG6Game;
import no.pag6.helpers.AssetLoader;
import no.pag6.ui.SimpleButton;

import java.util.ArrayList;
import java.util.List;

public class PauseState extends State {

    // Renderers
    private ShapeRenderer drawer;
    private TweenManager tweener;

    // Game objects

    // Game assets

    // Tween assets

    // Game UI
    private List<SimpleButton> pauseButtons = new ArrayList<SimpleButton>();
    private SimpleButton resumeButton;
    private SimpleButton highscoreButtonPause;
    private SimpleButton optionsButton;
    private SimpleButton menuButtonPause;

    public PauseState(PAG6Game game) {
        super(game);

        // Set up drawer and batcher
        drawer = new ShapeRenderer();
        drawer.setProjectionMatrix(cam.combined);

        // Init objects and assets
        initTweenAssets();

        initGameObjects();
        initGameAssets();

        initUI();
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render sprites
        game.spriteBatch.setProjectionMatrix(cam.combined);
        game.spriteBatch.begin();
        game.spriteBatch.enableBlending();

        drawUI();

        game.spriteBatch.end();
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        projected = cam.unproject(touchPoint);
        screenX = (int) touchPoint.x;
        screenY = (int) touchPoint.y;

        resumeButton.isTouchDown(screenX, screenY);
        highscoreButtonPause.isTouchDown(screenX, screenY);
        optionsButton.isTouchDown(screenX, screenY);
        menuButtonPause.isTouchDown(screenX, screenY);

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        projected = cam.unproject(touchPoint);
        screenX = (int) touchPoint.x;
        screenY = (int) touchPoint.y;

        if (resumeButton.isTouchUp(screenX, screenY)) {
            game.gameStack.pop();
            State previousState = game.gameStack.pop();
            game.gameStack.push(previousState);
            game.setScreen(previousState);
        } else if (highscoreButtonPause.isTouchUp(screenX, screenY)) {
            HighscoreMenu highscoreMenu = new HighscoreMenu(game);
            game.gameStack.push(highscoreMenu);
            game.setScreen(highscoreMenu);
        } else if (optionsButton.isTouchUp(screenX, screenY)) {
            OptionsMenu optionsMenu = new OptionsMenu(game);
            game.gameStack.push(optionsMenu);
            game.setScreen(optionsMenu);
        } else if (menuButtonPause.isTouchUp(screenX, screenY)) {
            goBackToPreviousPreviousState(game);
        }

        return true;
    }

    private void initTweenAssets() {
        // Register Tween Assets

        tweener = new TweenManager();

        // Tween animations
    }

    private void initGameObjects() {
    }

    private void initGameAssets() {
    }

    private void initUI() {
        resumeButton = new SimpleButton(2560 - AssetLoader.exitCrossButton.getRegionWidth() - 64, 64,
                AssetLoader.exitCrossButton.getRegionWidth(), AssetLoader.exitCrossButton.getRegionHeight(),
                AssetLoader.exitCrossButton, AssetLoader.exitCrossButton);
        pauseButtons.add(resumeButton);
        highscoreButtonPause = new SimpleButton(2560/2 - AssetLoader.highscoreButtonUp.getRegionWidth()/2 + 500, 1200,
                AssetLoader.highscoreButtonUp.getRegionWidth(), AssetLoader.highscoreButtonUp.getRegionHeight(),
                AssetLoader.highscoreButtonUp, AssetLoader.highscoreButtonDown);
        pauseButtons.add(highscoreButtonPause);
        optionsButton = new SimpleButton(2560/2 - AssetLoader.optionsButtonUp.getRegionWidth()/2 - 500, 1200,
                AssetLoader.optionsButtonUp.getRegionWidth(), AssetLoader.optionsButtonUp.getRegionHeight(),
                AssetLoader.optionsButtonUp, AssetLoader.optionsButtonDown);
        pauseButtons.add(optionsButton);
        menuButtonPause = new SimpleButton(2560/2 - AssetLoader.mainMenuButtonUp.getRegionWidth()/2, 1200,
                AssetLoader.mainMenuButtonUp.getRegionWidth(), AssetLoader.mainMenuButtonUp.getRegionHeight(),
                AssetLoader.mainMenuButtonUp, AssetLoader.mainMenuButtonDown);
        pauseButtons.add(menuButtonPause);
    }

    private void drawUI() {
        for (SimpleButton button : pauseButtons) {
            button.draw(game.spriteBatch);
        }
    }

}
