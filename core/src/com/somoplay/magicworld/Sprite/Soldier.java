package com.somoplay.magicworld.Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
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
import com.somoplay.magicworld.MagicWorld;
import com.somoplay.magicworld.Resource.LoadResource;
import com.somoplay.magicworld.Screens.PlayScreen;

import javax.management.remote.SubjectDelegationPermission;


public class Soldier extends Enemy {

    SpriteBatch batch;
    public float health = 100;
    public boolean destroyed = false;
    private boolean behindPlayer = false;
    float statetime;
    int attackormove=0;//0 means moving ,1 means attacking
    float duration=0;
    public static boolean touch=false;
    float opacity=0;
    Sprite miss;
    ParticleEffect blood;
    int bloodstate=0;

    Texture bar,txright[],idleright[] ;
    public Sprite redbar;
    public Animation<Texture> soldierright,stopright;
    public Animation<TextureRegion>  soldierleft,stopleft;
    public int soldierstate=0;// 0 left,   1 right




    float move=0;
   public  Body hand;

    public Soldier(PlayScreen screen, float x, float y){
        super(screen, x, y);

        defineAnimation();
        blood=new ParticleEffect();
        blood.load(Gdx.files.internal("images/blood.p"),Gdx.files.internal("images/"));

        blood.scaleEffect(0.001f);

    }

    public void defineAnimation()
    {
        batch=screen.batch;
        Texture temp= (LoadResource.assetManager.get("images/miss.png"));
        miss=new Sprite(temp);
        //-----------run    right
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
        //--------------runleft
        for(int i=0;i<11;i++)
        {
            tx[i]=new TextureRegion(txright[i]);
            tx[i].flip(true,false);
        }
        soldierleft=new Animation<TextureRegion>(Gdx.graphics.getDeltaTime(),tx);

        //-------idleright
        idleright=new Texture[5];
        idleright[0]=new Texture(Gdx.files.internal("enemy/Attacking_000.png"));
        idleright[1]=new Texture(Gdx.files.internal("enemy/Attacking_003.png"));
        idleright[2]=new Texture(Gdx.files.internal("enemy/Attacking_006.png"));
        idleright[3]=new Texture(Gdx.files.internal("enemy/Attacking_008.png"));
        idleright[4]=new Texture(Gdx.files.internal("enemy/Attacking_013.png"));

        stopright=new Animation<Texture>(Gdx.graphics.getDeltaTime(),idleright);
        //-----idleleft
        TextureRegion tr[]=new TextureRegion[5];
        for(int i=0;i<5;i++)
        {
            tr[i]=new TextureRegion(idleright[i]);
            tr[i].flip(true,false);
        }
        stopleft=new Animation<TextureRegion>(Gdx.graphics.getDeltaTime(),tr);



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

        bdef.gravityScale=0;
        hand=  world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(19/MagicWorld.PPM);
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);// body----part1

        // creates two circles used for hitboxes
        body.createFixture(fdef).setUserData(this);
        shape.setPosition(new Vector2(0,19/MagicWorld.PPM));
        body.createFixture(fdef).setUserData(this);// ------body-----part2


