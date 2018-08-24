package com.somoplay.magicworld.Resource;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;

import javax.xml.soap.Text;

public class LoadResource  // This class is to load the resources before running
{
    public static AssetManager assetManager;

    public LoadResource()
    {
        assetManager=new AssetManager();
        assetManager.load("images/menu.png",Texture.class);
        assetManager.load("images/runnerleft.png",Texture.class);
        assetManager.load("images/start.jpg",Texture.class);
        assetManager.load("images/runnerright.png",Texture.class);
        assetManager.load("images/rightstop.png",Texture.class);
        assetManager.load("images/leftstop.png",Texture.class);
        assetManager.load("images/bg1.png",Texture.class);
        assetManager.load("images/bg2.jpg",Texture.class);
        assetManager.load("images/buttonLeft.png",Texture.class);
        assetManager.load("images/buttonRight.png",Texture.class);
        assetManager.load("images/buttonA.png",Texture.class);
        assetManager.load("images/buttonB.png",Texture.class);
        assetManager.load("images/blt.png", Texture.class);
        assetManager.load("Background.mp3", Music.class);
        assetManager.load("images/door.jpg",Texture.class);
        assetManager.load("images/empty.jpg",Texture.class);
        assetManager.load("images/full.jpg",Texture.class);
        assetManager.load("mwfont.png",Texture.class);
        assetManager.load("mwfont.fnt", BitmapFont.class);

        assetManager.load("enemy/Walking_000.png",Texture.class);
        assetManager.load("enemy/Walking_003.png",Texture.class);
        assetManager.load("enemy/Walking_006.png",Texture.class);
        assetManager.load("enemy/Walking_009.png",Texture.class);
        assetManager.load("enemy/Walking_012.png",Texture.class);
        assetManager.load("enemy/Walking_015.png",Texture.class);
        assetManager.load("enemy/Walking_018.png",Texture.class);
        assetManager.load("enemy/Walking_021.png",Texture.class);
        assetManager.load("enemy/Walking_024.png",Texture.class);
        assetManager.load("enemy/Walking_027.png",Texture.class);
        assetManager.load("enemy/Walking_030.png",Texture.class);

        assetManager.load("enemy2/Walking_000.png",Texture.class);
        assetManager.load("enemy2/Walking_003.png",Texture.class);
        assetManager.load("enemy2/Walking_006.png",Texture.class);
        assetManager.load("enemy2/Walking_009.png",Texture.class);
        assetManager.load("enemy2/Walking_012.png",Texture.class);
        assetManager.load("enemy2/Walking_015.png",Texture.class);
        assetManager.load("enemy2/Walking_018.png",Texture.class);
        assetManager.load("enemy2/Walking_021.png",Texture.class);
        assetManager.load("enemy2/Walking_024.png",Texture.class);
        assetManager.load("enemy2/Walking_027.png",Texture.class);
        assetManager.load("enemy2/Walking_030.png",Texture.class);

        assetManager.load("Sball.png",Texture.class);
        assetManager.load("traps.jpg",Texture.class);
        assetManager.load("spike.jpg",Texture.class);
        assetManager.load("images/miss.png",Texture.class);

        assetManager.load("images/button_sound.png",Texture.class);
        assetManager.load("images/button_sound_mute.png",Texture.class);

        assetManager.finishLoading();
    }


}
