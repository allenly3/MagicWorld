package com.somoplay.magicworld.Sprite;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.somoplay.magicworld.MagicWorld;
import com.somoplay.magicworld.Screens.PlayScreen;


public class Soldier extends Enemy {

    public float health = 100;
    public boolean destroyed = false;

    public Soldier(PlayScreen screen, float x, float y){
        super(screen, x, y);

    }
    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();

        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16/ MagicWorld.PPM,32/MagicWorld.PPM);
        fdef.shape = shape;

        body.createFixture(fdef).setUserData(this);
        body.setGravityScale(10);

    }

    @Override
    public void onHit(Bullet bullet) {
        if(health > 0){
            health -= 25;
        }
        System.out.println(health);
    }

    @Override
    public void update(float dt){
        body.setLinearVelocity(new Vector2(-1,0));
    }

    public void hitPlayer(){
        body.applyLinearImpulse(60f,0,body.getWorldCenter().x,body.getWorldCenter().y,true);
    }
}
