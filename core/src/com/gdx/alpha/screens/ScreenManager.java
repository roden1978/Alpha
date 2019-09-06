package com.gdx.alpha.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Ro|)e|\| on 01.01.2015.
 */
public class ScreenManager {
    private static ObjectScreen currentScreen;
    private String line;
    //private String soundParam;
    private Music screenMusic;
    private Sound buttonClickSound;
    private Sound screenTapSound;
    private Sound levelCompleteSound;
    private Sound levelBeginSound;
    private Boolean soundOnOff;
    private int currentSelectedGirl;

    public ScreenManager(){
        soundOnOff = true;
        currentScreen = null;

        //loadLevelParamFile();

        loadSoundParamFile();

        screenMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/backgroundMenuMusic.mp3"));
        buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/buttonClick.mp3"));
        screenTapSound = Gdx.audio.newSound(Gdx.files.internal("sounds/screensTapSound.mp3"));
        levelCompleteSound = Gdx.audio.newSound(Gdx.files.internal("sounds/levelComplete.mp3"));
        levelBeginSound = Gdx.audio.newSound(Gdx.files.internal("sounds/levelBegin.mp3"));
        currentSelectedGirl = 0;
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
/*
    private void loadLevelParamFile(){
        FileHandle handle = Gdx.files.internal("levelparam.txt");
        line = handle.readString();
    }*/

    private void loadSoundParamFile() {
        Preferences pref =  Gdx.app.getPreferences("sound");
        soundOnOff = pref.getBoolean("sound");
    }

   /* String getLevelParams(){return line;}

    void setLevelParams(String line){this.line = line;}*/

    Music getScreenMusic() {
        return screenMusic;
    }

    Sound getButtonClickSound() {return buttonClickSound;}

    Sound getScreenTapSound(){return  screenTapSound;}

    Sound getLevelCompleteSound() {return  levelCompleteSound;}

    Sound getLevelBeginSound() {return  levelBeginSound;}

    public Boolean getOnoff() {
        return soundOnOff;
    }

    void setOnoff(Boolean onoff) {
        this.soundOnOff = onoff;
    }

    void setCurrentSelectedGirl(int index) {currentSelectedGirl = index;}

    int getCurrentSelectedGirl(){return currentSelectedGirl;}
}//end of class
