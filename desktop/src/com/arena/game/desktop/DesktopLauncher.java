package com.arena.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.arena.game.Arena;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.foregroundFPS = 200;
		config.backgroundFPS = 200;
		config.vSyncEnabled = false;
		config.title = "WitchArena";
		config.pauseWhenBackground = false;
		config.pauseWhenMinimized = false;
		config.forceExit = true;
		new LwjglApplication(new Arena(), config);
	}
}
