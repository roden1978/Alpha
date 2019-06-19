package com.gdx.alpha.entitys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Класс "Сперматозоид"
 * Назначение класса создавать объект сперматозоида
 * отрисовывать анаимацию движения и двигать сперматозоид с заданной
 * скоростью в направлении от правой границы экрана к левой
 * Created by Ro|)e|\| on 11.01.2015.
 */
public class Sperm extends Actor {
    private TextureAtlas spermAtlas;
    private Vector2 position;
    //private Vector2 direction;
    private float speed;
    private Animation sprites;
    private float stateTime;
    private Bounds spermBound;
    private float boundWidth;
    private float boundHeight;

    public Sperm(Vector2 position, float speed, TextureAtlas spermAtlas){
        this.position = position;
        //this.direction = direction;
        this.speed = speed;
        stateTime = 0.0f;
        this.spermAtlas = spermAtlas;
        sprites = new Animation(1/30f,spermAtlas.getRegions());
        boundWidth = spermAtlas.findRegion("sperm001").getRegionWidth();
        boundHeight = spermAtlas.findRegion("sperm001").getRegionHeight();
        spermBound = new Bounds(position.x, position.y, boundWidth, boundHeight);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(sprites.getKeyFrame(stateTime,true),position.x,position.y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        position.x -= speed;
        stateTime +=delta;
        if (position.x  <= -spermAtlas.findRegion("sperm001").getRegionWidth())
            position.x = Gdx.graphics.getWidth();
        spermBound.update(position.x, position.y, boundWidth, boundHeight);
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

   /* public Vector2 getDirection() {
        return direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }
    public void setDirection(float x, float y){
        this.direction.x = x;
        this.direction.y = y;
    }*/

    public Bounds getBound(){
        return spermBound;
    }
}
