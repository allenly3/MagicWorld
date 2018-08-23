package com.somoplay.magicworld.Sprite;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.somoplay.magicworld.MagicWorld;
import com.somoplay.magicworld.Screens.PlayScreen;

public class TrackingBullet extends Bullet{

    Enemy enemy;

    public TrackingBullet(PlayScreen screen, Vector2 position, Enemy enemy) {
        super(screen, position);
        this.enemy = enemy;
    }

    // Creates the body and fixture of the tracking bullet
    @Override
    public void defineBullet() {
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
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        // Calculates the movement based on target location so it creates the tracking effect
        if(!destroyed) {
            if (bulletBody.getPosition().x >= enemy.body.getPosition().x && bulletBody.getPosition().y > enemy.body.getPosition().y) {
                bulletBody.setLinearVelocity(-1, -1);
            } else if (bulletBody.getPosition().x >= enemy.body.getPosition().x && bulletBody.getPosition().y == enemy.body.getPosition().y) {
                bulletBody.setLinearVelocity(-1, 0);
            } else if (bulletBody.getPosition().x >= enemy.body.getPosition().x && bulletBody.getPosition().y < enemy.body.getPosition().y) {
                bulletBody.setLinearVelocity(-1, 1);
            } else if (bulletBody.getPosition().x < enemy.body.getPosition().x && bulletBody.getPosition().y > enemy.body.getPosition().y) {
                bulletBody.setLinearVelocity(1, -1);
            } else if (bulletBody.getPosition().x < enemy.body.getPosition().x && bulletBody.getPosition().y == enemy.body.getPosition().y) {
                bulletBody.setLinearVelocity(1, 0);
            } else if (bulletBody.getPosition().x < enemy.body.getPosition().x && bulletBody.getPosition().y < enemy.body.getPosition().y) {
                bulletBody.setLinearVelocity(1, 1);
            } else {
                bulletBody.setLinearVelocity(0, 0);
            }
        }
    }
}
