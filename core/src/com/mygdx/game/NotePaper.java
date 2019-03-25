package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

import java.util.ArrayList;
import java.util.List;

// actor class for the note paper graphic, see NoteText for the text itself

public class NotePaper extends ActorBeta {

    float startX;
    float startY;

    NoteText text;
    Xbutton xButton;

    public NotePaper(float x, float y, float scale, Stage s, String textFile)
    {
        super(x,y,s);
        loadTexture("paper_narrow.png");
        scaleToScreenWidth(scale);
        text = new NoteText(this,scale,s,textFile);
        xButton = new Xbutton(this, s);
        setVisible(false);
    }

    public void SetStartPosition(float x, float y)
    {
        startX = x;
        startY = y;
        setPosition(x, y);
    }

    public void SlideToPosition(float x, float y)
    {
        setVisible(true);
        MoveToAction move = new MoveToAction();
        move.setPosition(x,y);
        move.setDuration(0.3f);
        addAction(move);
    }

    public void ResetPosition() {
        setVisible(false);
        setPosition(startX,startY);
    }

}
