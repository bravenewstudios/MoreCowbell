package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Xbutton extends ActorBeta {

    NotePaper parent;

    public Xbutton(NotePaper parent, Stage s) {
        super();
        this.parent = parent;
        loadTexture("Xbutton.png");
        scaleToScreenWidth(0.06f);
        setPosition(parent.getX() + parent.getWidth()-getWidth()*1.5f, parent.getY()+parent.getHeight()-getHeight()*1.5f);
        s.addActor(this);
    }

    @Override
    public void act(float dt) {
        super.act(dt);

        setPosition(parent.getX() + parent.getWidth()-getWidth()*1.5f, parent.getY()+parent.getHeight()-getHeight()*1.5f);
    }
}
