package com.gdx.alpha.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class DynamicBackground extends Actor {
    private Vector2 position;
    private Vector2 playerPosition;
    private Vector2 origin;
    private TextureRegion textureRegion;
    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;

    DynamicBackground(Texture texture, float playerPositionX, float playerPositionY) {
        position =  new Vector2(-20.0f, -20.0f);
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();
        textureRegion = new TextureRegion(texture);
        playerPosition = new Vector2(playerPositionX, playerPositionY);
        origin = new Vector2(SCREEN_WIDTH / 2.0f, SCREEN_HEIGHT / 2.0f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        batch.draw(textureRegion, position.x, position.y,SCREEN_WIDTH + 60, SCREEN_HEIGHT + 60);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
       if (playerPosition.x > origin.x && position.x <= 0)
            position.x += 1;

        if (playerPosition.x < origin.x && position.x >= - 30)
            position.x -= 1;

        if(playerPosition.y > origin.y && position.y <= 0)
            position.y += 1;

        if(playerPosition.y < origin.y && position.y >= -30)
            position.y -=1;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        playerPosition.x = x;
        playerPosition.y = y;
    }
}
