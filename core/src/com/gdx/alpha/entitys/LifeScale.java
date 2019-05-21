package com.gdx.alpha.entitys;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by admin on 07.03.2015.
 */
public class LifeScale extends Actor {
    private TextureAtlas textureAtlas;
    private TextureRegion lifeScale;
    private float x;
    private float y;
    private int width;
    private int currentWidth;

    public LifeScale(TextureAtlas textureAtlas, float x, float y, int width) {
        this.textureAtlas = textureAtlas;
        this.x = x;
        this.y = y;
        this.width = width;
        this.currentWidth = width;
        lifeScale = new TextureRegion(textureAtlas.findRegion("green"));
        lifeScale.setRegionWidth(currentWidth);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        if (currentWidth > width/2){
            lifeScale.setRegion(textureAtlas.findRegion("green"));
        }
        if (currentWidth < width/2){
            lifeScale.setRegion(textureAtlas.findRegion("yellow"));
        }
        if (currentWidth < width/3){
            lifeScale.setRegion(textureAtlas.findRegion("red"));
        }
        if (currentWidth < width){
           batch.draw(lifeScale, x, y,currentWidth,lifeScale.getRegionHeight());
        }
    }

    @Override
    public void act(float delta) {
        //super.act(delta);
        lifeScale.setRegionWidth((int) getWidth());
    }

    @Override
    public void setWidth(float width) {
        //super.setWidth(width);
        currentWidth = (int)width;
    }

    @Override
    public float getWidth() {
        return currentWidth;
    }

    @Override
    public void setPosition(float x, float y) {
        //super.setPosition(x, y);
        this.x = x;
        this.y = y;
    }
}
