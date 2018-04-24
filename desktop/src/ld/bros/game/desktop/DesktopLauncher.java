package ld.bros.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ld.bros.game.LudumDare41;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = LudumDare41.TITLE;
		config.width = LudumDare41.WIDTH;
		config.height = LudumDare41.HEIGHT;
		config.foregroundFPS = LudumDare41.FPS;

		new LwjglApplication(new LudumDare41(), config);
	}
}
