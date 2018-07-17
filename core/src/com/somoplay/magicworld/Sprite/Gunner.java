package com.somoplay.magicworld.Sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.somoplay.magicworld.MagicWorld;
import com.somoplay.magicworld.Resource.LoadResource;
import com.somoplay.magicworld.Screens.PlayScreen;


public class Gunner extends Enemy {

    private static Texture texture;
    private static TextureRegion region;

    public float health = 100;
    private float timeSinceLastFire = 0;
    public boolean destroyed = false;
    private boolean behindPlayer = false;

    public Gunner(PlayScreen screen, float x, float y) {
        super(screen, x, y);
    }

    @Override
    protected void defineEnemy() {

        if(texture == null){
            texture = LoadResource.assetManager.get("images/blt.png");
            region = new TextureRegion(texture);
        }

        BodyDef bdef = new BodyDef();

        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16/ MagicWorld.PPM,16/MagicWorld.PPM);
        fdef.shape = shape;

        Fixture fixture = body.createFixture(fdef);
        fixture.setUserData(this);

    }

    @Override
    public void onHit(Bullet bullet) {
        if(health > 0){
            health -= 25;
        }
        System.out.println(health);
    }

    @Override
    public void update(float dt) {

        if(Math.abs(screen.player.getPosition().x - body.getPosition().x) <= 200/MagicWorld.PPM){
            body.setLinearVelocity(0,0);
            if(timeSinceLastFire >= 0.8f){
                fire();
                timeSinceLastFire = 0;
            }
        } else {
            if (!behindPlayer) {
                body.setLinearVelocity(new Vector2(-1, body.getLinearVelocity().y));
            } else if (behindPlayer) {
                body.setLinearVelocity(new Vector2(1, body.getLinearVelocity().y));
            }

        }

        if(body.getPosition().x + 1.5f <= screen.player.body.getPosition().x){
            behindPlayer = true;
        } else if (body.getPosition().x >= screen.player.body.getPosition().x + 1.5f){
            behindPlayer = false;
        }

        timeSinceLastFire += dt;
    }

    public void fire(){
        screen.getEnemyBullets().add(new EnemyBullet(screen, body.getPosition()));
        if(behindPlayer){
            screen.getEnemyBullets().get(screen.getEnemyBullets().size() - 1).getBody().setLinearVelocity(2,0);
        } else if(!behindPlayer){
            screen.getEnemyBullets().get(screen.getEnemyBullets().size() - 1).getBody().setLinearVelocity(-2,0);
        }
    }
}
