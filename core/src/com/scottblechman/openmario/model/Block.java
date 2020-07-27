package com.scottblechman.openmario.model;

import com.badlogic.gdx.math.Vector2;

public class Block {

    private Vector2 position;

    public Block(Vector2 position) {
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
