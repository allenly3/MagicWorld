package com.somoplay.magicworld.Sprite;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.somoplay.magicworld.MagicWorld;
import com.somoplay.magicworld.Screens.PlayScreen;

public class Ally extends Sprite {
    private World world;
    private PlayScreen screen;

    public Body body;
    private boolean behindPlayer = true;
    private boolean active = false;

    public Ally(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;

        setPosition(x, y);
        defineAlly();
    }

    public void defineAlly() {
        BodyDef bdef = new BodyDef();

        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16 / MagicWorld.PPM, 16 / MagicWorld.PPM);
        fdef.shape = shape;

        Fixture fixture = body.createFixture(fdef);
        fixture.setUserData(this);

        EdgeShape leftSide = new EdgeShape();
        leftSide.set(new Vector2(-18 / MagicWorld.PPM, -8 / MagicWorld.PPM), new Vector2(-18 / MagicWorld.PPM, 8 / MagicWorld.PPM));
        fdef.shape = leftSide;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("AllyLeftSide");

        EdgeShape rightSide = new EdgeShape();
        rightSide.set(new Vector2(18 / MagicWorld.PPM, -8 / MagicWorld.PPM), new Vector2(18 / MagicWorld.PPM, 8 / MagicWorld.PPM));
        fdef.shape = rightSide;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("AllyRightSide");
    }

    public void update(float dt) {

        if(body.getPosition().y <= 0) {
            if (behindPlayer) {
                body.setTransform(screen.getPlayer().getPosition().x - 80 / MagicWorld.PPM, screen.getPlayer().getPosition().y, body.getAngle());
            } else{
                body.setTransform(screen.getPlayer().getPosition().x + 80 / MagicWorld.PPM, screen.getPlayer().getPosition().y, body.getAngle());
            }
        }

        if (active) {
            if (Math.abs(body.getPosition().x - screen.getPlayer().getPosition().x) >= 80 / MagicWorld.PPM) {
                if (behindPlayer) {
                    body.setLinearVelocity(new Vector2(1, body.getLinearVelocity().y));
                } else {
                    body.setLinearVelocity(new Vector2(-1, body.getLinearVelocity().y));
                }
            } else {
                body.setLinearVelocity(new Vector2(0, body.getLinearVelocity().y));
            }
        }

        if(body.getPosition().x + 1f <= screen.player.body.getPosition().x){
            behindPlayer = true;
        } else if (body.getPosition().x >= screen.player.body.getPosition().x + 1f){
            behindPlayer = false;
        }
    }

    public void follow(){
        active = true;
    }

    public void fire() {
        if (active) {
            screen.getAllyBullets().add(new AllyBullet(screen, body.getPosition()));
            if (screen.player.state == 1 || screen.player.state == 2) {
                screen.getAllyBullets().get(screen.getAllyBullets().size() - 1).getBody().setLinearVelocity(2, 0);
            } else if (screen.player.state == 3 || screen.player.state == 4) {
                screen.getAllyBullets().get(screen.getAllyBullets().size() - 1).getBody().setLinearVelocity(-2, 0);
            }
        }
    }
}
