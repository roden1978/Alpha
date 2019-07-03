package com.gdx.alpha.game;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gdx.alpha.effects.HitParticleEffect;
import com.gdx.alpha.entitys.Bacteriophage;
import com.gdx.alpha.entitys.ScoreCloud;

class InteractionManager {

    private GameManager gameManager;
    private AudioManager audioManager;
    private Bacteriophage bacteriophage;
    private HitParticleEffect hitParticleEffect;
    private ScoreCloud scoreCloud;

    InteractionManager(GameManager gameManager, AudioManager audioManager) {
        this.gameManager = gameManager;
        this.audioManager = audioManager;
        //this.bacteriophage = new Bacteriophage();
        System.out.println("InteractionManager create");
    }
    //Ввад в игру бактериофага со случайными свойтвами
    Bacteriophage randomizeBacteriophages(Integer i) {
        if (MathUtils.random(100) > 15 && gameManager.getEnemies().get(i).getEntity().equals("v")) { //////////////////////
            int weapon_type_bacteriophage;
            if (gameManager.getLevel() < 5)
                 weapon_type_bacteriophage = MathUtils.random(0, 1); //////////////
            else
                weapon_type_bacteriophage = MathUtils.random(0, 3);

            //System.out.println("Weapon type: "+ weapon_type_bacteriophage);
            switch (weapon_type_bacteriophage) {
                case 0:
                    this.bacteriophage = new Bacteriophage(new Vector2(gameManager.getEnemies().get(i).getPosition()),
                            0.0f, gameManager.bacteriophageAtlas, "h001",0);
                    System.out.println("Pos Bacteriophage + X: "+ gameManager.getEnemies().get(i).getPosition().x +" Y: " + gameManager.getEnemies().get(i).getPosition().y);
                    break;
                case 1:
                    this.bacteriophage = new Bacteriophage(new Vector2(gameManager.getEnemies().get(i).getPosition()),
                            0.0f, gameManager.bacteriophage_weapon_maceAtlas, "m001",1);
                    System.out.println("Pos Bacteriophage mace X: "+ gameManager.getEnemies().get(i).getPosition().x +" Y: " + gameManager.getEnemies().get(i).getPosition().y);
                    break;
                case 2:
                    this.bacteriophage = new Bacteriophage(new Vector2(gameManager.getEnemies().get(i).getPosition()),
                            0.0f, gameManager.bacteriophage_weapon_stoneAtlas, "s001",2);
                    System.out.println("Pos Bacteriophage stone X: "+ gameManager.getEnemies().get(i).getPosition().x +" Y: " + gameManager.getEnemies().get(i).getPosition().y);
                    break;
                case 3:
                    this.bacteriophage = new Bacteriophage(new Vector2(gameManager.getEnemies().get(i).getPosition()),
                            0.0f, gameManager.getBacteriophage_weapon_cudgelAtlas(), "000",3);
                    System.out.println("Pos Bacteriophage cudgel X: "+ gameManager.getEnemies().get(i).getPosition().x +" Y: " + gameManager.getEnemies().get(i).getPosition().y);
                    break;

            }
            System.out.println("Weapon type: "+ weapon_type_bacteriophage);
            System.out.println("Bacter array siaze interact manager: "+ gameManager.getBacteriophages().size);
        }
        return this.bacteriophage;
    }
    //Создание эффекта взрыва от столкновения  с врагом
    void createParticleEffectBlow(Integer i){
        hitParticleEffect = new HitParticleEffect(new ParticleEffect(gameManager.blow), 0.5f);
        hitParticleEffect.setPositionEffect(gameManager.getEnemies().get(i).getPositionX() + gameManager.getEnemies().get(i).getBound().getBox().getWidth() / 2,
                gameManager.getEnemies().get(i).getPositionY() + gameManager.getEnemies().get(i).getBound().getBox().getHeight() / 2);
        gameManager.hitParticleEffectArray.add(hitParticleEffect);
        playBlowEnemySound();
    }
    //создание облака очко от столкновения игрока с врагом
     void createScoreCloudToPlayer(Integer i){
        scoreCloud = new ScoreCloud(new Vector2(gameManager.player.getPositionX() + gameManager.player.getBound().getBox().getWidth() / 2,
                gameManager.player.getPositionY() + gameManager.player.getBound().getBox().getHeight() / 2),
                gameManager.fontScoreCloudRed, gameManager.getEnemies().get(i).getPrice(), false);
        gameManager.scoreCloudArray.add(scoreCloud);
    }
    //Изменение уровня жизни игрока от столкновения с врагом
    void changePlayerHealthEnemies(Integer i){
        gameManager.player.setHealth(gameManager.player.getHealth() - gameManager.getEnemies().get(i).getPrice());
    }

    //Создание эффекта взрыва от столкновения  с пулями
    void createParticleEffectBlowSmall( Integer i){
        hitParticleEffect = new HitParticleEffect(new ParticleEffect(gameManager.blow_small), 0.5f);
        hitParticleEffect.setPositionEffect(gameManager.getBullets().get(i).getPositionX() + gameManager.getBullets().get(i).getBound().getBox().getWidth() / 2,
                gameManager.getBullets().get(i).getPositionY() + gameManager.getBullets().get(i).getBound().getBox().getHeight() / 2);
        gameManager.hitParticleEffectArray.add(hitParticleEffect);
       // return hitParticleEffect;
    }

