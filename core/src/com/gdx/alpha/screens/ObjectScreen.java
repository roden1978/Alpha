package com.gdx.alpha.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by admin on 01.01.2015.
 */
public class ObjectScreen implements Screen {

    private String NAME;
    private float lifeTime;
    private float time;
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // | GL20.GL_DEPTH_BUFFER_BIT
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
    public float getLifeTime(){
        return lifeTime;
    }

    public float getTime(){
        return time;
    }

    public String getNAME() {
        return NAME;
    }

    public Stage getGameStage(){
        return null;
    }

    public void setGameState(int state){}

    public void setStringLevelParamsSave(Boolean stringLevelParamsSave) {}
}
