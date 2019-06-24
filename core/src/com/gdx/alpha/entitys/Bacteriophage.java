package com.gdx.alpha.entitys;

import com.badlogic.gdx.graphics.Texture;
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
    private float speed;
   /* private String name_region;
    private static final int HEALTH = 0;
    private static final int WEAPON_AXE = 1;
    private static final int WEAPON_MACE = 2;
    private static final int WEAPON_STONE = 3;*/
    private Integer typeBacteriophage;

    public Bacteriophage(Vector2 position, float speed, TextureAtlas bacteriophageAtlas, String name_region, Integer type) {
        //super(position, speed);
        //this.bacteriophageAtlas = bacteriophageAtlas;
        //this.name_region = name_region;
        this.position = position;
        this.speed = speed;
        bacAnimation = new Animation<TextureRegion>(1/30f, bacteriophageAtlas.getRegions());
        boundWidth = bacteriophageAtlas.findRegion(name_region).getRegionWidth();
        boundHeight = bacteriophageAtlas.findRegion(name_region).getRegionHeight();
        bacBound = new Bounds(position.x, position.y, boundWidth, boundHeight);
        stateTime = 0.0f;
        this.typeBacteriophage = type;
        setHealth(50);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        batch.draw(bacAnimation.getKeyFrame(stateTime, true), position.x, position.y);
    }

    @Override
    public void act(float delta) {
        //super.act(delta);
        bacBound.update(position.x, position.y,boundWidth,boundHeight);
        stateTime += delta;
    }

    @Override
    public Bounds getBound() {
        return bacBound;
    }

    public int getType() {return typeBacteriophage;}

    @Override
    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
