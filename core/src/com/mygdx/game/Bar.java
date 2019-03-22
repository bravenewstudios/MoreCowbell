package com.mygdx.game;

public class Bar {

    // which type of note equals a beat is defined by the time signature
    // EXAMPLE:
    // >in 4/4 time the quarter note is one beat
    // >timeSigBottom (4.0f) * QUARTER (0.25f) * beat (60.0f/bpm) = duration of note

    Note[] notes;
    int numNotes;

    Bar(int numNotes) {
        this.numNotes = numNotes;
        notes = new Note[numNotes];
    }

    // doesn't work because of float arithmetic error
    boolean Verify(float beat, float timeSigTop, float timeSigBottom) {
        float expectedDuration = timeSigTop * beat;
        float checkedDuration = 0.0f;
        for (int i = 0; i < numNotes; i++)
        {
            checkedDuration += notes[i].time * beat * timeSigBottom;
        }
        if (checkedDuration == expectedDuration)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}