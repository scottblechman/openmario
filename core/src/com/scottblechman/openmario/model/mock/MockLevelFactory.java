package com.scottblechman.openmario.model.mock;

import com.badlogic.gdx.math.Vector2;
import com.scottblechman.openmario.model.Block;
import com.scottblechman.openmario.model.Level;
import com.scottblechman.openmario.state.MusicState;

public class MockLevelFactory {

    public static Level buildMockLevel() {
        Level level = new Level();
        level.setMusicState(MusicState.OVERWORLD);
        level.setStartPosition(new Vector2(0, 1));

        level.addBlock(new Block(new Vector2(0, 0)));
        level.addBlock(new Block(new Vector2(1, 0)));
        level.addBlock(new Block(new Vector2(2, 0)));

        return level;
    }
}
