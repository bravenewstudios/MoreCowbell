package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;

public class GameScreen extends ScreenBeta {

    final MyGame game;

    BitmapFont scoreFont;
    Label scoreLabel;
    Label comboLabel;
    Conductor conductor;
    ActorBeta dangerOverlay;
    ActorBeta background;
    ActorBeta cowbell;
    ActorBeta scoreCounter;
    ActorBeta comboCounter;
    ActorBeta exit;
    ActorBeta beatBar;
    FireParticle fire;
    BurstParticle burst;
    Dot[] dots;

    int health;
    int score;
    int combo;
    int maxCombo;
    int totalHits;
    float target;
    float hitWindow;

    AlphaAction dangerFlash;

    public GameScreen(final MyGame game) {
        this.game = game;
    }

    void SetupCowbell() {
        cowbell = new ActorBeta();
        cowbell.loadTexture("cowbell.png");
        cowbell.scaleToScreenHeight(0.5f);
        cowbell.setPosition(screenWidth/2.0f-cowbell.getWidth()/2.0f,screenHeight/2.0f-cowbell.getHeight()/2.0f);
        mainStage.addActor(cowbell);
    }

    void SetupBackground() {
        background = new ActorBeta();
        background.loadTexture("stage_dark.png");
        background.setSize(screenWidth,screenHeight);
        mainStage.addActor(background);

        scoreCounter = new ActorBeta();
        scoreCounter.loadTexture("score_counter.png");
        scoreCounter.scaleToScreenHeight(0.1f);
        scoreCounter.setWidth(scoreCounter.getWidth()*1.32f);
        scoreCounter.setPosition(scoreCounter.getHeight()*0.25f,scoreCounter.getHeight()*0.25f);
        mainStage.addActor(scoreCounter);

        comboCounter = new ActorBeta();
        comboCounter.loadTexture("combo_counter.png");
        comboCounter.scaleToScreenHeight(0.1f);
        comboCounter.setWidth(comboCounter.getWidth()*1.32f);
        comboCounter.setPosition(comboCounter.getHeight()*0.25f,comboCounter.getHeight()*1.25f);
        mainStage.addActor(comboCounter);

        scoreFont = new BitmapFont(Gdx.files.internal("fonts/Oloron.fnt"), Gdx.files.internal("fonts/Oloron.png"), false);
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(scoreFont, Color.WHITE));
        scoreLabel.setPosition(scoreCounter.getX()+scoreCounter.getWidth()*0.07f, scoreCounter.getY()+scoreCounter.getHeight()*0.25f);
        scoreLabel.setFontScale(2);
        mainStage.addActor(scoreLabel);

        comboLabel = new Label(String.format("%03d", combo), new Label.LabelStyle(scoreFont, Color.WHITE));
        comboLabel.setPosition(comboCounter.getX()+comboCounter.getWidth()*0.13f, comboCounter.getY()+comboCounter.getHeight()*0.25f);
        comboLabel.setFontScale(2);
        mainStage.addActor(comboLabel);

