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
import com.somoplay.magicworld.Resource.LoadResource;
import com.somoplay.magicworld.Screens.PlayScreen;
import com.somoplay.magicworld.WorldContactListener;


import java.util.ArrayList;

public class Bullet{

    public static Texture texture;
    public static TextureRegion region;

    public Vector2 position;
    public boolean destroyed = false;
    public boolean toBeDestroyed = false;

    public PlayScreen screen;
    public World world;
    public Body bulletBody;


    public Bullet(PlayScreen screen, Vector2 position){
        this.position = position;
        this.screen = screen;


        world = screen.getWorld();
        if(texture == null){
            texture = LoadResource.assetManager.get("images/blt.png");
            region = new TextureRegion(texture );
        }

        defineBullet();

    }
    public void defineBullet(){
        BodyDef bdef = new BodyDef();

        if(screen.player.state==1||screen.player.state==2) {
            bdef.position.set(screen.player.body.getPosition().x + 0.16f, screen.player.body.getPosition().y + 0.10f);
        }
        if(screen.player.state==3||screen.player.state==4) {
            bdef.position.set(screen.player.body.getPosition().x - 0.10f, screen.player.body.getPosition().y + 0.10f);
        }

        bdef.type = BodyDef.BodyType.DynamicBody;
        bulletBody = world.createBody(bdef);
        bulletBody.setGravityScale(0);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8/ MagicWorld.PPM,8/MagicWorld.PPM);

        fdef.shape = shape;
        fdef.isSensor = true;
        bulletBody.createFixture(fdef).setUserData(this);

        if(screen.player.state==1||screen.player.state==2) {
            bulletBody.setLinearVelocity(2, 0);
        }
        if(screen.player.state==3||screen.player.state==4) {
            bulletBody.setLinearVelocity(-2, 0);
        }

    }
    public void render(SpriteBatch batch){
        if(!destroyed)
            batch.draw(region, bulletBody.getPosition().x - 8/ MagicWorld.PPM , bulletBody.getPosition().y - 8/ MagicWorld.PPM, 16/MagicWorld.PPM,16/MagicWorld.PPM);
    }

    public void update(float dt){
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
