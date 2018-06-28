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

public class MagicWorld extends Game {

	public static float screenWidth,screenHeight;// get screen width and height from user device
	public MenuScreen menuScreen;


	public void create () {
		screenWidth=Gdx.graphics.getWidth();
		screenHeight=Gdx.graphics.getHeight();

		menuScreen=new MenuScreen(this);
		setScreen(menuScreen);

	}


	@Override
	public void dispose () {

	}
}
