package com.somoplay.magicworld.Sprite;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.somoplay.magicworld.Screens.PlayScreen;

// Base class for all enemies
public abstract class Enemy extends Sprite{
    protected World world;
    protected PlayScreen screen;

    public Body body;
    public Vector2 velocity;

    public boolean slowed;
    public float slowedTimer;

    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;

        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(-1, 0);
        body.setActive(false);
    }

    public void reverseVelocity(boolean x, boolean y){
        if(x){
            velocity.x = - velocity.x;
        }
        if(y){
            velocity.y = -velocity.y;
        }
    }

    public void setSlowed(boolean slowed) {
        this.slowed = slowed;
    }

    protected abstract void defineEnemy();
    public abstract void onHit();
    public abstract void update(float dt);
}
