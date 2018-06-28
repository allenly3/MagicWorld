package com.somoplay.magicworld;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.somoplay.magicworld.Screens.PlayScreen;

import java.util.ArrayList;

public class WorldCreator {
    private ArrayList<Soldier> soldiers;

    public WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        Map map = screen.getMap();

        soldiers = new ArrayList<Soldier>();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //Create coins
        for (MapObject object : map.getLayers().get("Coin").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / NewMario.PPM, (rect.getY() + rect.getHeight() / 2) / NewMario.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / NewMario.PPM, rect.getHeight() / 2 / NewMario.PPM);
            fdef.shape = shape;
            Fixture fixture = body.createFixture(fdef);
            fixture.setUserData("Coin");
            fixture.setSensor(true);
        }

        //Create Ground
        for (MapObject object : map.getLayers().get("Ground").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / NewMario.PPM, (rect.getY() + rect.getHeight() / 2) / NewMario.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / NewMario.PPM, rect.getHeight() / 2 / NewMario.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //Create Soldiers
        for (MapObject object : map.getLayers().get("Soldier").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            soldiers.add(new Soldier(screen, rect.getX() / NewMario.PPM, rect.getY() / NewMario.PPM));

        }
    }

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }
}
