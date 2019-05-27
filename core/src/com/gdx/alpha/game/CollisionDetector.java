package com.gdx.alpha.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gdx.alpha.effects.HitParticleEffect;
import com.gdx.alpha.entitys.Axe;
import com.gdx.alpha.entitys.Bacteriophage;
import com.gdx.alpha.entitys.Microbe;
import com.gdx.alpha.entitys.ScoreCloud;
import com.gdx.alpha.entitys.Sperm;
import com.gdx.alpha.entitys.VirusBullet;

/**
 * Created by Admin on 04.02.15.
 */
public class CollisionDetector {

    private GameManager gameManager;
    private InteractionManager interactionManager;
    private Microbe removedMicrobe;
    private VirusBullet removedBullet;
    private Axe removedAxe;
    private Sperm removedSperm;
    private Float removedIndex;
    private Bacteriophage bacRemoved;

    private HitParticleEffect hitParticleEffect;
    private ScoreCloud scoreCloud;
    private Bacteriophage bacteriophage;

    private int deleteAxe;
    private int deleteMicrobe;
    private int deleteSperm;
    private int deleteBac;
    private boolean overlap;

    private Rectangle playerRect;
    private Rectangle enemyRect;
    private Rectangle bulletRect;
    private Rectangle axeRect;
    private Rectangle spermRect;
    private Rectangle bacRect;
    private Rectangle ovumRect;

    public CollisionDetector(GameManager gameManager, InteractionManager interactionManager) {
        this.gameManager = gameManager;
        this.interactionManager = interactionManager;
        playerRect = new Rectangle();
        enemyRect = new Rectangle();
        bulletRect = new Rectangle();
        axeRect = new Rectangle();
        spermRect = new Rectangle();
        ovumRect = new Rectangle();

        removedMicrobe = null;
        removedBullet = null;
        removedAxe = null;
        removedSperm = null;
        removedIndex = null;
        bacRemoved = null;

        deleteAxe = -1;
        deleteMicrobe = -1;
        deleteSperm = -1;
        deleteBac = -1;

        overlap = false;
        System.out.println("CollisionDetector create");
    }

    public void detectPlayerCollisions() {
        //обработка столкновений игрока с врагами
        if (gameManager.enemies.size > 0) {
            for (int i = 0; i < gameManager.enemies.size; i++) {
                if (gameManager.player != null && gameManager.enemies.get(i) != null) {
                    playerRect = gameManager.player.getBound().getBox();
                    enemyRect = gameManager.enemies.get(i).getBound().getBox();
                    if (playerRect.overlaps(enemyRect) || playerRect.contains(enemyRect)) {

                        //Создаем эффект взрыва от столкновения игрока с врагом
                        interactionManager.createParticleEffectBlow(i);
                        //Изменение уровня жизни игрока от столкновения с врагом
                        interactionManager.changePlayerHealthEnemys(i);
                        //Создаем облако очков от столкновения игрока с врагом
                        interactionManager.createScoreCloudToPlayer(i);

                        gameManager.enemies.get(i).remove();
                        removedMicrobe = gameManager.enemies.removeIndex(i);
                        removedMicrobe = null;
                    }
                }
            }
        }
        //обработка столкновений игрока с пулями
        if (gameManager.bullets.size > 0) {
            for (int i = 0; i < gameManager.bullets.size; i++) {
                if (gameManager.player != null && gameManager.bullets.get(i) != null) {
                    playerRect = gameManager.player.getBound().getBox();
                    bulletRect = gameManager.bullets.get(i).getBound().getBox();
                    if (playerRect.overlaps(bulletRect) || playerRect.contains(bulletRect)) {
                      //Создаем эффект взрыва от столкновения игрока с пулями
                       interactionManager.createParticleEffectBlowSmall(i);
                       //Изменяем уровнь жизьни игрока от столкновения с пулями
                       interactionManager.changePlayerHealthBullets(i);
                       //Создаем облако очков от столкновения игрока с пулями
                       interactionManager.createScoreCloudToBullets(i);
                       gameManager.bullets.get(i).remove();
                       removedBullet = gameManager.bullets.removeIndex(i);
                       removedBullet = null;
                    }
                }
            }
        }
        //обработка столкновений игрока с бактериофагами
        if (gameManager.bacteriophages.size > 0) {
            for (int i = 0; i < gameManager.bacteriophages.size; i++) {
                if (gameManager.player != null && gameManager.bacteriophages.get(i) != null) {
                    playerRect = gameManager.player.getBound().getBox();
                    bacRect = gameManager.bacteriophages.get(i).getBound().getBox();
                    if (playerRect.contains(bacRect) || playerRect.overlaps(bacRect)) {
                        gameManager.player.setHealth(gameManager.player.getHealth() + gameManager.bacteriophages.get(i).health);
                        if (gameManager.player.getHealth() > 300)
                            gameManager.player.setHealth(300);
                        gameManager.bacteriophages.get(i).remove();
                        bacRemoved = gameManager.bacteriophages.removeIndex(i);
                        bacRemoved = null;
                    }
                }
            }
        }
    }


