package com.somoplay.magicworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.somoplay.magicworld.Resource.LoadResource;
import com.somoplay.magicworld.Screens.PlayScreen;
import com.somoplay.magicworld.Sprite.Ally;
import com.somoplay.magicworld.Sprite.Bat;
import com.somoplay.magicworld.Sprite.Gunner;
import com.somoplay.magicworld.Sprite.Soldier;

import java.util.ArrayList;

public class WorldCreator {

    ArrayList<Soldier> soldiers;
    ArrayList<Gunner> gunners;
    ArrayList<Bat> bats;
    ArrayList<Body> ceilingTraps;
    ArrayList<Ally> allies;
    ArrayList<Body> spikes;

    Texture img_spike;

    Sprite door;
    float doorX,doorY;
    ParticleEffect effect;
    PlayScreen screen;


    public WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        Map map = screen.getMap();
        this.screen=screen;
        img_spike=LoadResource.assetManager.get("spike.jpg");
        Texture tx=LoadResource.assetManager.get("images/door.jpg");
        door=new Sprite(tx);
        door.setSize(35/MagicWorld.PPM,55/MagicWorld.PPM);
        effect=new ParticleEffect();
        effect.load(Gdx.files.internal("images/purplefire.p"),Gdx.files.internal("images/"));
        effect.scaleEffect(0.002f  );

