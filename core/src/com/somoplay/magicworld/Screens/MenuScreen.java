package com.somoplay.magicworld.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.somoplay.magicworld.MagicWorld;
import com.somoplay.magicworld.Resource.LoadResource;

public class MenuScreen implements Screen
{
    LoadResource loadResource;

    Viewport viewport;
    MagicWorld game;
    Stage stage;
    Body body;

    public MenuScreen(MagicWorld game)
    {
        this.game=game;
        init();
    }

    public void init()
    {
        loadResource=new LoadResource();



    }

    public void show() {

    }

    @Override
    public void render(float delta) {

            Gdx.gl.glClearColor(1, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        }



    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }




}
