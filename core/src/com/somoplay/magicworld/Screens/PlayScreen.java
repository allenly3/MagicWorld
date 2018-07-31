package com.somoplay.magicworld.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.somoplay.magicworld.MagicWorld;
import com.somoplay.magicworld.Resource.LoadResource;
import com.somoplay.magicworld.Sprite.Ally;
import com.somoplay.magicworld.Sprite.AllyBullet;
import com.somoplay.magicworld.Sprite.Bullet;
import com.somoplay.magicworld.Sprite.EnemyBullet;
import com.somoplay.magicworld.Sprite.Gunner;
import com.somoplay.magicworld.Sprite.Player;
import com.somoplay.magicworld.Sprite.Soldier;
import com.somoplay.magicworld.Stats;
import com.somoplay.magicworld.WorldContactListener;
import com.somoplay.magicworld.WorldCreator;

import java.util.ArrayList;

import static com.somoplay.magicworld.MagicWorld.screenHeight;
import static com.somoplay.magicworld.MagicWorld.screenWidth;

public class PlayScreen implements Screen {

    World world;
    OrthographicCamera cam;
    Viewport viewport;
    Box2DDebugRenderer renderer;
    public SpriteBatch batch;
    public static float velocity=2.5f;
    public static float friction=0;



    public static int level=1;

    private MagicWorld game;

    //private Controller controller;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    public Player player;
    public boolean movingR,movingL,jumping,firing;
    private ArrayList<Bullet> bullets;
    private ArrayList<EnemyBullet> enemyBullets;
    private ArrayList<AllyBullet> allyBullets;
    private WorldCreator creator;

    private LoadResource loadResource;
    private Music music;
    private Stats stats;
    private float deathTimer = 0;
    private float elapsedTime = 0;
    private int trapIndex = 0;

    private float statetime;
    private float timeSinceLastFire = 1.5f;
    private Bullet bullet;
    private ArrayList<Float> ceilingTrapHeights;

    Texture tbg,btLeft,btRight,btA,btB;
    Image background;
    public ImageButton buttonLeft,buttonRight,buttonA,buttonB;
    public InputListener AL,BL;




    public Stage stage;
    public Stage controlStage;
    Label label;


    public PlayScreen(MagicWorld game){
        this.game = game;
        cam = new OrthographicCamera();


        this.batch=game.batch;
        viewport = new FitViewport(screenWidth/MagicWorld.PPM, screenHeight/MagicWorld.PPM, cam);
        world = new World(new Vector2(0, -10), true);
        world.setContactListener(new WorldContactListener(this));
        renderer = new Box2DDebugRenderer();

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level_0"+Integer.toString(level)+".tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / MagicWorld.PPM);


        creator = new WorldCreator(this);
        player = new Player(this);

        bullets = new ArrayList<Bullet>();
        enemyBullets = new ArrayList<EnemyBullet>();
        allyBullets = new ArrayList<AllyBullet>();

        loadResource = new LoadResource();

        ceilingTrapHeights = new ArrayList<Float>();
        for(Body ct: creator.getCeilingTraps()){
            ceilingTrapHeights.add(ct.getPosition().y);
        }

        tbg= LoadResource.assetManager.get("images/bg1.png");
        background=new Image(tbg);
        background.setSize(screenWidth,screenHeight);

        Label.LabelStyle fontstyle=new Label.LabelStyle(new BitmapFont(Gdx.files.internal("mwfont.fnt")), Color.WHITE);
        label=new Label(Integer.toString(WorldContactListener.score),fontstyle);
        label.setPosition(screenWidth*0.10f,screenHeight*0.87f);

        stage = new Stage();
        stage.addActor(background);
        stage.addActor(label);

        stats = new Stats(game.batch);



        controlStage =new Stage();
        //---------------setup button-----------------
        btLeft=LoadResource.assetManager.get("images/buttonLeft.png");
        btRight=LoadResource.assetManager.get("images/buttonRight.png");
        btA=LoadResource.assetManager.get("images/buttonA.png");
        btB=LoadResource.assetManager.get("images/buttonB.png");
        //------button left

