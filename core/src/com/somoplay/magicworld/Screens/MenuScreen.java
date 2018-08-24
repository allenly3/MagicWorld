package com.somoplay.magicworld.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.somoplay.magicworld.MagicWorld;
import com.somoplay.magicworld.Resource.LoadResource;

public class MenuScreen implements Screen
{
    Texture tStart,tMenu,tbg;
    Viewport viewport;
    MagicWorld game;
    Image imageStart,imageMenu[],background;
    Stage stage;
    LoadResource loadResource;
    public static boolean debug=true;// flag of whether showing the start image

    public MenuScreen(MagicWorld game)
    {
        this.game=game;
        init();
    }

    public void init()
    {

        loadResource=new LoadResource();

        viewport=new StretchViewport(game.screenWidth,game.screenHeight);
        stage=new Stage(viewport);

        tStart=loadResource.assetManager.get("images/start.jpg");
        tMenu=loadResource.assetManager.get("images/menu.png");


        TextureRegion tr[][]=TextureRegion.split(tMenu,256,172);//split the Menu into 4 pieces

        imageStart=new Image(tStart);
        imageStart.setSize(game.screenWidth,game.screenHeight);

        imageMenu=new Image[4];
        for(int i=0;i<4;i++)
        {
            imageMenu[i]=new Image(tr[i][0]);
            imageMenu[i].setSize(game.screenWidth/2.5f,game.screenHeight/2.5f);

        }
        imageMenu[0].setPosition(game.screenWidth*0.06f,game.screenHeight/2+10);
        imageMenu[1].setPosition(game.screenWidth/2+10,game.screenHeight/2+10);
        imageMenu[2].setPosition(game.screenWidth*0.06f,game.screenHeight*0.05f);
        imageMenu[3].setPosition(game.screenWidth/2+10,game.screenHeight*0.05f);

        tbg=loadResource.assetManager.get("images/bg1.png");
        background=new Image(tbg);
        background.setSize(game.screenWidth,game.screenHeight);


        initListener();
        Gdx.input.setInputProcessor(stage);


    }
    public void initListener() // Add Input Listener on the LIST IMAGES
    {
        imageStart.addListener(new ClickListener(){

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                debug=false;
                return true;

            }

        });

            imageMenu[0].addListener(new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y,int pointer, int button)
            {

                PlayScreen.level=1;
                stage.getActors().clear();
                PlayScreen ps=new PlayScreen(game);
                game.setScreen(ps);
                Gdx.input.setInputProcessor(ps.controlStage);

                return  true;
            }
        });

        imageMenu[1].addListener(new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y,int pointer, int button)
            {

                PlayScreen.level=2;
                stage.getActors().clear();
                PlayScreen ps=new PlayScreen(game);
                game.setScreen(ps);
                Gdx.input.setInputProcessor(ps.controlStage);

                return  true;
            }
        });
        imageMenu[2].addListener(new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y,int pointer, int button)
            {

                PlayScreen.level=3;
                stage.getActors().clear();
                PlayScreen ps=new PlayScreen(game);
                game.setScreen(ps);
                Gdx.input.setInputProcessor(ps.controlStage);


                return  true;
            }
        });
        imageMenu[3].addListener(new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y,int pointer, int button)
            {

                PlayScreen.level=4;
                stage.getActors().clear();
                PlayScreen ps=new PlayScreen(game);
                game.setScreen(ps);
                Gdx.input.setInputProcessor(ps.controlStage);
               return  true;
            }
        });


    }





    public void render(float delta) {

            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
           update(  delta);
            stage.act();
            stage.draw();


        }
    public void update(float dt) {
        if (debug) {
            // stage.getActors().clear();

            stage.addActor(imageStart);
        }
        else
            {
                stage.addActor(background);

            for (int i = 0; i < imageMenu.length; i++) {
                stage.addActor(imageMenu[i]);
            }

        }
    }



    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
    public void show()
    {

    }



}
