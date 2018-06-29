package com.somoplay.magicworld.Sprite;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.somoplay.magicworld.Resource.LoadResource;

public class Protagonist extends GameSprite {

    public   int state=2;

    public Texture txRightMove,txRightStop,txLeftMove,txLeftStop;
    Animation<TextureRegion> rightMoving,rightStop,leftMoving,leftStop;

    public Protagonist(Body body) {
        super(body);
        //-----------------Right side running------------------------------
        txRightMove = LoadResource.assetManager.get("images/runnerright.png" );
        TextureRegion[] tr1 = new TextureRegion[12];
        int a = 0;
        for (int i = 0; i < TextureRegion.split(txRightMove, 135, 130).length; i++) {
            for (int j = 0; j < TextureRegion.split(txRightMove, 135, 130)[i].length; j++) {
                tr1[a] = TextureRegion.split(txRightMove, 135, 130)[i][j];
                a++;
            }
        }
        rightMoving=setAnimation(tr1,1/12f,rightMoving);
          a=0;
        //-----------------Left side running------------------------------
        txLeftMove = LoadResource.assetManager.get("images/runnerleft.png");
        TextureRegion[] tr2 = new TextureRegion[12];

        for (int i = 0; i < TextureRegion.split(txLeftMove, 137, 130).length; i++) {
            for (int j = 0; j < TextureRegion.split(txLeftMove, 137, 130)[i].length; j++) {
                tr2[11-a] = TextureRegion.split(txLeftMove, 137, 130)[i][j];
                a++;
            }
        }
        leftMoving=setAnimation(tr2,1/12f,leftMoving);

        //----------------------Right side stop-----------------------
        txRightStop=LoadResource.assetManager.get("images/rightstop.png");
        TextureRegion[]tr3=new TextureRegion[1];

        tr3[0]=new TextureRegion(txRightStop);
        rightStop=setAnimation(tr3,1/12f,rightStop);

        //--------------------Left side stop-------------------
        txLeftStop=LoadResource.assetManager.get("images/leftstop.png");
        TextureRegion[]tr4=new TextureRegion[1];

        tr4[0]=new TextureRegion(txLeftStop);
        leftStop=setAnimation(tr4,1/12f,leftStop);

    }



    public Animation setAnimation(TextureRegion[] reg,float delay,Animation ani)
    {
        ani=new Animation(delay,reg);
        width=reg[0].getRegionWidth();
        height=reg[0].getRegionHeight();
        return ani;

    }
    public void render(SpriteBatch batch, float delta)
    {
        if(state==1)
        {


        batch.begin();

            batch.draw(rightMoving.getKeyFrame(delta, true),
             body.getPosition().x - width / 2,
             body.getPosition().y - height / 2);
        batch.end();
        }
        if(state==2)
        {


            batch.begin();

            batch.draw(rightStop.getKeyFrame(delta, true),
                    body.getPosition().x - width / 2,
                    body.getPosition().y - height / 2);
            batch.end();
        }
        if(state==3)
        {


            batch.begin();

            batch.draw(leftMoving.getKeyFrame(delta, true),
                    body.getPosition().x - width / 2,
                    body.getPosition().y - height / 2);
            batch.end();
        }
        if(state==4)
        {


            batch.begin();

            batch.draw(leftStop.getKeyFrame(delta, true),
                    body.getPosition().x - width / 2,
                    body.getPosition().y - height / 2);
            batch.end();
        }
    }



}
