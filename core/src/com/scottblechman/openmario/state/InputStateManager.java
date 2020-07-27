package com.scottblechman.openmario.state;

public class InputStateManager {

    InputState state;

    public InputStateManager() {
        this.state = InputState.NONE;
    }

    public void setState(InputState state) {
        this.state = state;
    }

    public InputState getState() {
        return state;
    }
}
