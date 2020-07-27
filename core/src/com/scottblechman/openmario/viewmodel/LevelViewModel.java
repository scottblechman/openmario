package com.scottblechman.openmario.viewmodel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class LevelViewModel {

    private final int BASE_MOVEMENT = 200;

    private final Vector2 playerPosition;

    public LevelViewModel() {
        playerPosition = new Vector2(0, 0);
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
}