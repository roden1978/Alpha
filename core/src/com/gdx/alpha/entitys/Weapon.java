package com.gdx.alpha.entitys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Weapon extends Actor {

    protected Vector2 position;
    protected int health;
    private Bounds weaponBound;
    TextureAtlas weaponTextureAtlas;
    private float interval;

    Weapon() {
        //super();
        position = new Vector2(0.0f, 0.0f);
        //Vector2 direction = new Vector2(0.0f, 0.0f);
        //float speed = 0.0f;
        health = 0;
        interval = 0.3f;
        weaponBound = new Bounds(0.0f, 0.0f, 0.0f, 0.0f);
        weaponTextureAtlas = new TextureAtlas(Gdx.files.internal("weapon/weapon.pack"));
    }

   /* @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }*/

    public Vector2 getPosition(){return position;}
    public float getPositionX(){
        return position.x;
    }
    public float getPositionY(){
        return position.y;
    }
    public Bounds getBounds(){
        return weaponBound;
    }
    //public TextureAtlas getWeaponTextureAtlas() {return weaponTextureAtlas;}
    public int getHealth(){return health;}

   /* public void setIntervalDefault(float interval) {
        this.interval = interval;
    }*/
    public float getInterval(){return interval;}

    public void setHealth(int health) {
        this.health = health;
    }
}
