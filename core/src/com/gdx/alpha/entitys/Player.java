package com.gdx.alpha.entitys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by admin on 20.01.2015.
 */
public class Player extends Actor {

    private Vector2 position;
    private Vector2 direction;
    private float velocity;
    private int health;
    private int maxHealth;
    private TextureAtlas cavemanAtlas;
    private TextureAtlas lifeScaleAtlas;
    private Animation cavemanAnimation;
    private float stateTime;
    private Bounds playerBound;
    private LifeScale lifeScale;
    private float boundWidth;
    private float boundHeight;
    private boolean throwing;

    public Player(Vector2 position, Vector2 direction, float velocity){
        this.position = position;
        this.direction = direction;
        this.velocity = velocity;
        throwing = true;
        cavemanAtlas = new TextureAtlas(Gdx.files.internal("caveman/caveman.pack"));
        lifeScaleAtlas = new TextureAtlas(Gdx.files.internal("uiGame/lifescale.pack"));
        boundWidth = cavemanAtlas.findRegion("right_shot_game001").getRegionWidth();
        boundHeight = cavemanAtlas.findRegion("right_shot_game001").getRegionHeight();
        lifeScale = new LifeScale(lifeScaleAtlas,position.x, position.y,lifeScaleAtlas.findRegion("green").getRegionWidth());
        playerBound = new Bounds(position.x, position.y, boundWidth, boundHeight);
        cavemanAnimation = new Animation(1/30f,cavemanAtlas.getRegions());
        stateTime = 0.0f;
        health = maxHealth = 200;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        batch.draw(cavemanAnimation.getKeyFrame(stateTime, true), position.x, position.y);
        lifeScale.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
       // super.act(delta);
        playerBound.update(position.x, position.y, boundWidth, boundHeight);
        lifeScale.setWidth(lifeScaleAtlas.findRegion("green").getRegionWidth()*health/maxHealth);
        lifeScale.setPosition(position.x + boundWidth/2 - lifeScaleAtlas.findRegion("green").getRegionWidth()/2,
                position.y + boundHeight);
        if (throwing)
            stateTime +=delta;
        else
            stateTime=0.0f;
    }

    public void setPosition(Vector2 position){
        this.position = position;
    }

    public Vector2 getPosition(){
        return this.position;
    }

    public void setPosition(float x, float y){
        this.position.x = x;
        this.position.y = y;
    }

    public float getPositionX(){
        return position.x;
    }

    public float getPositionY(){
        return position.y;
    }

    public float getSpriteRegionWidth(){
        return boundWidth;
    }

    public float getSpriteRegionHeight(){
        return boundHeight;
    }

    public Bounds getBound(){
        return playerBound;
    }

    public void setHealth(int health){
        this.health = health;
    }
    public void changeHealth(int changeValue){
        this.health -= changeValue;
    }
    public int getHealth(){
        return health;
    }
    public void setThrowing(boolean throwing){
        this.throwing = throwing;
    }
}
