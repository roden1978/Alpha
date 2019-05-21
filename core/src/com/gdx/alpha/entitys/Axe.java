package com.gdx.alpha.entitys;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Created by admin on 27.01.2015.
 */
public class Axe extends Actor {

    private Vector2 position;
    private Vector2 direction;
    private float speed;
    public int health;
    private Animation animation;
    private TextureAtlas axeAtlas;
    private float stateTime;
    private Bounds axeBound;
    private float boundWidth;
    private float boundHeight;

    public Axe(Vector2 position, TextureAtlas axeAtlas){
        this.position = position;
        direction = new Vector2(0,0);
        speed = 10.0f;
        health = 41;
        stateTime = 0.0f;
        this.axeAtlas = axeAtlas;
        boundWidth = axeAtlas.findRegion("axe001").getRegionWidth();
        boundHeight = axeAtlas.findRegion("axe001").getRegionHeight();
        animation = new Animation(1/36f, axeAtlas.getRegions());
        axeBound = new Bounds(position.x, position.y, boundWidth, boundHeight);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        position.x -= speed;
        axeBound.update(position.x, position.y, boundWidth, boundHeight);
        stateTime +=delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(animation.getKeyFrame(stateTime, true),position.x, position.y);
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

    public Vector2 getDirection() {
        return direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }
    public void setDirection(float x, float y){
        this.direction.x = x;
        this.direction.y = y;
    }
    public void setBounds(Bounds bounds){
        this.axeBound = bounds;
    }
    public Bounds getBounds(){
        return axeBound;
    }
}
