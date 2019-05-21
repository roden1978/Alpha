package com.gdx.alpha.entitys;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by admin on 01.03.2015.
 */
public class Bacteriophage extends Microbe {
    private TextureAtlas bacteriophageAtlas;
    private Animation bacAnimation;
    private Bounds bacBound;
    private float stateTime;
    private float boundWidth;
    private float boundHeight;
    private String name_region;
    private static final int TYPE_HEALTH = 0;
    private static final int TYPE_WEAPON = 1;

    public Bacteriophage(Vector2 position, float speed, TextureAtlas bacteriophageAtlas, String name_region) {
        super(position, speed);
        this.bacteriophageAtlas = bacteriophageAtlas;
        this.name_region = name_region;
        bacAnimation = new Animation(1/60f, this.bacteriophageAtlas.getRegions());
        boundWidth = this.bacteriophageAtlas.findRegion(name_region).getRegionWidth();
        boundHeight = this.bacteriophageAtlas.findRegion(name_region).getRegionHeight();
        bacBound = new Bounds(position.x, position.y, boundWidth, boundHeight);
        stateTime = 0.0f;
        setHealth(50);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(bacAnimation.getKeyFrame(stateTime, true), position.x, position.y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        bacBound.update(position.x, position.y,boundWidth,boundHeight);
        stateTime += delta;
    }

    @Override
    public Bounds getBound() {
        return bacBound;
    }

    public int getTypeWeapon() {return TYPE_WEAPON;}

    public int getTypeHealth() {return TYPE_HEALTH;}
}
