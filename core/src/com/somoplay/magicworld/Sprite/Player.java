package com.somoplay.magicworld.Sprite;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.somoplay.magicworld.MagicWorld;
import com.somoplay.magicworld.Resource.LoadResource;
import com.somoplay.magicworld.Screens.PlayScreen;

import static com.somoplay.magicworld.MagicWorld.screenHeight;
import static com.somoplay.magicworld.MagicWorld.screenWidth;

public class Player extends GameSprite {

    public int state = 1;

    public float health = 100;
    public float healthcopy = health;
    private boolean destroyed = false;
    protected HealthBar healthbar;

    public Texture txRightMove, txRightStop, txLeftMove, txLeftStop;
    Animation<TextureRegion> rightMoving, rightStop, leftMoving, leftStop;

    //POWER UPS
    public boolean doubleFire = false;


    public Player(PlayScreen screen) {
        super(screen);

        batch = new SpriteBatch();
        bodyDef = new BodyDef();
        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();

        FixtureDef fixturedef = new FixtureDef();

        bodyDef.position.set(160 / MagicWorld.PPM, 200 / MagicWorld.PPM);

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        shape.setRadius(20/MagicWorld.PPM);
        fixturedef.shape = shape;
        fixture = body.createFixture(fixturedef);
        fixture.setUserData(this);

        init();

        healthbar = new HealthBar(this);


    }

    public void init() {

        //-----------------Right side running------------------------------
        txRightMove = LoadResource.assetManager.get("images/runnerright.png");
        TextureRegion[] tr1 = new TextureRegion[12];
        int a = 0;
        for (int i = 0; i < TextureRegion.split(txRightMove, 135, 130).length; i++) {
            for (int j = 0; j < TextureRegion.split(txRightMove, 135, 130)[i].length; j++) {
                tr1[a] = TextureRegion.split(txRightMove, 135, 130)[i][j];
                a++;
            }
        }
        rightMoving = setAnimation(tr1, 1 / 12f, rightMoving);
        a = 0;
        //-----------------Left side running------------------------------
        txLeftMove = LoadResource.assetManager.get("images/runnerleft.png");
        TextureRegion[] tr2 = new TextureRegion[12];

        for (int i = 0; i < TextureRegion.split(txLeftMove, 137, 130).length; i++) {
            for (int j = 0; j < TextureRegion.split(txLeftMove, 137, 130)[i].length; j++) {
                tr2[11 - a] = TextureRegion.split(txLeftMove, 137, 130)[i][j];
                a++;
            }
        }
        leftMoving = setAnimation(tr2, 1 / 12f, leftMoving);

        //----------------------Right side stop-----------------------
        txRightStop = LoadResource.assetManager.get("images/rightstop.png");
        TextureRegion[] tr3 = new TextureRegion[1];

        tr3[0] = new TextureRegion(txRightStop);
        rightStop = setAnimation(tr3, 1 / 12f, rightStop);

        //--------------------Left side stop-------------------
        txLeftStop = LoadResource.assetManager.get("images/leftstop.png");
        TextureRegion[] tr4 = new TextureRegion[1];

        tr4[0] = new TextureRegion(txLeftStop);
        leftStop = setAnimation(tr4, 1 / 12f, leftStop);

    }


    public Animation setAnimation(TextureRegion[] reg, float delay, Animation ani) {
        ani = new Animation(delay, reg);
        width = reg[0].getRegionWidth() / 100f;
        height = reg[0].getRegionHeight() / 100f;

        return ani;

    }

    public void render(SpriteBatch batch, float delta) {

        if (state == 1) {

            batch.begin();

            batch.draw(rightMoving.getKeyFrame(delta, true),
                    body.getPosition().x - 0.5f / 2f,
                    body.getPosition().y - 0.5f / 2f, 0.5f, 0.5f);
            batch.end();
        }
        if (state == 2) {


            batch.begin();

            batch.draw(rightStop.getKeyFrame(delta, true),
                    body.getPosition().x - 0.326f / 2f,
                    body.getPosition().y - 0.49f / 2f, 0.326f, 0.49f);
            batch.end();
        }
        if (state == 3) {

            batch.begin();

            batch.draw(leftMoving.getKeyFrame(delta, true),
                    body.getPosition().x - 0.5f / 2f,
                    body.getPosition().y - 0.5f / 2f, 0.5f, 0.5f);
            batch.end();
        }
        if (state == 4) {


            batch.begin();

            batch.draw(leftStop.getKeyFrame(delta, true),
                    body.getPosition().x - 0.326f / 2f,
                    body.getPosition().y - 0.49f / 2f, 0.326f, 0.49f);
            batch.end();
        }


    //-------profile and health bar
        batch.begin();
        batch.draw(healthbar.profile,body.getPosition().x-screenWidth/100/2f,body.getPosition().y+screenHeight/100/2-0.65f,0.6f,0.6f);

        healthbar.redprofile.setPosition(body.getPosition().x-screenWidth/100/2f,body.getPosition().y+screenHeight/100/2-0.65f);
        healthbar.redprofile.setColor(1,0,0,healthbar.opacity);
        healthbar.redprofile.draw(batch);

        batch.draw(healthbar.emptytx,body.getPosition().x-screenWidth/100/2f+0.65f,body.getPosition().y+screenHeight/100/2-0.65f,2f,0.2f);

        healthbar.redhealthbar.setPosition(body.getPosition().x-screenWidth/100/2f+0.65f,body.getPosition().y+screenHeight/100/2-0.65f);
        healthbar.redhealthbar.setSize(health/50f,0.2f);
        healthbar.redhealthbar.setRegionWidth((int)health*3);
        healthbar.redhealthbar.draw(batch);

        healthbar.blood.setRegionWidth((int)(healthcopy)*3);
        batch.draw(healthbar.blood,body.getPosition().x-screenWidth/100/2f+0.65f,body.getPosition().y+screenHeight/100/2-0.65f,healthcopy/50f,0.2f);

        slowdown();
        batch.end();

    }


    public void slowdown()
    {

        if(health>healthcopy && health>0)
        {
            health-=  Gdx.graphics.getDeltaTime()*150;
        }
         if(health<healthcopy)
        {
            health+=Gdx.graphics.getDeltaTime()*150;
        }
        if(healthbar.opacity>0.05f)
        {
            healthbar.opacity-=0.05f;
        }
    }

    public void onHit(int value){
        if(healthcopy > 0) {
            healthcopy -= value;
        }
        System.out.println("Player Health: " + healthcopy);
        healthbar.opacity=1;
    }

    public void addHealth(float value){
        healthcopy += value;
    }

    public float getHealth(){ return healthcopy;}

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public void setDoubleFire(boolean powerup) {
        doubleFire = powerup;
    }
}
