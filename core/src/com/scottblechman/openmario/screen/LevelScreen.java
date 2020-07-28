package com.scottblechman.openmario.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.scottblechman.openmario.OpenMario;
import com.scottblechman.openmario.data.CsvReader;
import com.scottblechman.openmario.model.Block;
import com.scottblechman.openmario.model.Level;
import com.scottblechman.openmario.model.mock.MockLevelFactory;
import com.scottblechman.openmario.state.InputState;
import com.scottblechman.openmario.state.MusicState;
import com.scottblechman.openmario.util.TextureUtil;
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
    ArrayList<Block> blocksInViewport;

    public LevelScreen(OpenMario game, String levelName) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.WINDOW_WIDTH * game.windowScale,
                game.WINDOW_HEIGHT * game.windowScale);
        cameraPosition = new Vector2(camera.position.x, camera.position.y);

        viewModel = new LevelViewModel();

        level = CsvReader.readLevelData(levelName);

        // Read level metadata
        game.musicStateManager.setState(level.getMusicState());
        viewModel.setPlayerPosition(level.getStartPosition().x * getTileToPixelMultiplier(),
                level.getStartPosition().y * getTileToPixelMultiplier());

        texturePlayer = new Texture("spritesheet/player1.jpg");
        textureTiles = new Texture("spritesheet/tile.png");

        rectPlayer = new Rectangle(viewModel.getPlayerPosition().x,
                viewModel.getPlayerPosition().y,
                getTileToPixelMultiplier(),
                getTileToPixelMultiplier());
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

        // Draw blocks
        for(Block block : blocksInViewport) {
            Rectangle rect = new Rectangle(block.getPosition().x * getTileToPixelMultiplier(),
                    block.getPosition().y * getTileToPixelMultiplier(),
                    getTileToPixelMultiplier(), getTileToPixelMultiplier());
            Vector2 index = TextureUtil.getBlockRegionIndex(block.getType(), game.BASE_TILE_SIZE);
            TextureRegion region = new TextureRegion(textureTiles, (int) index.x, (int) index.y, 16, 16);
            game.batch.draw(region, rect.x, rect.y, rect.width, rect.height);
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

    private float getTileToPixelMultiplier() {
        return game.BASE_TILE_SIZE * game.windowScale;
    }

    private void updateTilesInViewport() {
        // Get the bottom left map coordinates
        float cameraLeft = camera.position.x - (camera.viewportWidth / 2);
        float cameraBottom = camera.position.y - (camera.viewportHeight / 2);

        int viewportStartX = (int) (cameraLeft / game.BASE_TILE_SIZE / game.windowScale);
        int viewportStartY = (int) (cameraBottom / game.BASE_TILE_SIZE / game.windowScale);
        int viewportWidth = (int) (game.WINDOW_WIDTH * game.windowScale) / game.BASE_TILE_SIZE;
        int viewportHeight = (int) (game.WINDOW_HEIGHT * game.windowScale) / game.BASE_TILE_SIZE;

        // Remove blocks no longer in viewport
        if(blocksInViewport.size() > 0) {
            for (Block block : blocksInViewport) {
                if (block.getPosition().x < viewportStartX ||
                        block.getPosition().x > viewportStartX + viewportWidth ||
                        block.getPosition().y < viewportStartY ||
                        block.getPosition().y > viewportStartY + viewportHeight) {
                    blocksInViewport.remove(block);
                }
            }
        }

        // Add new blocks to viewport
        ArrayList<Block> blocksInBounds = level.getBlocksInBounds(viewportStartX, viewportStartY,
                viewportWidth, viewportHeight);
        blocksInViewport.addAll(blocksInBounds);
    }
}
