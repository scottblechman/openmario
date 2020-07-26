package com.scottblechman.openmario.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.scottblechman.openmario.OpenMario;

import java.awt.*;

public class DesktopLauncher {

	public static void main (String[] arg) {
		WindowState windowState = new WindowState();
		LwjglApplicationConfiguration config = createConfiguration(windowState);
		new LwjglApplication(new OpenMario(windowState.serialize()), config);
	}

	private static LwjglApplicationConfiguration createConfiguration(WindowState windowState) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.width = (int) (windowState.getBaseWidth() * windowState.getWindowScale());
		config.height = (int) (windowState.getBaseHeight() * windowState.getWindowScale());
		return config;
	}
}
