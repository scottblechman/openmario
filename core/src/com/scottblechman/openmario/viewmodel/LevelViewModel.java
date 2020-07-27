package com.scottblechman.openmario.viewmodel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class LevelViewModel {

    private final int BASE_MOVEMENT = 200;

    private final Vector2 playerPosition;

    // Current camera bounds
    private final Vector2 boundsBottomLeft;
    private final Vector2 boundsTopRight;

    public LevelViewModel() {
        playerPosition = new Vector2(0, 0);
        boundsBottomLeft = new Vector2(0, 0);
        boundsTopRight = new Vector2(0, 0);
    }

    public Vector2 getPlayerPosition() {
        return playerPosition;
    }

    public void movePlayerLeft(float windowScale, float delta) {
        playerPosition.x -= BASE_MOVEMENT * windowScale * delta;
    }

    public void movePlayerRight(float windowScale, float delta) {
        playerPosition.x += BASE_MOVEMENT * windowScale * delta;
    }

    public Vector2 getBoundsBottomLeft() {
        return boundsBottomLeft;
    }

    public Vector2 getBoundsTopRight() {
        return boundsTopRight;
    }
}
