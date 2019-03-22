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
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;

public class MapScreen extends ScreenBeta {

    final MyGame game;

    MapLocation[] mapLocations;
    TourMap map;
    NotePaper note;
    ActorBeta background;
    ActorBeta exitButton;
    ActorBeta playButton;
    Sound engineStart;
    Music mapMusic;

    int numLocs;
    boolean mapActive;
    boolean noteActive;

    public MapScreen(final MyGame game) {
        this.game = game;
    }

    void SetupMap() {
        mapActive = false;
        noteActive = false;
        map = new TourMap(0.0f, screenHeight, mainStage);
        numLocs = 1;
        mapLocations = new MapLocation[numLocs];
        mapLocations[0] = new MapLocation();
        mapLocations[0].setScreenRelativePosition(0.6f, 0.15f);
        mapLocations[0].setInfo("The Troubadour","Hollywood, CA", "Don't Fear The Reaper","Easy");
        //mapLocations[0].loadSongFile("DontFearTheReaper.mp3");
        mainStage.addActor(mapLocations[0]);
        note = new NotePaper(screenWidth,screenHeight,0.6f, mainStage,"venue_overlay.png");
        note.SetStartPosition(screenWidth * 0.5f - note.getWidth()*0.5f, screenHeight);
    }

    void ActivateMap() {
        mapActive = true;
        for (int i = 0; i < mapLocations.length; i++)
        {
            mapLocations[i].setVisible(true);
            mapLocations[i].setScale(0.0f);
            ScaleToAction scale = new ScaleToAction();
            scale.setScale(1.0f);
            scale.setDuration(0.3f);
            mapLocations[i].addAction(scale);
        }
        exitButton.setVisible(true);
        MoveToAction exitPopup = new MoveToAction();
        exitPopup.setPosition(exitButton.getX(),screenHeight*0.75f);
        exitPopup.setDuration(0.3f);
        exitButton.addAction(exitPopup);
    }

    void SetupBackground() {
        background = new ActorBeta();
        background.loadTexture("dashboard.png");
        background.scaleToScreenWidth(1.0f);
        background.setY(screenHeight - background.getHeight());
        mainStage.addActor(background);
    }

    void SetupButtons() {
        exitButton = new ActorBeta();
        exitButton.loadTexture("exit_sign.png");
        exitButton.scaleToScreenWidth(0.3f);
        exitButton.setPosition(screenWidth*0.1f,screenHeight*0.5f);
        exitButton.setVisible(false);
        mainStage.addActor(exitButton);

        playButton = new ActorBeta();
        playButton.loadTexture("play_sign.png");
        playButton.scaleToScreenWidth(0.3f);
        playButton.setPosition(screenWidth*0.6f,screenHeight*0.5f);
        playButton.setVisible(false);
        mainStage.addActor(playButton);
    }

    void ResetActors() {
        for (int i = 0; i < mapLocations.length; i++)
        {
            mapLocations[i].setVisible(false);
        }
        exitButton.setPosition(screenWidth*0.1f,screenHeight*0.5f);
        exitButton.setVisible(false);
        playButton.setPosition(screenWidth*0.6f,screenHeight*0.5f);
        playButton.setVisible(false);
        map.ResetPosition();
        note.ResetPosition();
    }

    void OpenNote() {
        noteActive = true;
        note.SlideToPosition(screenWidth*0.5f-note.getWidth()*0.5f, screenHeight * 0.2f);
        playButton.setVisible(true);
        MoveToAction move = new MoveToAction();
        move.setPosition(playButton.getX(),screenHeight*0.7f);
        move.setDuration(0.3f);
        playButton.addAction(move);
    }

    void CloseNote() {
        noteActive = false;
        playButton.setPosition(screenWidth*0.6f,screenHeight*0.5f);
        playButton.setVisible(false);
        note.ResetPosition();
    }

    @Override
    public void initialize() {
        ActorBeta.setWorldBounds(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //mapMusic = game.mapMusic;
        SetupBackground();
        SetupButtons();
        SetupMap();
    }

    @Override
    public void show() {
        super.show();
        map.TriggerPullDown(0.0f,0.0f);
        Timer.schedule(new Timer.Task(){

            @Override
            public void run() {
                ActivateMap();
            }

        },2.0f);
        game.mapMusic.play();
    }

    @Override
    public void hide() {
        super.hide();
        mapActive = false;
        noteActive = false;
        game.mapMusic.stop();
        ResetActors();
    }

    @Override
    public void update(float dt) {
        //ActorBeta.setWorldBounds(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);

        if (mapActive) {

            if (exitButton.checkBounds(screenX, Gdx.graphics.getHeight() - screenY)) {
                //mapMusic.stop();
                game.setScreen(game.startScreen);
            }
            if (noteActive)
            {
                if (playButton.checkBounds(screenX, Gdx.graphics.getHeight() - screenY)) {
                    game.setScreen(game.gameScreen);
                }
                else if (note.xButton.checkBounds(screenX, Gdx.graphics.getHeight() - screenY)) {
                    CloseNote();
                }
            }
            if (!noteActive) {
                for (int i = 0; i < mapLocations.length; i++) {
                    if (mapLocations[i].checkBounds(screenX, Gdx.graphics.getHeight() - screenY)) {
                        // open location details
                        //mapMusic.stop();

                        OpenNote();
                    }
                }
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
