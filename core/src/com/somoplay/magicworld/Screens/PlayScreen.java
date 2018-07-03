package com.somoplay.magicworld.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.somoplay.magicworld.MagicWorld;
import com.somoplay.magicworld.Sprite.Protagonist;

public class PlayScreen implements Screen {

    public static int level=0;
    MagicWorld game;
    Protagonist  protagonist;
    float statetime=0;
    SpriteBatch batch;


    public PlayScreen(MagicWorld game){
        this.game=game;
        init();
    }
    public void init()
    {
        batch=new SpriteBatch();
        protagonist=new Protagonist();
    }



    @Override

    public void render(float delta) {
        handleInput(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        statetime=statetime+delta;
        protagonist.render(batch,statetime);






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
    public void show()
    {
        Gdx.input.setCatchBackKey(true);
        batch = game.getBatch();

    }

    public void handleInput(float delta) {


        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            protagonist.state = 3;

            protagonist.getBody().setLinearVelocity(-1, protagonist.getBody().getLinearVelocity().y);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            protagonist.state = 1;

            protagonist.getBody().setLinearVelocity(1, protagonist.getBody().getLinearVelocity().y);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            protagonist.state = 2;

            protagonist.getBody().setLinearVelocity(-1, protagonist.getBody().getLinearVelocity().y);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            protagonist.state = 4;

            protagonist.getBody().setLinearVelocity(-1, protagonist.getBody().getLinearVelocity().y);
        }

    }
}
