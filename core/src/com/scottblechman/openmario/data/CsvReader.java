package com.scottblechman.openmario.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.scottblechman.openmario.model.Block;
import com.scottblechman.openmario.model.BlockType;
import com.scottblechman.openmario.model.Level;
import com.scottblechman.openmario.state.MusicState;

public class CsvReader {

    /**
     * Reads level metadata and world data from a .csv file and creates a Level.
     * @param name - filename of .csv file for level to be loaded
     * @return loaded level
     */
    public static Level readLevelData(String name) {
        Level level = new Level();

        FileHandle handle = Gdx.files.local("level/" + name + ".csv");
        String text = handle.readString();
        String[] lines = text.split("\\r?\\n");
        for(String line : lines) {
            line = line.replace(", ", ",");
            String[] tokens = line.split(",");
            parseLevelTokens(tokens, level);
        }

        return level;
    }

    /**
     * Adds to or modifies the provided level based on the token value.
     * @param tokens -
     * @param level -
     */
    private static void parseLevelTokens(String[] tokens, Level level) {
        switch (tokens[0]) {
            case "music":
                String music = tokens[1];
                setLevelMusic(music, level);
            case "nextlevel":
                String nextLevel = tokens[1];
                level.setNextLevel(nextLevel);
                break;
            case "startpos":
                float startX = Float.parseFloat(tokens[1]);
                float startY = Float.parseFloat(tokens[2]);
                level.setStartPosition(new Vector2(startX, startY));
                break;
            case "block":
                addBlockToLevel(tokens, level);
                break;
            default:
                break;
        }
    }

    /**
     * Sets the music to be played on the level based on the token value.
     * @param musicType -
     * @param level -
     */
    private static void setLevelMusic(String musicType, Level level) {
        switch(musicType) {
            case "overworld":
                level.setMusicState(MusicState.OVERWORLD);
                break;
            default:
                level.setMusicState(MusicState.NONE);
                break;
        }
    }

    /**
     * Adds a block to the provided level, using type and position from the tokens.
     * @param tokens -
     * @param level -
     */
    private static void addBlockToLevel(String[] tokens, Level level) {
        String type = tokens[1];
        BlockType blockType = BlockType.NONE;
        switch(type) {
            case "ground":
                blockType = BlockType.GROUND;
                break;
            case "brick":
                blockType = BlockType.BRICK;
                break;
            case "question":
                blockType = BlockType.QUESTION;
                break;
            default:
                break;
        }

        int posX = Integer.parseInt(tokens[2]);
        int posY = Integer.parseInt(tokens[3]);

        Block block = new Block(blockType, new Vector2(posX, posY));
        level.addBlock(block);
    }
}
