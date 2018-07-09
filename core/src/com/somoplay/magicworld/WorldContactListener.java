package com.somoplay.magicworld;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.somoplay.magicworld.Screens.PlayScreen;
import com.somoplay.magicworld.Sprite.Bullet;
import com.somoplay.magicworld.Sprite.Enemy;
import com.somoplay.magicworld.Sprite.Player;
import com.somoplay.magicworld.Sprite.Soldier;


public class WorldContactListener implements ContactListener {

    PlayScreen screen;
    public static int counter=0;

    public WorldContactListener(PlayScreen screen){
        this.screen = screen;

    }
    @Override
    public void beginContact(Contact contact) {
        Fixture a, b;

        if(contact.getFixtureA().getUserData() instanceof Player && contact.getFixtureB().getUserData() == "Coin"){
            a = contact.getFixtureA();
            b = contact.getFixtureB();

            screen.getCell(b.getBody()).setTile(null);
            b.setUserData(null);
            //screen.addScore(100);

        } else if(contact.getFixtureA().getUserData() == "Coin" && contact.getFixtureB().getUserData() instanceof Player){
            a = contact.getFixtureB();
            b = contact.getFixtureA();

            screen.getCell(b.getBody()).setTile(null);
            b.setUserData(null);
            //screen.addScore(100);

        } else if(contact.getFixtureA().getUserData() instanceof Bullet && contact.getFixtureB().getUserData() instanceof Soldier) {
            a = contact.getFixtureA();
            b = contact.getFixtureB();

            ((Enemy)b.getUserData()).onHit((Bullet)a.getUserData());
            ((Bullet) a.getUserData()).setToDestroy();

        } else if(contact.getFixtureA().getUserData() instanceof Soldier && contact.getFixtureB().getUserData() instanceof Bullet) {
            a = contact.getFixtureB();
            b = contact.getFixtureA();

            ((Enemy)b.getUserData()).onHit((Bullet)a.getUserData());
            ((Bullet) a.getUserData()).setToDestroy();

        } else if(contact.getFixtureA().getUserData() instanceof Player && contact.getFixtureB().getUserData() instanceof Soldier){
            a = contact.getFixtureA();
            b = contact.getFixtureB();

            ((Player)a.getUserData()).onHit((Enemy)b.getUserData());
            ((Soldier)b.getUserData()).hitPlayer();
            //screen.setHealth(0.5f);

        } else if(contact.getFixtureA().getUserData() instanceof Soldier && contact.getFixtureB().getUserData() instanceof Player){
            a = contact.getFixtureB();
            b = contact.getFixtureA();

            ((Player)a.getUserData()).onHit((Enemy)b.getUserData());
            ((Soldier)b.getUserData()).hitPlayer();
            //screen.setHealth(0.5f);
        }

        if(contact.getFixtureA().getUserData() instanceof Player && contact.getFixtureB().getUserData() == "Ground"){

            counter++;

        } else if(contact.getFixtureA().getUserData() == "Ground" && contact.getFixtureB().getUserData() instanceof Player){

            counter++;

        }

        System.out.println(counter);
    }

    @Override
    public void endContact(Contact contact) {

        if(contact.getFixtureA().getUserData() instanceof Player && contact.getFixtureB().getUserData() == "Ground"){

            counter--;

        } else if(contact.getFixtureA().getUserData() == "Ground" && contact.getFixtureB().getUserData() instanceof Player){

            counter--;

        }

        System.out.println(counter);

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
