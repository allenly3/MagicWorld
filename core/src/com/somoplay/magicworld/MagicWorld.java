package com.somoplay.magicworld;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.somoplay.magicworld.Resource.LoadResource;
import com.somoplay.magicworld.Screens.MenuScreen;
import com.somoplay.magicworld.Screens.PlayScreen;

public class MagicWorld extends Game {

	public static float screenWidth,screenHeight;
	public MenuScreen menuScreen;

	public static final float PPM = 100;
	public  SpriteBatch batch;


	public void create () {
	    batch = new SpriteBatch();

		// get screen width and height from user device
		screenWidth=Gdx.graphics.getWidth();
		screenHeight=Gdx.graphics.getHeight();

		menuScreen=new MenuScreen(this);
		setScreen(menuScreen);

	}

	@Override
	public void dispose () {

	}
}