        exit = new ActorBeta();
        exit.loadTexture("exit.png");
        exit.scaleToScreenWidth(0.1f);
        exit.setPosition(screenWidth-exit.getWidth()*1.25f,comboCounter.getHeight()*0.25f);
        mainStage.addActor(exit);
    }

    void SetupOverlay () {
        dangerOverlay = new ActorBeta();
        dangerOverlay.loadTexture("danger_overlay.png");
        dangerOverlay.setSize(screenWidth,screenHeight);
        dangerOverlay.setPosition(0.0f,0.0f);
        dangerOverlay.getColor().a = 0.0f;
        mainStage.addActor(dangerOverlay);
    }

    void EndLevel(boolean win) {
        game.result = new Results(totalHits, conductor.song.totalNotes, maxCombo, score, win);
        for (int i = 0; i < dots.length; i++)
        {
            dots[i].remove();
        }
        game.setScreen(game.resultScreen);
    }

    void MissHit() {
        health -= 10;
        if (health == 30 || health == 25)
        {
            game.dontBlowThis.play();
        }
        else if (health == 60 || health == 55)
        {
            game.couldUseMore.play();
        }
        if (health <= 30)
        {
            dangerOverlay.clearActions();
            dangerOverlay.getColor().a = 0.8f;
            dangerFlash = Actions.alpha(0.0f, 1.0f);
            dangerOverlay.addAction(dangerFlash);
        }
        if (health <= 0)
        {
            EndLevel(false);
        }
        combo = 0;
        UpdateUI();
    }

    void SetupBar() {
        beatBar = new ActorBeta();
        beatBar.loadTexture("beat_bar.png");
        beatBar.scaleToScreenWidth(0.8f);
        beatBar.setPosition(screenWidth/2.0f-beatBar.getWidth()/2.0f,screenHeight/2.0f-beatBar.getHeight()/2.0f);
        mainStage.addActor(beatBar);
        target = beatBar.getX()+beatBar.getWidth()*0.06f;
        hitWindow = 80.0f;
    }

    void SetupDots()
    {
        dots = new Dot[8];
        for (int i = 0; i < 8; i++)
        {
            dots[i] = new Dot(beatBar,screenHeight);
            mainStage.addActor(dots[i]);
        }
    }

    void SetupConductor() {
        score = 0;
        combo = 0;
        maxCombo = 0;
        totalHits = 0;
        health = 100;
        conductor = new Conductor();
        conductor.song = game.reaper;
        conductor.dots = dots;
        conductor.Start();
    }

    void StartMusic() {
        conductor.Start();
    }

    void UpdateUI() {
        scoreLabel.setText(String.format("%06d", score));
        comboLabel.setText(String.format("%03d", combo));
    }

    @Override
    public void initialize() {
        ActorBeta.setWorldBounds(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //music = game.reaper.track;
        //cowbellHit = game.cowbellHit;
        SetupBackground();
        SetupCowbell();
        SetupBar();
        SetupOverlay();
    }

    @Override
    public void show() {
        super.show();
        SetupDots();
        SetupConductor();
    }

    @Override
    public void hide() {
        super.hide();
        conductor.Stop();
    }

    @Override
    public void update(float dt) {
        if (conductor.Update())
        {
            EndLevel(true);
        }
        for (int i = 0; i < dots.length; i++) {
            if (!dots[i].hit && dots[i].getX() < target - hitWindow) {
                dots[i].hit = true;
                MissHit();
            }
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);

        if (cowbell.checkBounds(screenX, Gdx.graphics.getHeight() - screenY)) {
            game.cowbellHit.play();
            boolean hit = false;
            for (int i = 0; i < dots.length ; i++)
            {
                if (!dots[i].hit) {
                    if (dots[i].getX() < target + hitWindow && dots[i].getX() > target - hitWindow) {
                        fire = new FireParticle();
                        fire.setPosition(target + dots[0].getWidth() * 0.5f, screenHeight / 2.0f);
                        fire.setScale(2.0f);
                        mainStage.addActor(fire);
                        fire.start();
                        score += combo / 10 + 1;
                        combo++;
                        if (combo > maxCombo)
                        {
                            maxCombo = combo;
                        }
                        totalHits++;
                        if (health < 100) {
                            health += 5;
                        }
                        dots[i].hit = true;
                        hit = true;
                        if ((combo % 10) == 0) {
                            game.prescription.play();
                            burst = new BurstParticle();
                            burst.setPosition(screenWidth * 0.5f, screenHeight * 0.7f);
                            burst.setScale(2.0f);
                            mainStage.addActor(burst);
                            burst.start();
                        }
                        UpdateUI();
                    }
                }
            }
            if (!hit)
            {
                MissHit();
            }

        }
        if (exit.checkBounds(screenX, Gdx.graphics.getHeight() - screenY)) {
            for (int i = 0; i < dots.length; i++)
            {
                dots[i].remove();
            }
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
