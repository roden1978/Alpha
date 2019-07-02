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
        this.position = new Vector2(-256.0f,Gdx.graphics.getHeight()/2 - 128);
        this.stateTime = 0.0f;
        this.speed = 0.1f;
        this.ovumAtlas = ovumAtlas;
        this.boundWidth = ovumAtlas.findRegion("ovum").getRegionWidth();
        this.boundHeight = ovumAtlas.findRegion("ovum").getRegionHeight();
        this.sprite = new TextureRegion(ovumAtlas.findRegion("ovum"));
        this.ovumBound = new Bounds(this.position.x, this.position.y, this.boundWidth, this.boundHeight);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(this.sprite, this.position.x, this.position.y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.position.x += speed;
        this.ovumBound.update(this.position.x, this.position.y, this.boundWidth, this.boundHeight);
        this.stateTime += delta;
        if (this.position.x  >= 0)
            this.speed = 0.0f;
    }

    public void setPosition(Vector2 position){
        this.position = position;
    }
    public void setPosition(float x, float y){
        this.position.x = x;
        this.position.y = y;
    }
    public Vector2 getPosition(){
        return this.position;
    }
    public float getPositionX(){
        return this.position.x;
    }
    public float getPositionY(){
        return this.position.y;
    }

    public Bounds getBound(){
        return this.ovumBound;
    }

    public float getBoundWidth() {
        return this.boundWidth;
    }

    public float getBoundHeight() {
        return this.boundHeight;
    }
}
