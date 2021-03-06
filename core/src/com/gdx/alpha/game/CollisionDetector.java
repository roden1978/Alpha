package com.gdx.alpha.game;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Ro|)e|\| on 04.02.15.
 */
class CollisionDetector {

    private GameManager gameManager;
    private InteractionManager interactionManager;

    private int deleteAxe;
    private int deleteMicrobe;
    private int deleteSperm;
    private int deleteBac;
    private boolean overlap;

    private Rectangle playerRect;
    private Rectangle enemyRect;
    private Rectangle bulletRect;
    private Rectangle weaponRect;
    private Rectangle spermRect;
    private Rectangle bacRect;
    private Rectangle ovumRect;
    private Rectangle bonusRect;

    CollisionDetector(GameManager gameManager, InteractionManager interactionManager) {
        this.gameManager = gameManager;
        this.interactionManager = interactionManager;
        playerRect = new Rectangle();
        enemyRect = new Rectangle();
        bonusRect = new Rectangle();
        bulletRect = new Rectangle();
        weaponRect = new Rectangle();
        spermRect = new Rectangle();
        ovumRect = new Rectangle();

        deleteAxe = -1;
        deleteMicrobe = -1;
        deleteSperm = -1;
        deleteBac = -1;

        overlap = false;
        System.out.println("CollisionDetector create");
    }

    void detectPlayerEnemyCollisions() {
        //обработка столкновений игрока с врагами
        if (gameManager.getEnemies().size > 0) {
            for (int i = 0; i < gameManager.getEnemies().size; i++) {
                if (gameManager.getPlayer() != null && gameManager.getEnemies().get(i) != null) {
                    playerRect = gameManager.getPlayer().getBound().getBox();
                    enemyRect = gameManager.getEnemies().get(i).getBound().getBox();
                    if (playerRect.overlaps(enemyRect) || playerRect.contains(enemyRect)) {

                        //Создаем эффект взрыва от столкновения игрока с врагом
                        interactionManager.createParticleEffectBlow(i);
                        //Изменение уровня жизни игрока от столкновения с врагом
                        interactionManager.changePlayerHealthEnemies(i);
                        //Создаем облако очков от столкновения игрока с врагом
                        interactionManager.createScoreCloudToPlayer(i);

                        gameManager.getEnemies().get(i).remove();
                        gameManager.getEnemies().removeIndex(i);
                       /* removedMicrobe = gameManager.getEnemies().removeIndex(i);
                        removedMicrobe = null;*/
                    }
                }
            }
        }
    }
    void detectPlayerBulletsCollisions() {
        //обработка столкновений игрока с пулями
        if (gameManager.getBullets().size > 0) {
            for (int i = 0; i < gameManager.getBullets().size; i++) {
                if (gameManager.getPlayer() != null && gameManager.getBullets().get(i) != null) {
                    playerRect = gameManager.getPlayer().getBound().getBox();
                    bulletRect = gameManager.getBullets().get(i).getBound().getBox();
                    if (playerRect.overlaps(bulletRect) || playerRect.contains(bulletRect)) {
                        //Создаем эффект взрыва от столкновения игрока с пулями
                        interactionManager.createParticleEffectBlowSmall(i);
                        //Изменяем уровнь жизьни игрока от столкновения с пулями
                        interactionManager.changePlayerHealthBullets(i);
                        //Создаем облако очков от столкновения игрока с пулями
                        interactionManager.createScoreCloudToPlayerBullets(i);

                        gameManager.getBullets().get(i).remove();
                        gameManager.getBullets().removeIndex(i);
                       /* removedBullet = gameManager.getBullets().removeIndex(i);
                        removedBullet = null;*/
                    }
                }
            }
        }
    }
    void detectPlayerBacteriophgesCollisions(){
        //обработка столкновений игрока с бактериофагами
        if (gameManager.getBacteriophages().size > 0) {
            for (int i = 0; i < gameManager.getBacteriophages().size; i++) {
                if (gameManager.getPlayer() != null && gameManager.getBacteriophages().get(i) != null) {
                    playerRect = gameManager.getPlayer().getBound().getBox();
                    bacRect = gameManager.getBacteriophages().get(i).getBound().getBox();
                    if (playerRect.contains(bacRect) || playerRect.overlaps(bacRect)) {
                        interactionManager.useBacteriophage(i);
                        gameManager.getBacteriophages().get(i).remove();
                        gameManager.getBacteriophages().removeIndex(i);
                       /* System.out.println("Collision player bacter array size: " + gameManager.getBacteriophages().size);
                        bacRemoved = gameManager.getBacteriophages().removeIndex(i);
                        bacRemoved = null;*/
                    }
                }
            }
        }
    }


