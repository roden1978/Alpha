package com.gdx.alpha.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.gdx.alpha.screens.MenuScreen;
import com.gdx.alpha.screens.ScreenManager;
import com.gdx.alpha.screens.StartScreen;


public class Alpha extends Game {

    private ScreenManager screenManager;
    private String current_screen_name;
    //private static final float koeff =
	@Override
	public void create () {
        screenManager = new ScreenManager();
        screenManager.setCurrentScreen(new StartScreen(screenManager));
        current_screen_name = screenManager.getCurrentScreen().getNAME();
        if (screenManager.getCurrentScreen() != null){
            setScreen(screenManager.getCurrentScreen());
        }
	}

    @Override
    public void dispose() {
        if (screenManager.getCurrentScreen() != null){
            screenManager.getCurrentScreen().dispose();
        }
    }

    @Override
	public void render () {
        super.render();
        if (screenManager.getCurrentScreen().getLifeTime() != 0.0f){
            if (screenManager.getCurrentScreen().getTime() >= screenManager.getCurrentScreen().getLifeTime()){
                if (Gdx.graphics.getWidth() / 1280 >= 1){
                    screenManager.setCurrentScreen(new MenuScreen(screenManager));
                    current_screen_name = screenManager.getCurrentScreen().getNAME();
                    setScreen(screenManager.getCurrentScreen());
                }
                else
                    Gdx.app.exit();
            }
        }
        updateScreen();
	}

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    private void updateScreen() {
        if (current_screen_name != screenManager.getCurrentScreen().getNAME()) {
            if (screenManager.getCurrentScreen() != null) {
                current_screen_name = screenManager.getCurrentScreen().getNAME();
                setScreen(screenManager.getCurrentScreen());
            }
        }
    }
}
