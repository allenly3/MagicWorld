package com.somoplay.magicworld;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.somoplay.magicworld.Resource.LoadResource;
import com.somoplay.magicworld.Screens.PlayScreen;
import com.somoplay.magicworld.Sprite.Gunner;
import com.somoplay.magicworld.Sprite.Soldier;

import java.util.ArrayList;

public class WorldCreator {

    ArrayList<Soldier> soldiers;
    ArrayList<Gunner> gunners;



    public WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        Map map = screen.getMap();
     


        soldiers = new ArrayList<Soldier>();
        gunners = new ArrayList<Gunner>();

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

        //Create NextLevelLoader
        for (MapObject object : map.getLayers().get("NextLevelLoader").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
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
        }

    }

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }

    public ArrayList<Gunner> getGunners() { return gunners; }
}
