package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.Set;

/**
 * Created by markapptist on 2018-09-26.
 */

public class MyGame extends GameBeta {

    Results result;

    StartScreen startScreen;
    MapScreen mapScreen;
    GameScreen gameScreen;
    ResultScreen resultScreen;
    MapLocation[] mapLocations;

    Music titleMusic;
    Music mapMusic;
    Sound cowbellHit;
    Sound moreCowbell;
    Sound dontBlowThis;
    Sound prescription;
    Sound comeOnGene;
    Sound dynamiteSound;
    Sound diapers;
    Sound couldUseMore;
    Song reaper;
    float volume;

    int numLocs;
    boolean paused = false;

    void LoadLevels() {
        numLocs = 1;
        mapLocations = new MapLocation[numLocs];
        mapLocations[0] = new MapLocation();
        mapLocations[0].setScreenRelativePosition(0.6f, 0.15f);
        mapLocations[0].setInfo("The Troubadour","Hollywood, CA", "Don't Fear The Reaper","Easy");
    }

    @Override
    public void create() {

        super.create();

        //LoadLevels();
        volume = 1.0f;
        SetupSong();
        LoadAudio();

        mapScreen = new MapScreen(this);
        startScreen = new StartScreen(this);
        gameScreen = new GameScreen(this);
        resultScreen = new ResultScreen(this);

        setScreen(startScreen);
    }

    void LoadAudio() {
        titleMusic = Gdx.audio.newMusic(Gdx.files.internal("title.mp3"));
        mapMusic = Gdx.audio.newMusic(Gdx.files.internal("surf.mp3"));
        cowbellHit = Gdx.audio.newSound(Gdx.files.internal("Cowbell.wav"));
        moreCowbell = Gdx.audio.newSound(Gdx.files.internal("morecowbell.wav"));
        dontBlowThis = Gdx.audio.newSound(Gdx.files.internal("dontblowthis.wav"));
        prescription = Gdx.audio.newSound(Gdx.files.internal("prescription.wav"));
        comeOnGene = Gdx.audio.newSound(Gdx.files.internal("comeongene.wav"));
        dynamiteSound = Gdx.audio.newSound(Gdx.files.internal("dynamitesound.wav"));
        diapers = Gdx.audio.newSound(Gdx.files.internal("diapers.wav"));
        couldUseMore = Gdx.audio.newSound(Gdx.files.internal("couldusemore.wav"));
    }

    void SetupSong() {
        reaper = new Song(42,142.0f,4.0f,4.0f,3.48f,"reaper.wav");
        Bar fullRestBar = new Bar(1);
        Note wholeRest = new Note(Note.WHOLE*reaper.timeSigBottom*reaper.beat,true);
        fullRestBar.notes[0] = wholeRest;
        Bar quarters = new Bar(4);
        Note quarterNote = new Note(Note.QUARTER*reaper.timeSigBottom*reaper.beat);
        for (int i = 0; i < 4; i++)
        {
            quarters.notes[i] = quarterNote;
        }
        reaper.bars[0] = fullRestBar;
        reaper.bars[1] = fullRestBar;
        reaper.bars[2] = fullRestBar;
        reaper.bars[3] = fullRestBar;
        for (int i = 4; i < 40; i++)
        {
            reaper.bars[i] = quarters;
            reaper.totalNotes += 4;
        }
        reaper.bars[40] = fullRestBar;
        reaper.bars[41] = fullRestBar;
        /*
        for (int i = 0; i < reaper.bars.length; i++)
        {
            if(!reaper.bars[i].Verify(reaper.beat,reaper.timeSigTop,reaper.timeSigBottom))
            {
                Gdx.app.error("InvalidBar","Song: Reaper, Bar: " + Integer.toString(i));
            }
        }
        */
    }
}
