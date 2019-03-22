package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;

public class MapLocation extends ActorBeta{

    String venue;
    String city;
    String song;
    String difficulty;

    public MapLocation() {
        super();

        loadTexture("star.png");
        setVisible(false);
    }

    public void setScreenRelativePosition(float x, float y)
    {
        x *= (float)Gdx.graphics.getWidth();
        y *= (float)Gdx.graphics.getHeight();
        setPosition(x,y);
        setOrigin(Align.bottomLeft);
    }

    public void setInfo(String venue, String city, String song, String difficulty)
    {
        this.venue = venue;
        this.city = city;
        this.song = song;
        this.difficulty = difficulty;
    }
}
