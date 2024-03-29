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
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.somoplay.magicworld.MagicWorld;
import com.somoplay.magicworld.Resource.LoadResource;
import com.somoplay.magicworld.Sprite.Ally;
import com.somoplay.magicworld.Sprite.AllyBullet;
import com.somoplay.magicworld.Sprite.Bat;
import com.somoplay.magicworld.Sprite.Bullet;
import com.somoplay.magicworld.Sprite.Enemy;
import com.somoplay.magicworld.Sprite.EnemyBullet;
import com.somoplay.magicworld.Sprite.Gunner;
import com.somoplay.magicworld.Sprite.Player;
import com.somoplay.magicworld.Sprite.Smallsoldier;
import com.somoplay.magicworld.Sprite.Soldier;
import com.somoplay.magicworld.Sprite.TrackingBullet;
import com.somoplay.magicworld.Stats;
import com.somoplay.magicworld.WorldContactListener;
import com.somoplay.magicworld.WorldCreator;

import java.util.ArrayList;

import static com.somoplay.magicworld.MagicWorld.screenHeight;
import static com.somoplay.magicworld.MagicWorld.screenWidth;

public class PlayScreen implements Screen {

    World world;
    private OrthographicCamera cam;
    private Viewport viewport;
    public SpriteBatch batch;
    public static float velocity=2.5f;
    public static float friction=0;

    public static int level=1;

    private MagicWorld game;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    public Player player;
    public boolean movingR,movingL,jumping,firing;
    private ArrayList<Bullet> bullets;
    public ArrayList<TrackingBullet> trackingBullets;
    private ArrayList<EnemyBullet> enemyBullets;
    private ArrayList<AllyBullet> allyBullets;
    private WorldCreator creator;

    private LoadResource loadResource;
    private Music music;
    private Stats stats;
    public float deathTimer = 0;
    private float elapsedTime = 0;
    private int trapIndex = 0;

    private float statetime;
    private float timeSinceLastFire = 1.5f;
    private float allyFireTimer = 1.5f;
    private Bullet bullet;
    private TrackingBullet trackingBullet;
    private ArrayList<Float> ceilingTrapHeights;
    public boolean trackingResolved = true;

    private Texture tbg,btLeft,btRight,btA,btB,ceilingtraps;
    Image background;
    public ImageButton buttonLeft,buttonRight,buttonA,buttonB;

    private Drawable musicOff, musicOn;
    private ImageButton musicButton;
    public InputListener AL,BL;

    private ArrayList<Integer> tobeDestroyedSoldier;
    private ArrayList<Integer> tobeDestroyedGunner;
    private ArrayList<Integer> tobeDestroyedBat;
    private ArrayList<Integer> tobeDestroyedSmallSoldier;
    private ArrayList<Smallsoldier> smallsoldiers;

    public Stage stage;
    public Stage controlStage;
    private Label label;


    public PlayScreen(MagicWorld game){
        this.game = game;
        cam = new OrthographicCamera();

        // Creates the game world
        this.batch=game.batch;
        viewport = new FitViewport(screenWidth/MagicWorld.PPM, screenHeight/MagicWorld.PPM, cam);
        world = new World(new Vector2(0, -10), true);
        world.setContactListener(new WorldContactListener(this));

        // Creates the map for each level based on the Tiled map file
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level_0"+Integer.toString(level)+".tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / MagicWorld.PPM);

        ceilingtraps=LoadResource.assetManager.get("traps.jpg");

        creator = new WorldCreator(this);
        player = new Player(this);

        bullets = new ArrayList<Bullet>();
        trackingBullets = new ArrayList<TrackingBullet>();
        enemyBullets = new ArrayList<EnemyBullet>();
        allyBullets = new ArrayList<AllyBullet>();
        tobeDestroyedSoldier = new ArrayList<Integer>();
        tobeDestroyedGunner = new ArrayList<Integer>();
        tobeDestroyedBat = new ArrayList<Integer>();
        tobeDestroyedSmallSoldier = new ArrayList<Integer>();
        smallsoldiers=new ArrayList<Smallsoldier>();
        loadResource = new LoadResource();

        ceilingTrapHeights = new ArrayList<Float>();
        for(Body ct: creator.getCeilingTraps()){
            ceilingTrapHeights.add(ct.getPosition().y);
        }

