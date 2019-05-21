package com.gdx.alpha.entitys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by admin on 26.02.2015.
 */
public class ScoreCloud extends Actor {
    private Vector2 position;
    private BitmapFont bitmapFont;
    private Integer score;
    private boolean positive;

    public ScoreCloud(Vector2 position,BitmapFont bitmapFont ,Integer score, boolean positive) {
        this.position = position;
        this.score = score;
        this.bitmapFont = bitmapFont;
        this.positive = positive;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (positive)
            bitmapFont.draw(batch, "+"+String.valueOf(score),position.x, position.y);
        else
            bitmapFont.draw(batch, "-"+String.valueOf(score),position.x, position.y);
    }

    @Override
    public void act(float delta) {
        //super.act(delta);
        this.position.y += 4;
    }

    @Override
    public void setPosition(float x, float y) {
        //super.setPosition(x, y);
        this.position.x = x;
        this.position.y = y;
    }

    @Override
    public float getY() {
        //return super.getX();
        return position.y;
    }
}
