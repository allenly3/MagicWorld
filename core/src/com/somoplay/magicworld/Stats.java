package com.somoplay.magicworld;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Stats implements Disposable{
    public Stage stage;
    private Viewport viewport;

    static Label scoreLabel;
    Label timeLabel, timeTextLabel, scoreTextLabel;

    public Stats (SpriteBatch batch){

        viewport = new FitViewport(MagicWorld.screenWidth, MagicWorld.screenHeight, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label(String.format("%06d",00), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label(String.format("%03d", 00), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeTextLabel = new Label(String.format("Time", 0), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreTextLabel = new Label(String.format("Score", 0), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(timeTextLabel).expandX().pad(10);
        table.add(scoreTextLabel).expandX().pad(10);
        table.row();
        table.add(timeLabel).expandX();
        table.add(scoreLabel).expandX();

        stage.addActor(table);
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
