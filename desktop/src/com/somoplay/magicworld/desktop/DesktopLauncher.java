package com.somoplay.magicworld.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.somoplay.magicworld.MagicWorld;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.width=3500;
//		config.height=2600;

		new LwjglApplication(new MagicWorld(), config);
	}
}
