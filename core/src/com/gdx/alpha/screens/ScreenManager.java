package com.gdx.alpha.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

import java.io.IOException;

/**
 * Created by Ro|)e|\| on 01.01.2015.
 */
public class ScreenManager {
    private static ObjectScreen currentScreen;
    private String line;
    private String soundParam;
    private Music screenMusic;
    private Boolean onoff;

    public ScreenManager(){
        currentScreen = null;
        try {
            loadLevelParamFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            loadSoundParamFile();
            if (Integer.valueOf(soundParam.trim()) == 1){
                onoff = true;
            }
            else{
                onoff = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        screenMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/backgroundMenuMusic.mp3"));
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
    private void loadLevelParamFile() throws IOException{
        FileHandle handle = Gdx.files.internal("levelparam.txt");
        line = handle.readString();
    }

    private void loadSoundParamFile() throws IOException{
        FileHandle handle = Gdx.files.internal("gamesound.txt");
        soundParam = handle.readString();
    }

    String getLevelParams(){return line;}

    void setLevelParams(String line){this.line = line;}

    Music getScreenMusic() {
        return screenMusic;
    }

    public Boolean getOnoff() {
        return onoff;
    }

    void setOnoff(Boolean onoff) {
        this.onoff = onoff;
    }
}//end of class