    public void detectBacteriophageEnemyCollisions(){
        if (gameManager.bacteriophages.size > 0) {
            for (int i = 0; i < gameManager.bacteriophages.size; i++) {
                if (gameManager.enemies.size > 0) {
                    for (int j = 0; j < gameManager.enemies.size; j++) {
                        if (gameManager.bacteriophages.get(i) != null && gameManager.enemies.get(j) != null) {
                            bacRect = gameManager.bacteriophages.get(i).getBound().getBox();
                            enemyRect = gameManager.enemies.get(j).getBound().getBox();
                            if (enemyRect.overlaps(bacRect) || enemyRect.contains(bacRect)) {
                                gameManager.enemies.get(j).remove();
                                gameManager.bacteriophages.get(i).remove();
                                deleteBac = i;
                                overlap = true;
                                hitParticleEffect = new HitParticleEffect(new ParticleEffect(gameManager.blow), 0.5f);
                                hitParticleEffect.setPositionEffect(gameManager.enemies.get(j).getPositionX() + enemyRect.getWidth()/2,
                                        gameManager.enemies.get(j).getPositionY() + enemyRect.getHeight()/2);
                                gameManager.hitParticleEffectArray.add(hitParticleEffect);
                                removedMicrobe = gameManager.enemies.removeIndex(j);
                                removedMicrobe = null;
                            }
                        }
                    }
                }

            }
        }
        if (overlap && gameManager.bacteriophages.size > 0 && deleteBac != -1) {
            bacRemoved = gameManager.bacteriophages.removeIndex(deleteBac);

            bacRemoved = null;
            overlap = false;
        }
        deleteBac = -1;

    }

//Обработка столкновений оружия с врагами
    public void detectWeaponEnemyCollisions() {
        if (gameManager.enemies.size > 0) {
            for (int i = 0; i < gameManager.enemies.size; i++) {
                if (gameManager.axes.size > 0) {
                    for (int j = 0; j < gameManager.axes.size; j++) {
                        if (gameManager.axes.get(j) != null && gameManager.enemies.get(i) != null) {
                            axeRect = gameManager.axes.get(j).getBounds().getBox();
                            enemyRect = gameManager.enemies.get(i).getBound().getBox();
                            //определение столкновения врага с топором
                            if (enemyRect.overlaps(axeRect) || enemyRect.contains(axeRect)) {
                                //выводим эффект в массив эффектов
                                hitParticleEffect = new HitParticleEffect(new ParticleEffect(gameManager.hit), 0.2f);
                                hitParticleEffect.setPositionEffect(gameManager.axes.get(j).getPositionX(),
                                        gameManager.axes.get(j).getPositionY() + axeRect.getHeight() / 2);
                                gameManager.hitParticleEffectArray.add(hitParticleEffect);
                                //System.out.println(gameManager.hitParticleEffectArray.size);
                                //уменьшаем здоровье врага на величину здоровья оружия
                                gameManager.enemies.get(i).changeHealth(gameManager.axes.get(j).health);
                                //удаляем оружие со сцены
                                gameManager.axes.get(j).remove();
                                removedAxe = gameManager.axes.removeIndex(j);
                                removedAxe = null;
                                //определение обнуления здоровья врага
                                if (gameManager.enemies.get(i).health < 0) {
                                    gameManager.enemies.get(i).remove();
                                    overlap = true;
                                    deleteMicrobe = i;
                                    hitParticleEffect = new HitParticleEffect(new ParticleEffect(gameManager.blow), 0.5f);
                                    hitParticleEffect.setPositionEffect(gameManager.enemies.get(i).getPositionX() + enemyRect.getWidth() / 2,
                                            gameManager.enemies.get(i).getPositionY() + enemyRect.getHeight() / 2);
                                    gameManager.hitParticleEffectArray.add(hitParticleEffect);
                                    gameManager.setScoresAmount(gameManager.getScoresAmount()+ gameManager.enemies.get(i).getPrice());
                                    gameManager.updateScoresAmount();
                                    scoreCloud = new ScoreCloud(new Vector2(gameManager.enemies.get(i).getPositionX() + enemyRect.getWidth() / 2,
                                            gameManager.enemies.get(i).getPositionY() + enemyRect.getHeight() / 2),
                                            gameManager.fontScoreCloudGreen, gameManager.enemies.get(i).getPrice(), true);
                                    gameManager.scoreCloudArray.add(scoreCloud);
                                    //Добавление бактериофага в игру
                                    if (interactionManager.randomizeBacteriophages(i) != null)
                                        gameManager.bacteriophages.add(interactionManager.randomizeBacteriophages(i));
                                  }
                            }
                        }
                    }
                }
            }
            if (overlap && gameManager.enemies.size > 0 && deleteMicrobe != -1) {
                removedMicrobe = gameManager.enemies.removeIndex(deleteMicrobe);
                removedMicrobe = null;
                overlap = false;
            }
            deleteMicrobe = -1;
        }
    }
    //Обработка столкновений оружия с пулями
    public void detectWeaponBulletCollision() {
        if (gameManager.axes.size > 0)
            for (int i = 0; i < gameManager.axes.size; i++) {
                if (gameManager.bullets.size > 0)
                    for (int j = 0; j < gameManager.bullets.size; j++) {
                        if (gameManager.axes.get(i) != null && gameManager.bullets.get(j) != null) {
                            axeRect = gameManager.axes.get(i).getBounds().getBox();
                            bulletRect = gameManager.bullets.get(j).getBound().getBox();
                        }
                        if (bulletRect.overlaps(axeRect) || bulletRect.contains(axeRect)) {
                            deleteAxe = i;
                            hitParticleEffect = new HitParticleEffect(new ParticleEffect(gameManager.blow_small), 0.5f);
                            hitParticleEffect.setPositionEffect(gameManager.bullets.get(j).getPositionX() + bulletRect.getWidth()/2,
                                    gameManager.bullets.get(j).getPositionY() - bulletRect.getHeight()/2);
                            gameManager.hitParticleEffectArray.add(hitParticleEffect);
                            gameManager.axes.get(i).remove();
                            gameManager.bullets.get(j).remove();
                            gameManager.setScoresAmount(gameManager.getScoresAmount() + gameManager.bullets.get(j).getPrice());
                            gameManager.updateScoresAmount();
                            scoreCloud = new ScoreCloud(new Vector2(gameManager.bullets.get(j).getPositionX() + bulletRect.getWidth()/2,
                                    gameManager.bullets.get(j).getPositionY() + bulletRect.getHeight()/2),
                                    gameManager.fontScoreCloudGreen, gameManager.bullets.get(j).getPrice(),true);
                            gameManager.scoreCloudArray.add(scoreCloud);
                            removedMicrobe = gameManager.bullets.removeIndex(j);
                            removedMicrobe = null;
                            overlap = true;
                        }
                    }
            }
        if (overlap && gameManager.axes.size > 0 && deleteAxe != -1) {
            removedAxe = gameManager.axes.removeIndex(deleteAxe);
            removedAxe = null;
            overlap = false;
        }
        deleteAxe = -1;
    }