        soldiers = new ArrayList<Soldier>();
        gunners = new ArrayList<Gunner>();
        bats = new ArrayList<Bat>();
        allies = new ArrayList<Ally>();
        ceilingTraps = new ArrayList<Body>();
        spikes=new ArrayList<Body>();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //Create coins
        for (MapObject object : map.getLayers().get("Coin").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MagicWorld.PPM, (rect.getY() + rect.getHeight() / 2) / MagicWorld.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MagicWorld.PPM, rect.getHeight() / 2 / MagicWorld.PPM);
            fdef.shape = shape;
            Fixture fixture = body.createFixture(fdef);
            fixture.setUserData("Coin");
            fixture.setSensor(true);
        }

        //Create HealthUp
        for (MapObject object : map.getLayers().get("HealthUp").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MagicWorld.PPM, (rect.getY() + rect.getHeight() / 2) / MagicWorld.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MagicWorld.PPM, rect.getHeight() / 2 / MagicWorld.PPM);
            fdef.shape = shape;
            Fixture fixture = body.createFixture(fdef);
            fixture.setUserData("HealthUp");
            fixture.setSensor(true);
        }

        //Create PowerUp_Doublefire
        for (MapObject object : map.getLayers().get("PowerUp_Doublefire").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MagicWorld.PPM, (rect.getY() + rect.getHeight() / 2) / MagicWorld.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MagicWorld.PPM, rect.getHeight() / 2 / MagicWorld.PPM);
            fdef.shape = shape;
            Fixture fixture = body.createFixture(fdef);
            fixture.setUserData("PowerUp_Doublefire");
            fixture.setSensor(true);
        }
        //Create Ground
        for (MapObject object : map.getLayers().get("Ground").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MagicWorld.PPM, (rect.getY() + rect.getHeight() / 2) / MagicWorld.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MagicWorld.PPM, rect.getHeight() / 2 / MagicWorld.PPM);
            fdef.shape = shape;
            Fixture fixture = body.createFixture(fdef);
            fixture.setUserData("Ground");

        }

        //Create Soldiers
        for (MapObject object : map.getLayers().get("Soldier").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            soldiers.add(new Soldier(screen, rect.getX() / MagicWorld.PPM, rect.getY() / MagicWorld.PPM));

        }

        //Create Gunner
        for (MapObject object : map.getLayers().get("Gunner").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            gunners.add(new Gunner(screen, rect.getX() / MagicWorld.PPM, rect.getY() / MagicWorld.PPM));

        }

        //Create Bat
        for (MapObject object : map.getLayers().get("Bat").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bats.add(new Bat(screen, rect.getX() / MagicWorld.PPM, rect.getY() / MagicWorld.PPM));

        }

        //Create Ally
        for (MapObject object : map.getLayers().get("Ally").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            allies.add(new Ally(screen, rect.getX() / MagicWorld.PPM, rect.getY() / MagicWorld.PPM));

        }

        //Create NextLevelLoader
        for (MapObject object : map.getLayers().get("NextLevelLoader").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            doorX= rect.getX() / MagicWorld.PPM;
            doorY=  rect.getY() / MagicWorld.PPM;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MagicWorld.PPM, (rect.getY() + rect.getHeight() / 2) / MagicWorld.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MagicWorld.PPM, rect.getHeight() / 2 / MagicWorld.PPM);
            fdef.shape = shape;
            Fixture fixture = body.createFixture(fdef);

            fixture.setUserData("NextLevelLoader");
            fixture.setSensor(true);


        }

        //Create Spikes
        for (MapObject object : map.getLayers().get("Spike").getObjects().getByType(PolygonMapObject.class)) {
            Polygon polygon = ((PolygonMapObject) object).getPolygon();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((polygon.getX() + polygon.getBoundingRectangle().getWidth() / 2) / MagicWorld.PPM - 0.16f, (polygon.getY() + polygon.getBoundingRectangle().getHeight() / 2) / MagicWorld.PPM - 0.16f);

            body = world.createBody(bdef);

            float[] points = polygon.getVertices();
            for(int i = 0; i < points.length; i++){
                points[i] = points[i] / 100;
            }
            shape.set(points);
            //shape.setAsBox(rect.getWidth() / 2 / MagicWorld.PPM, rect.getHeight() / 2 / MagicWorld.PPM);
            fdef.shape = shape;
            Fixture fixture = body.createFixture(fdef);

            fixture.setUserData("Spike");
            fixture.setSensor(true);

            spikes.add(body);



        }
    //Create Ceiling Trap
    for (MapObject object : map.getLayers().get("CeilingTrap").getObjects().getByType(RectangleMapObject.class)) {
        Rectangle rect = ((RectangleMapObject) object).getRectangle();

        bdef.type = BodyDef.BodyType.KinematicBody;
        bdef.position.set((rect.getX() + rect.getWidth() / 2) / MagicWorld.PPM, (rect.getY() + rect.getHeight() / 2) / MagicWorld.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(rect.getWidth() / 2 / MagicWorld.PPM, rect.getHeight() / 2 / MagicWorld.PPM);
        fdef.shape = shape;
        Fixture fixture = body.createFixture(fdef);
        fixture.setUserData("CeilingTrap");

        ceilingTraps.add(body);

    }
    try {

        for (MapObject object : map.getLayers().get("Slider").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MagicWorld.PPM, (rect.getY() + rect.getHeight() / 2) / MagicWorld.PPM);
            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MagicWorld.PPM, rect.getHeight() / 2 / MagicWorld.PPM);
            fdef.shape = shape;
            Fixture fixture = body.createFixture(fdef);
            fixture.setUserData("Slider");
            fixture.setSensor(true);


        }
        for (MapObject object : map.getLayers().get("Ladder").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MagicWorld.PPM, (rect.getY() + rect.getHeight() / 2) / MagicWorld.PPM);
            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MagicWorld.PPM, rect.getHeight() / 2 / MagicWorld.PPM);
            fdef.shape = shape;
            Fixture fixture = body.createFixture(fdef);
            fixture.setUserData("Ladder");
            fixture.setSensor(true);

        }
    } catch (Exception e){}
}



    public void creatorrender()
    {
        door.setPosition(doorX,doorY);
        screen.batch.begin();
        door.draw(screen.batch);

        effect.setPosition(doorX+0.08f,doorY+0.02f);
        effect.draw(screen.batch,Gdx.graphics.getDeltaTime());

        for(Body spk:spikes)
        {
             screen.batch.draw(img_spike,spk.getPosition().x,spk.getPosition().y,0.33f,0.34f);
        }



        screen.batch.end();



    }

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }

    public ArrayList<Gunner> getGunners() { return gunners; }

    public ArrayList<Bat> getBats() { return bats; }

    public ArrayList<Body> getCeilingTraps() {
        return ceilingTraps;
    }

    public ArrayList<Ally> getAllies() {
        return allies;
    }

    public void removeSoldier(int index){
        screen.getWorld().destroyBody(soldiers.get(index).hand);
        soldiers.remove(index);
    }

    public void removeGunner(int index){
        gunners.remove(index);
    }

}