    void detectBacteriophageEnemyCollisions(){
        if (gameManager.getBacteriophages().size > 0) {
            for (int i = 0; i < gameManager.getBacteriophages().size; i++) {
                if (gameManager.getEnemies().size > 0) {
                    for (int j = 0; j < gameManager.getEnemies().size; j++) {
                        if (gameManager.getBacteriophages().get(i) != null && gameManager.getEnemies().get(j) != null) {
                            bacRect = gameManager.getBacteriophages().get(i).getBound().getBox();
                            enemyRect = gameManager.getEnemies().get(j).getBound().getBox();
                            if (enemyRect.overlaps(bacRect) || enemyRect.contains(bacRect)) {
                                gameManager.getEnemies().get(j).remove();
                                gameManager.getBacteriophages().get(i).remove();
                                System.out.println("Collision enemy bacter array size: " + gameManager.getBacteriophages().size);
                                deleteBac = i;
                                overlap = true;
                                interactionManager.createParticleEffectBlow(j);
                                //воспроизводим звук гибели бактериофага
                                interactionManager.playBacteriophageDeathSound();
                                gameManager.getEnemies().removeIndex(j);
                               /* removedMicrobe = gameManager.getEnemies().removeIndex(j);
                                removedMicrobe = null;*/
                            }
                        }
                    }
                }

            }
        }
        if (overlap && gameManager.getBacteriophages().size > 0 && deleteBac != -1) {
            gameManager.getBacteriophages().removeIndex(deleteBac);
            /*bacRemoved = gameManager.getBacteriophages().removeIndex(deleteBac);
            bacRemoved = null;*/
            overlap = false;
        }
        deleteBac = -1;

    }

//Обработка столкновений оружия с врагами
    void detectWeaponEnemyCollisions() {
        if (gameManager.getEnemies().size > 0) {
            for (int i = 0; i < gameManager.getEnemies().size; i++) {
                if (gameManager.getWeapons().size > 0) {
                    for (int j = 0; j < gameManager.getWeapons().size; j++) {
                        if (gameManager.getWeapons().get(j) != null && gameManager.getEnemies().get(i) != null) {
                            weaponRect = gameManager.getWeapons().get(j).getBounds().getBox();
                            enemyRect = gameManager.getEnemies().get(i).getBound().getBox();
                            //определение столкновения врага с топором
                            if (enemyRect.overlaps(weaponRect) || enemyRect.contains(weaponRect)) {
                                //выводим эффект в массив эффектов
                                interactionManager.createParticleEffectHit(j);
                                //уменьшаем здоровье врага на величину здоровья оружия
                                interactionManager.changeEnemiesHealth(i,j);
                                //удаляем оружие со сцены
                                gameManager.getWeapons().get(j).remove();
                                gameManager.getWeapons().removeIndex(j);
                                //определение обнуления здоровья врага
                                if (gameManager.getEnemies().get(i).getHealth() < 0) {
                                    //Создаем еффект взрыва
                                    interactionManager.createParticleEffectBlow(i);
                                    //Изменяем счетчик очков на велечину вознаграждения за поражение врага
                                    interactionManager.changeScoreAmountUIEnemiesKill(i);
                                    //Выводим облако очков поражения врага
                                    interactionManager.createScoreCloudToEnemies(i);
                                    //Проигрывание звука уничтожения врага
                                    interactionManager.playBlowEnemySound(i);
                                    //Добавление бактериофага в игру
                                    interactionManager.randomizeBacteriophage(gameManager.getEnemies().get(i));
                                    //Удаляем текущего врага из массива врагов
                                    gameManager.getEnemies().get(i).remove();
                                    overlap = true;
                                    deleteMicrobe = i;
                                  }
                            }
                        }
                    }
                }
            }
            if (overlap && gameManager.getEnemies().size > 0 && deleteMicrobe != -1) {
                gameManager.getEnemies().removeIndex(deleteMicrobe);
                overlap = false;
            }
            deleteMicrobe = -1;
        }
    }
    //Обработка столкновений оружия с пулями
    void detectWeaponBulletCollision() {
        if (gameManager.getWeapons().size > 0)
            for (int i = 0; i < gameManager.getWeapons().size; i++) {
                if (gameManager.getBullets().size > 0)
                    for (int j = 0; j < gameManager.getBullets().size; j++) {
                        if (gameManager.getWeapons().get(i) != null && gameManager.getBullets().get(j) != null) {
                            weaponRect = gameManager.getWeapons().get(i).getBounds().getBox();
                            bulletRect = gameManager.getBullets().get(j).getBound().getBox();
                        }
                        if (bulletRect.overlaps(weaponRect) || bulletRect.contains(weaponRect)) {
                            deleteAxe = i;
                            //Выводим эффект взрыва
                            interactionManager.createParticleEffectBlowSmall(j);
                            gameManager.getWeapons().get(i).remove();
                            gameManager.getBullets().get(j).remove();
                            //Изменяем счетчик очков на велечину вознаграждения за поражение пули врага
                            interactionManager.changeScoreAmountUIBulletsKill(j);
                           //Создание облака очков от столкновения пуль с оружием
                            interactionManager.createScoreCloudToBullets(j);
                            gameManager.getBullets().removeIndex(j);
                            /*removedMicrobe = gameManager.getBullets().removeIndex(j);
                            removedMicrobe = null;*/
                            overlap = true;
                        }
                    }
            }
        if (overlap && gameManager.getWeapons().size > 0 && deleteAxe != -1) {
            gameManager.getWeapons().removeIndex(deleteAxe);
            /*removedWeapon = gameManager.weapons.removeIndex(deleteAxe);
            removedWeapon = null;*/
            overlap = false;
        }
        deleteAxe = -1;
    }