        //------hand----
        shape.setRadius(5/MagicWorld.PPM);
        fdef.shape=shape;
        fdef.isSensor=true;
        hand.createFixture(fdef).setUserData("hand");


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
    public void update(float dt) {


        if(!destroyed) {
            if (attackormove == 0) {
                if (body.getPosition().x + 1.5f <= screen.player.body.getPosition().x) {
                    behindPlayer = true;

                } else if (body.getPosition().x >= screen.player.body.getPosition().x + 1.5f) {
                    behindPlayer = false;
                }
                if (!behindPlayer) {
                    soldierstate = 1;
                    if (Math.abs(body.getPosition().x - screen.player.getPosition().x) > 0.41f) {
                        if (slowed) {
                            velocity = new Vector2(-0.5f, body.getLinearVelocity().y);
                            slowedTimer += dt;
                        } else {
                            velocity = new Vector2(-1, body.getLinearVelocity().y);
                        }
                    } else {
                        soldierstate = 2;
                        velocity = new Vector2(0, body.getLinearVelocity().y);
                    }

                } else if (behindPlayer) {
                    soldierstate = 0;
                    if (Math.abs(body.getPosition().x - screen.player.getPosition().x) > 0.41f) {
                        if (slowed) {
                            velocity = new Vector2(0.5f, body.getLinearVelocity().y);
                            slowedTimer += dt;
                        } else {
                            velocity = new Vector2(1, body.getLinearVelocity().y);
                        }
                    } else {
                        soldierstate = 3;
                        velocity = new Vector2(0, body.getLinearVelocity().y);
                    }
                }
            }

            redbar.setSize(health / 3 / MagicWorld.PPM, 8 / MagicWorld.PPM);
            screen.batch.begin();
            redbar.setPosition(body.getPosition().x - 0.15f, body.getPosition().y + 0.38f);
            redbar.draw(screen.batch);


            if (soldierstate == 1) {
                screen.batch.draw(soldierleft.getKeyFrame(statetime * 0.4f, true),
                        body.getPosition().x - 0.16f,
                        body.getPosition().y - 0.22f, 0.35f, 0.65f);
                hand.setTransform(body.getPosition().x, body.getPosition().y - 0.05f, 0);
            } else if (soldierstate == 0) {
                screen.batch.draw(soldierright.getKeyFrame(statetime * 0.4f, true),
                        body.getPosition().x - 0.16f,
                        body.getPosition().y - 0.22f, 0.35f, 0.65f);
                hand.setTransform(body.getPosition().x, body.getPosition().y - 0.05f, 0);
            } else if (soldierstate == 2) {
                attackormove = 1;
                blood.setPosition(body.getPosition().x - 0.2f, body.getPosition().y + 0.04f);
                screen.batch.draw(stopleft.getKeyFrame(statetime * 0.1f, true),
                        body.getPosition().x - 0.25f,
                        body.getPosition().y - 0.22f, 0.47f, 0.68f);

                if (hand.getPosition().x - screen.player.getPosition().x > 0.12f) {
                    hand.setLinearVelocity(-0.30f, 0);

                    if (this.body.getPosition().x - hand.getPosition().x >= 0.217f) {
                        hand.setTransform(body.getPosition().x, body.getPosition().y - 0.05f, 0);
                        if (!touch) {
                            System.out.println("MISS");
                            opacity = 1;

                        } else {
                            bloodstate = 1;
                        }
                    }
                }

                if (bloodstate == 1) {
                    blood.draw(screen.batch, Gdx.graphics.getDeltaTime() * 2);
                }


                duration += Gdx.graphics.getDeltaTime();
                if (duration >= 0.80f) {

                    attackormove = 0;
                    duration = 0;
                    bloodstate = 0;
                    blood.reset();
                    blood.scaleEffect(0.001f);

                }

            } else if (soldierstate == 3) {
                attackormove = 1;
                blood.setPosition(body.getPosition().x + 0.2f, body.getPosition().y + 0.04f);
                screen.batch.draw(stopright.getKeyFrame(statetime * 0.1f, true),
                        body.getPosition().x - 0.16f,
                        body.getPosition().y - 0.22f, 0.47f, 0.68f);

                if (screen.player.getPosition().x - hand.getPosition().x > 0.12f) {
                    hand.setLinearVelocity(0.30f, 0);

                    if (hand.getPosition().x - this.body.getPosition().x >= 0.217f) {

                        hand.setTransform(body.getPosition().x, body.getPosition().y - 0.05f, 0);
                        if (!touch) {
                            System.out.println("MISS");
                            opacity = 1;

                        } else {
                            bloodstate = 1;
                        }
                    }
                }

                if (bloodstate == 1) {
                    blood.draw(screen.batch, Gdx.graphics.getDeltaTime() * 2);
                }

                duration += Gdx.graphics.getDeltaTime();
                if (duration >= 0.8f) {
                    attackormove = 0;
                    duration = 0;
                    bloodstate = 0;
                    blood.reset();
                    blood.scaleEffect(0.001f);

                }
            }

            miss.setPosition(body.getPosition().x - 0.1f, body.getPosition().y + 0.3f);
            miss.setSize(0.3f, 0.3f);
            miss.setColor(1, 1, 0, opacity);
            miss.draw(screen.batch);


            slowdown();


            screen.batch.end();
            statetime += dt;
            if (slowedTimer >= 3f) {
                slowed = false;
                slowedTimer = 0;
            }
            body.setLinearVelocity(velocity);
        }



    }
public void slowdown()
{
    if(opacity>0f)
    {
        opacity=opacity-0.05f;
    }
}

    public void hitPlayer(){
        //body.applyLinearImpulse(60f,0,body.getWorldCenter().x,body.getWorldCenter().y,true);
    }


}
