package com.scottblechman.openmario.desktop;

public class WindowState {

    private final int BASE_WIDTH = 320;

    private final int BASE_HEIGHT = 240;

    private final int BASE_TILE_SIZE = 16;

    private float windowScale = 2;

    public int getBaseWidth() {
        return BASE_WIDTH;
    }

    public int getBaseHeight() {
        return BASE_HEIGHT;
    }

    public int getBaseTileSize() {
        return BASE_TILE_SIZE;
    }

    public float getWindowScale() {
        return windowScale;
    }

    public void setWindowScale(float WINDOW_SCALE) {
        this.windowScale = WINDOW_SCALE;
    }

    public float[] serialize() {
        return new float[]{BASE_WIDTH, BASE_HEIGHT, windowScale, BASE_TILE_SIZE};
    }
}
