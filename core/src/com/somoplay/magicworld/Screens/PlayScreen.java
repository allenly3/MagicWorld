package com.somoplay.magicworld.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.somoplay.magicworld.MagicWorld;
import com.somoplay.magicworld.Resource.LoadResource;
import com.somoplay.magicworld.Sprite.Bullet;
import com.somoplay.magicworld.Sprite.Player;
import com.somoplay.magicworld.Sprite.Soldier;
import com.somoplay.magicworld.WorldContactListener;
import com.somoplay.magicworld.WorldCreator;

import java.util.ArrayList;

import static com.somoplay.magicworld.MagicWorld.batch;
import static com.somoplay.magicworld.MagicWorld.screenHeight;
import static com.somoplay.magicworld.MagicWorld.screenWidth;

public class PlayScreen implements Screen {

    World world;
    OrthographicCamera cam;
    Viewport viewport;
    Box2DDebugRenderer renderer;
    SpriteBatch batch;



    public static int level=0;

    private MagicWorld game;

    //private Controller controller;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    public Player player;
    boolean movingR,movingL,jumping,firing;
    private ArrayList<Bullet> bullets;
    private WorldCreator creator;

    private AssetManager manager;
    private Music music;
    // private HUD hud;
    private float deathTimer = 0;

    private float statetime;
    private Bullet bullet;

    Texture tbg,btLeft,btRight,btA,btB;
    Image background;
    public ImageButton buttonLeft,buttonRight,buttonA,buttonB;

    public Stage stage;
    public Stage controlStrage;


    //private HealthBar healthBar;

    public PlayScreen(MagicWorld game){
        this.game = game;
        cam = new OrthographicCamera();

        batch=new SpriteBatch();
        viewport = new FitViewport(screenWidth/MagicWorld.PPM, screenHeight/MagicWorld.PPM, cam);
        world = new World(new Vector2(0, -10), true);
        world.setContactListener(new WorldContactListener(this));
        renderer = new Box2DDebugRenderer();

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level_01.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / MagicWorld.PPM);


        creator = new WorldCreator(this);
        player = new Player(this);

        bullets = new ArrayList<Bullet>();
        manager = new AssetManager();

        tbg= LoadResource.assetManager.get("images/bg1.png");
        background=new Image(tbg);
        background.setSize(screenWidth,screenHeight);
        stage = new Stage();
        stage.addActor(background);



        controlStrage=new Stage();
        //---------------setup button-----------------
        btLeft=LoadResource.assetManager.get("images/buttonLeft.png");
        btRight=LoadResource.assetManager.get("images/buttonRight.png");
        btA=LoadResource.assetManager.get("images/buttonA.png");
        btB=LoadResource.assetManager.get("images/buttonB.png");
        //------button left
        TextureRegionDrawable up=new TextureRegionDrawable(TextureRegion.split(btLeft,99,145)[0][1]);
        TextureRegionDrawable down=new TextureRegionDrawable(TextureRegion.split(btLeft,99,145)[0][0]);
        buttonLeft=new ImageButton(up,down);
        buttonLeft.setPosition(10,5);
        controlStrage.addActor(buttonLeft);
        //button right------------
         up=new TextureRegionDrawable(TextureRegion.split(btRight,99,150)[0][0]);
         down=new TextureRegionDrawable(TextureRegion.split(btRight,99,150)[0][1]);
        buttonRight=new ImageButton(up,down);
        buttonRight.setPosition(100,5);
        controlStrage.addActor(buttonRight);
//----------------button A----------
        up=new TextureRegionDrawable(TextureRegion.split(btA   ,99,150)[0][0]);
        down=new TextureRegionDrawable(TextureRegion.split(btA,99,150)[0][1]);
        buttonA=new ImageButton(up,down);
        buttonA.setPosition(450,5);
        controlStrage.addActor(buttonA);
    //-------------button B-------------
        up=new TextureRegionDrawable(TextureRegion.split(btB,99,150)[0][0]);
       down=new TextureRegionDrawable(TextureRegion.split(btB,99,150)[0][1]);
        buttonB=new ImageButton(up,down);
        buttonB.setPosition(540,40);
        controlStrage.addActor(buttonB);



    }
    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);

    }

    public TiledMap getMap() {
        return map;
    }

    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime());
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        stage.act();

        mapRenderer.render();
        renderer.render(world, cam.combined);

        controlStrage.draw();
        controlStrage.act();

        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        for(Bullet bullet : bullets) {
            bullet.render(game.batch);
        }
        game.batch.end();

        statetime=statetime+delta;
        player.render(game.batch,statetime);



    }


    public void handleInput(float delta) {


        if (movingL) {
            player.state = 3;
            player.getBody().setLinearVelocity(-2, player.getBody().getLinearVelocity().y);
        }


        if (movingR) {
            player.state = 1;

            player.getBody().setLinearVelocity(2, player.getBody().getLinearVelocity().y);
        }
        if (jumping&&WorldContactListener.counter>0) {

            player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, 5);
        }
        if (firing ) {

            if(player.isDestroyed() == false) {
                bullet = new Bullet(this, new Vector2(player.body.getPosition().x, player.body.getPosition().y) );
                bullets.add(bullet);
            }
        }
