package com.gdx.alpha.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gdx.alpha.entitys.AlarmClock;
import com.gdx.alpha.entitys.BacteriasColony;
import com.gdx.alpha.entitys.BonusLife;
import com.gdx.alpha.entitys.Coin;
import com.gdx.alpha.entitys.Condom;
import com.gdx.alpha.entitys.Microbe;
import com.gdx.alpha.entitys.Skull;
import com.gdx.alpha.entitys.Virus;
import com.gdx.alpha.entitys.VirusBullet;
import com.gdx.alpha.screens.GameScreen;
import com.gdx.alpha.screens.ObjectScreen;

/**
 * Created by Ro|)e|\| on 04.02.15.
 */
public class GameDriver {

    private ObjectScreen gameScreen;
    private GameManager gameManager;
    private CollisionDetector collisionDetector;
    private InteractionManager interactionManager;
    private AudioManager audioManager;
    private Microbe microbe;
    private BacteriasColony colony;

    private float gameTime = 0.0f;
    //Передыдущий промежуток очков в котором появилась бонусная жизнь
    private int preScoreCountPart = 0;
    //Предыдущий промежуток времени в котором появился бонусный предмет
    private int preGameTime = 0;

    public GameManager getGameManager() {
        return gameManager;
    }
    public AudioManager getAudioManager(){return audioManager;}

