package com.somoplay.magicworld.Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.somoplay.magicworld.GifDecoder;
import com.somoplay.magicworld.MagicWorld;
import com.somoplay.magicworld.Screens.PlayScreen;

public class Ally extends Sprite {
    private World world;
    private PlayScreen screen;


    public Body body;
    private boolean behindPlayer = true;
    private boolean active = false;
    public float health = 250;
    public boolean destroyed = false;

    Animation<TextureRegion> idleright,idleleft,runleft, runright;
    int allystate=1;// stands for four kinds of animation

    float statetime=0;

    public Ally(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;


        runright= GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("images/run.gif").read());
        idleright =GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP,Gdx.files.internal("images/idle.gif").read());

        runleft=GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP,Gdx.files.internal("images/run.gif").read());
        idleleft=GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP,Gdx.files.internal("images/idle.gif").read());


        setPosition(x, y);
        defineAlly();
    }

    public void defineAlly() {
        BodyDef bdef = new BodyDef();

        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(19/MagicWorld.PPM);
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

    public void update(float dt,SpriteBatch batch) {
        statetime+=dt;


        if(body.getPosition().y <= 0) {
            if (behindPlayer) {
                body.setTransform(screen.getPlayer().getPosition().x - 80 / MagicWorld.PPM, screen.getPlayer().getPosition().y, body.getAngle());
            } else{
                body.setTransform(screen.getPlayer().getPosition().x + 80 / MagicWorld.PPM, screen.getPlayer().getPosition().y, body.getAngle());
            }
        }

        // Makes Ally follow player
        if (active) {
            if (Math.abs(body.getPosition().x - screen.getPlayer().getPosition().x) >= 80 / MagicWorld.PPM) {
                if (behindPlayer) {
                    body.setLinearVelocity(new Vector2(1, body.getLinearVelocity().y));
                    allystate=2;
                } else {
                    body.setLinearVelocity(new Vector2(-1, body.getLinearVelocity().y));
                    allystate=3;
                }
            } else {
                body.setLinearVelocity(new Vector2(0, body.getLinearVelocity().y));
                if(body.getPosition().x - screen.getPlayer().getPosition().x>0)
                {
                    allystate=4;
                }
                else
                {
                    allystate=1;
                }
            }
        }

        if(body.getPosition().x + 1f <= screen.player.body.getPosition().x){
            behindPlayer = true;
        } else if (body.getPosition().x >= screen.player.body.getPosition().x + 1f) {
            behindPlayer = false;
        }

        if(allystate==1)
        {
            batch.draw(idleright.getKeyFrame(statetime),body.getPosition().x-0.12f,body.getPosition().y-0.20f,0.3f,0.45f);
        }
        else if( allystate==2)
        {
            batch.draw(runright.getKeyFrame(statetime),body.getPosition().x-0.12f,body.getPosition().y-0.2f,0.3f,0.45f);
        }
        else if(allystate==3)
        {
            if(!runleft.getKeyFrame(statetime).isFlipX())
            {
                runleft.getKeyFrame(statetime).flip(true,false);
            }
            batch.draw(runleft.getKeyFrame(statetime),body.getPosition().x-0.12f,body.getPosition().y-0.2f,0.3f,0.45f);
        }
        else if (allystate==4)
        {
            if(!idleleft.getKeyFrame(statetime).isFlipX())
            {
                idleleft.getKeyFrame(statetime).flip(true,false);
            }
            batch.draw(idleleft.getKeyFrame(statetime),body.getPosition().x-0.12f,body.getPosition().y-0.2f,0.3f,0.45f);
        }

    }

    public void follow(){
        active = true;
    }

    public void fireRight() {
        if (active) {
            screen.getAllyBullets().add(new AllyBullet(screen, body.getPosition()));
            screen.getAllyBullets().get(screen.getAllyBullets().size() - 1).getBody().setLinearVelocity(2, 0);
        }
    }
    public void fireLeft() {
        if (active) {
            screen.getAllyBullets().add(new AllyBullet(screen, body.getPosition()));
            screen.getAllyBullets().get(screen.getAllyBullets().size() - 1).getBody().setLinearVelocity(-2, 0);
        }
    }
    public void onHit(int value){
        if(health > 0) {
            health -= value;
        }
    }
}
