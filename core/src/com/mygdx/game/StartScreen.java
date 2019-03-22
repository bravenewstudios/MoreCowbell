package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
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
import com.badlogic.gdx.utils.Align;

public class StartScreen extends ScreenBeta {

    final MyGame game;

    Music titleMusic;

    ActorBeta optionsButton;
    ActorBeta background;
    ActorBeta titleMore;
    ActorBeta titleCowbell;
    ActorBeta walkenHead;
    ActorBeta sliderBar;
    ActorBeta slider;

    NotePaper optionsMenu;

    boolean optionsOpen;

    public StartScreen(final MyGame game) {
        this.game = game;
    }

    void SetupButtons() {
        optionsButton = new ActorBeta();
        optionsButton.loadTexture("gear.png");
        optionsButton.setPosition(Gdx.graphics.getWidth() - optionsButton.getWidth() * 1.5f, optionsButton.getHeight() * 0.5f);
        mainStage.addActor(optionsButton);
        optionsMenu = new NotePaper(screenWidth,screenHeight,0.8f,mainStage,"options_template_text.png");
        optionsMenu.SetStartPosition(screenWidth * 0.5f - optionsMenu.getWidth()*0.5f, screenHeight);
        //sliderBar = new ActorBeta();
        //sliderBar.loadTexture("slider_bar.png");

    }

    void AnimateButtons() {
        RotateByAction gearRotate = new RotateByAction();
        gearRotate.setAmount(360.0f);
        gearRotate.setDuration(30.0f);

        RepeatAction repeatForever = new RepeatAction();
        repeatForever.setAction(gearRotate);
        repeatForever.setCount(RepeatAction.FOREVER);
        optionsButton.addAction(repeatForever);
    }

    void SetupTitle() {
        titleCowbell = new ActorBeta();
        titleCowbell.loadTexture("title_cowbell.png");
        titleCowbell.setPosition(Gdx.graphics.getWidth()/2.0f - titleCowbell.getWidth()/2.0f,Gdx.graphics.getHeight() * 0.75f - titleCowbell.getHeight()/2.0f);
        mainStage.addActor(titleCowbell);
        titleMore = new ActorBeta();
        titleMore.loadTexture("title_more.png");
        titleMore.setPosition(titleCowbell.getX() + titleMore.getWidth() * 0.33f,titleCowbell.getY()+titleMore.getHeight()*0.8f);
        mainStage.addActor(titleMore);
        titleMore.setOrigin(titleMore.getWidth(),0.0f);
        titleCowbell.setOrigin(titleMore.getWidth()*0.2f,0.0f);
    }

    void AnimateTitle() {
        RotateToAction rotate1 = new RotateToAction();
        rotate1.setRotation(-20.0f);
        RotateToAction rotate2 = new RotateToAction();
        rotate2.setRotation(0.0f);
        DelayAction delay1 = new DelayAction(0.98f);
        DelayAction delay2 = new DelayAction(0.98f);

        SequenceAction repeatRotate = new SequenceAction();
        repeatRotate.addAction(rotate1);
        repeatRotate.addAction(delay1);
        repeatRotate.addAction(rotate2);
        repeatRotate.addAction(delay2);

        RepeatAction repeatForever = new RepeatAction();
        repeatForever.setAction(repeatRotate);
        repeatForever.setCount(RepeatAction.FOREVER);
        titleMore.addAction(repeatForever);

        RotateToAction rotate3 = new RotateToAction();
        rotate3.setRotation(0.0f);
        RotateToAction rotate4 = new RotateToAction();
        rotate4.setRotation(-5.0f);
        DelayAction delay3 = new DelayAction(0.98f);
        DelayAction delay4 = new DelayAction(0.98f);

        SequenceAction repeatRotate2 = new SequenceAction();
        repeatRotate2.addAction(rotate3);
        repeatRotate2.addAction(delay3);
        repeatRotate2.addAction(rotate4);
        repeatRotate2.addAction(delay4);

        RepeatAction repeatForever2 = new RepeatAction();
        repeatForever2.setAction(repeatRotate2);
        repeatForever2.setCount(RepeatAction.FOREVER);
        titleCowbell.addAction(repeatForever2);
    }

    void SetupHead() {
        walkenHead = new ActorBeta();
        walkenHead.loadTexture("Walken_head.png");
        walkenHead.scaleToScreenHeight(0.4f);
        walkenHead.setPosition(Gdx.graphics.getWidth()/2.0f - walkenHead.getWidth()/2.0f,Gdx.graphics.getHeight()/2.0f - walkenHead.getHeight()/2.0f);
        mainStage.addActor(walkenHead);
    }

    void AnimateHead() {
        RotateToAction rotate1 = new RotateToAction();
        rotate1.setRotation(-10.0f);
        RotateToAction rotate2 = new RotateToAction();
        rotate2.setRotation(10.0f);
        DelayAction delay1 = new DelayAction(0.98f);
        DelayAction delay2 = new DelayAction(0.98f);

        SequenceAction repeatRotate = new SequenceAction();
        repeatRotate.addAction(rotate1);
        repeatRotate.addAction(delay1);
        repeatRotate.addAction(rotate2);
        repeatRotate.addAction(delay2);

        RepeatAction repeatForever = new RepeatAction();
        repeatForever.setAction(repeatRotate);
        repeatForever.setCount(RepeatAction.FOREVER);
        walkenHead.addAction(repeatForever);
    }

    void SetupBackground() {
        background = new ActorBeta();
        background.loadTexture("sunburst.jpg");
        background.setPosition(Gdx.graphics.getWidth()/2.0f - background.getWidth()/2.0f,Gdx.graphics.getHeight()/2.0f - background.getHeight()/2.0f);
        mainStage.addActor(background);
        //this.mainStage.getCamera().position.set(background.getX(), background.getY(), this.mainStage.getCamera().position.z);
    }

    void AnimateBackground () {
        RotateByAction bgRotate = new RotateByAction();
        bgRotate.setAmount(-360.0f);
        bgRotate.setDuration(30.0f);

        RepeatAction repeatForever = new RepeatAction();
        repeatForever.setAction(bgRotate);
        repeatForever.setCount(RepeatAction.FOREVER);
        background.addAction(repeatForever);
    }

    void OpenOptions() {
        optionsOpen = true;
        optionsMenu.SlideToPosition(screenWidth*0.5f-optionsMenu.getWidth()*0.5f, screenHeight * 0.2f);
    }

    void CloseOptions() {
        optionsOpen = false;
        optionsMenu.ResetPosition();
    }

    @Override
    public void initialize() {
        ActorBeta.setWorldBounds(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //titleMusic = game.titleMusic;
        optionsOpen = false;
        SetupBackground();
        SetupHead();
        SetupTitle();
        SetupButtons();

        AnimateBackground();
        AnimateHead();
        AnimateTitle();
        AnimateButtons();
    }

    @Override
    public void show() {
        super.show();
        optionsOpen = false;
        game.titleMusic.play();
    }

    @Override
    public void hide() {
        super.hide();
        game.titleMusic.stop();
    }

    @Override
    public void update(float dt) {

        //ActorBeta.setWorldBounds(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);

        if (!optionsOpen) {
            if (optionsButton.checkBounds(screenX, Gdx.graphics.getHeight() - screenY)) {
                OpenOptions();
            } else {
                game.setScreen(game.mapScreen);
            }
        }
        else
        {
            if (optionsMenu.xButton.checkBounds(screenX, Gdx.graphics.getHeight() - screenY)) {
                CloseOptions();
            }
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