    public void detectSpermCollisions() {
        for (int i = 0; i < gameManager.sperms.size; i++) {
            for (int j = 0; j < gameManager.enemies.size; j++) {
                if (gameManager.sperms.get(i) != null && gameManager.enemies.get(j) != null) {
                    spermRect = gameManager.sperms.get(i).getBound().getBox();
                    enemyRect = gameManager.enemies.get(j).getBound().getBox();
                    if (enemyRect.contains(spermRect) || enemyRect.overlaps(spermRect)) {
                        gameManager.sperms.get(i).remove();
                        gameManager.setSpermAmount(gameManager.getSpermAmount() - 1);
                        gameManager.updateSpermAmount();
                        hitParticleEffect = new HitParticleEffect(new ParticleEffect(gameManager.blow), 0.5f);
                        hitParticleEffect.setPositionEffect(gameManager.enemies.get(j).getPositionX() + enemyRect.getWidth()/2,
                                gameManager.enemies.get(j).getPositionY() - enemyRect.getHeight()/2);
                        gameManager.hitParticleEffectArray.add(hitParticleEffect);
                        scoreCloud = new ScoreCloud(new Vector2(gameManager.sperms.get(i).getPositionX() + spermRect.getWidth()/2,
                                gameManager.sperms.get(i).getPositionY() + spermRect.getHeight()/2),
                                gameManager.fontScoreCloudRed, 1,false);
                        gameManager.scoreCloudArray.add(scoreCloud);
                        gameManager.enemies.get(j).remove();
                        deleteSperm = i;
                        overlap = true;
                        removedMicrobe = gameManager.enemies.removeIndex(j);
                        removedMicrobe = null;
                    }
                }
            }
        }
        if (overlap && gameManager.sperms.size > 0 && deleteSperm != -1) {
            removedSperm = gameManager.sperms.removeIndex(deleteSperm);
            removedSperm = null;
        }
        deleteSperm = -1;
    }

