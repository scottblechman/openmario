package com.scottblechman.openmario.util;

import com.badlogic.gdx.math.Vector2;
import com.scottblechman.openmario.model.BlockType;

public class TextureUtil {

    public static Vector2 getBlockRegionIndex(BlockType type, int tileSize) {
        Vector2 index = new Vector2(0, 0);
        switch (type) {
            case BRICK:
                index.x = 1;
                index.y = 0;
                break;
            case QUESTION:
                index.x = 24;
                index.y = 0;
                break;
            case GROUND:
            default:
                break;
        }
        index.x *= tileSize;
        index.y *= tileSize;
        return index;
    }
}
