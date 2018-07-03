package com.somoplay.magicworld.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.somoplay.magicworld.MagicWorld;
import com.somoplay.magicworld.WorldCreator;

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

        viewport = new FitViewport(800/MagicWorld.PPM, 480/MagicWorld.PPM, cam);
        world = new World(new Vector2(0, -10), true);
        //world.setContactListener(new WorldContactListener(this));
        renderer = new Box2DDebugRenderer();

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level_01.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / MagicWorld.PPM);
        cam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        creator = new WorldCreator(this);

        manager = new AssetManager();

        stage = new Stage();

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

        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        game.batch.end();

        stage.draw();
        stage.act();

    }
    public void handleInput(){

    }

    public void update(float dt){
        handleInput();
        world.step(1/60f, 6, 2);
        cam.position.set(0,0 ,0 );
        cam.update();

        // if it touches a ground tile, then dissappear, if it touches a enemy, dissappear and apply damage.
        // get the size of it correct and the spawn location right as well.

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

}
