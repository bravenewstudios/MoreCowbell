package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class TourMap extends ActorBeta {

    float startX;
    float startY;

    public TourMap(float x, float y, Stage s) {
        super(x, y, s);
        startX = x;
        startY = y;
        loadTexture("state_map.png");
        scaleToScreenWidth(1.1f);
        setRotation(-10.0f);
    }

    public void TriggerPullDown(float x, float y) {
        DelayAction delay = new DelayAction(1.0f);
        MoveToAction move = new MoveToAction();
        move.setPosition(x, y);
        move.setDuration(0.3f);
        SequenceAction entry = new SequenceAction();
        entry.addAction(delay);
        entry.addAction(move);
        addAction(entry);
    }

    public void ResetPosition() {
        setPosition(startX, startY);
    }


}