// --------------button Right-----
        buttonRight.addListener(new InputListener(){

            public  void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                player.state = 2;
                movingR = false;
 //               player.getBody().setLinearVelocity(0, 0);
                super.touchUp(event,x,y,pointer,button);
            }
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button)
            {
                player.state = 1;
                movingR=true;
               // player.body.applyForce(2f,0f,0,0,true);
//                player.bodyDef.linearVelocity.set(20f,0f);
                return true;
            }

        });

//---------------------------------button Left-----
        buttonLeft.addListener(new InputListener(){

            public  void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                player.state = 4;
                movingL= false;
//              player.getBody().setLinearVelocity(0, 0);
                super.touchUp(event,x,y,pointer,button);
            }
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button)
            {
                player.state = 3;
                movingL=true;
                // player.body.applyForce(2f,0f,0,0,true);
//                player.bodyDef.linearVelocity.set(20f,0f);
                return true;
            }

        });

        //---------------------------------button A----
        buttonA.addListener(new InputListener(){
            public  void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {

                jumping= false;
                super.touchUp(event,x,y,pointer,button);
            }

            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button)
            {
                jumping=true;
                return true;
            }

        });
//---------------------------button B----------------------
        buttonB.addListener(
                new InputListener(){
            public  void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                firing= false;
                super.touchUp(event,x,y,pointer,button);
            }

            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button)
            {
                firing=true;
                return true;
            }

        });




    }



    public void update(float dt){
        handleInput(dt);
        world.step(1/60f, 6, 2);
        cam.position.set(player.body.getPosition().x,player.body.getPosition().y ,0 );
        cam.update();

        // if it touches a ground tile, then dissappear, if it touches a enemy, dissappear and apply damage.
        // get the size of it correct and the spawn location right as well.
        for(Bullet bullet: bullets){
            if(bullet.destroyed == false) {


                if (bullet.getBody().getPosition().x - player.body.getPosition().x > 3 || bullet.getBody().getPosition().x - player.body.getPosition().x < -3) {

                    bullet.setToDestroy();
                }

                bullet.update(dt);
            }
        }

        for (Soldier soldier: creator.getSoldiers()){
            if(soldier.health <= 0 && !soldier.destroyed){
                world.destroyBody(soldier.body);
                soldier.destroyed = true;
            }

            if(soldier.destroyed == false){
                soldier.update(dt);
                if(soldier.body.getPosition().x < player.body.getPosition().x + 500 / MagicWorld.PPM){
                    soldier.body.setActive(true);
                }
            }

        }
        deathTimer += dt;

        if(player.getHealth() <= 0 && !player.isDestroyed()){
            world.destroyBody(player.body);
            player.setDestroyed(true);
            deathTimer = 0;
        }

        if(player.body.getPosition().y < - 0.175 && !player.isDestroyed()){
            world.destroyBody(player.body);
            player.setDestroyed(true);
            deathTimer = 1;
        }

        if(deathTimer >= 1.5 && player.isDestroyed()){
            game.setScreen(new MenuScreen(game));
            //music.stop();
            dispose();
        }
        mapRenderer.setView(cam);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);

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
        map.dispose();
        manager.dispose();
    }

    public World getWorld() {
        return world;
    }

    public TiledMapTileLayer.Cell getCell(Body body){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        return layer.getCell((int)(body.getPosition().x *MagicWorld.PPM / 32), (int)(body.getPosition().y * MagicWorld.PPM / 32));
    }

}
