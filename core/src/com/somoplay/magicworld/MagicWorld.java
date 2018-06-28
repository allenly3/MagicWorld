package com.somoplay.magicworld;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MagicWorld extends Game {

	public  static float screenWidth, screenHeight;// 



	@Override
	public void create () {
		screenWidth=Gdx.graphics.getWidth();
		screenHeight=Gdx.graphics.getHeight();
		//System.out.println(screenHeight+" "+screenWidth);


	}


//	public void render () {
//		Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//	}

	@Override
	public void dispose () {

	}
}
