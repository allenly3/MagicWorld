package com.somoplay.magicworld.Sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.somoplay.magicworld.MagicWorld;
import com.somoplay.magicworld.Resource.LoadResource;
import com.somoplay.magicworld.Screens.PlayScreen;

public class EnemyBullet {

    private static Texture texture;
    private static TextureRegion region;

    public Vector2 position;
    public boolean destroyed = false;
    public boolean toBeDestroyed = false;

    private PlayScreen screen;
    private World world;
    private Body bulletBody;



    public EnemyBullet(PlayScreen screen, Vector2 position){
        this.position = position;
        this.screen = screen;


        world = screen.getWorld();
        if(texture == null){
            texture = LoadResource.assetManager.get("Sball.png");
            region = new TextureRegion(texture );
        }

        defineEnemyBullet();

    }
    public void defineEnemyBullet(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bulletBody = world.createBody(bdef);
        bulletBody.setGravityScale(0);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8/ MagicWorld.PPM,8/MagicWorld.PPM);

        fdef.shape = shape;
        fdef.isSensor = true;
        bulletBody.createFixture(fdef).setUserData(this);
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
