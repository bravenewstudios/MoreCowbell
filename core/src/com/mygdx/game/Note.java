package com.mygdx.game;

public class Note {

    // which type of note equals a beat is defined by the time signature
    // EXAMPLE:
    // >in 4/4 time the quarter note is one beat
    // >timeSigBottom (4.0f) * QUARTER (0.25f) * beat (60.0f/bpm) = duration of note

    static final float WHOLE = 1.0f;
    static final float HALF = 0.5f;
    static final float QUARTER = 0.25f;
    static final float EIGHTH = 0.125f;
    static final float SIXTEENTH = 0.0625f;
    static final float THIRTYSECOND = 0.03125f;

    boolean isRest;
    float time;

    Note() {

    }
    Note(float time) {
        this.time = time;
        this.isRest = false;
    }
    Note(float time, boolean isRest) {
        this.time = time;
        this.isRest = isRest;
    }
}