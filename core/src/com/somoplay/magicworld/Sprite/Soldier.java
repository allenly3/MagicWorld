package com.somoplay.magicworld.Sprite;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.somoplay.magicworld.MagicWorld;
import com.somoplay.magicworld.Screens.PlayScreen;


public class Soldier extends Enemy {

    public float health = 100;
    public boolean destroyed = false;
    private boolean behindPlayer = false;

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

        Fixture fixture = body.createFixture(fdef);
        fixture.setUserData(this);

        EdgeShape leftSide = new EdgeShape();
        leftSide.set(new Vector2(-18/MagicWorld.PPM,10/MagicWorld.PPM), new Vector2(-18/MagicWorld.PPM,32/MagicWorld.PPM));
        fdef.shape = leftSide;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("leftSide");

    }

    @Override
    public void onHit(Bullet bullet) {
        if(health > 0){
            health -= 25;
        }
        System.out.println(health);
    }

    public void jump(){
        body.applyLinearImpulse(new Vector2(-1,2),body.getWorldCenter(),true);
    }

    @Override
    public void update(float dt){
        if(!behindPlayer){
        body.setLinearVelocity(new Vector2(-1,body.getLinearVelocity().y));}
        else if (behindPlayer){
            body.setLinearVelocity(new Vector2(1,body.getLinearVelocity().y));
        }

        if(body.getPosition().x + 1.5f <= screen.player.body.getPosition().x){
            behindPlayer = true;
        } else if (body.getPosition().x >= screen.player.body.getPosition().x + 1.5f){
            behindPlayer = false;
        }


    }

    public void hitPlayer(){
        //body.applyLinearImpulse(60f,0,body.getWorldCenter().x,body.getWorldCenter().y,true);
    }
}
