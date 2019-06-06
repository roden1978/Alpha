package com.gdx.alpha.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.IOException;

/**
 * Created by Ro|)e|\| on 01.01.2015.
 */
public class ScreenManager {
    private static ObjectScreen currentScreen;
    private String line;

    public ScreenManager(){
        currentScreen = null;
        try {
            loadLevelParamFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObjectScreen getCurrentScreen(){
        return currentScreen;
    }
    public void setCurrentScreen(ObjectScreen screen){
        if (currentScreen != null){
            currentScreen.dispose();
            currentScreen = screen;
        }else{
            currentScreen = screen;
        }

    }

    /*public ScreenManager getScreenManager(){
        return this;
    }*/
    public void loadLevelParamFile() throws IOException{
        FileHandle handle = Gdx.files.internal("levelparam.txt");
        line = handle.readString();
    }
    public String getLevelParams(){return line;}
}//end of class
