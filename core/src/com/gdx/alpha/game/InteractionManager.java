package com.gdx.alpha.game;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gdx.alpha.effects.HitParticleEffect;
import com.gdx.alpha.entitys.Bacteriophage;
import com.gdx.alpha.entitys.ScoreCloud;

class InteractionManager {

    private GameManager gameManager;
    private Bacteriophage bacteriophage;
    private HitParticleEffect hitParticleEffect;
    private ScoreCloud scoreCloud;

    InteractionManager(GameManager gameManager) {
        this.gameManager = gameManager;
        System.out.println("InteractionManager create");
    }
    //Ввад в игру бактериофага со случайными свойтвами
    Bacteriophage randomizeBacteriophages(Integer i) {
        if (MathUtils.random(100) > 70) {
            bacteriophage=null;
            int weapon_type_bacteriophage = MathUtils.random(0, 2);
            //System.out.println("Weapon type: "+ weapon_type_bacteriophage);
            switch (weapon_type_bacteriophage) {
                case 0:
                    bacteriophage = new Bacteriophage(new Vector2(gameManager.enemies.get(i).getPosition()),
                            0.0f, gameManager.bacteriophageAtlas, "h001");
                    break;
                case 1:
                    bacteriophage = new Bacteriophage(new Vector2(gameManager.enemies.get(i).getPosition()),
                            0.0f, gameManager.bacteriophage_weapon_maceAtlas, "m001");
                    break;
                case 2:
                    bacteriophage = new Bacteriophage(new Vector2(gameManager.enemies.get(i).getPosition()),
                            0.0f, gameManager.bacteriophage_weapon_stoneAtlas, "s001");
                    break;
            }
        }
        return bacteriophage;
    }
    //Создание эффекта взрыва от столкновения  с врагом
    void createParticleEffectBlow(Integer i){
        hitParticleEffect = new HitParticleEffect(new ParticleEffect(gameManager.blow), 0.5f);
        hitParticleEffect.setPositionEffect(gameManager.enemies.get(i).getPositionX() + gameManager.enemies.get(i).getBound().getBox().getWidth() / 2,
                gameManager.enemies.get(i).getPositionY() + gameManager.enemies.get(i).getBound().getBox().getHeight() / 2);
        gameManager.hitParticleEffectArray.add(hitParticleEffect);
    }
    //создание облака очко от столкновения игрока с врагом
     void createScoreCloudToPlayer(Integer i){
        scoreCloud = new ScoreCloud(new Vector2(gameManager.player.getPositionX() + gameManager.player.getBound().getBox().getWidth() / 2,
                gameManager.player.getPositionY() + gameManager.player.getBound().getBox().getHeight() / 2),
                gameManager.fontScoreCloudRed, gameManager.enemies.get(i).getPrice(), false);
        gameManager.scoreCloudArray.add(scoreCloud);
    }
    //Изменение уровня жизни игрока от столкновения с врагом
    void changePlayerHealthEnemies(Integer i){
        gameManager.player.setHealth(gameManager.player.getHealth() - gameManager.enemies.get(i).getPrice());
    }

    //Создание эффекта взрыва от столкновения  с пулями
    void createParticleEffectBlowSmall( Integer i){
        hitParticleEffect = new HitParticleEffect(new ParticleEffect(gameManager.blow_small), 0.5f);
        hitParticleEffect.setPositionEffect(gameManager.bullets.get(i).getPositionX() + gameManager.bullets.get(i).getBound().getBox().getWidth() / 2,
                gameManager.bullets.get(i).getPositionY() + gameManager.bullets.get(i).getBound().getBox().getHeight() / 2);
        gameManager.hitParticleEffectArray.add(hitParticleEffect);
       // return hitParticleEffect;
    }

