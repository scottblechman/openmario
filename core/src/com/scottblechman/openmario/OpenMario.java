package com.scottblechman.openmario;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scottblechman.openmario.screen.LevelScreen;
import com.scottblechman.openmario.state.ScreenStateManager;

public class OpenMario extends Game {
	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		//noinspection unused (required to initialize state machine)
		ScreenStateManager screenStateManager = new ScreenStateManager(this);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