        // Loading background image from LoadResource
        tbg= LoadResource.assetManager.get("images/bg1.png");
        background=new Image(tbg);
        background.setSize(screenWidth,screenHeight);

        // Creates the music button on top right side
        musicOff = new TextureRegionDrawable(new TextureRegion((Texture) LoadResource.assetManager.get("images/button_sound_mute.png")));
        musicOn = new TextureRegionDrawable(new TextureRegion((Texture) LoadResource.assetManager.get("images/button_sound.png")));
        musicButton = new ImageButton(musicOn, musicOn, musicOff);
        musicButton.setPosition(screenWidth*0.90f,screenHeight*0.87f);
        musicButton.setSize(48, 48);
        musicButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(musicButton.isChecked()){
                    musicButton.setChecked(true); music.play();
                } else{ musicButton.setChecked(false); music.pause();}
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });

        // Converts font file into a compatible one with libgdx
        Label.LabelStyle fontstyle=new Label.LabelStyle(new BitmapFont(Gdx.files.internal("mwfont.fnt")), Color.WHITE);
        label=new Label(Integer.toString(WorldContactListener.score),fontstyle);
        label.setPosition(screenWidth*0.10f,screenHeight*0.87f);

        stage = new Stage();
        stage.addActor(background);
        stage.addActor(label);

        WorldContactListener.score = 0;

        stats = new Stats(game.batch, this);

        controlStage =new Stage();
        controlStage.addActor(musicButton);
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

        handleInput(delta);  // setting Button Listener

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        label.setText(Integer.toString(WorldContactListener.score));
        stage.draw();
        stage.act();

        mapRenderer.render();

        update(delta);

        creator.creatorrender();
        controlStage.draw();
        controlStage.act();

        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        for(Bullet bullet : bullets) {
            bullet.render(game.batch);
        }
        for(AllyBullet bullet: allyBullets)
        {
            bullet.render(game.batch);
        }
        for(EnemyBullet bullet:enemyBullets)
        {
            bullet.render(game.batch);
        }

        for(TrackingBullet tb: trackingBullets){
            tb.render(game.batch);
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

            if (firing && timeSinceLastFire >= 0.6f) {

                if (!player.isDestroyed()) {

                    if(!player.tracking) {
                        firePlayerBullet();
                        if (player.doubleFire) {
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    firePlayerBullet();
                                }
                            }, 0.1f);
                        }
                        timeSinceLastFire = 0;
                    } else{
                        if(getNearestEnemy() != null && trackingResolved){
                            trackingBullet = new TrackingBullet(this, new Vector2(player.body.getPosition().x, player.body.getPosition().y), getNearestEnemy());
                            trackingBullets.add(trackingBullet);
                            timeSinceLastFire = 0;
                            trackingResolved = false;
                        }
                    }
                }
            }
        }

        timeSinceLastFire += delta;

    }

    public void update(float dt){

        world.step(1/60f, 6, 2);// update world
        if(!player.isDestroyed()) {
            cam.position.set(player.body.getPosition().x, player.body.getPosition().y, 0);
            cam.update();
        }

        // All gameobjects are kept in ArrayLists and there is a for loop in each to update each of them.
        for(Bullet bullet: bullets){
            if(!bullet.destroyed) {

                if (bullet.getBody().getPosition().x - player.body.getPosition().x > 3 || bullet.getBody().getPosition().x - player.body.getPosition().x < -3) {

                    bullet.setToDestroy();
                }

                bullet.update(dt);
            }
        }

        for(TrackingBullet tb: trackingBullets){
            tb.update(dt);
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

        for(Bat bat: creator.getBats()){
            if(bat.health <= 0 && !bat.destroyed){
                world.destroyBody(bat.body);
                bat.destroyed = true;
                WorldContactListener.score += 60;
            }
            if(!bat.destroyed) {
                bat.update(dt);
                // Only Active once player gets close
                if(bat.body.getPosition().x < player.body.getPosition().x + 500 / MagicWorld.PPM){
                    bat.body.setActive(true);
                }
            }

            if(bat.destroyed){
                tobeDestroyedBat.add(creator.getBats().indexOf(bat));
            }

        }

        if(tobeDestroyedBat.size() != 0){
            for(int i: tobeDestroyedBat){
                creator.removeBat(i);
            }
            tobeDestroyedBat.clear();
        }

        for(AllyBullet ab: allyBullets){
            if(!ab.destroyed) {

                if (Math.abs(ab.getBody().getPosition().x - player.getPosition().x) >= 3) {

                    ab.setToDestroy();
                }

                ab.update(dt);
            }
        }

        for (Soldier soldier: creator.getSoldiers()){
            if(soldier.health <= 0 && !soldier.destroyed){
                smallsoldiers.add(new Smallsoldier(this,soldier.body.getPosition().x-0.2f,soldier.body.getPosition().y+0.13f));
                smallsoldiers.add(new Smallsoldier(this,soldier.body.getPosition().x+0.3f,soldier.body.getPosition().y+0.19f));
                world.destroyBody(soldier.body);
                soldier.destroyed = true;
                WorldContactListener.score += 50;
            }

            // If soldier falls off platform, it will also be destoryed
            if(soldier.body.getPosition().y < -0.175 && !soldier.destroyed){
                world.destroyBody(soldier.body);
                soldier.destroyed = true;
            }

            if(!soldier.destroyed){
                soldier.update(dt);
                // only active once player gets close
                if(soldier.body.getPosition().x < player.body.getPosition().x + 500 / MagicWorld.PPM){
                    soldier.body.setActive(true);

                }
            }

            if(soldier.destroyed){
                tobeDestroyedSoldier.add(creator.getSoldiers().indexOf(soldier));
            }
        }

        if(tobeDestroyedSoldier.size() != 0){
            for(int i: tobeDestroyedSoldier){
                creator.removeSoldier(i);
            }
            tobeDestroyedSoldier.clear();
        }

        for(Smallsoldier small:smallsoldiers)
        {
            if(small.health <= 0 && !small.destroyed){
                world.destroyBody(small.body);
                small.destroyed = true;
                WorldContactListener.score += 10;
            }

            // If soldier falls off platform, it will also be destoryed
            if(small.body.getPosition().y < -0.175 && !small.destroyed){
                world.destroyBody(small.body);
                small.destroyed = true;
            }

            if(!small.destroyed){
                small.update(dt);
            }

            if(small.destroyed){
                tobeDestroyedSmallSoldier.add(smallsoldiers.indexOf(small));
            }
        }

        if(tobeDestroyedSmallSoldier.size() != 0){
            for(int i: tobeDestroyedSmallSoldier){
                smallsoldiers.remove(i);
            }
            tobeDestroyedSmallSoldier.clear();
        }

        for (Gunner gunner: creator.getGunners()){
            if(gunner.health <= 0 && !gunner.destroyed){
                world.destroyBody(gunner.body);
                gunner.destroyed = true;
                WorldContactListener.score += 75;
            }

            if(gunner.body.getPosition().y < - 0.175 && !gunner.destroyed){
                world.destroyBody(gunner.body);
                gunner.destroyed = true;
            }

            if(gunner.destroyed == false){
                batch.begin();
                gunner.update(dt);
                batch.end();
                if(gunner.body.getPosition().x < player.body.getPosition().x + 500 / MagicWorld.PPM){
                    gunner.body.setActive(true);
                }
            }

            if(gunner.destroyed){
                tobeDestroyedGunner.add(creator.getGunners().indexOf(gunner));
            }

        }

        if(tobeDestroyedGunner.size() != 0){
            for(int i: tobeDestroyedGunner){
                creator.removeGunner(i);
            }
            tobeDestroyedGunner.clear();
        }

        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        for(Ally ally: creator.getAllies()){

            if(ally.health <= 0 && !ally.destroyed){
                world.destroyBody(ally.body);
                ally.destroyed = true;
                WorldContactListener.score += 50;
            }

            if(!ally.destroyed) {
                ally.update(dt,game.batch);
                for (Gunner gunner : creator.getGunners()) {
                    // if enemy is to the right fire to the right, else fire left
                    if (gunner.body.getPosition().x > ally.body.getPosition().x && gunner.body.getPosition().x - ally.body.getPosition().x <= 480 / MagicWorld.PPM) {
                        if (allyFireTimer >= 1f) {
                            ally.fireRight();
                            allyFireTimer = 0;
                        }
                    } else if (gunner.body.getPosition().x < ally.body.getPosition().x && ally.body.getPosition().x - gunner.body.getPosition().x <= 480 / MagicWorld.PPM) {
                        if (allyFireTimer >= 1f) {
                            ally.fireLeft();
                            allyFireTimer = 0;
                        }
                    }
                }

                for (Soldier soldier : creator.getSoldiers()) {
                    // if enemy is to the right fire to the right
                    if (soldier.body.getPosition().x > ally.body.getPosition().x && soldier.body.getPosition().x - ally.body.getPosition().x <= 480 / MagicWorld.PPM) {
                        if (allyFireTimer >= 1f) {
                            ally.fireRight();
                            allyFireTimer = 0;
                        }
                    } else if (soldier.body.getPosition().x < ally.body.getPosition().x && ally.body.getPosition().x - soldier.body.getPosition().x <= 480 / MagicWorld.PPM) {
                        if (allyFireTimer >= 1f) {
                            ally.fireLeft();
                            allyFireTimer = 0;
                        }
                    }
                }

                for (Smallsoldier smallsoldier : smallsoldiers) {
                    // if enemy is to the right fire to the right
                    if (smallsoldier.body.getPosition().x > ally.body.getPosition().x && smallsoldier.body.getPosition().x - ally.body.getPosition().x <= 480 / MagicWorld.PPM) {
                        if (allyFireTimer >= 1f) {
                            ally.fireRight();
                            allyFireTimer = 0;
                        }
                    } else if (smallsoldier.body.getPosition().x < ally.body.getPosition().x && ally.body.getPosition().x - smallsoldier.body.getPosition().x <= 480 / MagicWorld.PPM) {
                        if (allyFireTimer >= 1f) {
                            ally.fireLeft();
                            allyFireTimer = 0;
                        }
                    }
                }
            }
        }
        allyFireTimer += dt;

        // Moves Ceiling Traps
        for(Body ct : creator.getCeilingTraps()){

            if(ct.getPosition().y <= (ceilingTrapHeights.get(trapIndex) - 96/MagicWorld.PPM)){
                ct.setLinearVelocity(0,0.7f);
                trapIndex++;
            } else if (ct.getPosition().y >= ceilingTrapHeights.get(trapIndex)){
                ct.setLinearVelocity(0,-0.7f);
                trapIndex++;
            }
            game.batch.draw(ceilingtraps,ct.getPosition().x-0.8f,ct.getPosition().y-0.2f,1.6f,0.5f);
        }

        game.batch.end();

        trapIndex = 0;
        deathTimer += dt;
        if(!player.isDestroyed()) {
            elapsedTime += dt;
        }

        // Sets player death from health reaching 0
        if(player.getHealth() <= 0 && !player.isDestroyed()){
            world.destroyBody(player.body);
            player.setDestroyed(true);
            deathTimer = 1;
        }
        // Sets player death from falling off of platform
        if(player.body.getPosition().y < - 0.175 && !player.isDestroyed()){
            world.destroyBody(player.body);
            player.setDestroyed(true);
            deathTimer = 1;
        }
        // If player has died or completed level, display status UI
        if(deathTimer >= 1.5 && player.isDestroyed()){
            loadStats();
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

    public void nextLevel(){
        game.setScreen(new MenuScreen(game));
    }

    public World getWorld() {
        return world;
    }

    public TiledMapTileLayer.Cell getCell(Body body){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        return layer.getCell((int)(body.getPosition().x *MagicWorld.PPM / 32), (int)(body.getPosition().y * MagicWorld.PPM / 32));
    }

    public Player getPlayer(){return player;}

    public void loadStats(){
        stats.setScoreLabel(WorldContactListener.score);
        stats.setTimeLabel(elapsedTime);
        controlStage.clear();

        displayStats();
        music.stop();
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
        Gdx.input.setInputProcessor(stats.stage);
        stats.stage.draw();
        stats.stage.act();

    }

    public void firePlayerBullet(){
        bullet = new Bullet(this, new Vector2(player.body.getPosition().x, player.body.getPosition().y));
        bullets.add(bullet);
    }

    public Enemy getNearestEnemy(){
        float x = 100;
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        enemies.addAll(creator.getGunners());
        enemies.addAll(creator.getSoldiers());
        Enemy enemy = null;

        for(Enemy e: enemies){
            if(e.body.isActive()) {
                float distance = (Math.abs(e.body.getPosition().x - player.body.getPosition().x)
                        + Math.abs(e.body.getPosition().y - player.body.getPosition().y));
                if (distance < x) {
                    x = distance;
                    enemy = e;
                }
            }
        }
        return enemy;
    }
}
