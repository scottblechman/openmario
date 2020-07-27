package com.scottblechman.openmario.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.scottblechman.openmario.OpenMario;
import com.scottblechman.openmario.model.Block;
import com.scottblechman.openmario.model.Level;
import com.scottblechman.openmario.model.mock.MockLevelFactory;
import com.scottblechman.openmario.state.InputState;
import com.scottblechman.openmario.state.MusicState;
import com.scottblechman.openmario.viewmodel.LevelViewModel;

import java.util.ArrayList;

import static com.scottblechman.openmario.state.InputState.*;

public class LevelScreen implements Screen, ScreenInterface {

    final OpenMario game;

    OrthographicCamera camera;

    // Tracks when the camera has changed its position
    final Vector2 cameraPosition;

    final LevelViewModel viewModel;

    final Level level;

    // Sprite sheets
    Texture texturePlayer;
    Texture textureTiles;

    // Entity geometry
    Rectangle rectPlayer;
    ArrayList<Rectangle> blocksInViewport;

    public LevelScreen(OpenMario game) {
        this.game = game;
        game.musicStateManager.setState(MusicState.OVERWORLD);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.WINDOW_WIDTH * game.windowScale,
                game.WINDOW_HEIGHT * game.windowScale);
        cameraPosition = new Vector2(camera.position.x, camera.position.y);

        viewModel = new LevelViewModel();

        // TODO: 7/26/20 replace factory method with data interface
        level = MockLevelFactory.buildMockLevel();

        texturePlayer = new Texture("spritesheet/player1.jpg");
        textureTiles = new Texture("spritesheet/tile.png");

        rectPlayer = new Rectangle(viewModel.getPlayerPosition().x, viewModel.getPlayerPosition().y,
                game.BASE_TILE_SIZE * game.windowScale, game.BASE_TILE_SIZE * game.windowScale);
        blocksInViewport = new ArrayList<>();
        updateTilesInViewport();
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
        for(Rectangle block : blocksInViewport) {
            game.batch.draw(texturePlayer, block.x, block.y, block.width, block.height);
        }
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

        // TODO: 7/26/20 Update camera
        if(camera.position.x != cameraPosition.x || camera.position.y != cameraPosition.y) {
            cameraPosition.x = camera.position.x;
            cameraPosition.y = camera.position.y;

            updateTilesInViewport();
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

    private void updateTilesInViewport() {
        // Get the bottom left map coordinates
        float cameraLeft = camera.position.x - (camera.viewportWidth / 2);
        float cameraBottom = camera.position.y - (camera.viewportHeight / 2);
        int viewportStartX = (int) (cameraLeft / game.BASE_TILE_SIZE / game.windowScale);
        int viewportStartY = (int) (cameraBottom / game.BASE_TILE_SIZE / game.windowScale);


        // Remove blocks no longer in viewport
        if(blocksInViewport.size() > 0) {
            for (Rectangle block : blocksInViewport) {
                if (block.x < camera.position.x ||
                        block.x > camera.position.x + (game.WINDOW_WIDTH * game.windowScale) ||
                        block.y < camera.position.y ||
                        block.y > camera.position.y + (game.WINDOW_HEIGHT * game.windowScale)) {
                    blocksInViewport.remove(block);
                }
            }
        }

        // Add new blocks to viewport
        ArrayList<Block> blocksInBounds = level.getBlocksInBounds(
                viewportStartX, viewportStartY,
                (int) (game.WINDOW_WIDTH * game.windowScale) / game.BASE_TILE_SIZE,
                (int) (game.WINDOW_HEIGHT * game.windowScale) / game.BASE_TILE_SIZE);
        for(Block block : blocksInBounds) {
            float tileSize = game.BASE_TILE_SIZE * game.windowScale;
            Rectangle rect = new Rectangle(block.getPosition().x * tileSize, block.getPosition().y * tileSize,
                    tileSize, tileSize);
            blocksInViewport.add(rect);
        }
    }
}