        TextureRegionDrawable up=new TextureRegionDrawable(TextureRegion.split(btLeft,99,145)[0][1]);
        TextureRegionDrawable down=new TextureRegionDrawable(TextureRegion.split(btLeft,99,145)[0][0]);
        buttonLeft=new ImageButton(up,down);
        buttonLeft.setPosition(screenWidth*0.02f,screenHeight*0.01f);

        controlStage.addActor(buttonLeft);
        //button right------------
        up=new TextureRegionDrawable(TextureRegion.split(btRight,99,150)[0][0]);
        down=new TextureRegionDrawable(TextureRegion.split(btRight,99,150)[0][1]);
        buttonRight=new ImageButton(up,down);
        buttonRight.setPosition(screenWidth*0.02f+90,screenHeight*0.01f);
        controlStage.addActor(buttonRight);
//----------------button A----------
        up=new TextureRegionDrawable(TextureRegion.split(btA   ,99,150)[0][0]);
        down=new TextureRegionDrawable(TextureRegion.split(btA,99,150)[0][1]);
        buttonA=new ImageButton(up,down);
        buttonA.setPosition(screenWidth*0.70f,screenHeight*0.01f);
        controlStage.addActor(buttonA);
    //-------------button B-------------
        TextureRegion ttt=(TextureRegion.split(btB,99,150)[0][0]);
        Image ttt1=new Image(ttt);

        ttt1.setScale(5);
        up=(TextureRegionDrawable) ttt1.getDrawable();
       down=new TextureRegionDrawable(TextureRegion.split(btB,99,150)[0][1]);
        buttonB=new ImageButton(up,down);

        buttonB.setPosition(screenWidth*0.70f+90,screenHeight*0.078f);
        controlStage.addActor(buttonB);

        music = loadResource.assetManager.get("Background.mp3", Music.class);
        music.play();
        music.setLooping(true);

