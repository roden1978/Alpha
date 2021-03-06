package com.gdx.alpha.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.alpha.game.GameDriver;

/**
 * Created by Ro|)e|\| on 19.01.2015.
 */
public class GameScreen extends ObjectScreen implements InputProcessor{

    private String NAME;
    private float lifeTime;
    private float time;
    private int level;
    private ScreenManager screenManager;
    private GameDriver gameDriver;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Image screenShot;
    private static final int READY_STATE = 0;
    private static final int RUNNING_STATE = 1;
    private static final int PAUSE_STATE = 2;
    private static final int LEVEL_END_STATE = 3;
    private static final int END_STATE = 4;
    // Для кнопки назад на экране паузы
    private Table table;
    private TextureAtlas buttonAtlas;
    private TextButton.TextButtonStyle textButtonStyle;
    private TextButton backButton;
    private BitmapFont bitmapFont;
    private FreeTypeFontGenerator freeTypeFontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private Skin button_skin;
    //фон промежуточных состояний
    private Texture backgroundTexture;
    private Image background;
    //
    private float WIDTH;
    private float HEIGHT;

    private int state;
    //private float delta;

    private Stage gameStage; //, uiStage
    //private Player player = null;
    private Vector2 playerPosition;

    private BitmapFont font;
    private SpriteBatch spriteBatch;

    private Boolean takeScreenshot;
    private Boolean isScreenshot;

    //Инициализируем массивы параметров уровней
    private  Array<Integer> levelNumber;
    private  Array<Integer> score;
    private  Array<Integer> sperms;
    private  Array<Integer> complete;
    private  Array<Integer> available;
    private String[] levels;
    private String[] levelString;
    private Boolean isStringLevelParamsSave;
    private StringBuilder builder;


    // конструктор класса
    GameScreen(ScreenManager screenManager, int level){
        NAME = "GameScreen";
        lifeTime = 0.0f;
        time = 0.0f;
        //delta = 0.0f;
        playerPosition = new Vector2(0,0);
        state = READY_STATE;
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();
        this.screenManager = screenManager;
        this.takeScreenshot = false;
        this.isScreenshot = false;
        camera = new OrthographicCamera(WIDTH,HEIGHT);
        //weaponType = 0;
        this.level = level;
        //Инициализируем массивы параметров уровней
        levelNumber =new Array<Integer>();
        score = new Array<Integer>();
        sperms = new Array<Integer>();
        complete = new Array<Integer>();
        available = new Array<Integer>();
        isStringLevelParamsSave = false;
        builder = new StringBuilder();

        font = new BitmapFont(Gdx.files.internal("font/stonefont.fnt"));
        backgroundTexture = new Texture(Gdx.files.internal("ui/cave.png"));
        //gameBackground = new Image(gameBackgroundTexture);
        background = new Image(backgroundTexture);
        spriteBatch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);

        //Инициализация кнопки "назад" на экране паузы
        //кнопку "Назад" делаем для телефонов у которых кнопки управления отсутствуют на корпусе

        buttonAtlas = new TextureAtlas(Gdx.files.internal("ui/buttons.pack"));
        button_skin = new Skin(buttonAtlas);
        table = new Table();
        freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("JFRocSol_rus.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.characters="АБВГДЕЁЖЗИКЛМНОПРСТУФХЦШЩЬЫЪЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ()";
        bitmapFont = freeTypeFontGenerator.generateFont(parameter);
        freeTypeFontGenerator.dispose();
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = bitmapFont;
        textButtonStyle.fontColor = Color.BLACK;
        textButtonStyle.downFontColor = Color.BLACK;
        textButtonStyle.up = button_skin.getDrawable("button_up");
        textButtonStyle.down = button_skin.getDrawable("button_down");
        textButtonStyle.pressedOffsetX = 5.0f;
        textButtonStyle.pressedOffsetY = -5.0f;
        backButton = new TextButton("BACK",textButtonStyle);

        //создаем игровую сцену
        gameStage = new Stage();
        //изменяем размер фона под размер экрана
        background.setWidth(gameStage.getWidth());
        background.setHeight(gameStage.getHeight());
        screenManager.getScreenMusic().stop();

    }

