package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Song {

    Bar[] bars;
    int numBars;
    int totalNotes;

    float beat;
    float bpm;
    float timeSigTop;
    float timeSigBottom;
    float startOffset;

    Music track;

    Song(int numBars, float bpm, float timeSigTop, float timeSigBottom, float startOffset, String filePath) {
        track = Gdx.audio.newMusic(Gdx.files.internal(filePath));
        this.totalNotes = 0;
        this.numBars = numBars;
        this.bpm = bpm;
        this.timeSigTop = timeSigTop;
        this.timeSigBottom = timeSigBottom;
        this.startOffset = startOffset;
        this.bars = new Bar[numBars];
        this.beat = 60.0f / bpm;
    }
}
