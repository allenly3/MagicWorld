package com.somoplay.magicworld.Resource;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

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

        assetManager.finishLoading();
    }


}
