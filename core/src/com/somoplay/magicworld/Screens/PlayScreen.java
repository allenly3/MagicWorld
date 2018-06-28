package com.somoplay.magicworld.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.somoplay.magicworld.MagicWorld;
import com.somoplay.magicworld.WorldContactListener;
import com.somoplay.magicworld.WorldCreator;

import java.util.ArrayList;

public class PlayScreen implements Screen {

    World world;
    OrthographicCamera cam;
    Viewport viewport;
    Box2DDebugRenderer renderer;

    private MagicWorld game;

    //private Controller controller;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    //public Player player;

    private WorldCreator creator;

    private AssetManager manager;
    private Music music;
    // private HUD hud;
    private float deathTimer = 0;

    //private Bullet bullet;
    //public ArrayList<Bullet> bullets;

    private Stage stage;
    //private HealthBar healthBar;

    public PlayScreen(MagicWorld game){
        this.game = game;
        cam = new OrthographicCamera();

        //hud = new HUD(game.batch);
        viewport = new FitViewport(800/MagicWorld.PPM, 480/MagicWorld.PPM, cam);
        world = new World(new Vector2(0, -10), true);
        world.setContactListener(new WorldContactListener(this));
        renderer = new Box2DDebugRenderer();

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level_01.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / MagicWorld.PPM);
        cam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        //player = new Player(this);

        //bullets = new ArrayList<Bullet>();

        creator = new WorldCreator(this);

        //controller = new Controller();

        manager = new AssetManager();
        manager.load("Background.mp3", Music.class);
        manager.finishLoading();
        music = manager.get("Background.mp3", Music.class);
        music.setLooping(true);
        music.play();

        stage = new Stage();
        //healthBar = new HealthBar(250, 20);
        //healthBar.setPosition(10, Gdx.graphics.getHeight() - 20);
        //stage.addActor(healthBar);
    }
    @Override
    public void show() {

    }

    public TiledMap getMap() {
        return map;
    }

    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime());
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.render();
        renderer.render(world, cam.combined);
        //controller.draw();

        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();

        //for(Bullet bullet : bullets) {
          //  bullet.render(game.batch);
        //}
        game.batch.end();

        //game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        //hud.stage.draw();
        //hud.stage.act();

        stage.draw();
        stage.act();

    }
    public void handleInput(){

        //handles player input pressing down to shoot
        if(controller.getRight()){
            player.body.setLinearVelocity(new Vector2(1, player.body.getLinearVelocity().y));
        }
        else if (controller.getLeft())
            player.body.setLinearVelocity(new Vector2(-1, player.body.getLinearVelocity().y));
        else
            player.body.setLinearVelocity(new Vector2(0, player.body.getLinearVelocity().y));
        if (controller.getUp() && player.body.getLinearVelocity().y == 0)
            player.body.applyLinearImpulse(new Vector2(0, 5f), player.body.getWorldCenter(), true);
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            if(player.isDestroyed() == false) {
                bullet = new Bullet(this, new Vector2(player.body.getPosition().x, player.body.getPosition().y));
                bullets.add(bullet);
            }
        }
    }

    public void update(float dt){
        handleInput();
        world.step(1/60f, 6, 2);
        cam.position.set(player.body.getPosition().x, player.body.getPosition().y, 0 );
        cam.update();

        // if it touches a ground tile, then dissappear, if it touches a enemy, dissappear and apply damage.
        // get the size of it correct and the spawn location right as well.
        for(Bullet bullet: bullets){
            if(bullet.destroyed == false) {
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
                if(soldier.body.getPosition().x < player.body.getPosition().x + 500 / NewMario.PPM){
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
            game.setScreen(new GameOverScreen(game));
            //music.stop();
            dispose();
        }
        mapRenderer.setView(cam);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        controller.resize(width,height);
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
        hud.dispose();
        manager.dispose();

    }

    public TiledMapTileLayer.Cell getCell(Body body){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        return layer.getCell((int)(body.getPosition().x *NewMario.PPM / 32), (int)(body.getPosition().y * NewMario.PPM / 32));
    }

    public World getWorld() {
        return world;
    }

    public void addScore(int value){
        hud.addScore(value);
    }

    public void setHealth(float value){
        healthBar.setValue(healthBar.getValue() - value);
    }
}
