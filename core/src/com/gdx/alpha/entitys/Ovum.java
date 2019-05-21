package com.gdx.alpha.entitys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Admin on 29.01.15.
 */
public class Ovum extends Actor{
    private TextureAtlas ovumAtlas;
    private Vector2 position;
    private TextureRegion sprite;
    private Bounds ovumBound;
    private float stateTime;
    private float speed;
    private float boundWidth;
    private float boundHeight;

    public Ovum(TextureAtlas ovumAtlas){
        position = new Vector2(-256.0f,Gdx.graphics.getHeight()/2 - 128);
        stateTime = 0.0f;
        speed = 0.1f;
        this.ovumAtlas = ovumAtlas;
        boundWidth = ovumAtlas.findRegion("ovum").getRegionWidth();
        boundHeight = ovumAtlas.findRegion("ovum").getRegionHeight();
        sprite = new TextureRegion(ovumAtlas.findRegion("ovum"));
        ovumBound = new Bounds(position.x, position.y, boundWidth, boundHeight);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(sprite,position.x,position.y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        position.x += speed;
        ovumBound.update(position.x, position.y, boundWidth, boundHeight);
        stateTime += delta;
        if (position.x  >= 0)
            speed = 0.0f;
    }

    public void setPosition(Vector2 position){
        this.position = position;
    }
    public void setPosition(float x, float y){
        this.position.x = x;
        this.position.y = y;
    }
    public Vector2 getPosition(){
        return position;
    }
    public float getPositionX(){
        return position.x;
    }
    public float getPositionY(){
        return position.y;
    }

    public Bounds getBound(){
        return ovumBound;
    }
}
