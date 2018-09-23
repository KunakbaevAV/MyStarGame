package ru.geekbrains.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.geekbrains.StarGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		float aspect = 3/4f;
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.x = 350;
		config.y = 50;
		config.width = 400;
		config.height = (int)(config.width/aspect);
		new LwjglApplication(new StarGame(), config);
	}
}
