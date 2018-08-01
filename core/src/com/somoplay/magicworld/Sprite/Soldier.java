package com.somoplay.magicworld.Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.somoplay.magicworld.MagicWorld;
import com.somoplay.magicworld.Resource.LoadResource;
import com.somoplay.magicworld.Screens.PlayScreen;

import javax.management.remote.SubjectDelegationPermission;


public class Soldier extends Enemy {

    SpriteBatch batch;
    public float health = 100;
    public boolean destroyed = false;
    private boolean behindPlayer = false;
    public float x,y;
    float statetime;


    Texture bar,txright[];
    public Sprite redbar;
    public Animation<Texture> soldierright;
    public Animation<TextureRegion>  soldierleft;
    public int soldierstate=0;// 0 left,   1 right

    public Soldier(PlayScreen screen, float x, float y){
        super(screen, x, y);
        this.x=x;
        this.y=y;

        defineAnimation();


    }

    public void defineAnimation()
    {
        batch=screen.batch;
        txright=new Texture[11];
        txright[0]= LoadResource.assetManager.get("enemy/Walking_000.png");
        txright[1]= LoadResource.assetManager.get("enemy/Walking_003.png");
        txright[2]= LoadResource.assetManager.get("enemy/Walking_006.png");
        txright[3]= LoadResource.assetManager.get("enemy/Walking_009.png");
        txright[4]= LoadResource.assetManager.get("enemy/Walking_012.png");
        txright[5]= LoadResource.assetManager.get("enemy/Walking_015.png");
        txright[6]= LoadResource.assetManager.get("enemy/Walking_018.png");
        txright[7]= LoadResource.assetManager.get("enemy/Walking_021.png");
        txright[8]= LoadResource.assetManager.get("enemy/Walking_024.png");
        txright[9]= LoadResource.assetManager.get("enemy/Walking_027.png");
        txright[10]= LoadResource.assetManager.get("enemy/Walking_030.png");

        soldierright=new Animation<Texture>(Gdx.graphics.getDeltaTime(),txright);
        TextureRegion tx[]=new TextureRegion[11];
        for(int i=0;i<11;i++)
        {
            tx[i]=new TextureRegion(txright[i]);
            tx[i].flip(true,false);
        }
        soldierleft=new Animation<TextureRegion>(Gdx.graphics.getDeltaTime(),tx);

        bar= LoadResource.assetManager.get("images/empty.jpg");
        redbar=new Sprite(new TextureRegion(bar));
        redbar.setColor(1,0,0,1);



    }
    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();

        bdef.position.set(getX(), getY());

        bdef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(19/MagicWorld.PPM);
        fdef.shape = shape;

        body.createFixture(fdef).setUserData(this);
        shape.setPosition(new Vector2(0,19/MagicWorld.PPM));
        body.createFixture(fdef).setUserData(this);


        EdgeShape leftSide = new EdgeShape();
        leftSide.set(new Vector2(-18/MagicWorld.PPM,-10/MagicWorld.PPM), new Vector2(-18/MagicWorld.PPM,20/MagicWorld.PPM));
        fdef.shape = leftSide;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("SoldierLeftSide");

        EdgeShape rightSide = new EdgeShape();
        rightSide.set(new Vector2(18/MagicWorld.PPM,-10/MagicWorld.PPM), new Vector2(18/MagicWorld.PPM,20/MagicWorld.PPM));
        fdef.shape = rightSide;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("SoldierRightSide");
    }

    @Override
    public void onHit() {
        if(health > 0){
            health -= 25;
        }
        System.out.println(health);
    }

    @Override
    public void update(float dt){


        if(body.getPosition().x + 1.5f <= screen.player.body.getPosition().x)
        {
            behindPlayer = true;

        } else if (body.getPosition().x >= screen.player.body.getPosition().x + 1.5f)
        {
            behindPlayer = false;
        }
        if(!behindPlayer)
        {
            soldierstate=1;
            velocity = new Vector2(-1,body.getLinearVelocity().y);

        }
        else if (behindPlayer)
        {
            soldierstate=0;
            velocity = new Vector2(1,body.getLinearVelocity().y);
        }

        redbar.setSize(health/3/MagicWorld.PPM,8/MagicWorld.PPM);
        screen.batch.begin();
        redbar.setPosition( body.getPosition().x-0.15f, body.getPosition().y+0.38f);
        redbar.draw(screen.batch);

        if( soldierstate==1) {
            screen.batch.draw( soldierleft.getKeyFrame(statetime * 0.4f, true),
                    body.getPosition().x - 0.16f,
                    body.getPosition().y - 0.22f, 0.35f, 0.65f);
        }
        else if( soldierstate==0)
        {
            screen.batch.draw( soldierright.getKeyFrame(statetime * 0.4f, true),
                    body.getPosition().x - 0.16f,
                    body.getPosition().y - 0.22f, 0.35f, 0.65f);
        }



        screen.batch.end();
        statetime+=dt;
        body.setLinearVelocity(velocity);


    }

    public void hitPlayer(){
        //body.applyLinearImpulse(60f,0,body.getWorldCenter().x,body.getWorldCenter().y,true);
    }
}
