package com.gdx.alpha.entitys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by admin on 12.01.2015.
 */
public class BacteryA extends Actor {
    private Vector2 position;
    private Vector2 direction;
    private float velocity;
    private TextureAtlas bacteryAAtlas;
    private Animation<TextureRegion> sprites;
    private float stateTime;

    public BacteryA(Vector2 position, Vector2 direction, float velocity) {
        this.position = position;
        this.direction = direction;
        this.velocity = velocity;
        bacteryAAtlas = new TextureAtlas(Gdx.files.internal("bacterias/bacA.pack"));
        sprites = new Animation<TextureRegion>(1/2f,bacteryAAtlas.getRegions());
        stateTime = 0.0f;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(sprites.getKeyFrame(stateTime, true), position.x, position.y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (velocity == 0)
            velocity = 1.0f;
        position.x += velocity;
        stateTime += delta*10;

        if (position.x >= Gdx.graphics.getWidth() + bacteryAAtlas.findRegion("bacA001").getRegionWidth()){
            position.x = -bacteryAAtlas.findRegion("bacA001").getRegionWidth();
        }
    }

    public void setPosition(Vector2 position){
        this.position.x = position.x;
        this.position.y = position.y;
    }
    public Vector2 getPosition(){
        return position;
    }
    public int getTextureRegionWidth (){
        return bacteryAAtlas.findRegion("bacA001").getRegionWidth();
    }
}
