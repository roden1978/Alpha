package com.gdx.alpha.screens;

import java.util.List;

/**
 * Created by admin on 01.01.2015.
 */
public class ScreenManager {
    private static ObjectScreen currentScreen;
    private static ObjectScreen prevScreen;

    public ScreenManager(){
        currentScreen = null;
    }

    public ObjectScreen getCurrentScreen(){
        return currentScreen;
    }
    public void setCurrentScreen(ObjectScreen screen){
        if (currentScreen != null){
            currentScreen.dispose();
            currentScreen = screen;
        }else{
            this.currentScreen = screen;
        }

    }

    public ScreenManager getScreenManager(){
        return this;
    }
}
