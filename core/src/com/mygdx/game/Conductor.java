package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class Conductor {
    Song song;

    static final float DOTMOVETIME = 1.5f;

    float nextBeatTime;

    int noteIndex;
    int barIndex;
    int numBars;
    int numNotes;
    int dotIndex;

    boolean isRest;
    boolean finished;

    Dot[] dots;

    Conductor() {
        dotIndex = 0;
        barIndex = 0;
        noteIndex = 0;
    }

    void Start() {
        finished = false;
        numBars = song.numBars;
        numNotes = song.bars[barIndex].numNotes;
        nextBeatTime = song.startOffset - DOTMOVETIME;
        isRest = song.bars[barIndex].notes[noteIndex].isRest;
        song.track.play();
    }

    void Stop() {
        dotIndex = 0;
        barIndex = 0;
        noteIndex = 0;
        finished = false;
        song.track.stop();
    }

    void SpawnDot() {
        dots[dotIndex].FireAction();
        dotIndex++;
        if (dotIndex >= dots.length)
        {
            dotIndex = 0;
        }
    }

    void AdvanceBeat() {
        noteIndex++;
        if (noteIndex >= numNotes)
        {
            barIndex++;
            if (barIndex >= numBars)
            {
                finished = true;
                return;
            }
            else
            {
                numNotes = song.bars[barIndex].numNotes;
                noteIndex = 0;
            }
        }
        nextBeatTime += song.bars[barIndex].notes[noteIndex].time;
        isRest = song.bars[barIndex].notes[noteIndex].isRest;
    }

    boolean Update() {
        if (song.track.isPlaying() && !finished)
        {
            if (song.track.getPosition() >= nextBeatTime)
            {
                if (!isRest) {
                    //Gdx.app.error("Beat", "Index: " + Integer.toString(noteIndex) + "Time: " + Float.toString(nextBeatTime));
                    SpawnDot();
                }
                else {
                    //Gdx.app.error("Rest", "Index: " + Integer.toString(noteIndex) + "Time: " + Float.toString(nextBeatTime));
                }
                //Gdx.app.error("SongPosition", Float.toString(song.track.getPosition()));
                AdvanceBeat();
            }
        }
        return finished;
    }
}
