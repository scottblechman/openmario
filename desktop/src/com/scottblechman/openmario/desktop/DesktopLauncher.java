package com.scottblechman.openmario.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.scottblechman.openmario.OpenMario;

public class DesktopLauncher {

	private static final double WINDOW_SCALE = 2;

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = createConfiguration();
		new LwjglApplication(new OpenMario(), config);
	}

	private static LwjglApplicationConfiguration createConfiguration() {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.width = (int) (320 * WINDOW_SCALE);
		config.height = (int) (240 * WINDOW_SCALE);
		return config;
	}
}