    //Создание эффекта взрыва от столкновения с оружием
    void createParticleEffectHit(Integer j){
        hitParticleEffect = new HitParticleEffect(new ParticleEffect(gameManager.hit), 0.2f);
        hitParticleEffect.setPositionEffect(gameManager.axes.get(j).getPositionX(),
                gameManager.axes.get(j).getPositionY() + gameManager.axes.get(j).getBounds().getBox().getHeight() / 2);
        gameManager.hitParticleEffectArray.add(hitParticleEffect);

       //return hitParticleEffect;
    }
    //Создаем эффукт столкновения с яйцк
    void createParticleEffectOvum(){
        hitParticleEffect = new HitParticleEffect(new ParticleEffect(gameManager.ovum_effect), 5.0f);
        hitParticleEffect.setPositionEffect(gameManager.ovum.getPositionX() + gameManager.ovum.getWidth(),
                gameManager.ovum.getPositionY() - gameManager.ovum.getHeight() / 2);
        gameManager.hitParticleEffectArray.add(hitParticleEffect);
        gameManager.setOvumEffectStart(true);
    }

    //Изменение уровня жизни игрока от столкновения с пулями
    void changePlayerHealthBullets(Integer i){
        gameManager.player.setHealth(gameManager.player.getHealth() - gameManager.bullets.get(i).getPrice());
    }
    //уменьшаем здоровье врага на величину силы оружия
    void changeEnemiesHealth(Integer i, Integer j){
        gameManager.enemies.get(i).changeHealth(gameManager.axes.get(j).health);
    }
    //Изменение счетчика очков на величину вознаграждения за поражение врага
    void changeScoreAmountUIEnemiesKill(Integer i){
        gameManager.setScoresAmount(gameManager.getScoresAmount()+ gameManager.enemies.get(i).getPrice());
        gameManager.updateScoresAmount();
    }

    void changeScoreAmountUIBulletsKill(Integer i){
        gameManager.setScoresAmount(gameManager.getScoresAmount()+ gameManager.bullets.get(i).getPrice());
        gameManager.updateScoresAmount();
    }

    //Создание облака очков от столкновения игрока с пулями
   void createScoreCloudToPlayerBullets(Integer i){
        scoreCloud = new ScoreCloud(new Vector2(gameManager.player.getPositionX() + gameManager.player.getBound().getBox().getWidth() / 2,
            gameManager.player.getPositionY() + gameManager.player.getBound().getBox().getHeight() / 2),
        gameManager.fontScoreCloudRed, gameManager.bullets.get(i).getPrice(), false);
        gameManager.scoreCloudArray.add(scoreCloud);
    }
    //Создание облака очков от столкновения врагов с оружием
    void createScoreCloudToEnemies(Integer i){
        scoreCloud = new ScoreCloud(new Vector2(gameManager.enemies.get(i).getPositionX() + gameManager.enemies.get(i).getBound().getBox().getWidth() / 2,
                gameManager.enemies.get(i).getPositionY() + gameManager.enemies.get(i).getBound().getBox().getHeight() / 2),
                gameManager.fontScoreCloudGreen, gameManager.enemies.get(i).getPrice(), true);
        gameManager.scoreCloudArray.add(scoreCloud);
    }
    //Создание облака очков для пуль
    void createScoreCloudToBullets(Integer j){
        scoreCloud = new ScoreCloud(new Vector2(gameManager.bullets.get(j).getPositionX() + gameManager.bullets.get(j).getBound().getBox().getWidth()/2,
                gameManager.bullets.get(j).getPositionY() + gameManager.bullets.get(j).getBound().getBox().getHeight()/2),
                gameManager.fontScoreCloudGreen, gameManager.bullets.get(j).getPrice(),true);
        gameManager.scoreCloudArray.add(scoreCloud);
    }
    //Создание облака очков для сперм
    void createScoreCloudToSperms(Integer i){
        scoreCloud = new ScoreCloud(new Vector2(gameManager.sperms.get(i).getPositionX() + gameManager.sperms.get(i).getBound().getBox().getWidth()/2,
                gameManager.sperms.get(i).getPositionY() + gameManager.sperms.get(i).getBound().getBox().getHeight()/2),
                gameManager.fontScoreCloudRed, 1,false);
        gameManager.scoreCloudArray.add(scoreCloud);
    }


}//end of class