    public void detectOvumSpermsCollisions(){
        if (gameManager.sperms.size > 0 && gameManager.getOvumEffectStart() == false) {
            for (int i = 0; i < gameManager.sperms.size; i++) {
                if (gameManager.ovum != null && gameManager.sperms.get(i) != null) {
                    ovumRect = gameManager.ovum.getBound().getBox();
                    spermRect = gameManager.sperms.get(i).getBound().getBox();
                    if (ovumRect.contains(spermRect) || ovumRect.overlaps(spermRect)) {
                        hitParticleEffect = new HitParticleEffect(new ParticleEffect(gameManager.ovum_effect), 5.0f);
                        hitParticleEffect.setPositionEffect(gameManager.sperms.get(i).getPositionX() + spermRect.getWidth() / 2,
                                gameManager.sperms.get(i).getPositionY() - spermRect.getHeight() / 2);
                        gameManager.hitParticleEffectArray.add(hitParticleEffect);
                        gameManager.setOvumEffectStart(true);

                    }
                }
            }
        }
    }

    public void detectLevelEnd(){
        //System.out.println("Delta: " + gameManager.getDeltaTimeParticleEffect());
        if (gameManager.getDeltaTimeParticleEffect() < 0.0f){
            gameManager.setOvumEffectStart(false);
            gameManager.setLevelEnd(true);
        }
    }
}//end fo class