    public GameDriver(GameScreen gameScreen, int level){
        this.gameScreen = gameScreen;
        //Создаем AudioManager
        audioManager = new AudioManager();
        //Создаем экземпляр класса GameManager
        gameManager = new GameManager(audioManager, level, gameScreen.getScreenManager().getOnoff());
        //interaction manager
        interactionManager = new InteractionManager(gameManager, audioManager);
        //Создаем экзкмпляр класса CollisionDetector и передаем в него экземпляр класса GameManager
        collisionDetector = new CollisionDetector(gameManager,interactionManager);
        //загружаем ресурсы игры (текстуры)
        gameManager.loadSources();
        //инициализируем игрока и каплю
        gameManager.buildGeneralPlayers();
        //инициализируем строку состояния игры
        gameManager.buildUiStateString();
        //инициализируем и стартуем процесс бросания оружия
        gameManager.buildActions();
        //Вкл/Выкл звук
        gameManager.setSoundOnOff(gameScreen.getScreenManager().getOnoff());
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
        addWeaponToGame();
        addHitParticleEffectToGame();
        addScoreCloudToGame();
        addBacteriophageToGame();
        addOvumToGame();
        addBacteriumToGame();
        addBonusItemsToGame();
        //---------------------------------------------------

        //Блок контроля столкновений и взаимодействия объектов
        collisionDetector.detectPlayerEnemyCollisions();
        collisionDetector.detectPlayerBulletsCollisions();
        collisionDetector.detectPlayerBacteriophgesCollisions();
        collisionDetector.detectWeaponBulletCollision();
        collisionDetector.detectWeaponEnemyCollisions();
        collisionDetector.detectSpermCollisions();
        collisionDetector.detectBacteriophageEnemyCollisions();
        collisionDetector.detectOvumSpermsCollisions();
        collisionDetector.detectLevelEnd();
        collisionDetector.detectPlayerBonusItemsCollisions();
        //----------------------------------------------------

        //Блок контроля нахождения объектов игрового процесса на игровой сцене
        // и удаления если объекты покинули пределы игровой сцены
        controlEnemiesPosition();
        controlBulletPosition();
        controlWeaponPosition();
        controlSpermsPosition();
        controlHitParticleEffect();
        controlScoreCloud();
        controlLifeScale();
        controlLevelEnd();
        controlBonusLife();
        controlBonusItemPosition();
        controlBonusItems(gameTime);
        controlDynamicBackground(gameManager.getPlayer().getPosition());
        /*Запуск задержки для отрисовки ovum_effect и выходом на экран с выбором уровня
        * */
        if(gameManager.getOvumEffectStart())
            changeDeltaTimeParticleEffect(delta);
    }
    //Функция ввода основных объектов в игру
    public void addGeneralActorsToScene(){
        gameScreen.getGameStage().addActor(gameManager.getDynamicBackground());
        gameScreen.getGameStage().addActor(gameManager.getBackgroundLayer00());
        gameScreen.getGameStage().addActor(gameManager.getBackgroundLayer01());
        gameScreen.getGameStage().addActor(gameManager.getUiTable());
        gameScreen.getGameStage().addActor(gameManager.getGroupLayer0());
        addSpermsToGame();
        gameScreen.getGameStage().addActor(gameManager.getPig());
        gameManager.getGroupLayer0().addActor(gameManager.getThrowWeapon());
        gameManager.getGroupLayer0().addActor(gameManager.getPlayer());
    }
    //Функция ввода в игру "врагов"
    private void addEnemiesToGame(float delta){
        //вводим врагов в игру
        gameTime += delta;
        for (int i = 0; i < gameManager.time.size;i++){
            if ((int)gameTime == gameManager.time.get(i)){
                buildEnemies(i);
                gameManager.time.removeIndex(i);
                gameManager.typeEnemie.removeIndex(i);
                gameManager.posX.removeIndex(i);
                gameManager.posY.removeIndex(i);
                gameManager.weight.removeIndex(i);
                gameManager.speed.removeIndex(i);
            }
        }
    }
    //Функция создания классов "врагов"
    private void buildEnemies(int i){
            //String s = gameManager.name.get(i);

        //System.out.println("Height: "+gameManager.virusAtlas);
        if(gameManager.typeEnemie.get(i).trim().equals("v")){
            microbe = new Virus(new Vector2(gameManager.posX.get(i),gameManager.posY.get(i) * Gdx.graphics.getHeight()),
                    gameManager.speed.get(i), gameManager.weight.get(i),gameManager.getPlayer(),gameManager.virusAtlas,
                    gameManager.virusBulletAtlas, gameManager.getLifeScaleAtlas(), audioManager.getEnemyShootSound(), gameManager.getSoundOnOff());
            gameManager.getEnemies().add(microbe);
            gameScreen.getGameStage().addActor(microbe);
        }
        if(gameManager.typeEnemie.get(i).trim().equals("b"))
        {
            colony = new BacteriasColony(new Vector2(gameManager.posX.get(i) * Gdx.graphics.getWidth(),
                    gameManager.posY.get(i) * Gdx.graphics.getHeight()), gameManager.getBacteriumAtlas());
            //if (colony != null)
            System.out.println("Colony create");
                gameManager.getBacteriasColonys().add(colony);

        }
        if(gameManager.typeEnemie.get(i).trim().equals("c")){
            microbe = new Condom(new Vector2(gameManager.posX.get(i), gameManager.posY.get(i) * Gdx.graphics.getHeight()),
                    gameManager.getCondomAtlasesArray(), gameManager.getLifeScaleAtlas());
            System.out.println("Condom create");
            gameManager.getEnemies().add(microbe);
            gameScreen.getGameStage().addActor(microbe);
        }
    }
    private void controlEnemiesPosition(){
        //отслеживание позиции врагов и удаление их со сцены если они вышли за пределы экрана
        for (int i = 0; i < gameManager.getEnemies().size; i++){
            if (gameManager.getEnemies().get(i) != null){
                if (gameManager.getEnemies().get(i).getPositionX() > gameScreen.getGameStage().getWidth()
                        || gameManager.getEnemies().get(i).getPositionX() < 0
                        || gameManager.getEnemies().get(i).getPositionY() > gameScreen.getGameStage().getHeight()
                        || gameManager.getEnemies().get(i).getPositionY() < 0) {
                    if (gameManager.getEnemies().get(i).remove()) {
                        gameManager.getEnemies().removeIndex(i);//удаляем объект(Actor) с индексом i из сцены
                       /* removedActor = gameManager.getEnemies().removeIndex(i);  //удаляет объект с индексом i из массива и возвращает объект
                        removedActor = null; // присваиваем объекту null чтобы его уничтожил сборщик мусора (в Java не нужно самому удалять объекты из памяти)
                        //такой принцип применяем для всех игровых объектов*/
                    }
                }
            }
        }
    }
    private void addBulletsToArray() {
        //добавляем пули врагов в общий массив пуль для дальнейшей обработки
        //затем массивы пуль каждого врага очищаем
        //отдельный массив пуль нуже для того чтобы пули не были привязаны к врагам и не уничтожались при уничтожении врага,
        //а прожили свой жизненный цикл
        for (int i = 0; i < gameManager.getEnemies().size; i++) {
            if (gameManager.getEnemies().get(i) != null) {
                for (int j = 0; j < gameManager.getEnemies().get(i).getBulletsArray().size; j++) {
                    if (gameManager.getEnemies().get(i).getBulletsArray().get(j) != null)
                        gameManager.getBullets().add(gameManager.getEnemies().get(i).getBulletsArray().get(j));
                }
                gameManager.getEnemies().get(i).getBulletsArray().clear();
            }
        }
    }
    private void addBulletsToGame(){
        //выводим пули врагов на игровую сцену
            for (int j = 0; j < gameManager.getBullets().size; j++) {
                if (gameManager.getBullets().get(j) != null) {
                    gameScreen.getGameStage().addActor(gameManager.getBullets().get(j));
                }
            }
    }
    private void controlBulletPosition(){
        //отслеживание позиции пуль вирусов и удаление их со сцены если они вышли за пределы экрана
        for (int i = 0; i < gameManager.getBullets().size; i++){
            if (gameManager.getBullets().get(i) != null){
                if (gameManager.getBullets().get(i).getPositionX() > gameScreen.getGameStage().getWidth()
                        || gameManager.getBullets().get(i).getPositionX() < 0
                        || gameManager.getBullets().get(i).getPositionY() > gameScreen.getGameStage().getHeight()
                        || gameManager.getBullets().get(i).getPositionY() < 0) {
                    if (gameManager.getBullets().get(i).remove()) {
                        VirusBullet removedBullet = gameManager.getBullets().removeIndex(i);
                        removedBullet.clear();
                    }
                }
            }
        }
    }
    private void addWeaponToGame(){
        //вводим массив топоров для дальнейшей обработки
        for (int i = 0; i < gameManager.getThrowWeapon().getWeaponArray().size; i++){
            if (gameManager.getThrowWeapon().getWeaponArray().get(i) != null){
                gameManager.getWeapons().add(gameManager.getThrowWeapon().getWeaponArray().get(i));
            }
        }
        gameManager.getThrowWeapon().getWeaponArray().clear();
        //выводим массив топоров на игровую сцену
        for (int i = 0; i < gameManager.getWeapons().size; i++){
            if (gameManager.getWeapons().get(i) != null){
                gameScreen.getGameStage().addActor(gameManager.getWeapons().get(i));
            }
        }
    }
    private void controlWeaponPosition(){
        for (int i = 0; i < gameManager.getWeapons().size; i++){
            if (gameManager.getWeapons().get(i) != null){
                if(gameManager.getWeapons().get(i).getPositionX() < 50) {
                    if (gameManager.getWeapons().get(i).remove()) {
                        gameManager.getWeapons().removeIndex(i);
                        /*removedAxe = gameManager.weapons.removeIndex(i);
                        removedAxe = null;*/
                    }
                }
            }
        }
    }
    //ВВод сперматозоидов в игру
    private void addSpermsToGame(){
        //вводим массив сперматозоидов для дальнейшей обработки
        for (int i = 0; i < gameManager.getSprinkle().getSpermArray().size; i++){
            if (gameManager.getSprinkle().getSpermArray().get(i) != null){
                gameManager.getSperms().add(gameManager.getSprinkle().getSpermArray().get(i));
            }
        }
        gameManager.getSprinkle().getSpermArray().clear();
        //выводим массив сперматозоидов на игровую сцену
        gameManager.setSpermAmount(gameManager.getSperms().size);
        gameManager.updateSpermAmount();
        for (int i = 0; i < gameManager.getSperms().size; i++){
            if (gameManager.getSperms().get(i) != null){
                gameManager.getGroupLayer0().addActorAfter(gameManager.getPlayer(), gameManager.getSperms().get(i));
                //gameScreen.getGameStage().addActor(gameManager.getGroupLayer0());///////////////////////////////////////
            }
        }
        //spermInGame = true;
    }
    //контроль позиций сперм
    private void controlSpermsPosition(){
        //отслеживание позиции сперм и удаление их со сцены если они вышли за пределы экрана
        for (int i = 0; i < gameManager.getSperms().size; i++){
            if (gameManager.getSperms().get(i) != null){
                if(gameManager.getSperms().get(i).getPositionX() < 0) {
                    if (gameManager.getSperms().get(i).remove()) {
                        gameManager.getSperms().removeIndex(i);
                        /*removedAxe = gameManager.weapons.removeIndex(i);
                        removedAxe = null;*/
                    }
                }
            }
        }
    }
    //Ввод колонии бактерий в игру
    private void addBacteriumToGame(){
        if (gameManager.getBacteriasColonys() != null) {
            for (int i = 0; i < gameManager.getBacteriasColonys().size; i++) {
                for (int j = 0; j < gameManager.getBacteriasColonys().get(i).getBacteriumArray().size; j++) {
                    if (gameManager.getBacteriasColonys().get(i).getBacteriumArray().get(j) != null) {
                            gameManager.getEnemies().add(gameManager.getBacteriasColonys().get(i).getBacteriumArray().get(j));
                            gameScreen.getGameStage().addActor(gameManager.getBacteriasColonys().get(i).getBacteriumArray().get(j));
                    }
                }
            }
            gameManager.getBacteriasColonys().clear();
        }
    }
    private void controlHitParticleEffect(){
        for (int i = 0; i < gameManager.hitParticleEffectArray.size; i++){
            if (gameManager.hitParticleEffectArray.get(i).isComplete()){
                gameManager.hitParticleEffectArray.get(i).resetEffect();
                gameManager.hitParticleEffectArray.get(i).remove();
                gameManager.hitParticleEffectArray.removeIndex(i);
                /*removedHPE = gameManager.hitParticleEffectArray.removeIndex(i);
                removedHPE = null;*/
            }
        }
    }
    private void addHitParticleEffectToGame(){
        for (int i = 0; i < gameManager.hitParticleEffectArray.size; i++) {
            gameScreen.getGameStage().addActor(gameManager.hitParticleEffectArray.get(i));
            gameManager.hitParticleEffectArray.get(i).allowCompletionEffect();
            gameManager.hitParticleEffectArray.get(i).startEffect();
        }
    }
    private void controlScoreCloud(){
        for (int i = 0; i < gameManager.scoreCloudArray.size; i++) {
            if(gameManager.scoreCloudArray.get(i).getY() > gameScreen.getGameStage().getHeight()){
                gameManager.scoreCloudArray.get(i).remove();
                gameManager.scoreCloudArray.removeIndex(i);
                /*removedScoreCloud  = gameManager.scoreCloudArray.removeIndex(i);
                removedScoreCloud = null;*/
            }
        }
    }
    private void addScoreCloudToGame(){
        for (int i = 0; i < gameManager.scoreCloudArray.size; i++) {
            gameScreen.getGameStage().addActor(gameManager.scoreCloudArray.get(i));
        }
    }
    private void controlLifeScale(){
        if (gameManager.getPlayer().getLifeCount() == 0 || gameManager.getSpermAmount() == 0){
            gameScreen.setGameState(4);
        } //Game over
    }
    private void addBacteriophageToGame(){
        for (int i = 0; i < gameManager.getBacteriophages().size; i++) {
            if (gameManager.getBacteriophages().get(i) != null)
                gameScreen.getGameStage().addActor(gameManager.getBacteriophages().get(i));
        }
        //System.out.println("Game driver bacter array size: "+ gameManager.getBacteriophages().size);
    }
    private void addOvumToGame(){
        if (gameManager.getOvum() != null){
            for (int i = 0; i < gameManager.getSperms().size; i++){
                if (gameManager.getSperms().get(i).getPositionX() <= gameScreen.getGameStage().getWidth() -
                        (gameScreen.getGameStage().getWidth()/3.0f)*2.0f)
                    gameScreen.getGameStage().addActor(gameManager.getOvum());
            }
        }
    }
    private void controlLevelEnd(){
        if(gameManager.getLevelEnd()){
            gameScreen.setStringLevelParamsSave(true);
            System.out.println("Full level time: " + gameTime);
            gameScreen.setGameState(3);
        } //Level end
    }
    //Изменение переменной delta_time_particle_effect которая применяется для
    //задержки окончание уровня до конца отрисовки еффекта ovum_effect
    private void changeDeltaTimeParticleEffect(float delta){
        gameManager.setDeltaTimeParticleEffect(gameManager.getDeltaTimeParticleEffect() - delta);
    }

