package com.scottblechman.openmario.model;

import com.badlogic.gdx.math.Vector2;

public class Block {

    private BlockType type;
    private Vector2 position;

    public Block(BlockType type, Vector2 position) {
        this.type = type;
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public BlockType getType() {
        return type;
    }

    public void setType(BlockType type) {
        this.type = type;
    }
}
