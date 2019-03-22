package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class NoteText extends ActorBeta {

    NotePaper parent;

    public NoteText(NotePaper parent, float scale, Stage s, String filePath) {
        super();
        this.parent = parent;
        loadTexture(filePath);
        scaleToScreenWidth(scale);
        setPosition(parent.getX(), parent.getY());
        s.addActor(this);
    }

    @Override
    public void act(float dt) {
        super.act(dt);

        setPosition(parent.getX(), parent.getY());
    }
}
