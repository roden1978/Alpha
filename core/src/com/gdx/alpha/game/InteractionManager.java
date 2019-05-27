package com.gdx.alpha.game;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gdx.alpha.effects.HitParticleEffect;
import com.gdx.alpha.entitys.Bacteriophage;
import com.gdx.alpha.entitys.ScoreCloud;

public class InteractionManager {

    private GameManager gameManager;
    private Bacteriophage bacteriophage;
    private HitParticleEffect hitParticleEffect;
    private ScoreCloud scoreCloud;
    //private Rectangle bulletRect;

    public InteractionManager(GameManager gameManager) {
        this.gameManager = gameManager;
        System.out.println("InteractionManager create");
    }
    //Ввад в игру бактериофага со случайными свойтвами
    public Bacteriophage randomizeBacteriophages(Integer i) {
        if (MathUtils.random(100) > 70) {
            bacteriophage=null;
            //bacteriophages.clear();
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
                /*
                    default:
                    bacteriophage = new Bacteriophage(new Vector2(this.enemies.get(i).getPosition()),
                            0.0f, bacteriophageAtlas, "h001");
                    break;
                    */
            }
        }
        return bacteriophage;
    }
    //Создание эффекта взрыва от столкновения игрока с врагом
    public HitParticleEffect createParticleEffectBlow(Integer i){
        hitParticleEffect = new HitParticleEffect(new ParticleEffect(gameManager.blow), 0.5f);
        hitParticleEffect.setPositionEffect(gameManager.enemies.get(i).getPositionX() + gameManager.enemies.get(i).getBound().getBox().getWidth() / 2,
                gameManager.enemies.get(i).getPositionY() + gameManager.enemies.get(i).getBound().getBox().getHeight() / 2);
        gameManager.hitParticleEffectArray.add(hitParticleEffect);
        return hitParticleEffect;
    }
    //создание облака очко от столкновения игрока с врагом
    public void createScoreCloudToPlayer(Integer i){
        scoreCloud = new ScoreCloud(new Vector2(gameManager.player.getPositionX() + gameManager.player.getBound().getBox().getWidth() / 2,
                gameManager.player.getPositionY() + gameManager.player.getBound().getBox().getHeight() / 2),
                gameManager.fontScoreCloudRed, gameManager.enemies.get(i).getPrice(), false);
        gameManager.scoreCloudArray.add(scoreCloud);
    }
    //Изменение уровня жизни игрока от столкновения с врагом
    public void changePlayerHealthEnemys(Integer i){
        gameManager.player.setHealth(gameManager.player.getHealth() - gameManager.enemies.get(i).getPrice());
    }

    //Создание эффекта взрыва от столкновения игрока с пулями
    public HitParticleEffect createParticleEffectBlowSmall( Integer i){
        hitParticleEffect = new HitParticleEffect(new ParticleEffect(gameManager.blow_small), 0.5f);
        hitParticleEffect.setPositionEffect(gameManager.bullets.get(i).getPositionX() + gameManager.bullets.get(i).getBound().getBox().getWidth() / 2,
                gameManager.bullets.get(i).getPositionY() + gameManager.bullets.get(i).getBound().getBox().getHeight() / 2);
        gameManager.hitParticleEffectArray.add(hitParticleEffect);
        return hitParticleEffect;
    }
    //Изменение уровня жизни игрока от столкновения с пулями
    public void changePlayerHealthBullets(Integer i){
        gameManager.player.setHealth(gameManager.player.getHealth() - gameManager.bullets.get(i).getPrice());
    }
    //Создание облака очков от столкновения игрока с пулями
    public void createScoreCloudToBullets(Integer i){
        scoreCloud = new ScoreCloud(new Vector2(gameManager.player.getPositionX() + gameManager.player.getBound().getBox().getWidth() / 2,
            gameManager.player.getPositionY() + gameManager.player.getBound().getBox().getHeight() / 2),
        gameManager.fontScoreCloudRed, gameManager.bullets.get(i).getPrice(), false);
        gameManager.scoreCloudArray.add(scoreCloud);
    }


}//end of class
