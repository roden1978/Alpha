package com.gdx.alpha.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {


    private Sound throwMaceSound;
    private Sound throwStoneSound;
    private Sound hitEnemySound;
    private Sound blowEnemySound;
    private Sound hitPlayerSound;
    private Sound hitSpermSound;
    private Sound bornBacteriophageSound;
    private Sound beginLevelSound;
    private Sound endLevelSound;
    private Music backgroundGameMusic;


    public AudioManager() {
        blowEnemySound = Gdx.audio.newSound(Gdx.files.internal("sounds/blowEnemy.mp3"));
        backgroundGameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/backgroundGameMusic.mp3"));
    }

    public Sound getBlowEnemySound() {
        return blowEnemySound;
    }
    public Music getBackgroundGameMusic(){return backgroundGameMusic;}
}
