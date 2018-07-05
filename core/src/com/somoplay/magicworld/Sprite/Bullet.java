package com.somoplay.magicworld.Sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.somoplay.magicworld.MagicWorld;
import com.somoplay.magicworld.Screens.PlayScreen;


import java.util.ArrayList;

public class Bullet{

    private static Texture texture;
    private static TextureRegion region;

    public Vector2 position;
    public boolean destroyed = false;
    public boolean toBeDestroyed = false;

    private PlayScreen screen;
    private World world;
    private Body bulletBody;

    private int state;

    public Bullet(PlayScreen screen, Vector2 position, int state){
        this.position = position;
        this.screen = screen;
        this.state = state;

        world = screen.getWorld();
        if(texture == null){
            texture = new Texture("red.jpg");
            region = new TextureRegion(texture, 0,0,32,32);
        }

        defineBullet();

    }
    public void defineBullet(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(screen.player.body.getPosition().x + 0.16f, screen.player.body.getPosition().y + 0.16f);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bulletBody = world.createBody(bdef);
        bulletBody.setGravityScale(0);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10/ MagicWorld.PPM,10/MagicWorld.PPM);

        fdef.shape = shape;
        fdef.isSensor = true;
        bulletBody.createFixture(fdef).setUserData(this);

    }
    public void render(SpriteBatch batch){
        if(!destroyed)
            batch.draw(region, bulletBody.getPosition().x - 16/ MagicWorld.PPM , bulletBody.getPosition().y - 16/ MagicWorld.PPM, 32/MagicWorld.PPM,32/MagicWorld.PPM);
    }

    public void update(float dt){
        if(state == 1){ bulletBody.setLinearVelocity(2, 0);}
        else if (state == 3){ bulletBody.setLinearVelocity(-2,0);}
        if(toBeDestroyed && !destroyed){
            world.destroyBody(bulletBody);
            destroyed = true;
        }
    }

    public Body getBody(){
        return bulletBody;
    }

    public void setToDestroy(){
        toBeDestroyed = true;
    }

}
