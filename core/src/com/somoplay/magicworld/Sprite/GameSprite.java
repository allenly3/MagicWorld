package com.somoplay.magicworld.Sprite;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class GameSprite { //this class is a frame to create every Animation

    protected Body body;
    protected Animation <TextureRegion> animation;
    public  float width=100;
    public   float height=100;

    public GameSprite(Body body)
    {
        this.body=body;
    }


    public void setAnimation(float delay,TextureRegion[] reg)
    {
        animation=new Animation(delay,reg);
        width=reg[0].getRegionWidth(); // get this data to fix rigid position
        height=reg[0].getRegionHeight();// get this data to fix rigid position
    }

    public void update(float delta)
    {

    }

    public void render(SpriteBatch batch,float delta)
    {
        batch.begin();
        batch.draw(animation.getKeyFrame(delta,true),
                body.getPosition().x -width/2,
                body.getPosition().y -height/2 );
        batch.end();
    }

    public Body getBody()
    {
        return  body;
    }

    public Vector2 getPosition()
    {
        return body.getPosition();
    }
    public float getWidth()
    {
        return width;
    }
    public float getHeight()
    {
        return height;
    }
}
