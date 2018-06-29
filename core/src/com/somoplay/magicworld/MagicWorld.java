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

	public static float screenWidth,screenHeight;// get screen width and height from user device
	public MenuScreen menuScreen;
	public PlayScreen playScreen;
	public SpriteBatch batch;


	public void create () {
		screenWidth=Gdx.graphics.getWidth();
		screenHeight=Gdx.graphics.getHeight();
		batch=new SpriteBatch();

		menuScreen=new MenuScreen(this);
		playScreen=new PlayScreen(this);
		setScreen(menuScreen);
		//setScreen(playScreen);

	}
	public SpriteBatch getBatch() {
		return batch;
	}

	@Override
	public void dispose () {

	}
}
