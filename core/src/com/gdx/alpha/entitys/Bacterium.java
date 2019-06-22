package com.gdx.alpha.entitys;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Bacterium extends Microbe {

    private TextureAtlas bacteriumAtlas;
    private TextureRegion bacteriumRegion;
    private Vector2 position;
    private Bounds bacteriumBound;
    private Integer bacteriumType;


    public Bacterium (Vector2 position, TextureAtlas bacteriumAtlas, Integer bacteriumType){
        super(position, 0.0f);
        this.position = position;
        this.bacteriumAtlas = bacteriumAtlas;
        this.bacteriumType = bacteriumType;
        this.bacteriumBound = new Bounds(position.x, position.y,
                bacteriumAtlas.findRegion("b001").getRegionWidth()/2.0f,
                bacteriumAtlas.findRegion("b001").getRegionHeight()/2.0f);
        setPrice(100);
        setHealth(40);
        setBacteriumRegions();
    }

    @Override
    public Bounds getBound() {
        //return super.getBound();
        return bacteriumBound;
    }

    @Override
    public int getPrice() {
        return super.getPrice();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        batch.draw(bacteriumRegion,position.x, position.y);
    }


    private void setBacteriumRegions(){
        switch(bacteriumType){
            case 0:
                bacteriumRegion = bacteriumAtlas.findRegion("b001");
                break;
            case 1:
                bacteriumRegion = bacteriumAtlas.findRegion("b002");
                break;
            case 2:
                bacteriumRegion = bacteriumAtlas.findRegion("b003");
                break;
            case 3:
                bacteriumRegion = bacteriumAtlas.findRegion("b004");
                break;
            case 4:
                bacteriumRegion = bacteriumAtlas.findRegion("b005");
                break;
            case 5:
                bacteriumRegion = bacteriumAtlas.findRegion("b006");
                break;
            case 6:
                bacteriumRegion = bacteriumAtlas.findRegion("b007");
                break;
            case 7:
                bacteriumRegion = bacteriumAtlas.findRegion("b008");
                break;
            case 8:
                bacteriumRegion = bacteriumAtlas.findRegion("b009");
                break;
            case 9:
                bacteriumRegion = bacteriumAtlas.findRegion("b010");
                break;
        }
    }

    @Override
    public void setPosition(Vector2 position) {
        //super.setPosition(position);
        this.position = position;
    }

    @Override
    public Vector2 getPosition() {
        //return super.getPosition();
        return this.position;
    }
}//end of class
