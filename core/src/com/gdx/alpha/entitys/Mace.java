package com.gdx.alpha.entitys;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class Mace extends Weapon {

    //private Vector2 position;
    //private Vector2 direction;
    private float speed;
    //private int health;
    private Float degree;
    private Bounds maceBound;
    private float interval;
    //private TextureAtlas textureAtlas;

    public Mace(Vector2 position) {
        this.position = position;
        this.speed = 15.0f;
        health = 61;
        //this.health = 61;
        this.degree = 0.0f;
        this.interval = 0.25f;
        this.maceBound = new Bounds(position.x, position.y,
                super.weaponTextureAtlas.findRegion("mace").getRegionWidth(),
                super.weaponTextureAtlas.findRegion("mace").getRegionHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        batch.draw(super.weaponTextureAtlas.findRegion("mace"),this.position.x, this.position.y,
                super.weaponTextureAtlas.findRegion("mace").getRegionWidth()/2.00f,
                super.weaponTextureAtlas.findRegion("mace").getRegionHeight()/2.00f,
                super.weaponTextureAtlas.findRegion("mace").getRegionWidth(),
                super.weaponTextureAtlas.findRegion("mace").getRegionHeight(),
                1.0f,1.0f,degree);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.position.x -= speed;
        if (degree > 359.0f)
            degree = 0.0f;
        degree +=30.0f;
        maceBound.update(this.position.x, this.position.y,
                super.weaponTextureAtlas.findRegion("mace").getRegionWidth(),
                super.weaponTextureAtlas.findRegion("mace").getRegionHeight());
    }


   /* public void setPosition(Vector2 position){
        position = position;
    }*/

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
        return maceBound;
    }

    /*@Override
    public int getHealth() {
        return health;
    }*/

    public float getInterval(){return interval;}
}