    //Создание эффекта взрыва от столкновения с оружием
    void createParticleEffectHit(Integer j){
        hitParticleEffect = new HitParticleEffect(new ParticleEffect(gameManager.hit), 0.2f);
        hitParticleEffect.setPositionEffect(gameManager.weapons.get(j).getPositionX(),
                gameManager.weapons.get(j).getPositionY() + gameManager.weapons.get(j).getBounds().getBox().getHeight() / 2);
        gameManager.hitParticleEffectArray.add(hitParticleEffect);

       //return hitParticleEffect;
    }
    //Создаем эффукт столкновения с яйцк
    void createParticleEffectOvum(){
        hitParticleEffect = new HitParticleEffect(new ParticleEffect(gameManager.ovum_effect), 3.0f);
        hitParticleEffect.setPositionEffect(gameManager.getOvum().getPosition().x + gameManager.getOvum().getBoundWidth(),
                gameManager.getOvum().getPosition().y + gameManager.getOvum().getBoundHeight() / 2);
        gameManager.hitParticleEffectArray.add(hitParticleEffect);
        gameManager.setOvumEffectStart(true);
    }

    //Изменение уровня жизни игрока от столкновения с пулями
    void changePlayerHealthBullets(Integer i){
        gameManager.player.setHealth(gameManager.player.getHealth() - gameManager.getBullets().get(i).getPrice());
    }
    //уменьшаем здоровье врага на величину силы оружия
    void changeEnemiesHealth(Integer i, Integer j){
        gameManager.getEnemies().get(i).changeHealth(gameManager.weapons.get(j).getHealth());
    }
    //Изменение счетчика очков на величину вознаграждения за поражение врага
    void changeScoreAmountUIEnemiesKill(Integer i){
        gameManager.setScoresAmount(gameManager.getScoresAmount()+ gameManager.getEnemies().get(i).getPrice());
        gameManager.updateScoresAmount();
    }
    //Изменение счетчика очков на величину вознаграждения за поражение пули
    void changeScoreAmountUIBulletsKill(Integer i){
        gameManager.setScoresAmount(gameManager.getScoresAmount()+ gameManager.getBullets().get(i).getPrice());
        gameManager.updateScoresAmount();
    }

    //Создание облака очков от столкновения игрока с пулями
   void createScoreCloudToPlayerBullets(Integer i){
        scoreCloud = new ScoreCloud(new Vector2(gameManager.player.getPositionX() + gameManager.player.getBound().getBox().getWidth() / 2,
            gameManager.player.getPositionY() + gameManager.player.getBound().getBox().getHeight() / 2),
        gameManager.fontScoreCloudRed, gameManager.getBullets().get(i).getPrice(), false);
        gameManager.scoreCloudArray.add(scoreCloud);
    }
    //Создание облака очков от столкновения врагов с оружием
    void createScoreCloudToEnemies(Integer i){
        scoreCloud = new ScoreCloud(new Vector2(gameManager.getEnemies().get(i).getPositionX() + gameManager.getEnemies().get(i).getBound().getBox().getWidth() / 2,
                gameManager.getEnemies().get(i).getPositionY() + gameManager.getEnemies().get(i).getBound().getBox().getHeight() / 2),
                gameManager.fontScoreCloudGreen, gameManager.getEnemies().get(i).getPrice(), true);
        gameManager.scoreCloudArray.add(scoreCloud);
    }
    //Создание облака очков для пуль
    void createScoreCloudToBullets(Integer j){
        scoreCloud = new ScoreCloud(new Vector2(gameManager.getBullets().get(j).getPositionX() + gameManager.getBullets().get(j).getBound().getBox().getWidth()/2,
                gameManager.getBullets().get(j).getPositionY() + gameManager.getBullets().get(j).getBound().getBox().getHeight()/2),
                gameManager.fontScoreCloudGreen, gameManager.getBullets().get(j).getPrice(),true);
        gameManager.scoreCloudArray.add(scoreCloud);
    }
    //Создание облака очков для сперм
    void createScoreCloudToSperms(Integer i){
        scoreCloud = new ScoreCloud(new Vector2(gameManager.getSperms().get(i).getPositionX() + gameManager.getSperms().get(i).getBound().getBox().getWidth()/2,
                gameManager.getSperms().get(i).getPositionY() + gameManager.getSperms().get(i).getBound().getBox().getHeight()/2),
                gameManager.fontScoreCloudRed, 1,false);
        gameManager.scoreCloudArray.add(scoreCloud);
    }
    //Проигрывание звука уничтожениея врага
    private void playBlowEnemySound (){
        if(gameManager.getSoundOnOff())
            audioManager.getBlowEnemySound().play();
    }

    //Подбор бектериофага
    void useBacteriophage(int i){
        //int type = gameManager.getBacteriophages().get(i).getType();
        switch (gameManager.getBacteriophages().get(i).getType()){
            case 0:
                gameManager.player.setHealth(gameManager.player.getHealth() + gameManager.getBacteriophages().get(i).getHealth());
                if (gameManager.player.getHealth() > 300)
                    gameManager.player.setHealth(300);
                break;
            case 1:
                gameManager.getThrowWeapon().changeType(1);
                break;
            case 2:
                gameManager.getThrowWeapon().changeType(2);
                break;
            case 3:
                gameManager.getThrowWeapon().changeType(3);
                break;
        }
        System.out.println("UseBacter array size interact manager: "+ gameManager.getBacteriophages().size);
    }

    public void changePlayerLifeCount(){
        if (gameManager.player.getLifeCount() < 3)
            gameManager.player.setLifeCount(gameManager.player.getLifeCount() + 1);
    }

}//end of class