    //Контроль количества очков для бонусной жизни////////////////////////////////////////////////////
    private void controlBonusLife(){
        int bonusLifeScoreAmount = 10000;
        if(preScoreCountPart < gameManager.getScoresAmount() / bonusLifeScoreAmount){
            preScoreCountPart = gameManager.getScoresAmount() / bonusLifeScoreAmount;
            gameManager.getPig().setIsDraw(true);
            gameManager.getBonusItemsArray().add(new BonusLife(gameManager.getBonusTextureAtlas().findRegion("caveman")));
        }
    }

    private void addBonusItemsToGame(){
        //выводим бонусные предметы на сцену
        for (int i = 0; i < gameManager.getBonusItemsArray().size; i++) {
            if (gameManager.getBonusItemsArray().get(i) != null) {
                gameScreen.getGameStage().addActor(gameManager.getBonusItemsArray().get(i));
            }
        }
    }

    private void controlBonusItemPosition(){
        //отслеживание позиции бонусных предметов и удаление их со сцены если они вышли за пределы экрана
        for (int i = 0; i < gameManager.getBonusItemsArray().size; i++){
            if (gameManager.getBonusItemsArray().get(i) != null){
                if (gameManager.getBonusItemsArray().get(i).getPosition().x > gameScreen.getGameStage().getWidth()
                        || gameManager.getBonusItemsArray().get(i).getPosition().x  < 0
                        || gameManager.getBonusItemsArray().get(i).getPosition().y > gameScreen.getGameStage().getHeight()
                        || gameManager.getBonusItemsArray().get(i).getPosition().y < 0) {
                    if (gameManager.getBonusItemsArray().get(i).remove()) {
                        gameManager.getBonusItemsArray().removeIndex(i);//удаляем объект(Actor) с индексом i из сцены
                        /*removedBonusItem = gameManager.getBonusItemsArray().removeIndex(i);  //удаляет объект с индексом i из массива и возвращает объект
                        removedBonusItem = null; // присваиваем объекту null чтобы его уничтожил сборщик мусора (в Java не нужно самому удалять объекты из памяти)
                        //такой принцип применяем для всех игровых объектов*/
                    }
                }
            }
        }
    }
    //Контроль времени игры для вывода бонусных предметов
    private void controlBonusItems(float gameTime){
        if (preGameTime < (int)(gameTime / 20.0f)) {
            preGameTime = (int) (gameTime / 20.0f);
            if (MathUtils.random(0, 100) > 50) {
                switch (MathUtils.random(0, 2)) {//MathUtils.random(0, 2)
                    case 0:
                        gameManager.getBonusItemsArray().add(new Skull(gameManager.getBonusTextureAtlas().findRegion("skull")));
                        gameManager.getPig().setIsDraw(true);
                        break;
                    case 1:
                        gameManager.getBonusItemsArray().add(new Coin(gameManager.getBonusTextureAtlas().findRegion("coin")));
                        gameManager.getPig().setIsDraw(true);
                        break;
                    case 2:
                        gameManager.getBonusItemsArray().add(new AlarmClock(gameManager.getBonusTextureAtlas().findRegion("alarmclock")));
                        gameManager.getPig().setIsDraw(true);
                        break;
                }
                if(gameManager.getSoundOnOff())
                    audioManager.getCreateBonusItemSound();
            }
        }
    }
    //Контроль положения динамического фона
    private void controlDynamicBackground(Vector2 playerPosition){
        gameManager.getDynamicBackground().setPosition(playerPosition.x, playerPosition.y);
    }
}//end of class
