package com.somoplay.magicworld.Sprite;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.somoplay.magicworld.MagicWorld;
import com.somoplay.magicworld.Screens.PlayScreen;

import java.util.Random;

public class Bat extends Enemy {

    public float health = 100;
    public boolean destroyed = false;
    Random random;

    public Bat(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        random=new Random();
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();

        bdef.position.set(getX(), getY());

        bdef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bdef);
        body.setGravityScale(0);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(22/ MagicWorld.PPM);
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void onHit() {
        if(health > 0){
            health -= 50;
        }
        System.out.println(health);
    }

    @Override
    public void update(float dt) {
        if(body.getPosition().y >= getY()){
            velocity = new Vector2(-1f*random.nextInt(2),-1f );
        } else if(body.getPosition().y <= (screen.player.getPosition().y+0.32)){ velocity = new Vector2(1,1);}
        body.setLinearVelocity(velocity);
    }
}
