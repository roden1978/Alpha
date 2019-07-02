package com.gdx.alpha.entitys;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Ro|)e|\| on 01.03.2015.
 */
public class Bacteriophage extends Microbe {
    //private TextureAtlas bacteriophageAtlas;
    private Animation<TextureRegion> bacAnimation;
    private Bounds bacBound;
    private float stateTime;
    private float boundWidth;
    private float boundHeight;
    private Vector2 position;
    private Integer typeBacteriophage;

    public Bacteriophage(Vector2 position, float speed, TextureAtlas bacteriophageAtlas, String name_region, Integer type) {
        super(position, speed);
        //this.bacteriophageAtlas = bacteriophageAtlas;
        //this.name_region = name_region;
        this.position = position;
        //this.speed = speed;
        this.bacAnimation = new Animation<TextureRegion>(1/30f, bacteriophageAtlas.getRegions());
        this.boundWidth = bacteriophageAtlas.findRegion(name_region).getRegionWidth();
        this.boundHeight = bacteriophageAtlas.findRegion(name_region).getRegionHeight();
        this.bacBound = new Bounds(this.position.x, this.position.y, this.boundWidth, this.boundHeight);
        this.stateTime = 0.0f;
        this.typeBacteriophage = type;
        setHealth(50);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        batch.draw(this.bacAnimation.getKeyFrame(this.stateTime, true), this.position.x, this.position.y);
    }

    @Override
    public void act(float delta) {
        //super.act(delta);
        this.bacBound.update(this.position.x, this.position.y,this.boundWidth,this.boundHeight);
        this.stateTime += delta;
    }

    @Override
    public Bounds getBound() {
        return this.bacBound;
    }

    public int getType() {return this.typeBacteriophage;}

    @Override
    public void setPosition(Vector2 position) {
        this.position = position;
    }

  /*  @Override
    public Vector2 getPosition() {
        return this.getPosition();
    }*/
}
