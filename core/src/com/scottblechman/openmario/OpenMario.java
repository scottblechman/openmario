package com.scottblechman.openmario;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scottblechman.openmario.state.InputStateManager;
import com.scottblechman.openmario.state.MusicState;
import com.scottblechman.openmario.state.MusicStateManager;
import com.scottblechman.openmario.state.ScreenStateManager;

public class OpenMario extends Game {

	// Window state
	public final int WINDOW_WIDTH;
	public final int WINDOW_HEIGHT;
	public float windowScale;
	public final int BASE_TILE_SIZE;

	public SpriteBatch batch;

	public InputStateManager inputStateManager;
	public MusicStateManager musicStateManager;
	public ScreenStateManager screenStateManager;

	public OpenMario(float[] windowState) {
		// All values of windowState except window scale arrive as int
		this.WINDOW_WIDTH = (int) windowState[0];
		this.WINDOW_HEIGHT = (int) windowState[1];
		this.windowScale = windowState[2];
		this.BASE_TILE_SIZE = (int) windowState [3];
	}

	@Override
	public void create () {
		batch = new SpriteBatch();

		inputStateManager = new InputStateManager();
		musicStateManager = new MusicStateManager();
		screenStateManager = new ScreenStateManager(this);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		musicStateManager.dispose();
	}
}
