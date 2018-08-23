package com.somoplay.magicworld.Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.somoplay.magicworld.MagicWorld;
import com.somoplay.magicworld.Resource.LoadResource;
import com.somoplay.magicworld.Screens.PlayScreen;


public class Gunner extends Enemy {

    private static Texture texture;
    private static TextureRegion region;
    private Texture bastion;

    public float health = 100;
    private float timeSinceLastFire = 0;
    public boolean destroyed = false;
    private boolean behindPlayer = false;

    public Gunner(PlayScreen screen, float x, float y) {
        super(screen, x, y);
    }

    // Creates the body and fixture for gunner, the edgeshapes are used to detect and bounce off walls
    @Override
    protected void defineEnemy() {

        if(texture == null){
            texture = LoadResource.assetManager.get("images/blt.png");
            region = new TextureRegion(texture);
        }
        bastion=new Texture(Gdx.files.internal("enemy/bastion.png"));


        BodyDef bdef = new BodyDef();

        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bdef);
        body.setUserData(this);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16/ MagicWorld.PPM,16/MagicWorld.PPM);
        fdef.shape = shape;

        Fixture fixture = body.createFixture(fdef);
        fixture.setUserData(this);

        EdgeShape leftSide = new EdgeShape();
        leftSide.set(new Vector2(-18/MagicWorld.PPM,-8/MagicWorld.PPM), new Vector2(-18/MagicWorld.PPM,8/MagicWorld.PPM));
        fdef.shape = leftSide;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("GunnerLeftSide");

        EdgeShape rightSide = new EdgeShape();
        rightSide.set(new Vector2(18/MagicWorld.PPM,-8/MagicWorld.PPM), new Vector2(18/MagicWorld.PPM,8/MagicWorld.PPM));
        fdef.shape = rightSide;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("GunnerRightSide");

    }

    // Damage taken from player hits and total health of gunner can be easily changed
    @Override
    public void onHit() {
        if(health > 0){
            health -= 20;
        }
    }

    // Controls the movement of gunner, it will move back and forth when it hits a wall
    // and stop to shoot the player if player gets within a certain range
    @Override
    public void update(float dt) {

        if(Math.abs(screen.player.getPosition().x - body.getPosition().x) <= 200/MagicWorld.PPM){
            body.setLinearVelocity(new Vector2 (0,0));
            if(timeSinceLastFire >= 0.8f){
                fire();
                timeSinceLastFire = 0;
            }
        } else{
            body.setLinearVelocity(velocity);
        }

        if(body.getPosition().x + 1.5f <= screen.player.body.getPosition().x){
            behindPlayer = true;
        } else if (body.getPosition().x >= screen.player.body.getPosition().x + 1.5f){
            behindPlayer = false;
        }
        timeSinceLastFire += dt;

        screen.batch.draw(bastion,body.getPosition().x-0.15f,body.getPosition().y-0.15f,0.35f,0.35f);
    }

    // Fires bullets in the direction of the player
    public void fire(){
        screen.getEnemyBullets().add(new EnemyBullet(screen, body.getPosition()));
        if(behindPlayer){
            screen.getEnemyBullets().get(screen.getEnemyBullets().size() - 1).getBody().setLinearVelocity(2,0);
        } else if(!behindPlayer){
            screen.getEnemyBullets().get(screen.getEnemyBullets().size() - 1).getBody().setLinearVelocity(-2,0);
        }
    }
}