    void detectSpermCollisions() {
        for (int i = 0; i < gameManager.getSperms().size; i++) {
            for (int j = 0; j < gameManager.getEnemies().size; j++) {
                if (gameManager.getSperms().get(i) != null && gameManager.getEnemies().get(j) != null) {
                    spermRect = gameManager.getSperms().get(i).getBound().getBox();
                    enemyRect = gameManager.getEnemies().get(j).getBound().getBox();
                    if (enemyRect.contains(spermRect) || enemyRect.overlaps(spermRect)) {
                        //Выводим эффект взрыва
                        interactionManager.createParticleEffectBlow(j);
                       //Создаем облако очков для сперм
                        interactionManager.createScoreCloudToSperms(i);
                        //Удаляем сперматозоида
                        gameManager.getSperms().get(i).remove();
                        //уменьшаем счетчик прерм
                        gameManager.setSpermAmount(gameManager.getSpermAmount() - 1);
                        //Обновляем счетчик сперм
                        gameManager.updateSpermAmount();
                        //Удаляем врага если он не босс
                        if (gameManager.getEnemies().get(j).getWeight() < 3){
                            gameManager.getEnemies().get(j).remove();
                            gameManager.getEnemies().removeIndex(j);
                        }
                        //воспроизводим звук гибели сперм
                        interactionManager.playHitSpermSound();

                        deleteSperm = i;
                        overlap = true;

                       /* removedMicrobe = gameManager.getEnemies().removeIndex(j);
                        removedMicrobe = null;*/
                    }
                }
            }
        }
        if (overlap && gameManager.getSperms().size > 0 && deleteSperm != -1) {
            gameManager.getSperms().removeIndex(deleteSperm);
        }
        deleteSperm = -1;
    }

    void detectOvumSpermsCollisions(){
        if (gameManager.getSperms().size > 0 && !gameManager.getOvumEffectStart()) {
            for (int i = 0; i < gameManager.getSperms().size; i++) {
                if (gameManager.getOvum() != null && gameManager.getSperms().get(i) != null) {
                    ovumRect = gameManager.getOvum().getBound().getBox();
                    spermRect = gameManager.getSperms().get(i).getBound().getBox();
                    if (ovumRect.contains(spermRect) || ovumRect.overlaps(spermRect)) {
                        interactionManager.createParticleEffectOvum();
                    }
                }
            }
        }
    }

    void detectLevelEnd(){
        //System.out.println("Delta: " + gameManager.getDeltaTimeParticleEffect());
        if (gameManager.getDeltaTimeParticleEffect() < 0.0f){
            gameManager.setOvumEffectStart(false);
            gameManager.setLevelEnd(true);
        }
    }

    void detectPlayerBonusItemsCollisions(){
        //обработка столкновений игрока с бонусными предметами
        if (gameManager.getBonusItemsArray().size > 0) {
            for (int i = 0; i < gameManager.getBonusItemsArray().size; i++) {
                if (gameManager.getPlayer() != null && gameManager.getBonusItemsArray().get(i) != null) {
                    playerRect = gameManager.getPlayer().getBound().getBox();
                    bonusRect = gameManager.getBonusItemsArray().get(i).getBonusItemsBound().getBox();
                    if (playerRect.overlaps(bonusRect) || playerRect.contains(bonusRect)) {

                        //Создаем эффект взрыва от столкновения игрока с бонусным предметом
                        interactionManager.createParticleEffectBonus(i);
                        //Изменение уровня жизни игрока от столкновения с врагом
                        interactionManager.usingBonusItems(i);
                     /*   //Создаем облако очков от столкновения игрока с врагом
                        interactionManager.createScoreCloudToPlayer(i);*/

                        gameManager.getBonusItemsArray().get(i).remove();
                        gameManager.getBonusItemsArray().removeIndex(i);
                       /* removeBonusItem = gameManager.getBonusItemsArray().removeIndex(i);
                        removeBonusItem = null;*/
                    }
                }
            }
        }
    }
}//end fo class
