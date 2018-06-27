package com.somoplay.magicworld;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.somoplay.magicworld.Screens.MenuScreen;

public class MagicWorld extends Game {
	public static SpriteBatch batch;
	public static final float PPM = 100;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
	    super.render();
	}

	@Override
	public void dispose () {
		super.dispose();
	    batch.dispose();
	}
}
