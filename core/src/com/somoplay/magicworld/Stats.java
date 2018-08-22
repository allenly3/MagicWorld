package com.somoplay.magicworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.somoplay.magicworld.Screens.MenuScreen;
import com.somoplay.magicworld.Screens.PlayScreen;

import javax.xml.soap.Text;

public class Stats implements Disposable{
    public Stage stage;
    private Viewport viewport;

    static Label scoreLabel;
    Label timeLabel, timeTextLabel, scoreTextLabel;
    Drawable menuImage;
    TextButton playAgain;
    PlayScreen screen;

    public Stats (SpriteBatch batch, final PlayScreen screen){

        Skin skin = new Skin();
        skin.add("menu",new Texture("images/scoreui.png"));
        skin.add("button_up", new Texture("images/b_4.png"));
        skin.add("button_down", new Texture("images/b_5.png"));
        menuImage = skin.getDrawable("menu");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PoetsenOne-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24; // font size
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose(); // avoid memory leaks, important

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(skin.getDrawable("button_up"),
                skin.getDrawable("button_down"), skin.getDrawable("button_up"), font);
        playAgain = new TextButton("Play Again", buttonStyle);
        playAgain.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.print("Work");
                screen.nextLevel();
            }
        });



        viewport = new FitViewport(MagicWorld.screenWidth, MagicWorld.screenHeight, new OrthographicCamera());
        stage = new Stage(viewport, batch);


        Table table = new Table();
        Table globalTable = new Table();

        table.top();
        table.setFillParent(true);
        globalTable.bottom().left();
        globalTable.setFillParent(true);

        scoreLabel = new Label(String.format("%06d",00), new Label.LabelStyle(font, Color.BLUE));
        timeLabel = new Label(String.format("%03d", 00), new Label.LabelStyle(font, Color.BLUE));
        timeTextLabel = new Label(String.format("Time", 0), new Label.LabelStyle(font, Color.BLUE));
        scoreTextLabel = new Label(String.format("Score", 0), new Label.LabelStyle(font, Color.BLUE));


        table.padTop(64);
        table.add(timeTextLabel).padRight(50);
        table.add(scoreTextLabel).padRight(112);
        table.row();
        table.add(timeLabel).padRight(50);
        table.add(scoreLabel).padRight(112);
        table.row();
        table.addActor(playAgain);
        table.add(playAgain).size(200,80).padTop(180).padLeft(196);
        globalTable.setBackground(menuImage);
        globalTable.addActor(table);
        //table.setDebug(true);
        stage.addActor(globalTable);

    }


    @Override
    public void dispose() {
        stage.dispose();
    }

    public void setScoreLabel(int score){
        scoreLabel.setText(String.format("%06d",score));
    }

    public void setTimeLabel(float time){
        timeLabel.setText(String.format("%.2f",time));
    }

}
