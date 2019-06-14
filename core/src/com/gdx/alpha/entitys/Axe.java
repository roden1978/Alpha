package com.gdx.alpha.entitys;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
/**
 * Created by Ro|)e|\| on 27.01.2015.
 */
public class Axe extends Weapon {

    //private Vector2 position;
    //private Vector2 direction;
    private float speed;
    //private int health;
    //private Animation animation;
    //private TextureAtlas axeAtlas;
    //private float stateTime;
    private Bounds axeBound;
    //private float boundWidth;
    //private float boundHeight;
    private Float degree;

    public Axe(Vector2 position){
        this.position = position;
        //direction = new Vector2(0,0);
        speed = 10.0f;
        health = 41;
        this.degree = 0.0f;
        //float interval = 0.25f;
        this.axeBound = new Bounds(position.x, position.y,
                super.weaponTextureAtlas.findRegion("axe").getRegionWidth(),
                super.weaponTextureAtlas.findRegion("axe").getRegionHeight());
    }

    @Override
    public void act(float delta) {
        //super.act(delta);
        position.x -= speed;
        if (degree > 359.0f)
            degree = 0.0f;
        degree +=30.0f;
        axeBound.update(this.position.x, this.position.y,
                super.weaponTextureAtlas.findRegion("axe").getRegionWidth(),
                super.weaponTextureAtlas.findRegion("axe").getRegionHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        batch.draw(super.weaponTextureAtlas.findRegion("axe"),this.position.x, this.position.y,
                super.weaponTextureAtlas.findRegion("axe").getRegionWidth()/2.00f,
                super.weaponTextureAtlas.findRegion("axe").getRegionHeight()/2.00f,
                super.weaponTextureAtlas.findRegion("axe").getRegionWidth(),
                super.weaponTextureAtlas.findRegion("axe").getRegionHeight(),
                1.0f,1.0f,degree);
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
    /*public void setBounds(Bounds bounds){
        this.axeBound = bounds;
    }*/
    public Bounds getBounds(){
        return axeBound;
    }
}
