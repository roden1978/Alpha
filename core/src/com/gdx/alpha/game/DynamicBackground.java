package com.gdx.alpha.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class DynamicBackground extends Actor {
    private Vector2 position;
    private Vector2 playerPosition;
    private Vector2 prePlayerPosition;
    private Vector2 origin;
    private TextureRegion textureRegion;
    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;
    private int regionWidth;
    private int regionHeight;
    private int partOfScreenWidth;
    private int partOfScreenHeight;
    private int preX1;
    private int preX2;
    private int preY1;
    private int preY2;

    public DynamicBackground(TextureAtlas textureAtlas, float playerPositionX, float playerPositionY) {
        textureRegion = textureAtlas.findRegion("db");
        regionWidth = textureRegion.getRegionWidth();
        regionHeight = textureRegion.getRegionHeight();
        position =  new Vector2(-20.0f, -20.0f);
        prePlayerPosition = playerPosition = new Vector2(playerPositionX, playerPositionY);
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();
        textureRegion.setRegionWidth(SCREEN_WIDTH + 40);
        textureRegion.setRegionHeight(SCREEN_HEIGHT + 40);
        origin = new Vector2(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
        partOfScreenWidth = SCREEN_WIDTH / 40;
        partOfScreenHeight = SCREEN_HEIGHT / 40;
        preX1 = 0;
        preY1 = 0;
        preX2 = 0;
        preY2 = 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        batch.draw(textureRegion, position.x, position.y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
       /* if (playerPosition.x > prePlayerPosition.x){

            position.x += (playerPosition.x - prePlayerPosition.x)/partOfScreenWidth;
            prePlayerPosition.x = playerPosition.x;
            //preX1 = (int)(playerPosition.x - origin.x)/partOfScreenWidth;
        }
        if (playerPosition.x < prePlayerPosition.x) {

            position.x -= (prePlayerPosition.x - playerPosition.x) / partOfScreenWidth;
            prePlayerPosition.x = playerPosition.x;
            //preX2 = (int)(playerPosition.x - origin.x)/partOfScreenWidth;
        }
        if(playerPosition.y > prePlayerPosition.y){
            //preY1 = 0;

            position.y += (playerPosition.y - prePlayerPosition.y)/partOfScreenHeight;
            prePlayerPosition.y = playerPosition.y;
            //preY1 = (int)(playerPosition.y - origin.y)/partOfScreenHeight;
        }

        if(playerPosition.y < prePlayerPosition.y){

            position.y -=(prePlayerPosition.y - playerPosition.y)/partOfScreenHeight;
            prePlayerPosition.y = playerPosition.y;
            //preY2 = (int)(origin.y - playerPosition.y)/partOfScreenHeight;
        }*/
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        playerPosition.x = x;
        playerPosition.y = y;
    }
}
