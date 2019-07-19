package com.gdx.alpha.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {


    private Sound blowEnemySound;
    private Sound hitPlayerSound;
    private Sound hitSpermSound;
    private Sound bornBacteriophageSound;
    private Sound enemyShootSound;
    private Sound bacteriumDeathSound;
    private Sound createBonusItemSound;
    private Sound bonusCoinSound;
    private Sound bonusSkullSound;
    private Music backgroundGameMusic;
    private Sound throwAxeSound;
    private Sound bacteriophageCatchSound;
    private Sound blowCondomSound;
    private Sound newLifeSound;
    private Sound alarmClockSound;
    private Sound gameOverSound;
    private Sound bacteriophageDeathSound;


    AudioManager() {
        //звук уничтожения вируса
        blowEnemySound = Gdx.audio.newSound(Gdx.files.internal("sounds/blowEnemy.mp3"));
        //фоновая музыка в игре
        backgroundGameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/backgroundGameMusic.mp3"));
        //звук выстрела вируса
        enemyShootSound =  Gdx.audio.newSound(Gdx.files.internal("sounds/enemyshoot.mp3"));
        //звук уничтожения бактерии
        bacteriumDeathSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bacteriumDeath.mp3"));
        //звук появления бонусного предмета
        createBonusItemSound = Gdx.audio.newSound(Gdx.files.internal("sounds/createBonusItem.mp3"));
        //звук подбора бонусной монеты
        bonusCoinSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bonusCoin.mp3"));
        //звук подбора черепа
        bonusSkullSound = Gdx.audio.newSound(Gdx.files.internal("sounds/usingSkull.mp3"));
        //взук бросания оружия(топора)
        throwAxeSound = Gdx.audio.newSound(Gdx.files.internal("sounds/axeThrow.mp3"));
        //Звук подбора бактериофага
        bacteriophageCatchSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bacterCatchSound.mp3"));
        //Звук сдувания кондома
        blowCondomSound = Gdx.audio.newSound(Gdx.files.internal("sounds/blowCondom.mp3"));
        //Звук подбора жизни
        newLifeSound = Gdx.audio.newSound(Gdx.files.internal("sounds/newLifeSound.mp3"));
        //Звук конца игры
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/gameOverSound.mp3"));
        //Звук будильника
        alarmClockSound = Gdx.audio.newSound(Gdx.files.internal("sounds/alarmClockSound.mp3"));
        //звук уничтожения сперматозоида
        bacteriophageDeathSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bacterEnemyHit.mp3"));
        //звук уничтожения сперм
        hitSpermSound = Gdx.audio.newSound(Gdx.files.internal("sounds/blowSperm.mp3"));
        //Звук создания бактериофага
        bornBacteriophageSound = Gdx.audio.newSound(Gdx.files.internal("sounds/createBacteriophage.mp3"));
    }

    Sound getBlowEnemySound() {
        return blowEnemySound;
    }
    public Music getBackgroundGameMusic(){return backgroundGameMusic;}
    Sound getEnemyShootSound() {return enemyShootSound;}
    Sound getBacteriumDeathSound() {return bacteriumDeathSound;}
    Sound getCreateBonusItemSound() {return createBonusItemSound;}
    Sound getBonusCoinSound() {return bonusCoinSound;}
    Sound getBonusSkullSound() {return bonusSkullSound;}
    Sound getThrowAxeSound() {return throwAxeSound;}
    Sound getBacteriophageCatchSound() {return bacteriophageCatchSound;}
    Sound getBlowCondomSound(){return blowCondomSound;}
    Sound getNewLifeSound(){return newLifeSound;}
    Sound getGameOverSound() {return gameOverSound;}
    Sound getAlarmClockSound() {return alarmClockSound;}
    Sound getBacteriophageDeathSound() {return bacteriophageDeathSound;}
    Sound getHitSpermSound() {return hitSpermSound;}
    Sound getBornBacteriophageSound() {return bornBacteriophageSound;}
}
