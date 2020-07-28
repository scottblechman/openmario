package com.scottblechman.openmario.viewmodel;

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

    public void setPlayerPosition(float x, float y) {
        this.playerPosition.x = x;
        this.playerPosition.y = y;
    }

    public void movePlayerLeft(float windowScale, float delta) {
        playerPosition.x -= BASE_MOVEMENT * windowScale * delta;
    }

    public void movePlayerRight(float windowScale, float delta) {
        playerPosition.x += BASE_MOVEMENT * windowScale * delta;
    }
}
