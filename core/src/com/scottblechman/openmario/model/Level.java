package com.scottblechman.openmario.model;

import com.badlogic.gdx.math.Vector2;
import com.scottblechman.openmario.state.MusicState;

import java.util.ArrayList;

public class Level {

    private String nextLevel;
    private MusicState musicState;
    private Vector2 startPosition;

    private final ArrayList<Block> blocks;
    private final ArrayList<Entity> entities;

    public Level() {
        blocks = new ArrayList<>();
        entities = new ArrayList<>();
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void addBlock(Block block) {
        blocks.add(block);
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public String getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(String nextLevel) {
        this.nextLevel = nextLevel;
    }

    public MusicState getMusicState() {
        return musicState;
    }

    public void setMusicState(MusicState musicState) {
        this.musicState = musicState;
    }

    public Vector2 getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Vector2 startPosition) {
        this.startPosition = startPosition;
    }

    public ArrayList<Block> getBlocksInBounds(int x, int y, int width, int height) {
        System.out.println(x + ", " + y + ", " + width + ", " + height);
        ArrayList<Block> blocksInBounds = new ArrayList<>();
        for(Block block : blocks) {
            if(block.getPosition().x >= x && block.getPosition().x <= block.getPosition().x + width
            && block.getPosition().y >= y && block.getPosition().y <= block.getPosition().y + height) {
                blocksInBounds.add(block);
            }
        }
        return blocksInBounds;
    }
}
