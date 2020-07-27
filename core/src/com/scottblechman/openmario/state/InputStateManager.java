package com.scottblechman.openmario.state;

import com.scottblechman.openmario.state.InputState;

import java.util.ArrayList;
import java.util.Queue;

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
