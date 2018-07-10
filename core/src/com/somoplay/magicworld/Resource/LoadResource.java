package com.somoplay.magicworld.Resource;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;

import javax.xml.soap.Text;

public class LoadResource
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







        assetManager.finishLoading();
    }


}
