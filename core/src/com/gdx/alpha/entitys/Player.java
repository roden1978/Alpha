package com.gdx.alpha.entitys;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Ro|)e|\| on 20.01.2015.
 */
public class Player extends Actor {

    private Vector2 position;
    private float health;
    private float maxHealth;
    private float defaultHealth;
    private TextureAtlas cavemanAtlas;
    private TextureAtlas lifeScaleAtlas;
    private TextureAtlas lifeCountAtlas;
    private TextureAtlas newlifeTextureAtlas;
    private Animation cavemanAnimation;
    private Animation caveman_newlifeAnimation;
    private float stateTime;
    private float stateTimeNewLife;
    private Bounds playerBound;
    private LifeScale lifeScale;
    private Lifes lifes;
    private Integer lifeCount;
    private float boundWidth;
    private float boundHeight;
    private float frameDuration;
    private boolean throwing;
    private Boolean newlife;
    private Boolean stateTimeCheck;



    public Player(Vector2 position, TextureAtlas cavemanAtlas, TextureAtlas lifeScaleAtlas,
                  TextureAtlas lifeCountAtlas, TextureAtlas newlifeTextureAtlas){
        this.position = position;
        this.stateTime = 0.0f;
        this.stateTimeNewLife =0.0f;
        this.health = this.maxHealth = this.defaultHealth = 200.0f;
        this.lifeCount = 3;
        this.throwing = true;
        this.newlife = false;
        this.stateTimeCheck = false;
        this.cavemanAtlas = cavemanAtlas;
        this.lifeScaleAtlas = lifeScaleAtlas;
        this.lifeCountAtlas = lifeCountAtlas;
        this.newlifeTextureAtlas = newlifeTextureAtlas;
        this.frameDuration = 1/30f;
        boundWidth = cavemanAtlas.findRegion("right_shot_game001").getRegionWidth();
        boundHeight = cavemanAtlas.findRegion("right_shot_game001").getRegionHeight();
        lifeScale = new LifeScale(lifeScaleAtlas,position.x, position.y,lifeScaleAtlas.findRegion("green").getRegionWidth());
        lifes = new Lifes(lifeCountAtlas, lifeCount, position.x + boundWidth, position.y);
        playerBound = new Bounds(position.x, position.y, boundWidth, boundHeight);
        cavemanAnimation = new Animation(frameDuration,cavemanAtlas.getRegions());
        caveman_newlifeAnimation = new Animation(frameDuration, newlifeTextureAtlas.getRegions());
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        //if (throwing) //если бросание и не новая жизнь - бросающий герой
        if (newlife)
            batch.draw(caveman_newlifeAnimation.getKeyFrame(stateTime, true), position.x, position.y);
        else
            batch.draw(cavemanAnimation.getKeyFrame(stateTime, true), position.x, position.y);
        /*if (!throwing && !newlife)//если не бросание и не новая жизнь - бросающий герой
            batch.draw(cavemanAnimation.getKeyFrame(stateTime, true), position.x, position.y);
        if (throwing && newlife)//если бросание и новая жизнь - новая жизнь
            batch.draw(caveman_newlifeAnimation.getKeyFrame(stateTime, false), position.x, position.y);*/

        lifeScale.draw(batch, parentAlpha);
        lifes.draw(batch,parentAlpha);
        if (stateTime > stateTimeNewLife + 1.0f)
            newlife = false;
        //throwing = true;
    }

    @Override
    public void act(float delta) {
       // super.act(delta);
        playerBound.update(position.x, position.y, boundWidth, boundHeight);
        lifeScale.setWidth(lifeScaleAtlas.findRegion("green").getRegionWidth()*health/maxHealth);
        lifeScale.setPosition(position.x + boundWidth/2.0f - lifeScaleAtlas.findRegion("green").getRegionWidth()/2.0f,
                position.y + boundHeight);
        lifes.setLife_count(lifeCount);
        lifes.setPosition(position.x + boundWidth,position.y);
        if (throwing || newlife)
            stateTime +=delta;
        else
            stateTime=0.0f;

        /*if(newlife)
            stateTime += delta;*/
        lifeControl();
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

    public void setHealth(float health){
        this.health = health;
    }

    public float getHealth(){
        return health;
    }
    public void setThrowing(boolean throwing){
        this.throwing = throwing;
    }
    public boolean getThrowing(){return this.throwing;}
    public void setLifeCount(Integer lifeCount){
        this.lifeCount = lifeCount;
    }
    public Integer getLifeCount(){
        return this.lifeCount;
    }
    public float getDefaultHealth() {
        return defaultHealth;
    }
    public Boolean getNewlife() {
        return newlife;
    }

    public void setNewlife(Boolean newlife) {
        this.newlife = newlife;
    }

    void TimeOut(float delta){
        stateTime +=delta;
    }

    public void lifeControl() {
        if (getHealth() <= 0) {
            setLifeCount(getLifeCount() - 1);
            setHealth(getDefaultHealth());
            newlife = true;
            stateTimeCheck = true;
            if (stateTimeCheck){
                stateTimeNewLife = stateTime;
                stateTimeCheck = false;
            }
        }
    }
}
