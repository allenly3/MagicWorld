package com.somoplay.magicworld;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.somoplay.magicworld.Screens.PlayScreen;
import com.somoplay.magicworld.Sprite.Ally;
import com.somoplay.magicworld.Sprite.AllyBullet;
import com.somoplay.magicworld.Sprite.Bullet;
import com.somoplay.magicworld.Sprite.Enemy;
import com.somoplay.magicworld.Sprite.EnemyBullet;
import com.somoplay.magicworld.Sprite.Gunner;
import com.somoplay.magicworld.Sprite.Player;
import com.somoplay.magicworld.Sprite.Soldier;


public class WorldContactListener implements ContactListener {

    PlayScreen screen;
    public static int score=0;
    InputListener A,B;


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

            ((Enemy)b.getUserData()).onHit();
            ((Bullet) a.getUserData()).setToDestroy();

        } else if(contact.getFixtureA().getUserData() instanceof Soldier && contact.getFixtureB().getUserData() instanceof Bullet) {
            a = contact.getFixtureB();
            b = contact.getFixtureA();

            ((Enemy)b.getUserData()).onHit();
            ((Bullet) a.getUserData()).setToDestroy();


        } else if(contact.getFixtureA().getUserData() instanceof AllyBullet && contact.getFixtureB().getUserData() instanceof Soldier) {
            a = contact.getFixtureA();
            b = contact.getFixtureB();

            ((Enemy)b.getUserData()).onHit();
            ((AllyBullet) a.getUserData()).setToDestroy();

        } else if(contact.getFixtureA().getUserData() instanceof Soldier && contact.getFixtureB().getUserData() instanceof AllyBullet) {
            a = contact.getFixtureB();
            b = contact.getFixtureA();

            ((Enemy)b.getUserData()).onHit();
            ((AllyBullet) a.getUserData()).setToDestroy();


        } else if(contact.getFixtureA().getUserData() instanceof Bullet && contact.getFixtureB().getUserData() instanceof Gunner) {
            a = contact.getFixtureA();
            b = contact.getFixtureB();

            ((Enemy)b.getUserData()).onHit();
            ((Bullet) a.getUserData()).setToDestroy();

        } else if(contact.getFixtureA().getUserData() instanceof Gunner && contact.getFixtureB().getUserData() instanceof Bullet) {
            a = contact.getFixtureB();
            b = contact.getFixtureA();

            ((Enemy)b.getUserData()).onHit();
            ((Bullet) a.getUserData()).setToDestroy();


        } else if(contact.getFixtureA().getUserData() instanceof AllyBullet && contact.getFixtureB().getUserData() instanceof Gunner) {
            a = contact.getFixtureA();
            b = contact.getFixtureB();

            ((Enemy)b.getUserData()).onHit();
            ((AllyBullet) a.getUserData()).setToDestroy();

        } else if(contact.getFixtureA().getUserData() instanceof Gunner && contact.getFixtureB().getUserData() instanceof AllyBullet) {
            a = contact.getFixtureB();
            b = contact.getFixtureA();

            ((Enemy)b.getUserData()).onHit();
            ((AllyBullet) a.getUserData()).setToDestroy();


        } else if(contact.getFixtureA().getUserData() instanceof Player && contact.getFixtureB().getUserData()=="hand"){
            a = contact.getFixtureA();
            b = contact.getFixtureB();
            Soldier.touch=true;
            System.out.println("hand");

            ((Player)a.getUserData()).onHit(2);
            //((Soldier)b.getUserData()).hitPlayer();
            //screen.setHealth(0.5f);

        } else if(contact.getFixtureA().getUserData() =="hand"&& contact.getFixtureB().getUserData() instanceof Player){
            a = contact.getFixtureB();
            b = contact.getFixtureA();
            System.out.println("hand");
            Soldier.touch=true;
            ((Player)a.getUserData()).onHit(2);
           // ((Soldier)b.getUserData()).hitPlayer();

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

            ((Player)a.getUserData()).onHit(20);

        } else if(contact.getFixtureA().getUserData() == "Spike" && contact.getFixtureB().getUserData() instanceof Player) {
            a = contact.getFixtureB();
            b = contact.getFixtureA();

            ((Player)a.getUserData()).onHit(100);
        } else if((contact.getFixtureA().getUserData() == "Ground" && contact.getFixtureB().getUserData() == "SoldierLeftSide") ||
                (contact.getFixtureA().getUserData() == "Ground" && contact.getFixtureB().getUserData() == "SoldierRightSide")) {
            a = contact.getFixtureA();
            b = contact.getFixtureB();

            if(b.getUserData() == "SoldierLeftSide") {
                if (b.getBody().getLinearVelocity().y == 0) {
                    b.getBody().applyLinearImpulse(new Vector2(-1, 6), b.getBody().getWorldCenter(), true);
                }
            } else{
                if (b.getBody().getLinearVelocity().y == 0) {
                    b.getBody().applyLinearImpulse(new Vector2(1, 6), b.getBody().getWorldCenter(), true);
                }
            }
        } else if((contact.getFixtureA().getUserData() == "SoldierLeftSide" && contact.getFixtureB().getUserData() == "Ground") ||
                (contact.getFixtureA().getUserData() == "SoldierRightSide" && contact.getFixtureB().getUserData() == "Ground")){
            a = contact.getFixtureB();
            b = contact.getFixtureA();

            if(b.getUserData() == "SoldierLeftSide") {
                if (b.getBody().getLinearVelocity().y == 0) {
                    b.getBody().applyLinearImpulse(new Vector2(-1, 6), b.getBody().getWorldCenter(), true);
                }
            } else{
                if (b.getBody().getLinearVelocity().y == 0) {
                    b.getBody().applyLinearImpulse(new Vector2(1, 6), b.getBody().getWorldCenter(), true);
                }
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
        } else if(contact.getFixtureA().getUserData() instanceof Player && contact.getFixtureB().getUserData() instanceof EnemyBullet){
            a = contact.getFixtureA();
            b = contact.getFixtureB();


            ((Player)a.getUserData()).onHit(50);
            ((EnemyBullet)b.getUserData()).setToDestroy();

        } else if(contact.getFixtureA().getUserData() instanceof EnemyBullet && contact.getFixtureB().getUserData() instanceof Player){
            a = contact.getFixtureB();
            b = contact.getFixtureA();

            ((Player)a.getUserData()).onHit(50);
            ((EnemyBullet)b.getUserData()).setToDestroy();
        } else if((contact.getFixtureA().getUserData() == "GunnerLeftSide" && contact.getFixtureB().getUserData() == "Ground") ||
        (contact.getFixtureA().getUserData() == "GunnerRightSide" && contact.getFixtureB().getUserData() == "Ground")){
            a = contact.getFixtureB();
            b = contact.getFixtureA();

            ((Gunner)b.getBody().getUserData()).reverseVelocity(true,false);

        } else if((contact.getFixtureA().getUserData() == "Ground" && contact.getFixtureB().getUserData() == "GunnerLeftSide") ||
        (contact.getFixtureA().getUserData() == "Ground" && contact.getFixtureB().getUserData() == "GunnerRightSide")){
            a = contact.getFixtureA();
            b = contact.getFixtureB();

            ((Gunner) b.getBody().getUserData()).reverseVelocity(true,false);
        } else if(contact.getFixtureA().getUserData() instanceof Player && contact.getFixtureB().getUserData() instanceof Ally){
            a = contact.getFixtureA();
            b = contact.getFixtureB();

            ((Ally)b.getUserData()).follow();
        } else if(contact.getFixtureA().getUserData() instanceof Ally && contact.getFixtureB().getUserData() instanceof Player){
            a = contact.getFixtureB();
            b = contact.getFixtureA();

            ((Ally)b.getUserData()).follow();
        } else if((contact.getFixtureA().getUserData() == "Ground" && contact.getFixtureB().getUserData() == "AllyLeftSide") ||
                (contact.getFixtureA().getUserData() == "Ground" && contact.getFixtureB().getUserData() == "AllyRightSide")) {
            a = contact.getFixtureA();
            b = contact.getFixtureB();
            if(b.getUserData() == "AllyLeftSide") {
                if (b.getBody().getLinearVelocity().y == 0) {
                    b.getBody().applyLinearImpulse(new Vector2(-1, 4.5f), b.getBody().getWorldCenter(), true);
                }
            } else{
                if (b.getBody().getLinearVelocity().y == 0) {
                    b.getBody().applyLinearImpulse(new Vector2(1, 4.5f), b.getBody().getWorldCenter(), true);
                }
            }
        } else if((contact.getFixtureA().getUserData() == "AllyLeftSide" && contact.getFixtureB().getUserData() == "Ground") ||
                (contact.getFixtureA().getUserData() == "AllyRightSide" && contact.getFixtureB().getUserData() == "Ground")){
            a = contact.getFixtureB();
            b = contact.getFixtureA();

            if(b.getUserData() == "AllyLeftSide") {
                if (b.getBody().getLinearVelocity().y == 0) {
                    b.getBody().applyLinearImpulse(new Vector2(-1, 4.5f), b.getBody().getWorldCenter(), true);
                }
            } else{
                if (b.getBody().getLinearVelocity().y == 0) {
                    b.getBody().applyLinearImpulse(new Vector2(1, 4.5f), b.getBody().getWorldCenter(), true);
                }
            }
        } else if(contact.getFixtureA().getUserData() instanceof Player && contact.getFixtureB().getUserData() == "CeilingTrap"){
            a = contact.getFixtureA();
            b = contact.getFixtureB();

            ((Player)a.getUserData()).onHit(100);
        } else if(contact.getFixtureA().getUserData() == "CeilingTrap" && contact.getFixtureB().getUserData() instanceof Player) {
            a = contact.getFixtureB();
            b = contact.getFixtureA();

            ((Player)a.getUserData()).onHit(100);
        } else if(contact.getFixtureA().getUserData() instanceof Player && contact.getFixtureB().getUserData() == "Slider"){
            a = contact.getFixtureA();
            b = contact.getFixtureB();

            PlayScreen.friction=1.5f;




        } else if(contact.getFixtureA().getUserData() == "Slider" && contact.getFixtureB().getUserData() instanceof Player){
            a = contact.getFixtureA();
            b = contact.getFixtureB();
            PlayScreen.friction=1.5f;



        }
        if(contact.getFixtureA().getUserData() instanceof Player && contact.getFixtureB().getUserData() == "Ladder"){
            a = contact.getFixtureA();
            b = contact.getFixtureB();





        } else if(contact.getFixtureA().getUserData() == "Ladder" && contact.getFixtureB().getUserData() instanceof Player){
            a = contact.getFixtureA();
            b = contact.getFixtureB();

            screen.player.getBody().setGravityScale(0);

            screen.buttonA.removeListener(screen.AL);
            screen.buttonB.removeListener(screen.BL);

            screen.buttonA.addListener(A=new InputListener(){
                public  void touchUp(InputEvent event, float x, float y, int pointer, int button)
                {
                    screen.player.getBody().setLinearVelocity(0,0);
                }

                public boolean touchDown(InputEvent event,float x,float y,int pointer,int button)
                {
                    screen.player.getBody().setLinearVelocity(0,1);
                    return true;
                }

            });
            screen.buttonB.addListener(B=new InputListener(){
                public  void touchUp(InputEvent event, float x, float y, int pointer, int button)
                {
                    screen.player.getBody().setLinearVelocity(0,0);
                }

                public boolean touchDown(InputEvent event,float x,float y,int pointer,int button)
                {
                    screen.player.getBody().setLinearVelocity(0,-1);
                    return true;
                }

            });



        }




    }

    @Override
    public void endContact(Contact contact) {
        Fixture a, b;

        //---------------slider
        if(contact.getFixtureA().getUserData() instanceof Player && contact.getFixtureB().getUserData() == "Slider"){
            a = contact.getFixtureA();
            b = contact.getFixtureB();

            PlayScreen.friction=0;



        } else if(contact.getFixtureA().getUserData() == "Slider" && contact.getFixtureB().getUserData() instanceof Player){
            a = contact.getFixtureA();
            b = contact.getFixtureB();
            PlayScreen.friction=0;

        }
        //--------------------ladder
        if(contact.getFixtureA().getUserData() instanceof Player && contact.getFixtureB().getUserData() == "Ladder"){
            a = contact.getFixtureA();
            b = contact.getFixtureB();
            System.out.println("touched ladder2");
            screen.player.getBody().setGravityScale(1);
       }
        else if(contact.getFixtureA().getUserData() == "Ladder" && contact.getFixtureB().getUserData() instanceof Player){
            a = contact.getFixtureA();
            b = contact.getFixtureB();

            screen.player.getBody().setGravityScale(1);
            screen.player.getBody().setLinearVelocity(0,0);
            screen.buttonA.removeListener(A);
            screen.buttonB.removeListener(B);

            screen.buttonA.addListener(screen.AL);
            screen.buttonB.addListener(screen.BL);


        }

        if(contact.getFixtureA().getUserData() instanceof Player && contact.getFixtureB().getUserData()=="hand"){
            a = contact.getFixtureA();
            b = contact.getFixtureB();
            Soldier.touch=false;



        } else if(contact.getFixtureA().getUserData() =="hand"&& contact.getFixtureB().getUserData() instanceof Player){
            a = contact.getFixtureB();
            b = contact.getFixtureA();
            Soldier.touch=false;



        }



    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        if(contact.getFixtureA().getUserData() instanceof Gunner && contact.getFixtureB().getUserData() instanceof Soldier){
            contact.setEnabled(false);
        } else if(contact.getFixtureA().getUserData() instanceof Soldier && contact.getFixtureB().getUserData() instanceof Gunner){
            contact.setEnabled(false);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
