package com.scottblechman.openmario.state;

import com.scottblechman.openmario.OpenMario;
import com.scottblechman.openmario.screen.LevelScreen;

public class ScreenStateManager {

    final OpenMario game;
    private ScreenState state;

    public ScreenStateManager(OpenMario game) {
        this.game = game;
        this.state = ScreenState.LEVEL; // State to load on application start
        updateScreen();
    }

    public ScreenState getState() {
        return state;
    }

    public void setState(ScreenState state) {
        this.state = state;
    }

    private void updateScreen() {
        switch (state) {
            case LEVEL:
                game.setScreen(new LevelScreen(game));
                break;
            default:
                break;
        }
    }
}
