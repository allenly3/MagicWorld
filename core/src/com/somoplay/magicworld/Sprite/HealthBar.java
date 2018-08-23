package com.somoplay.magicworld.Sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.somoplay.magicworld.Resource.LoadResource;
import com.somoplay.magicworld.Screens.PlayScreen;

import static com.somoplay.magicworld.MagicWorld.screenHeight;
import static com.somoplay.magicworld.MagicWorld.screenWidth;

public class HealthBar {

    Player player;
    Texture tx,fulltx,emptytx;
    TextureRegion profile,blood;
    Sprite redprofile,redhealthbar;
    float opacity=0;

    // Creates the player healthbar displayed on top left side of screen
    public HealthBar(Player player) {
        this.player=player;
        tx= player.txRightMove;
        profile=new TextureRegion(tx,70,12,60,60);
        redprofile=new Sprite(profile);

        redprofile.setSize(0.60f,0.6f);
        fulltx= LoadResource.assetManager.get("images/full.jpg");
        emptytx=LoadResource.assetManager.get("images/empty.jpg");
        blood=new TextureRegion(fulltx);

        redhealthbar=new Sprite(blood);
        redhealthbar.setSize(2,0.2f);
        redhealthbar.setColor(1,0,0,1);

    }

}
