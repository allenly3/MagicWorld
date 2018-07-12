package com.somoplay.magicworld.Sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.somoplay.magicworld.Resource.LoadResource;
import com.somoplay.magicworld.Screens.PlayScreen;

public class HealthBar {
   Player player;
    Texture tx,fulltx,emptytx;
    TextureRegion profile,blood;
    public HealthBar(Player player)
    {
        this.player=player;
        tx= player.txRightMove;
        profile=new TextureRegion(tx,70,12,60,60);

        fulltx= LoadResource.assetManager.get("images/full.jpg");
        emptytx=LoadResource.assetManager.get("images/empty.jpg");
        blood=new TextureRegion(fulltx);


    }





}
