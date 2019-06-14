package com.gdx.alpha.entitys;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class Cudgel extends Weapon {
    private float speed; // Скорость движения оружия
    private Float degree; // Угол вращения оружия
    private Bounds cudgelBound; // Область обработки столкновения оружия (bounding box)
    private float interval; //Интервал выстрела

    public Cudgel(Vector2 position) {
        this.position = position;
        this.speed = 25.0f;
        health = 200;
        this.degree = 0.0f;
        this.interval = 0.15f;
        this.cudgelBound = new Bounds(position.x, position.y,
                super.weaponTextureAtlas.findRegion("cudgel").getRegionWidth(),
                super.weaponTextureAtlas.findRegion("cudgel").getRegionHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(super.weaponTextureAtlas.findRegion("cudgel"),this.position.x, this.position.y,
                super.weaponTextureAtlas.findRegion("cudgel").getRegionWidth()/2.00f,
                super.weaponTextureAtlas.findRegion("cudgel").getRegionHeight()/2.00f,
                super.weaponTextureAtlas.findRegion("cudgel").getRegionWidth(),
                super.weaponTextureAtlas.findRegion("cudgel").getRegionHeight(),
                1.0f,1.0f,degree);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.position.x -= speed;
        if (degree > 359.0f)
            degree = 0.0f;
        degree +=30.0f;
        cudgelBound.update(this.position.x, this.position.y,
                super.weaponTextureAtlas.findRegion("cudgel").getRegionWidth(),
                super.weaponTextureAtlas.findRegion("cudgel").getRegionHeight());
    }

    @Override
    public void setPosition(float x, float y){
        position.x = x;
        position.y = y;
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public float getPositionX() {
        return position.x;
    }

    @Override
    public float getPositionY() {
        return position.y;
    }

    @Override
    public Bounds getBounds() {
        return cudgelBound;
    }

    public float getInterval(){return interval;}
}
