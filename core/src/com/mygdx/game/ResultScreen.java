package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

public class ResultScreen extends ScreenBeta {

    final MyGame game;

    ActorBeta background;
    NotePaper result;

    BitmapFont resultsFont;
    Label percentage;
    Label score;
    Label combo;
    Label grade;

    public ResultScreen(final MyGame game) {
        this.game = game;
    }

    void SetupResults() {
        result = new NotePaper(screenHeight,screenWidth,0.8f,mainStage,"results_overlay.png");
        result.SetStartPosition(screenWidth * 0.5f - result.getWidth()*0.5f, screenHeight);

        resultsFont = new BitmapFont(Gdx.files.internal("fonts/jitters.fnt"), Gdx.files.internal("fonts/jitters.png"), false);
        percentage = new Label("00", new Label.LabelStyle(resultsFont, Color.BLACK));
        score = new Label("00", new Label.LabelStyle(resultsFont, Color.BLACK));
        combo = new Label("00", new Label.LabelStyle(resultsFont, Color.BLACK));
        grade = new Label("B", new Label.LabelStyle(resultsFont, Color.RED));
        percentage.setPosition(screenWidth*0.655f,screenHeight * 0.659f);
        percentage.setFontScale(2);
        combo.setPosition(screenWidth*0.49f,screenHeight * 0.59f);
        combo.setFontScale(2);
        score.setPosition(screenWidth*0.7f,screenHeight * 0.52f);
        score.setFontScale(2);
        grade.setPosition(screenWidth*0.61f,screenHeight * 0.38f);
        grade.setFontScale(5);
        mainStage.addActor(percentage);
        mainStage.addActor(score);
        mainStage.addActor(combo);
        mainStage.addActor(grade);
    }

    void GetResultData() {
        int pct = game.result.notesHit * 100 / game.result.totalNotes;
        percentage.setText(Integer.toString(pct));
        String letter;
        if (pct >= 80)
        {
            letter = "A";
            if (pct >= 90)
                game.diapers.play();
            else
                game.dynamiteSound.play();
        }
        else if (pct >= 70)
        {
            letter = "B";
            game.couldUseMore.play();
        }
        else if (pct >= 60)
        {
            letter = "C";
            game.couldUseMore.play();
        }
        else if (pct >= 50)
        {
            letter = "D";
            game.couldUseMore.play();
        }
        else
        {
            letter = "F";
            game.couldUseMore.play();
        }
        if (!game.result.win)
        {
            letter = "F";
            game.comeOnGene.play();
        }
        grade.setText(letter);
        score.setText(Integer.toString(game.result.score));
        combo.setText(Integer.toString(game.result.maxCombo));
    }

    void SetupBackground() {
        background = new ActorBeta();
        background.loadTexture("stage_dark.png");
        background.setSize(screenWidth,screenHeight);
        mainStage.addActor(background);
    }

    @Override
    public void initialize() {
        ActorBeta.setWorldBounds(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        SetupBackground();
        SetupResults();
    }

    @Override
    public void show() {
        super.show();
        if (game.result != null)
            GetResultData();
        result.SlideToPosition(screenWidth*0.5f-result.getWidth()*0.5f, screenHeight * 0.2f);
    }

    @Override
    public void hide() {
        super.hide();
        result.ResetPosition();
    }

    @Override
    public void update(float dt) {
        //ActorBeta.setWorldBounds(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);

        if (result.xButton.checkBounds(screenX, Gdx.graphics.getHeight() - screenY)) {
            game.setScreen(game.mapScreen);
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        super.touchUp(screenX, screenY, pointer, button);

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        super.touchDragged(screenX, screenY, pointer);

        return true;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }
}
