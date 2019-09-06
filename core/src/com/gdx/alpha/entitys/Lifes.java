package com.gdx.alpha.entitys;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Lifes extends Actor {
    private TextureAtlas textureAtlas;
    private TextureRegion lifes_region;
    private Integer life_count;
    private Float x;
    private Float y;

    public Lifes(TextureAtlas textureAtlas, Integer life_count, Float x, Float y) {
        this.textureAtlas = textureAtlas;
        this.lifes_region = new TextureRegion(textureAtlas.findRegion("caveman_life03"));
        this.life_count = life_count;
        this.x = x;
        this.y = y;
    }

    public void setLife_count(Integer life_count){
        this.life_count = life_count;
   }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        switch (life_count){
            case 6:
                lifes_region.setRegion(textureAtlas.findRegion("caveman_life06"));
                break;
            case 5:
                lifes_region.setRegion(textureAtlas.findRegion("caveman_life05"));
                break;
            case 4:
                lifes_region.setRegion(textureAtlas.findRegion("caveman_life04"));
                break;
            case 3:
                lifes_region.setRegion(textureAtlas.findRegion("caveman_life03"));
                break;
            case 2:
                lifes_region.setRegion(textureAtlas.findRegion("caveman_life02"));
                break;
            case 1:
                lifes_region.setRegion(textureAtlas.findRegion("caveman_life01"));
                break;
        }
        if (life_count > 0)
            batch.draw(lifes_region,x,y);
    }

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

}
