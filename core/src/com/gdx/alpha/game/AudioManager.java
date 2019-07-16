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
    private Sound enemyShootSound;
    private Sound bacteriumDeathSound;
    private Sound createBonusItemSound;
    private Sound bonusCoinSound;
    private Sound bonusSkullSound;
    private Music backgroundGameMusic;
    private Sound throwAxeSound;


    public AudioManager() {
        //звук уничтожения вируса
        blowEnemySound = Gdx.audio.newSound(Gdx.files.internal("sounds/blowEnemy.mp3"));
        //фоновая музыка в игре
        backgroundGameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/backgroundGameMusic.mp3"));
        //звук завершения уровня
        endLevelSound = Gdx.audio.newSound(Gdx.files.internal("sounds/levelComplete.mp3"));
        //звук выстрела вируса
        enemyShootSound =  Gdx.audio.newSound(Gdx.files.internal("sounds/enemyshoot.mp3"));
        //звук уничтожения бактерии
        bacteriumDeathSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bacteriumDeath.mp3"));
        //звук появления бонусного предмета
        createBonusItemSound = Gdx.audio.newSound(Gdx.files.internal("sounds/createBonusItem.mp3"));
        //звук подбора бонусной монеты
        bonusCoinSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bonusCoin.mp3"));
        //звук подбора черепа
        bonusSkullSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bonusSkull.mp3"));
        //взук бросания оружия(топора)
        throwAxeSound = Gdx.audio.newSound(Gdx.files.internal("sounds/axeThrow.mp3"));
    }

    public Sound getBlowEnemySound() {
        return blowEnemySound;
    }
    public Music getBackgroundGameMusic(){return backgroundGameMusic;}
    public Sound getEndLevelSound() {return endLevelSound;}
    public Sound getEnemyShootSound() {return enemyShootSound;}
    public Sound getBacteriumDeathSound() {return bacteriumDeathSound;}
    public Sound getCreateBonusItemSound() {return createBonusItemSound;}
    public Sound getBonusCoinSound() {return bonusCoinSound;}
    public Sound getBonusSkullSound() {return bonusSkullSound;}

    public Sound getThrowAxeSound() {
        return throwAxeSound;
    }
}
