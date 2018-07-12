package com.somoplay.magicworld;

import com.badlogic.gdx.math.Vector2;
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
    public static int score=0;


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
           score+=100;

        } else if(contact.getFixtureA().getUserData() == "Coin" && contact.getFixtureB().getUserData() instanceof Player){
            a = contact.getFixtureB();
            b = contact.getFixtureA();

            screen.getCell(b.getBody()).setTile(null);
            b.setUserData(null);
            score+=100;

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

            ((Player)a.getUserData()).onHit(50);
            ((Soldier)b.getUserData()).hitPlayer();
            //screen.setHealth(0.5f);

        } else if(contact.getFixtureA().getUserData() instanceof Soldier && contact.getFixtureB().getUserData() instanceof Player){
            a = contact.getFixtureB();
            b = contact.getFixtureA();

            ((Player)a.getUserData()).onHit(50);
            ((Soldier)b.getUserData()).hitPlayer();
            //screen.setHealth(0.5f);
        } else if(contact.getFixtureA().getUserData() instanceof Player && contact.getFixtureB().getUserData() == "NextLevelLoader") {
            a = contact.getFixtureA();
            b = contact.getFixtureB();

            screen.nextLevel();
        } else if(contact.getFixtureA().getUserData() == "NextLevelLoader" && contact.getFixtureB().getUserData() instanceof Player) {
            a = contact.getFixtureA();
            b = contact.getFixtureB();

            screen.nextLevel();
        } else if(contact.getFixtureA().getUserData() instanceof Player && contact.getFixtureB().getUserData() == "Spike") {
            a = contact.getFixtureA();
            b = contact.getFixtureB();

            ((Player)a.getUserData()).onHit(100);

        } else if(contact.getFixtureA().getUserData() == "Spike" && contact.getFixtureB().getUserData() instanceof Player) {
            a = contact.getFixtureB();
            b = contact.getFixtureA();

            ((Player)a.getUserData()).onHit(100);
        } else if(contact.getFixtureA().getUserData() == "Ground" && contact.getFixtureB().getUserData() == "leftSide") {
            a = contact.getFixtureA();
            b = contact.getFixtureB();

            if(b.getBody().getLinearVelocity().y == 0) {
                b.getBody().applyLinearImpulse(new Vector2(-1, 6), b.getBody().getWorldCenter(), true);
            }
        } else if(contact.getFixtureA().getUserData() == "leftSide" && contact.getFixtureB().getUserData() == "Ground") {
            a = contact.getFixtureB();
            b = contact.getFixtureA();

            if(b.getBody().getLinearVelocity().y == 0) {
                b.getBody().applyLinearImpulse(new Vector2(-1, 6), b.getBody().getWorldCenter(), true);
            }
        } else if(contact.getFixtureA().getUserData() == "Ground" && contact.getFixtureB().getUserData() == "rightSide") {
            a = contact.getFixtureA();
            b = contact.getFixtureB();

            if(b.getBody().getLinearVelocity().y == 0) {
                b.getBody().applyLinearImpulse(new Vector2(1, 6), b.getBody().getWorldCenter(), true);
            }
        } else if(contact.getFixtureA().getUserData() == "rightSide" && contact.getFixtureB().getUserData() == "Ground") {
            a = contact.getFixtureB();
            b = contact.getFixtureA();

            if(b.getBody().getLinearVelocity().y == 0) {
                b.getBody().applyLinearImpulse(new Vector2(1, 6), b.getBody().getWorldCenter(), true);
            }

        } else if(contact.getFixtureA().getUserData() instanceof Player && contact.getFixtureB().getUserData() =="HealthUp") {
            a = contact.getFixtureA();
            b = contact.getFixtureB();

            screen.getCell(b.getBody()).setTile(null);
            b.setUserData(null);
            ((Player)a.getUserData()).addHealth(50);
        } else if(contact.getFixtureA().getUserData() == "HealthUp" && contact.getFixtureB().getUserData() instanceof Player){
            a = contact.getFixtureB();
            b = contact.getFixtureA();

            screen.getCell(b.getBody()).setTile(null);
            b.setUserData(null);
            ((Player)a.getUserData()).addHealth(50);
        }




    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
