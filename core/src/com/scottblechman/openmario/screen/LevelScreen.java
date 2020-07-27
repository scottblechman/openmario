package com.scottblechman.openmario.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.scottblechman.openmario.OpenMario;
import com.scottblechman.openmario.state.InputState;
import com.scottblechman.openmario.state.MusicState;
import com.scottblechman.openmario.viewmodel.LevelViewModel;

import static com.scottblechman.openmario.state.InputState.*;

public class LevelScreen implements Screen, ScreenInterface {

    final OpenMario game;

    OrthographicCamera camera;

    final LevelViewModel viewModel;

    // Sprite sheets
    Texture texturePlayer;

    // Entity geometry
    Rectangle rectPlayer;

    public LevelScreen(OpenMario game) {
        this.game = game;
        game.musicStateManager.setState(MusicState.OVERWORLD);
        camera = new OrthographicCamera();
        viewModel = new LevelViewModel();
        camera.setToOrtho(false, game.WINDOW_WIDTH * game.windowScale,
                game.WINDOW_HEIGHT * game.windowScale);
        texturePlayer = new Texture("badlogic.jpg");
        rectPlayer = new Rectangle(viewModel.getPlayerPosition().x, viewModel.getPlayerPosition().y,
                game.BASE_TILE_SIZE * game.windowScale, game.BASE_TILE_SIZE * game.windowScale);
    }

    @Override
    public void show() {
        game.musicStateManager.play();
    }

    @Override
    public void render(float delta) {
        update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(texturePlayer, rectPlayer.x, rectPlayer.y, rectPlayer.width, rectPlayer.height);
        game.batch.end();
    }

    @Override
    public void update() {
        // Input state should only be non-empty if a key is currently pressed.
        if(!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            game.inputStateManager.setState(NONE);
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
                game.inputStateManager.setState(LEFT);
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                game.inputStateManager.setState(RIGHT);
        }

        InputState inputEvent = game.inputStateManager.getState();
        switch (inputEvent) {
            case LEFT:
                viewModel.movePlayerLeft(game.windowScale, Gdx.graphics.getDeltaTime());
                break;
            case RIGHT:
                viewModel.movePlayerRight(game.windowScale, Gdx.graphics.getDeltaTime());
                break;
            case NONE:
            default:
                break;
        }

        rectPlayer.x = viewModel.getPlayerPosition().x;
        rectPlayer.y = viewModel.getPlayerPosition().y;
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
        texturePlayer.dispose();
    }
}
