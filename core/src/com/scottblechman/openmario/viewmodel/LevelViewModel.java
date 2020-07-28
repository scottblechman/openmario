package com.scottblechman.openmario.viewmodel;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.scottblechman.openmario.OpenMario;
import com.scottblechman.openmario.data.CsvReader;
import com.scottblechman.openmario.model.Block;
import com.scottblechman.openmario.model.Level;
import com.scottblechman.openmario.model.LevelType;

import java.util.ArrayList;

public class LevelViewModel {

    final OpenMario game;

    final Level level;

    private final int BASE_MOVEMENT = 200;

    private final Vector2 playerPosition;

    // Tracks when the camera position needs updating
    private final Vector2 lastCameraPosition;

    public LevelViewModel(OpenMario game, String levelName) {
        this.game = game;
        level = CsvReader.readLevelData(levelName);

        // Read level metadata
        game.musicStateManager.setState(level.getMusicState());
        playerPosition = new Vector2(level.getStartPosition().x * getTileToPixelMultiplier(),
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
        return playerPosition.x > position.x;
    }

    public Vector2 getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(float x, float y) {
        this.playerPosition.x = x;
        this.playerPosition.y = y;
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
        playerPosition.x -= BASE_MOVEMENT * windowScale * delta;
    }

    public void movePlayerRight(float windowScale, float delta) {
        playerPosition.x += BASE_MOVEMENT * windowScale * delta;
    }

    public int getBaseMovement() {
        return BASE_MOVEMENT;
    }

    private float getTileToPixelMultiplier() {
        return game.BASE_TILE_SIZE * game.windowScale;
    }
}
