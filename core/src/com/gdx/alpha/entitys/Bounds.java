package com.gdx.alpha.entitys;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Admin on 03.02.15.
 */
public class Bounds {
    private Rectangle bounds;

    public Bounds (float x, float y, float width, float height){
        bounds = new Rectangle(x, y, width, height);
    }

    public Rectangle getBox(){
        return bounds;
    }

    public void update(float x, float y, float width, float height){
        bounds.set(x, y, width, height);
    }
}
