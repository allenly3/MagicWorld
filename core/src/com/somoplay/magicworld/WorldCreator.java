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
import com.somoplay.magicworld.Sprite.Soldier;

import java.util.ArrayList;

public class WorldCreator {

    ArrayList<Soldier> soldiers;

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
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MagicWorld.PPM, (rect.getY() + rect.getHeight() / 2) / MagicWorld.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MagicWorld.PPM, rect.getHeight() / 2 / MagicWorld.PPM);
            fdef.shape = shape;
            Fixture fixture = body.createFixture(fdef);
            fixture.setUserData("Coin");
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
            body.createFixture(fdef);
        }

        //Create Soldiers
        for (MapObject object : map.getLayers().get("Soldier").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            System.out.println(rect.getX()+" "+rect.getY());
            soldiers.add(new Soldier(screen, rect.getX() / MagicWorld.PPM, rect.getY() / MagicWorld.PPM));
            //getX() : the x-coordinate of the bottom left corner
            //getY();the y-coordinate of the bottom left corner
        }
    }

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }
}