    //опрос устройств ввода
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK){
            if (state == PAUSE_STATE || state == END_STATE || state == LEVEL_END_STATE || state == READY_STATE) {
                screenManager.setCurrentScreen(new LevelScreen(screenManager));
            }
            takeScreenshot = true;
            state = PAUSE_STATE;
        }
        if (keycode == Input.Keys.P){
            state = PAUSE_STATE;
            if(!isScreenshot)
                takeScreenshot = true;
        }

            //screenManager.setCurrentScreen(new LevelScreen(screenManager));
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        //if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK)
        //    screenManager.setCurrentScreen(new LevelScreen(screenManager));
        //System.out.println(keycode);
        return true;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //System.out.println("X: "+screenX);
        //System.out.println("Y: " + screenY);
        //System.out.println("State in: " + state);
        //Отлеживание продолжения игрового процесса после нажатия на экране паузы
        if (state == PAUSE_STATE) {
            if (screenX > 0 && screenX < gameStage.getWidth() && screenY > gameStage.getHeight() - 100 && screenY > 0.0f) { //(int)(gameStage.getHeight() -(gameStage.getHeight() - 100))
               /* if(screenManager.getOnoff())
                    screenManager.getButtonClickSound().play();*/
                gameDriver.getAudioManager().getBackgroundGameMusic().stop();
                gameDriver.getAudioManager().getBackgroundGameMusic().dispose();
                //spriteBatch.dispose();
                //gameStage.dispose();
                //screenManager.setCurrentScreen(new LevelScreen(screenManager));
                state = END_STATE;
            } else {

                if (screenManager.getOnoff())
                    screenManager.getScreenTapSound().play();
                state = RUNNING_STATE;
                gameDriver.getAudioManager().getBackgroundGameMusic().play();

                System.out.println("Screen X: " + screenX);
                System.out.println("Screen Y: " + screenY);
                System.out.println("State in: " + state);

            }
        }
        //Бросание оружия при одиночном клике по экрану
        gameDriver.getGameManager().getPlayer().setThrowing(true);
        gameDriver.getGameManager().getThrowWeapon().setThrowing(true);
        //System.out.println("X >: " + (int)gameDriver.gameManager.pauseImage.getImageX() +
        //        " X <: " + (int)(gameStage.getWidth() - (gameStage.getWidth() - gameDriver.gameManager.pauseImage.getImageWidth()))+
        //        " Y <: " + (int)(gameStage.getHeight() - (gameStage.getHeight() - gameDriver.gameManager.pauseImage.getImageHeight())) +
        //        " Y >: " + 0);
        //Отслеживание нажатия на кнопку пауза во время игрового процесса
        if (screenX > (int)gameDriver.getGameManager().pauseImage.getImageX() &&
                screenX < (int)(gameStage.getWidth() - (gameStage.getWidth() - gameDriver.getGameManager().pauseImage.getImageWidth()* 2.0f))&&
                screenY < (int)(gameStage.getHeight() - (gameStage.getHeight() - gameDriver.getGameManager().pauseImage.getImageHeight()*2.0f)) &&
                screenY > 0.0f){
                                    if(screenManager.getOnoff())
                                        screenManager.getScreenTapSound().play();
                                    state = PAUSE_STATE;
                                    takeScreenshot = true;
        }
        //Если уровень заокнчен по клику переходим к экрану выбора уровня и останавливаем музыку игрового процесса
        if(state == LEVEL_END_STATE){
            gameDriver.getAudioManager().getBackgroundGameMusic().stop();
            gameDriver.getAudioManager().getBackgroundGameMusic().dispose();
            state = END_STATE;
        }
        //Если game over останавливаем музыку игрового процесса
        if(state == END_STATE){
            gameDriver.getAudioManager().getBackgroundGameMusic().stop();
            gameDriver.getAudioManager().getBackgroundGameMusic().dispose();
        }
        return true;
    }

    @Override
    public boolean touchUp(int i, int i2, int i3, int i4) {
        //Остановка процесса бросания оружия при отсутствии нажания экрана или удержания кнопки мыши
       // if(!gameDriver.getGameManager().player.getNewlife()) {
            gameDriver.getGameManager().getPlayer().setThrowing(false);
            gameDriver.getGameManager().getThrowWeapon().setThrowing(false);
       // }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //Бросание оружия при удержании нажания на экране или левой кнопки мыши в игровом процессе
        if (!gameDriver.getGameManager().getPlayer().getThrowing()) {
            gameDriver.getGameManager().getPlayer().setThrowing(true);
            gameDriver.getGameManager().getThrowWeapon().setThrowing(true);
        }

        if(screenX < gameStage.getWidth() &&
                screenX > gameDriver.getGameManager().getPlayer().getSpriteRegionWidth() &&
                screenY  > gameDriver.getGameManager().getPlayer().getSpriteRegionHeight() / 2 &&
                screenY < gameStage.getHeight() - gameDriver.getGameManager().getPlayer().getSpriteRegionHeight() / 2){
            //Положение игрока со смещением от точки касания экрана
            playerPosition.x = screenX - gameDriver.getGameManager().getPlayer().getSpriteRegionWidth()*3
                    - gameDriver.getGameManager().getPlayer().getSpriteRegionWidth();
            playerPosition.y = (gameStage.getHeight() - screenY);// - gameDriver.getGameManager().player.getSpriteRegionHeight() / 2;
            //установка позиции игрока
            gameDriver.getGameManager().getPlayer().setPosition(playerPosition);
            //установка начальной позиции брошенного оружия
            gameDriver.getGameManager().getThrowWeapon().updatePosition(playerPosition);
        }
        //System.out.println("X: " + player.getPosition().x + " Y: " + player.getPosition().y);
        //System.out.println("X: " + screenX + " Y: " + screenY);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        //player.setPosition(screenX, screenY);
        return true;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }

    @Override
    public void show() {
        //super.show();
        //создаем класс управления игрой

            gameDriver = new GameDriver(this, level);
            gameDriver.addGeneralActorsToScene();//#1
            if (screenManager.getOnoff()) {
                //Запускаем музыку игрового процесса
                gameDriver.getAudioManager().getBackgroundGameMusic().setLooping(true);
                gameDriver.getAudioManager().getBackgroundGameMusic().play();
            }

}

    public void update(float delta){
        if (delta > 0.1f)
            delta = 0.1f;

        switch (state){
            case READY_STATE:
                //готовность игры к запуску
                updateReady();
                break;
            case RUNNING_STATE:
                //процес игры
                updateRunning(delta);
                break;
            case PAUSE_STATE:
                //пауза
                updatePause();
                break;
            case LEVEL_END_STATE:
                //уровень пройден
                updateLevelEnd();
                break;
            case END_STATE:
                //игра закончена
                updateGameOver();
                break;
        }
    }

    //Обработка состояний игры
    //состояние готовноти
    private void updateReady(){
        if (Gdx.input.justTouched()) {
            state = RUNNING_STATE;
        }
    }
    //состояние игрового процесса
    private void updateRunning(float delta){

        //обновляем состояние игровой сцены
        gameStage.act(delta);
        //обновляем состояние строки состояний
        gameDriver.gameDrive(delta);

    }
    //состояние паузы
    private void updatePause(){
        if (Gdx.input.justTouched() && state == RUNNING_STATE) {
            state = PAUSE_STATE;
        }
    }
    //состояние завершения уровня
    private void updateLevelEnd(){
        if (Gdx.input.justTouched()){
            state = LEVEL_END_STATE;
        }
    }
    //состояние конец игры
    private void updateGameOver(){
        if (Gdx.input.justTouched()) {
            screenManager.setCurrentScreen(new LevelScreen(screenManager));
        }
    }

    private void drawStates(){
        switch (state){
            case READY_STATE:
                //готовность игры к запуску
                presentReady();
                break;
            case RUNNING_STATE:
                //процес игры
                presentRunning();
                break;
            case PAUSE_STATE:
                //пауза
                presentPaused();
                break;
            case LEVEL_END_STATE:
                //уровень пройден
                presentLevelEnd();
                break;
            case END_STATE:
                //игра закончена
                presentGameOver();
                break;
        }
    }
    //отрисова состояний игры
    //отрисовка состояния готовности
    private void presentReady(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        spriteBatch.begin();
            background.draw(spriteBatch, 0.5f);
            font.draw(spriteBatch,"TAP TO PLAY",0.0f, gameStage.getHeight()/2.0f, gameStage.getWidth(), Align.center, true);
        spriteBatch.end();
    }
    //отрисовка состояния игры
    private void presentRunning(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        gameStage.draw();
    }
    //отрисовка состояния паузы
    private void presentPaused(){
        if(takeScreenshot){
            screenShot = new Image(ScreenUtils.getFrameBufferTexture());
            takeScreenshot = false;
            isScreenshot = true;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        //Останавливаем музыку во время паузы
        if (gameDriver.getAudioManager().getBackgroundGameMusic().isPlaying())
            gameDriver.getAudioManager().getBackgroundGameMusic().pause();
        spriteBatch.begin();
            if (isScreenshot)
                screenShot.draw(spriteBatch, 0.5f);
            font.draw(spriteBatch,"PAUSE",0.0f, gameStage.getHeight()/2.0f + 100, gameStage.getWidth(), Align.center, true);
            font.draw(spriteBatch,"TAP TO CONTINUE\n OR BACK TO EXIT",0.0f, gameStage.getHeight()/3.0f, gameStage.getWidth(), Align.center, true);
            backButton.left().bottom();
            backButton.draw(spriteBatch, 1.0f);
        spriteBatch.end();
    }
    //отрисовка состояния окончания уровная
    private void presentLevelEnd(){
        Gdx.gl.glClearColor(0, 0, 0, 1); //Gdx.gl.glClearColor(1, 0.784f, 0.784f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        spriteBatch.begin();
            background.draw(spriteBatch, 0.5f);
            font.draw(spriteBatch,"LEVEL COMPLETE",0.0f, gameStage.getHeight()/2.0f + 100, gameStage.getWidth(), Align.center, true);
            String str = "SCORE " + gameDriver.getGameManager().getScoresAmount() + "    SPERM " + gameDriver.getGameManager().getSpermAmount();
            font.draw(spriteBatch, str, 0.0f,gameStage.getHeight()/ 3.0f + 100, gameStage.getWidth(),Align.center, true);
        spriteBatch.end();

        //Сохранение результатов пройденного уровня
        if(isStringLevelParamsSave) {
            setLevelParams();
            //createStringForSaveLevelParams();
            saveLevelParamFile();

            isStringLevelParamsSave = false;

            if(gameDriver.getAudioManager().getBackgroundGameMusic().isPlaying())
                gameDriver.getAudioManager().getBackgroundGameMusic().stop();

            if(screenManager.getOnoff())
                screenManager.getLevelCompleteSound().play();
        }


    }
    //отрисовка окончания игры
    private void presentGameOver(){
        Gdx.gl.glClearColor(0, 0, 0, 1); //Gdx.gl.glClearColor(1, 0.784f, 0.784f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        spriteBatch.begin();
            background.draw(spriteBatch, 0.5f);
            font.draw(spriteBatch,"GAME OVER",0.0f, gameStage.getHeight()/2.0f, gameStage.getWidth(), Align.center, true);
        spriteBatch.end();
    }


    @Override
    public void render(float delta) {
        //super.render(delta);
        update(delta);
        drawStates();

    }

    @Override
    public void resize(int width, int height) {
       // super.resize(width, height);
        camera.setToOrtho(true,WIDTH,HEIGHT );
        gameStage.getCamera().update();
        //uiStage.getCamera().update();
    }

    @Override
    public void pause() {
        super.pause();
        if (state == RUNNING_STATE)
            state = PAUSE_STATE;
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        font.dispose();
        gameStage.dispose();
        super.dispose();
       // uiStage.dispose();
    }

    @Override
    public float getLifeTime() {
        return super.getLifeTime();
    }

    @Override
    public float getTime() {
        return super.getTime();
    }

    @Override
    public String getNAME() {
        return super.getNAME();
    }

    public Stage getGameStage(){
        return gameStage;
    }

    public void setGameState(int state){
        this.state = state;
    }

    private void setLevelParams() {
       /* levels = line.split("#");
        for (int i = 0; i < levels.length; i++) {
            levelString = levels[i].split(";");
            levelNumber.add(Integer.valueOf(levelString[0].trim()));
            score.add(Integer.valueOf(levelString[1].trim()));
            sperms.add(Integer.valueOf(levelString[2].trim()));
            complete.add(Integer.valueOf(levelString[3].trim()));
            available.add(Integer.valueOf(levelString[4].trim()));
            //System.out.println(levelString[0]+" "+levelString[1]+" "+levelString[2]+" "+levelString[3]+" "+levelString[4]);
        }*/
        Preferences levelPref = Gdx.app.getPreferences("levelparam");
        for (int i = 0; i < 15; i ++){
            levelNumber.add(levelPref.getInteger("levelNumber_"  + i));
            score.add(levelPref.getInteger("score_" + i));
            sperms.add(levelPref.getInteger("sperm_" + i));
            complete.add(levelPref.getInteger("complete_" + i));
            available.add(levelPref.getInteger("available_" + i));
        }
        //Изменяем в массивах количество очков и кол-во сперм, устанавливаем признак что уровень выполнен,
        // устанавливаем признак что следующий уровень открыт
        score.set(level, gameDriver.getGameManager().getScoresAmount());
        sperms.set(level, gameDriver.getGameManager().getSpermAmount());
        complete.set(level, 1);
        available.set(level + 1, 1);
    }
  /* private void createStringForSaveLevelParams(){
        for (int i =0; i < levelNumber.size; i++){
           builder.append(levelNumber.get(i).toString()).append(";").append(score.get(i).toString()).append(";").
                    append(sperms.get(i).toString()).append(";").append(complete.get(i).toString()).append(";").
                    append(available.get(i).toString()).append("#");
        }
        //System.out.println("Line: "+builder.toString());
    }*/

    public void setStringLevelParamsSave(Boolean stringLevelParamsSave) {
        isStringLevelParamsSave = stringLevelParamsSave;
    }
    private void saveLevelParamFile(){
        Preferences savePref = Gdx.app.getPreferences("levelparam");
        for (int i = 0; i < levelNumber.size; i++){
            savePref.putInteger("levelNumber_" + i, levelNumber.get(i));
            savePref.putInteger("score_" + i, score.get(i));
            savePref.putInteger("sperm_" + i, sperms.get(i));
            savePref.putInteger("complete_" + i, complete.get(i));
            savePref.putInteger("available_" + i, available.get(i));
        }
        savePref.flush();
        /*FileHandle handle = Gdx.files.local("levelparam.txt");
        handle.writeString(builder.toString(), false);
        screenManager.setLevelParams(builder.toString());
        builder.delete(0, builder.length());*/
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }
}
