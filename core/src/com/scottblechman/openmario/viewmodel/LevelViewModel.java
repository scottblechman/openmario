package com.scottblechman.openmario.viewmodel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.scottblechman.openmario.OpenMario;
import com.scottblechman.openmario.data.CsvReader;
import com.scottblechman.openmario.model.Block;
import com.scottblechman.openmario.model.Level;
import com.scottblechman.openmario.model.LevelType;
import com.scottblechman.openmario.model.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LevelViewModel {

    final OpenMario game;

    final Level level;

    private final int BASE_MOVEMENT = 200;

    private final Player player;

    // Tracks when the camera position needs updating
    private final Vector2 lastCameraPosition;

    public LevelViewModel(OpenMario game, String levelName) {
        this.game = game;
        level = CsvReader.readLevelData(levelName);
        player = new Player();

        // Read level metadata
        game.musicStateManager.setState(level.getMusicState());
        setPlayerPosition(level.getStartPosition().x * getTileToPixelMultiplier(),
                level.getStartPosition().y * getTileToPixelMultiplier());

        lastCameraPosition = new Vector2(0, 0);
    }

    public LevelType getLevelType() {
        return level.getLevelType();
    }

    public ArrayList<Block> getBlocksInBounds(int x, int y, int width, int height) {
        return level.getBlocksInBounds(x, y, width, height);
    }

    public boolean canAdvanceCamera(Vector3 position) {
        return player.getPosition().x > position.x;
    }

    public Vector2 getPlayerPosition() {
        return player.getPosition();
    }

    public void setPlayerPosition(float x, float y) {
        player.setPosition(new Vector2(x, y));
    }

    public boolean cameraPositionChanged(Vector3 currentPosition) {
        return currentPosition.x != lastCameraPosition.x ||
                currentPosition.y != lastCameraPosition.y;
    }

    public void setLastCameraPosition(Vector3 currentPosition) {
        lastCameraPosition.x = currentPosition.x;
        lastCameraPosition.y = currentPosition.y;
    }

    public void movePlayerLeft(float windowScale, float delta) {
        player.getPosition().x -= BASE_MOVEMENT * windowScale * delta;
    }

    public void movePlayerRight(float windowScale, float delta) {
        player.getPosition().x += BASE_MOVEMENT * windowScale * delta;
    }

    public void playerJump(float delta) {
        player.addAcceleration(0, 800);
    }

    public void updatePlayerForces() {
        player.update(Gdx.graphics.getDeltaTime(), getBaseMovement());
    }

    public void applyBottomForce() {
        player.setOnSurface(true);
    }

    public boolean playerIsOnSurface(ArrayList<Block> blocksToCheck) {
        Rectangle playerIfMoved = new Rectangle(player.getPosition().x,
                player.getPosition().y - 1,
                getTileToPixelMultiplier(), getTileToPixelMultiplier());
        for(Block block : blocksToCheck) {
            Rectangle rect = new Rectangle(block.getPosition().x * getTileToPixelMultiplier(),
                    block.getPosition().y * getTileToPixelMultiplier(),
                    getTileToPixelMultiplier(), getTileToPixelMultiplier());
            if(playerIfMoved.overlaps(rect) && rect.x <= playerIfMoved.getX()) {
                return true;
            }
        }

        return false;
    }

    public int getBaseMovement() {
        return BASE_MOVEMENT;
    }

    private float getTileToPixelMultiplier() {
        return game.BASE_TILE_SIZE * game.windowScale;
    }
}