        //----listener

// --------------button Right-----
        buttonRight.addListener(new InputListener(){


            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                player.state = 2;
                movingR = false;
           player.getBody().setLinearVelocity(0, 0);
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
             player.getBody().setLinearVelocity(0, 0);
                movingL= false;
                super.touchUp(event,x,y,pointer,button);
            }
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button)
            {
                player.state = 3;
                movingL=true;
                return true;
            }

        });

        //---------------------------------button A----
        buttonA.addListener(AL=new InputListener(){
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
        buttonB.addListener(BL=new InputListener(){
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
    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);

    }

    public TiledMap getMap() {
        return map;
    }

    @Override
    public void render(float delta) {

        handleInput(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        label.setText(Integer.toString(WorldContactListener.score));
        stage.draw();
        stage.act();

        mapRenderer.render();
        renderer.render(world, cam.combined);

        update(delta);
        creator.creatorrender();
        controlStage.draw();
        controlStage.act();

        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        for(Bullet bullet : bullets) {
            bullet.render(game.batch);
        }


        game.batch.end();
        statetime=statetime+delta;
        if(!player.isDestroyed()) {
            player.render(game.batch, statetime);
        }




    }


    public void handleInput(float delta) {

        if(!player.isDestroyed()) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || movingL) {//--movingL
                player.state = 3;
                player.getBody().setLinearVelocity(-(velocity - friction), player.getBody().getLinearVelocity().y);
            }


            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || movingR) {//---nmovingR
                player.state = 1;

                player.getBody().setLinearVelocity((velocity - friction), player.getBody().getLinearVelocity().y);
            }

            if ((Gdx.input.isKeyPressed(Input.Keys.UP) || jumping) && player.getBody().getLinearVelocity().y == 0) {// --jumping

                player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, 5);
            }

            if (firing && timeSinceLastFire >= 0.3f) {

                if (player.isDestroyed() == false) {
                    bullet = new Bullet(this, new Vector2(player.body.getPosition().x, player.body.getPosition().y));
                    bullets.add(bullet);
                    for (Ally ally : creator.getAllies()) {
                        ally.fire();
                    }
                    timeSinceLastFire = 0;
                }
            }
        }

        timeSinceLastFire += delta;

    }

    public void update(float dt){

        world.step(1/60f, 6, 2);
        if(!player.isDestroyed()) {
            cam.position.set(player.body.getPosition().x, player.body.getPosition().y, 0);
            cam.update();
        }

        // if it touches a ground tile, then disappear, if it touches a enemy, disappears and apply damage.
        // get the size of it correct and the spawn location right as well.
        for(Bullet bullet: bullets){
            if(bullet.destroyed == false) {

                if (bullet.getBody().getPosition().x - player.body.getPosition().x > 3 || bullet.getBody().getPosition().x - player.body.getPosition().x < -3) {

                    bullet.setToDestroy();
                }

                bullet.update(dt);
            }
        }

        for(EnemyBullet eb: enemyBullets){
            if(eb.destroyed == false) {

                if (Math.abs(eb.getBody().getPosition().x - player.getPosition().x) >= 3) {
                //绝对值
                    eb.setToDestroy();
                }

                eb.update(dt);
            }
        }

        for(AllyBullet ab: allyBullets){
            if(ab.destroyed == false) {

                if (Math.abs(ab.getBody().getPosition().x - player.getPosition().x) >= 3) {

                    ab.setToDestroy();
                }

                ab.update(dt);
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

        for (Gunner gunner: creator.getGunners()){
            if(gunner.health <= 0 && !gunner.destroyed){
                world.destroyBody(gunner.body);
                gunner.destroyed = true;
            }

            if(gunner.destroyed == false){
                gunner.update(dt);
                if(gunner.body.getPosition().x < player.body.getPosition().x + 500 / MagicWorld.PPM){
                    gunner.body.setActive(true);
                }
            }

        }

        for(Ally ally: creator.getAllies()){
            ally.update(dt);
        }

        for(Body ct : creator.getCeilingTraps()){

            if(ct.getPosition().y <= (ceilingTrapHeights.get(trapIndex) - 96/MagicWorld.PPM)){
                ct.setLinearVelocity(0,0.7f);
                trapIndex++;
            } else if (ct.getPosition().y >= ceilingTrapHeights.get(trapIndex)){
                ct.setLinearVelocity(0,-0.7f);
                trapIndex++;
            }
        }
        trapIndex = 0;
        deathTimer += dt;
        if(!player.isDestroyed()) {
            elapsedTime += dt;
        }
        if(player.getHealth() <= 0 && !player.isDestroyed()){
            world.destroyBody(player.body);
            player.setDestroyed(true);
            deathTimer = 0;
            //WorldContactListener.score=0;
        }

        if(player.body.getPosition().y < - 0.175 && !player.isDestroyed()){
            world.destroyBody(player.body);
            player.setDestroyed(true);
            deathTimer = 1;
            //WorldContactListener.score=0;
        }

        if(deathTimer >= 1.5 && player.isDestroyed()){
            //game.setScreen(new MenuScreen(game));
            stats.setScoreLabel(WorldContactListener.score);
            stats.setTimeLabel(elapsedTime);
            displayStats();
            music.stop();
            //dispose();
        }
        mapRenderer.setView(cam);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update((int)screenWidth, (int)screenHeight);

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
        music.dispose();
       //world.dispose();
    }

    public World getWorld() {
        return world;
    }

    public TiledMapTileLayer.Cell getCell(Body body){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        return layer.getCell((int)(body.getPosition().x *MagicWorld.PPM / 32), (int)(body.getPosition().y * MagicWorld.PPM / 32));
    }

    public Player getPlayer(){return player;}

    public void nextLevel(){
        game.setScreen(new MenuScreen(game));
        dispose();
    }

    public ArrayList<EnemyBullet> getEnemyBullets() {
        return enemyBullets;
    }

    public ArrayList<AllyBullet> getAllyBullets() {
        return allyBullets;
    }

    public void displayStats(){
        game.batch.setProjectionMatrix(stats.stage.getCamera().combined);
        stats.stage.draw();
    }
}
