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
import com.scottblechman.openmario.model.Block;
import com.scottblechman.openmario.state.InputState;
import com.scottblechman.openmario.util.TextureUtil;
import com.scottblechman.openmario.viewmodel.LevelViewModel;

import java.util.ArrayList;

import static com.scottblechman.openmario.state.InputState.*;

public class LevelScreen implements Screen, ScreenInterface {

    final OpenMario game;

    OrthographicCamera camera;

    final LevelViewModel viewModel;

    // Sprite sheets
    Texture texturePlayer;
    Texture textureTiles;

    // Entity geometry
    Rectangle rectPlayer;
    ArrayList<Block> blocksInViewport;

    public LevelScreen(OpenMario game, String levelName) {
        this.game = game;

        viewModel = new LevelViewModel(game, levelName);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.WINDOW_WIDTH * game.windowScale,
                game.WINDOW_HEIGHT * game.windowScale);
        viewModel.setLastCameraPosition(camera.position);

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

        switch(viewModel.getLevelType()) {
            case OVERWORLD:
                Gdx.gl.glClearColor(126f/255f, 154f/255f, 238f/255f, 1);
                break;
            case CASTLE:
            case NONE:
            default:
                Gdx.gl.glClearColor(0, 0, 0, 1);
                break;
        }
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
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && viewModel.getPlayerPosition().x > camera.position.x - camera.viewportWidth / 2) {
            viewModel.movePlayerLeft(game.windowScale, Gdx.graphics.getDeltaTime());
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            viewModel.movePlayerRight(game.windowScale, Gdx.graphics.getDeltaTime());
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && viewModel.playerIsOnSurface(blocksInViewport)) {
            viewModel.playerJump(Gdx.graphics.getDeltaTime());
        }

        if(viewModel.canAdvanceCamera(camera.position)) {
            camera.position.x += viewModel.getBaseMovement()
                    * game.windowScale * Gdx.graphics.getDeltaTime();
        }

        if(viewModel.cameraPositionChanged(camera.position)) {
            viewModel.setLastCameraPosition(camera.position);
            updateTilesInViewport();
        }

        if(viewModel.playerIsOnSurface(blocksInViewport) || xDirectionCollision() || yDirectionCollision()) {
            viewModel.applyBottomForce();
        }
        viewModel.updatePlayerForces();

        rectPlayer.x = viewModel.getPlayerPosition().x;
        rectPlayer.y = viewModel.getPlayerPosition().y;

        // Fix collision errors and adjust position accordingly
        Vector2 correctedPosition = correctCollisions(viewModel.getPlayerPosition());
        rectPlayer.setPosition(correctedPosition);
        viewModel.setPlayerPosition(correctedPosition.x, correctedPosition.y);
    }

    private Vector2 correctCollisions(Vector2 originalPosition) {
        boolean xCollision = xDirectionCollision();
        boolean yCollision = yDirectionCollision();

        if(xCollision) {
            originalPosition.x = snapToGrid(originalPosition.x);
        }

        if(yCollision) {
            originalPosition.y = snapToGrid(originalPosition.y);
        }

        return originalPosition;
    }

    private boolean yDirectionCollision() {
        for(Block block : blocksInViewport) {
            Rectangle rect = new Rectangle(block.getPosition().x * getTileToPixelMultiplier(),
                    block.getPosition().y * getTileToPixelMultiplier(),
                    getTileToPixelMultiplier(), getTileToPixelMultiplier());

            float xDifference = Math.max(rectPlayer.x - rect.x, (rect.x + rect.width) - (rectPlayer.x + rectPlayer.width));
            float yDifference = Math.max(rectPlayer.y - rect.y, (rect.y + rect.height) - (rectPlayer.y + rectPlayer.height));
            if(rectPlayer.overlaps(rect) && yDifference > xDifference) {
                return true;
            }
        }
        return false;
    }

    private boolean xDirectionCollision() {
        for(Block block : blocksInViewport) {
            Rectangle rect = new Rectangle(block.getPosition().x * getTileToPixelMultiplier(),
                    block.getPosition().y * getTileToPixelMultiplier(),
                    getTileToPixelMultiplier(), getTileToPixelMultiplier());

            float xDifference = Math.max(rectPlayer.x - rect.x, (rect.x + rect.width) - rectPlayer.x);
            float yDifference = Math.max(rectPlayer.y - rect.y, (rect.y + rect.height) - rectPlayer.y);
            if(rectPlayer.overlaps(rect) && xDifference > yDifference) {
                return true;
            }
        }
        return false;
    }

    private int snapToGrid(float f) {
        int nearestLow = (int) f;
        int nearestHigh = (int) f;
        while(nearestLow % getTileToPixelMultiplier() != 0) {
            nearestLow--;
        }
        while(nearestHigh % getTileToPixelMultiplier() != 0) {
            nearestHigh++;
        }
        if((nearestHigh - f) > (f - nearestLow))
                return nearestLow;
        return nearestHigh;
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
        int size = blocksInViewport.size();
        blocksInViewport = new ArrayList<>();

        // Get the bottom left map coordinates
        float cameraLeft = camera.position.x - (camera.viewportWidth / 2);
        float cameraBottom = camera.position.y - (camera.viewportHeight / 2);

        int viewportStartX = (int) (cameraLeft / game.BASE_TILE_SIZE / game.windowScale);
        int viewportStartY = (int) (cameraBottom / game.BASE_TILE_SIZE / game.windowScale);
        int viewportWidth = (int) (game.WINDOW_WIDTH * game.windowScale) / game.BASE_TILE_SIZE;
        int viewportHeight = (int) (game.WINDOW_HEIGHT * game.windowScale) / game.BASE_TILE_SIZE;

        // Add new blocks to viewport
        ArrayList<Block> blocksInBounds = viewModel.getBlocksInBounds(viewportStartX, viewportStartY,
                viewportWidth, viewportHeight);
        blocksInViewport.addAll(blocksInBounds);
    }
}
