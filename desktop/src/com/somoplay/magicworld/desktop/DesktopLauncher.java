package com.somoplay.magicworld.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.somoplay.magicworld.MagicWorld;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.width=2500;
//		config.height=1600;

		new LwjglApplication(new MagicWorld(), config);
		System.out.println(arg.length);
	}
}
