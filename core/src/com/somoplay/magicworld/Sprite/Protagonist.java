package com.somoplay.magicworld.Sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.somoplay.magicworld.Resource.LoadResource;

public class Protagonist extends GameSprite
{
    public static int state=2;//This is to represent 4 kind of state of protagonist.
    LoadResource loadResource;
    Texture txrunRight,txrunLeft,txstopright,txstopleft;
    Animation<TextureRegion> RightMoving, RightStop,LeftMoving, LeftStop;

    public Protagonist(Body body)
    {
        super(body);
        loadResource=new LoadResource();
        txrunRight=loadResource.assetManager.get("images/runnerright.png");
        txrunLeft=loadResource.assetManager.get("images/runnerleft.png");
        txstopright=loadResource.assetManager.get("images/rightstop.png");
        txstopleft=loadResource.assetManager.get("images/leftstop.png");
    }
    public Animation setAnimation( float delay,TextureRegion[] reg, Animation ani)
    {
        ani=new Animation(delay,reg);
        width=reg[0].getRegionWidth();
        height=reg[0].getRegionHeight();
        return ani;

    }


}
