package com.gdx.alpha.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Ro|)e|\| on 01.01.2015.
 */
public class StartScreen extends ObjectScreen {
    private String NAME;
    private SpriteBatch spriteBatch;
    private Sprite sprite;
    private float lifeTime;
    private float time;
    private ScreenManager screenManager;

    public StartScreen(ScreenManager screenManager){
        Texture texture = new Texture(Gdx.files.internal("ui/redhairs_logo.png")); //Gdx.files.internal("ui/redhairs_logo.png")
        spriteBatch = new SpriteBatch();
        sprite = new Sprite(texture);
        NAME = "StartScreen";
        lifeTime = 1.0f;
        time = 0.0f;
        this.screenManager = screenManager;
        System.out.println("Screen width: " + Gdx.graphics.getWidth() + "Screen height" + Gdx.graphics.getHeight());
    }
    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        sprite.setX(Gdx.graphics.getWidth()/2.0f - sprite.getWidth()/2.0f);
        sprite.setY(Gdx.graphics.getHeight()/2.0f - sprite.getHeight()/2.0f);
        spriteBatch.begin();
            sprite.draw(spriteBatch);
        spriteBatch.end();
        time += delta;
    }

    @Override
    public void resize(int width, int height) {
       // super.resize(width, height);

    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        sprite.getTexture().dispose();
    }

    public float getLifeTime(){
        return lifeTime;
    }

    public float getTime(){
        return time;
    }

    public String getNAME(){
        return NAME;
    }
}
