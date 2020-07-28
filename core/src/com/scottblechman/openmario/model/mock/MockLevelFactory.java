package com.scottblechman.openmario.model.mock;

import com.badlogic.gdx.math.Vector2;
import com.scottblechman.openmario.model.Block;
import com.scottblechman.openmario.model.BlockType;
import com.scottblechman.openmario.model.Level;
import com.scottblechman.openmario.state.MusicState;

public class MockLevelFactory {

    public static Level buildMockLevel() {
        Level level = new Level();
        level.setMusicState(MusicState.OVERWORLD);
        level.setNextLevel("1-2");
        level.setStartPosition(new Vector2(0, 1));

        level.addBlock(new Block(BlockType.GROUND, new Vector2(0, 0)));
        level.addBlock(new Block(BlockType.GROUND, new Vector2(1, 0)));
        level.addBlock(new Block(BlockType.GROUND, new Vector2(2, 0)));
        level.addBlock(new Block(BlockType.GROUND, new Vector2(3, 0)));
        level.addBlock(new Block(BlockType.GROUND, new Vector2(4, 0)));
        level.addBlock(new Block(BlockType.GROUND, new Vector2(5, 0)));
        level.addBlock(new Block(BlockType.GROUND, new Vector2(6, 0)));
        level.addBlock(new Block(BlockType.GROUND, new Vector2(7, 0)));
        level.addBlock(new Block(BlockType.GROUND, new Vector2(8, 0)));
        level.addBlock(new Block(BlockType.GROUND, new Vector2(9, 0)));
        level.addBlock(new Block(BlockType.GROUND, new Vector2(10, 0)));
        level.addBlock(new Block(BlockType.GROUND, new Vector2(11, 0)));

        level.addBlock(new Block(BlockType.BRICK, new Vector2(7, 3)));
        level.addBlock(new Block(BlockType.QUESTION, new Vector2(8, 3)));
        level.addBlock(new Block(BlockType.BRICK, new Vector2(9, 3)));

        return level;
    }
}
