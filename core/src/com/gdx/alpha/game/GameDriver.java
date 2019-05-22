package com.gdx.alpha.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gdx.alpha.effects.HitParticleEffect;
import com.gdx.alpha.entitys.Axe;
import com.gdx.alpha.entitys.Microbe;
import com.gdx.alpha.entitys.ScoreCloud;
import com.gdx.alpha.entitys.Virus;
import com.gdx.alpha.entitys.VirusA;
import com.gdx.alpha.entitys.VirusB;
import com.gdx.alpha.entitys.VirusBullet;
import com.gdx.alpha.screens.GameScreen;
import com.gdx.alpha.screens.ObjectScreen;

/**
 * Created by Admin on 04.02.15.
 */
public class GameDriver {

    private ObjectScreen gameScreen;
    public GameManager gameManager;
    private CollisionDetector collisionDetector;
    private VirusBullet removedBullet = null;
    private Actor removedActor = null;
    private Axe removedAxe = null;
    private Float removedTime = null;
    private HitParticleEffect removedHPE = null;
    private ScoreCloud removedScoreCloud=null;

    private float gameTime = 0.0f;

    public GameDriver(GameScreen gameScreen, int level){
        this.gameScreen = gameScreen;
        gameManager = new GameManager(level);
        collisionDetector = new CollisionDetector(gameManager);
        //загружаем ресурсы игры (текстуры)
        gameManager.loadSources();
        //инициализируем игрока и каплю
        gameManager.buildGeneralPlayers();
        //инициализируем и стартуем процесс бросания оружия
        gameManager.buildActions();
        //инициализируем строку состояния игры
        gameManager.buildUiStateString();
        System.out.println("GameDriver create");
    }

/*gameDrive фукция которая является игровым процессом
* функция вводит в игровой процесс всех объектов
* контролирует процессы взаимодействия объектов
* и удаление объектов из игрововго процесса*/
    public void gameDrive(float delta){
        //Блок ввода в игру объектов игрововго процесса
        addEnemiesToGame(delta);
        addBulletsToArray();
        addBulletsToGame();
        addAxesToGame();
        addSpermsToGame();
        addHitParticleEffectToGame();
        addScoreCloudToGame();
        addBacteriophageToGame();
        addOvumToGame();
        //---------------------------------------------------

        //Блок контроля столкновений и взаимодействия объектов
        collisionDetector.detectPlayerCollisions();
        collisionDetector.detectWeaponBulletCollision();
        collisionDetector.detectWeaponEnemyCollisions();
        collisionDetector.detectSpermCollisions();
        collisionDetector.detectBacteriophageEnemyCollisions();
        collisionDetector.detectOvumSpermsCollisions();
        //----------------------------------------------------

        //Блок контроля нахождения объектов игрового процесса на игровой сцене
        // и удаления если объекты покинули пределы игровой сцены
        controlEnemiesPosition();
        controlBulletPosition();
        controlAxePosition();
        controlHitParticleEffect();
        controlScoreCloud();
        controlLifeScale();
        controlLevelEnd();
    }
    //Функция ввода основных объектов в игру
    public void addGeneralActorsToScene(){
        gameScreen.getGameStage().addActor(gameManager.backgroundLayer01);
        gameScreen.getGameStage().addActor(gameManager.backgroundLayer00);
        gameScreen.getGameStage().addActor(gameManager.sprinkle);
        gameScreen.getGameStage().addActor(gameManager.uiTable);
        gameScreen.getGameStage().addActor(gameManager.throwWeapon);
        gameScreen.getGameStage().addActor(gameManager.player);
    }
    //Функция ввода в игру "врагов"
    public void addEnemiesToGame(float delta){
        //вводим врагов в игру
        gameTime += delta;
        for (int i = 0; i < gameManager.time.size;i++){
            if ((int)gameTime == gameManager.time.get(i)){
                buildEnemies(i);
                gameManager.time.removeIndex(i);
                //ameManager.name.removeIndex(i);
                gameManager.posX.removeIndex(i);
                gameManager.posY.removeIndex(i);
                gameManager.weight.removeIndex(i);
                gameManager.speed.removeIndex(i);
            }
        }
    }
    //Функция создания классов "врагов"
    public void buildEnemies(int i){
            //String s = gameManager.name.get(i);
            Microbe microbe = null;
        //System.out.println("Height: "+gameManager.virusAtlas);
            microbe = new Virus(new Vector2(gameManager.posX.get(i),gameManager.posY.get(i) * Gdx.graphics.getHeight()),gameManager.speed.get(i),
                    gameManager.weight.get(i),gameManager.player,gameManager.virusAtlas, gameManager.virusBulletAtlas, gameManager.lifeAtlas);
            if (microbe != null){
                gameManager.enemies.add(microbe);
                gameScreen.getGameStage().addActor(microbe);
            }
    }
    public void controlEnemiesPosition(){
        //отслеживание позиции врагов и удаление их со сцены если они вышли за пределы экрана
        for (int i = 0; i < gameManager.enemies.size; i++){
            if (gameManager.enemies.get(i) != null){
                if (gameManager.enemies.get(i).getPositionX() > gameScreen.getGameStage().getWidth()
                        || gameManager.enemies.get(i).getPositionX() < 0
                        || gameManager.enemies.get(i).getPositionY() > gameScreen.getGameStage().getHeight()
                        || gameManager.enemies.get(i).getPositionY() < 0) {
                    if (gameManager.enemies.get(i).remove()) {              //удаляем объект(Actor) с индексом i из сцены
                        removedActor = gameManager.enemies.removeIndex(i);  //удаляет объект с индексом i из массива и возвращает объект
                        removedActor = null; // присваиваем объекту null чтобы его уничтожил сборщик мусора (в Java не нужно самому удалять объекты из памяти)
                        //такой принцип применяем для всех игровых объектов
                    }
                }
            }
        }
    }
    public void addBulletsToArray() {
        //добавляем пули врагов в общий массив пуль для дальнейшей обработки
        //затем массивы пуль каждого врага очищаем
        //отдельный массив пуль нуже для того чтобы пули не были привязаны к врагам и не уничтожались при уничтожении врага,
        //а прожили свой жизненный цикл
        for (int i = 0; i < gameManager.enemies.size; i++) {
            if (gameManager.enemies.get(i) != null) {
                for (int j = 0; j < gameManager.enemies.get(i).getBulletsArray().size; j++) {
                    if (gameManager.enemies.get(i).getBulletsArray().get(j) != null)
                        gameManager.bullets.add(gameManager.enemies.get(i).getBulletsArray().get(j));
                }
                gameManager.enemies.get(i).getBulletsArray().clear();
            }
        }
    }
    public void addBulletsToGame(){
        //выводим пули врагов на игровую сцену
            for (int j = 0; j < gameManager.bullets.size; j++) {
                if (gameManager.bullets.get(j) != null) {
                    gameScreen.getGameStage().addActor(gameManager.bullets.get(j));
                }
            }
    }
    public void controlBulletPosition(){
        //отслеживание позиции пуль вирусов и удаление их со сцены если они вышли за пределы экрана
        for (int i = 0; i < gameManager.bullets.size; i++){
            if (gameManager.bullets.get(i) != null){
                if (gameManager.bullets.get(i).getPositionX() > gameScreen.getGameStage().getWidth()
                        || gameManager.bullets.get(i).getPositionX() < 0
                        || gameManager.bullets.get(i).getPositionY() > gameScreen.getGameStage().getHeight()
                        || gameManager.bullets.get(i).getPositionY() < 0) {
                    if (gameManager.bullets.get(i).remove()) {
                        removedBullet = gameManager.bullets.removeIndex(i);
                        removedBullet = null;
                    }
                }
            }
        }
    }
    public void addAxesToGame(){
        //вводим массив топоров для дальнейшей обработки
        for (int i = 0; i < gameManager.throwWeapon.getAxeArray().size; i++){
            if (gameManager.throwWeapon.getAxeArray().get(i) != null){
                gameManager.axes.add(gameManager.throwWeapon.getAxeArray().get(i));
            }
        }
        gameManager.throwWeapon.getAxeArray().clear();
        //выводим массив топоров на игровую сцену
        for (int i = 0; i < gameManager.axes.size; i++){
            if (gameManager.axes.get(i) != null){
                gameScreen.getGameStage().addActor(gameManager.axes.get(i));
            }
        }
    }
    public void controlAxePosition(){
        for (int i = 0; i < gameManager.axes.size; i++){
            if (gameManager.axes.get(i) != null){
                if(gameManager.axes.get(i).getPositionX() < 50) {
                    if (gameManager.axes.get(i).remove()) {
                        removedAxe = gameManager.axes.removeIndex(i);
                        removedAxe = null;
                    }
                }
            }
        }
    }
    public void addSpermsToGame(){
        //вводим массив сперматозоидов для дальнейшей обработки
        for (int i = 0; i < gameManager.sprinkle.getSpermArray().size; i++){
            if (gameManager.sprinkle.getSpermArray().get(i) != null){
                gameManager.sperms.add(gameManager.sprinkle.getSpermArray().get(i));
            }
        }
        gameManager.sprinkle.getSpermArray().clear();
        //выводим массив сперматозоидов на игровую сцену
        gameManager.spermAmount = gameManager.sperms.size;
        gameManager.updateSpermAmount();
        for (int i = 0; i < gameManager.sperms.size; i++){
            if (gameManager.sperms.get(i) != null){
                gameScreen.getGameStage().addActor(gameManager.sperms.get(i));
            }
        }
    }
    public void controlHitParticleEffect(){
        for (int i = 0; i < gameManager.hitParticleEffectArray.size; i++){
            if (gameManager.hitParticleEffectArray.get(i).isComplete()){
                gameManager.hitParticleEffectArray.get(i).resetEffect();
                gameManager.hitParticleEffectArray.get(i).remove();
                removedHPE = gameManager.hitParticleEffectArray.removeIndex(i);
                removedHPE = null;
            }
        }
    }
    public void addHitParticleEffectToGame(){
        for (int i = 0; i < gameManager.hitParticleEffectArray.size; i++) {
            gameScreen.getGameStage().addActor(gameManager.hitParticleEffectArray.get(i));
            gameManager.hitParticleEffectArray.get(i).allowCompletionEffect();
            gameManager.hitParticleEffectArray.get(i).startEffect();
        }
    }
    public void controlScoreCloud(){
        for (int i = 0; i < gameManager.scoreCloudArray.size; i++) {
            if(gameManager.scoreCloudArray.get(i).getY() > gameScreen.getGameStage().getHeight()){
                gameManager.scoreCloudArray.get(i).remove();
                removedScoreCloud  = gameManager.scoreCloudArray.removeIndex(i);
                removedScoreCloud = null;
            }
        }
    }
    public void addScoreCloudToGame(){
        for (int i = 0; i < gameManager.scoreCloudArray.size; i++) {
            gameScreen.getGameStage().addActor(gameManager.scoreCloudArray.get(i));
        }
    }
    public void controlLifeScale(){
        if (gameManager.player.getHealth() <= 0)
            gameScreen.setGameState(4); //Game over
    }
    public void addBacteriophageToGame(){
        for (int i = 0; i < gameManager.bacteriophages.size; i++) {
            if (gameManager.bacteriophages.get(i) != null)
                gameScreen.getGameStage().addActor(gameManager.bacteriophages.get(i));
        }
    }
    public void addOvumToGame(){
        if (gameManager.ovum != null){
            for (int i = 0; i < gameManager.sperms.size; i++){
                if (gameManager.sperms.get(i).getPositionX() <= gameScreen.getGameStage().getWidth() -
                        (gameScreen.getGameStage().getWidth()/3.0f)*2.0f)
                    gameScreen.getGameStage().addActor(gameManager.ovum);

            }
        }
    }
    public void controlLevelEnd(){
        if(gameManager.getLevelEnd())
            gameScreen.setGameState(3); //Level end
    }
}
